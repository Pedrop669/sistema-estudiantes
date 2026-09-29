package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.*;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenVentaDAOImpl implements OrdenVentaDAO {

    @Override
    public void guardar(OrdenVenta o, List<DetalleOrdenVenta> detalles) {
        String sqlOV  = "INSERT INTO orden_venta (fecha, estado, observaciones, total, id_cliente, id_vendedor) VALUES (?,?,?,?,?,?)";
        String sqlDet = "INSERT INTO detalle_orden_venta (id_orden_venta, id_producto, cantidad, precio_unitario, descuento, subtotal) VALUES (?,?,?,?,?,?)";
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            long idOrden;
            try (PreparedStatement ps = con.prepareStatement(sqlOV, Statement.RETURN_GENERATED_KEYS)) {
                ps.setTimestamp(1, Timestamp.valueOf(o.getFecha()));
                ps.setString(2, o.getEstado() != null ? o.getEstado().name() : "REGISTRADA");
                ps.setString(3, o.getObservaciones());
                ps.setBigDecimal(4, o.getTotal());
                ps.setLong(5, o.getIdCliente());
                ps.setLong(6, o.getIdVendedor());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); idOrden = rs.getLong(1); }
            }
            o.setIdOrdenVenta(idOrden);

            // insertar detalles y descontar stock
            ProductoDAO prodDAO = new ProductoDAOImpl();
            for (DetalleOrdenVenta d : detalles) {
                try (PreparedStatement ps = con.prepareStatement(sqlDet)) {
                    ps.setLong(1, idOrden);
                    ps.setLong(2, d.getIdProducto());
                    ps.setInt(3, d.getCantidad());
                    ps.setBigDecimal(4, d.getPrecioUnitario());
                    ps.setBigDecimal(5, d.getDescuento());
                    ps.setBigDecimal(6, d.getSubtotal());
                    ps.executeUpdate();
                }
                // descontar stock y registrar movimiento
                prodDAO.ajustarStock(d.getIdProducto().intValue(), TipoMovimiento.VENTA,
                        d.getCantidad(), idOrden, "ORDEN_VENTA", "Venta orden #" + idOrden);
            }
            con.commit();
        } catch (Exception e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) { }
            throw new RuntimeException("Error al guardar orden de venta: " + e.getMessage(), e);
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { }
        }
    }

    @Override
    public List<OrdenVenta> listar() {
        List<OrdenVenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden_venta ORDER BY id_orden_venta DESC";
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException("Error al listar ordenes: "+e.getMessage(),e); }
        return lista;
    }

    @Override
    public OrdenVenta buscarPorId(int id) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM orden_venta WHERE id_orden_venta=?")) {
            ps.setInt(1,id); try (ResultSet rs=ps.executeQuery()) { return rs.next()?map(rs):null; }
        } catch (SQLException e) { throw new RuntimeException("Error al buscar orden: "+e.getMessage(),e); }
    }

    @Override
    public List<DetalleOrdenVenta> listarDetalles(int idOrden) {
        List<DetalleOrdenVenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_orden_venta WHERE id_orden_venta=?";
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,idOrden);
            try (ResultSet rs=ps.executeQuery()) { while (rs.next()) lista.add(mapDet(rs)); }
        } catch (SQLException e) { throw new RuntimeException("Error al listar detalles: "+e.getMessage(),e); }
        return lista;
    }

    private OrdenVenta map(ResultSet rs) throws SQLException {
        OrdenVenta o = new OrdenVenta();
        o.setIdOrdenVenta(rs.getLong("id_orden_venta"));
        o.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        try { o.setEstado(EstadoOrdenVenta.valueOf(rs.getString("estado"))); } catch(Exception e) { }
        o.setObservaciones(rs.getString("observaciones"));
        o.setTotal(rs.getBigDecimal("total"));
        o.setIdCliente(rs.getLong("id_cliente"));
        o.setIdVendedor(rs.getLong("id_vendedor"));
        return o;
    }

    private DetalleOrdenVenta mapDet(ResultSet rs) throws SQLException {
        DetalleOrdenVenta d = new DetalleOrdenVenta();
        d.setIdDetalle(rs.getLong("id_detalle"));
        d.setIdOrdenVenta(rs.getLong("id_orden_venta"));
        d.setIdProducto(rs.getLong("id_producto"));
        d.setCantidad(rs.getInt("cantidad"));
        d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        d.setDescuento(rs.getBigDecimal("descuento"));
        d.setSubtotal(rs.getBigDecimal("subtotal"));
        return d;
    }
}
