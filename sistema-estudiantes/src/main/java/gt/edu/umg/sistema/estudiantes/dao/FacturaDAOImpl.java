package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.FacturaVenta;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio
 */
public class FacturaDAOImpl implements FacturaDAO {

    @Override
    public List<FacturaVenta> listar() {
        List<FacturaVenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM factura ORDER BY id_factura DESC";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar facturas: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public FacturaVenta buscarPorId(long id) {
        String sql = "SELECT * FROM factura WHERE id_factura = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar factura: " + e.getMessage(), e);
        }
    }

    @Override
    public FacturaVenta buscarPorOrdenVenta(long idOrdenVenta) {
        String sql = "SELECT * FROM factura WHERE id_orden_venta = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idOrdenVenta);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar factura por orden de venta: " + e.getMessage(), e);
        }
    }

    private FacturaVenta map(ResultSet rs) throws SQLException {
        FacturaVenta f = new FacturaVenta();
        f.setIdFactura(rs.getLong("id_factura"));
        f.setIdOrdenVenta(rs.getLong("id_orden_venta"));
        f.setIdOrdenDespacho(rs.getLong("id_orden_despacho"));
        f.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        f.setTotal(rs.getBigDecimal("total"));
        f.setEstado(rs.getString("estado"));
        return f;
    }
}
