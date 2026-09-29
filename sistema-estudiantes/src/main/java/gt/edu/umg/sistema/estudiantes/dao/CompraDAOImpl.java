package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.*;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAOImpl implements CompraDAO {

    @Override
    public void guardar(Compra c, List<DetalleCompra> detalles) {
        String sqlC   = "INSERT INTO compra (fecha, estado, total, id_proveedor) VALUES (?,?,?,?)";
        String sqlDet = "INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario, subtotal) VALUES (?,?,?,?,?)";
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            long idCompra;
            try (PreparedStatement ps = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS)) {
                ps.setTimestamp(1, Timestamp.valueOf(c.getFecha()));
                ps.setString(2, c.getEstado() != null ? c.getEstado().name() : "REGISTRADA");
                ps.setBigDecimal(3, c.getTotal());
                ps.setLong(4, c.getIdProveedor());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); idCompra = rs.getLong(1); }
            }
            c.setIdCompra(idCompra);

            ProductoDAO prodDAO = new ProductoDAOImpl();
            for (DetalleCompra d : detalles) {
                try (PreparedStatement ps = con.prepareStatement(sqlDet)) {
                    ps.setLong(1, idCompra);
                    ps.setLong(2, d.getIdProducto());
                    ps.setInt(3, d.getCantidad());
                    ps.setBigDecimal(4, d.getPrecioUnitario());
                    ps.setBigDecimal(5, d.getSubtotal());
                    ps.executeUpdate();
                }
                // aumentar stock y registrar movimiento
                prodDAO.ajustarStock(d.getIdProducto().intValue(), TipoMovimiento.COMPRA,
                        d.getCantidad(), idCompra, "COMPRA", "Compra #" + idCompra);
            }
            con.commit();
        } catch (Exception e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) { }
            throw new RuntimeException("Error al guardar compra: " + e.getMessage(), e);
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { }
        }
    }

    @Override
    public List<Compra> listar() {
        List<Compra> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM compra ORDER BY id_compra DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException("Error al listar compras: "+e.getMessage(),e); }
        return lista;
    }

    @Override
    public Compra buscarPorId(int id) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM compra WHERE id_compra=?")) {
            ps.setInt(1,id); try (ResultSet rs=ps.executeQuery()) { return rs.next()?map(rs):null; }
        } catch (SQLException e) { throw new RuntimeException("Error al buscar compra: "+e.getMessage(),e); }
    }

    @Override
    public List<DetalleCompra> listarDetalles(int idCompra) {
        List<DetalleCompra> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM detalle_compra WHERE id_compra=?")) {
            ps.setInt(1,idCompra);
            try (ResultSet rs=ps.executeQuery()) { while(rs.next()) lista.add(mapDet(rs)); }
        } catch (SQLException e) { throw new RuntimeException("Error al listar detalles compra: "+e.getMessage(),e); }
        return lista;
    }

    private Compra map(ResultSet rs) throws SQLException {
        Compra c = new Compra();
        c.setIdCompra(rs.getLong("id_compra"));
        c.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        try { c.setEstado(EstadoCompra.valueOf(rs.getString("estado"))); } catch(Exception e) { }
        c.setTotal(rs.getBigDecimal("total"));
        c.setIdProveedor(rs.getLong("id_proveedor"));
        return c;
    }

    private DetalleCompra mapDet(ResultSet rs) throws SQLException {
        DetalleCompra d = new DetalleCompra();
        d.setIdDetalleCompra(rs.getLong("id_detalle_compra"));
        d.setIdCompra(rs.getLong("id_compra"));
        d.setIdProducto(rs.getLong("id_producto"));
        d.setCantidad(rs.getInt("cantidad"));
        d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        d.setSubtotal(rs.getBigDecimal("subtotal"));
        return d;
    }
}
