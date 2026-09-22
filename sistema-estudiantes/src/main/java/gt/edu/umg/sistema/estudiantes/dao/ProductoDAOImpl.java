package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Producto;
import gt.edu.umg.sistema.estudiantes.modelo.TipoMovimiento;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void guardar(Producto p) {
        String sql = """
            INSERT INTO producto (codigo, nombre, descripcion, precio_venta,
                                  stock_actual, stock_minimo, activo)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setBigDecimal(4, p.getPrecioVenta());
            ps.setInt(5, p.getStockActual());
            ps.setInt(6, p.getStockMinimo());
            ps.setBoolean(7, p.isActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Producto> listar() {
        return buscar(null, null);
    }

    @Override
    public List<Producto> buscar(String codigo, String nombre) {
        StringBuilder sql = new StringBuilder(
            "SELECT * FROM producto WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (codigo != null && !codigo.isBlank()) {
            sql.append(" AND codigo LIKE ?");
            params.add("%" + codigo.trim() + "%");
        }
        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND nombre LIKE ?");
            params.add("%" + nombre.trim() + "%");
        }
        sql.append(" ORDER BY id_producto");

        List<Producto> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Producto buscarPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Producto p) {
        String sql = """
            UPDATE producto SET codigo=?, nombre=?, descripcion=?, precio_venta=?,
                                stock_actual=?, stock_minimo=?, activo=?
            WHERE id_producto=?
            """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setBigDecimal(4, p.getPrecioVenta());
            ps.setInt(5, p.getStockActual());
            ps.setInt(6, p.getStockMinimo());
            ps.setBoolean(7, p.isActivo());
            ps.setInt(8, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement("DELETE FROM producto WHERE id_producto=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto: " + e.getMessage(), e);
        }
    }

    //  INVENTARIO: ajusta stock + registra el movimiento en una transaccion
    @Override
    public void ajustarStock(int idProducto, TipoMovimiento tipo, int cantidad,
                             Long referenciaId, String referenciaTipo, String observaciones) {
        int delta = signo(tipo) * cantidad;

        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            // 1) leer stock actual
            int stockAnterior;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT stock_actual FROM producto WHERE id_producto=? FOR UPDATE")) {
                ps.setInt(1, idProducto);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Producto no existe: " + idProducto);
                    stockAnterior = rs.getInt(1);
                }
            }

            int stockNuevo = stockAnterior + delta;
            if (stockNuevo < 0) {
                throw new IllegalStateException(
                    "Stock insuficiente. Actual: " + stockAnterior + ", solicitado: " + cantidad);
            }

            // 2) actualizar el stock del producto
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE producto SET stock_actual=? WHERE id_producto=?")) {
                ps.setInt(1, stockNuevo);
                ps.setInt(2, idProducto);
                ps.executeUpdate();
            }

            // 3) registrar el movimiento en el historial
            String insMov = """
                INSERT INTO movimiento_inventario
                    (id_producto, fecha, tipo, referencia_id, referencia_tipo,
                     cantidad, stock_anterior, stock_nuevo, observaciones)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
            try (PreparedStatement ps = con.prepareStatement(insMov)) {
                ps.setInt(1, idProducto);
                ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(3, tipo.name());
                if (referenciaId != null) ps.setLong(4, referenciaId); else ps.setNull(4, Types.BIGINT);
                ps.setString(5, referenciaTipo);
                ps.setInt(6, cantidad);
                ps.setInt(7, stockAnterior);
                ps.setInt(8, stockNuevo);
                ps.setString(9, observaciones);
                ps.executeUpdate();
            }

            con.commit();
        } catch (SQLException | IllegalStateException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) {}
            throw new RuntimeException("Error al ajustar stock: " + e.getMessage(), e);
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException ex) { }
        }
    }

    /** Define si el movimiento suma o resta stock. */
    private int signo(TipoMovimiento tipo) {
        return switch (tipo) {
            case COMPRA, DEVOLUCION_VENTA -> +1;
            case VENTA, DESPACHO, DEVOLUCION_COMPRA -> -1;
            case AJUSTE -> +1;
        };
    }

    private Producto map(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getInt("id_producto"));
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecioVenta(rs.getBigDecimal("precio_venta"));
        p.setStockActual(rs.getInt("stock_actual"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }
}
