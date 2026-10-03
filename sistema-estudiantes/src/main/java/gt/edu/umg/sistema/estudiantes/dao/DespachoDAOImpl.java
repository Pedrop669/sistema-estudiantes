package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.*;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DespachoDAOImpl implements DespachoDAO {

    @Override
    public void generarDespacho(long idOrdenVenta) {
        String sqlDespacho = "INSERT INTO orden_despacho (id_orden_venta, fecha, estado, observaciones) VALUES (?,?,?,?)";
        String sqlCopiarDetalle = """
            INSERT INTO detalle_despacho (id_orden_despacho, id_producto, cantidad, observaciones)
            SELECT ?, id_producto, cantidad, NULL
            FROM detalle_orden_venta
            WHERE id_orden_venta = ?
            """;

        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            long idDespacho;
            try (PreparedStatement ps = con.prepareStatement(sqlDespacho, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, idOrdenVenta);
                ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(3, EstadoDespacho.PENDIENTE.name());
                ps.setString(4, null);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); idDespacho = rs.getLong(1); }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlCopiarDetalle)) {
                ps.setLong(1, idDespacho);
                ps.setLong(2, idOrdenVenta);
                ps.executeUpdate();
            }

            // marcar la orden de venta como APROBADA
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE orden_venta SET estado=? WHERE id_orden_venta=?")) {
                ps.setString(1, EstadoOrdenVenta.APROBADA.name());
                ps.setLong(2, idOrdenVenta);
                ps.executeUpdate();
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) { }
            throw new RuntimeException("Error al generar despacho: " + e.getMessage(), e);
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { }
        }
    }

    @Override
    public void confirmarDespacho(long idOrdenDespacho) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            // 1. traer el id_orden_venta asociado a este despacho
            long idOrdenVenta;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT id_orden_venta FROM orden_despacho WHERE id_orden_despacho=?")) {
                ps.setLong(1, idOrdenDespacho);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Despacho no existe: " + idOrdenDespacho);
                    idOrdenVenta = rs.getLong(1);
                }
            }

            // 2. rebajar stock de cada producto del detalle de despacho,
            ProductoDAOImpl prodDAO = new ProductoDAOImpl();
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT id_producto, cantidad FROM detalle_despacho WHERE id_orden_despacho=?")) {
                ps.setLong(1, idOrdenDespacho);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int idProducto = rs.getInt("id_producto");
                        int cantidad = rs.getInt("cantidad");
                        prodDAO.ajustarStock(con, idProducto, TipoMovimiento.DESPACHO,
                                cantidad, idOrdenDespacho, "ORDEN_DESPACHO",
                                "Despacho #" + idOrdenDespacho);
                    }
                }
            }

            // 3. marcar el despacho como DESPACHADA
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE orden_despacho SET estado=? WHERE id_orden_despacho=?")) {
                ps.setString(1, EstadoDespacho.DESPACHADA.name());
                ps.setLong(2, idOrdenDespacho);
                ps.executeUpdate();
            }

            // 4. generar la factura
            BigDecimalHolder totalHolder = new BigDecimalHolder();
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT total FROM orden_venta WHERE id_orden_venta=?")) {
                ps.setLong(1, idOrdenVenta);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) totalHolder.valor = rs.getBigDecimal("total");
                }
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO factura (id_orden_venta, id_orden_despacho, fecha, total, estado) VALUES (?,?,?,?,?)")) {
                ps.setLong(1, idOrdenVenta);
                ps.setLong(2, idOrdenDespacho);
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                ps.setBigDecimal(4, totalHolder.valor);
                ps.setString(5, "EMITIDA");
                ps.executeUpdate();
            }

            con.commit();
        } catch (SQLException | IllegalStateException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) { }
            throw new RuntimeException("Error al confirmar despacho: " + e.getMessage(), e);
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { }
        }
    }

    @Override
    public List<OrdenDespacho> listarPendientes() {
        List<OrdenDespacho> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden_despacho WHERE estado='PENDIENTE' ORDER BY id_orden_despacho DESC";
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException("Error al listar despachos pendientes: "+e.getMessage(),e); }
        return lista;
    }

    @Override
    public List<OrdenDespacho> listar() {
        List<OrdenDespacho> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden_despacho ORDER BY id_orden_despacho DESC";
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException("Error al listar despachos: "+e.getMessage(),e); }
        return lista;
    }

    @Override
    public OrdenDespacho buscarPorId(long id) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM orden_despacho WHERE id_orden_despacho=?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException("Error al buscar despacho: "+e.getMessage(),e); }
    }

    @Override
    public OrdenDespacho buscarPorOrdenVenta(long idOrdenVenta) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM orden_despacho WHERE id_orden_venta=?")) {
            ps.setLong(1, idOrdenVenta);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException("Error al buscar despacho por orden: "+e.getMessage(),e); }
    }

    @Override
    public List<DetalleDespacho> listarDetalles(long idOrdenDespacho) {
        List<DetalleDespacho> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_despacho WHERE id_orden_despacho=?";
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idOrdenDespacho);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) lista.add(mapDet(rs)); }
        } catch (SQLException e) { throw new RuntimeException("Error al listar detalles de despacho: "+e.getMessage(),e); }
        return lista;
    }

    private OrdenDespacho map(ResultSet rs) throws SQLException {
        OrdenDespacho o = new OrdenDespacho();
        o.setIdOrdenDespacho(rs.getLong("id_orden_despacho"));
        o.setIdOrdenVenta(rs.getLong("id_orden_venta"));
        o.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        try { o.setEstado(EstadoDespacho.valueOf(rs.getString("estado"))); } catch (Exception e) { }
        o.setObservaciones(rs.getString("observaciones"));
        return o;
    }

    private DetalleDespacho mapDet(ResultSet rs) throws SQLException {
        DetalleDespacho d = new DetalleDespacho();
        d.setIdDetalleDespacho(rs.getLong("id_detalle_despacho"));
        d.setIdOrdenDespacho(rs.getLong("id_orden_despacho"));
        d.setIdProducto(rs.getLong("id_producto"));
        d.setCantidad(rs.getInt("cantidad"));
        d.setObservaciones(rs.getString("observaciones"));
        return d;
    }

    private static class BigDecimalHolder {
        java.math.BigDecimal valor;
    }
}
