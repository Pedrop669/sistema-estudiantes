package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Vendedor;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendedorDAOImpl implements VendedorDAO {

    @Override
    public void guardar(Vendedor v) {
        String sqlPersona = "INSERT INTO persona (tipo_documento, numero_documento, nombres, apellidos, telefono, email, direccion, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlVendedor = "INSERT INTO vendedor (id_vendedor, codigo_vendedor, zona, comision_porcentaje) VALUES (?, ?, ?, ?)";
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, v.getTipoDocumento());
                ps.setString(2, v.getNumeroDocumento());
                ps.setString(3, v.getNombres());
                ps.setString(4, v.getApellidos());
                ps.setString(5, v.getTelefono());
                ps.setString(6, v.getEmail());
                ps.setString(7, v.getDireccion());
                ps.setBoolean(8, v.getActivo() != null ? v.getActivo() : true);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idPersona = rs.getInt(1);
                        v.setIdVendedor((long) idPersona);
                        try (PreparedStatement ps2 = con.prepareStatement(sqlVendedor)) {
                            ps2.setInt(1, idPersona);
                            ps2.setString(2, v.getCodigoVendedor());
                            ps2.setString(3, v.getZona());
                            ps2.setBigDecimal(4, v.getComisionPorcentaje());
                            ps2.executeUpdate();
                        }
                    }
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) { }
            throw new RuntimeException("Error al guardar vendedor: " + e.getMessage(), e);
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { }
        }
    }

    @Override
    public List<Vendedor> listar() { return buscar(null, null); }

    @Override
    public List<Vendedor> buscar(String codigo, String nombre) {
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, v.codigo_vendedor, v.zona, v.comision_porcentaje " +
            "FROM vendedor v JOIN persona p ON v.id_vendedor = p.id_persona WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (codigo != null && !codigo.isBlank()) { sql.append(" AND v.codigo_vendedor LIKE ?"); params.add("%" + codigo.trim() + "%"); }
        if (nombre != null && !nombre.isBlank()) { sql.append(" AND p.nombres LIKE ?"); params.add("%" + nombre.trim() + "%"); }
        sql.append(" ORDER BY p.id_persona");
        List<Vendedor> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) lista.add(map(rs)); }
        } catch (SQLException e) { throw new RuntimeException("Error al listar vendedores: " + e.getMessage(), e); }
        return lista;
    }

    @Override
    public Vendedor buscarPorId(int id) {
        String sql = "SELECT p.*, v.codigo_vendedor, v.zona, v.comision_porcentaje FROM vendedor v JOIN persona p ON v.id_vendedor=p.id_persona WHERE v.id_vendedor=?";
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException("Error al buscar vendedor: " + e.getMessage(), e); }
    }

    @Override
    public void actualizar(Vendedor v) {
        String sqlP = "UPDATE persona SET tipo_documento=?,numero_documento=?,nombres=?,apellidos=?,telefono=?,email=?,direccion=?,activo=? WHERE id_persona=?";
        String sqlV = "UPDATE vendedor SET codigo_vendedor=?,zona=?,comision_porcentaje=? WHERE id_vendedor=?";
        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(sqlP)) {
                ps.setString(1,v.getTipoDocumento()); ps.setString(2,v.getNumeroDocumento()); ps.setString(3,v.getNombres());
                ps.setString(4,v.getApellidos()); ps.setString(5,v.getTelefono()); ps.setString(6,v.getEmail());
                ps.setString(7,v.getDireccion()); ps.setBoolean(8,v.getActivo()!=null?v.getActivo():true); ps.setLong(9,v.getIdVendedor()); ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement(sqlV)) {
                ps.setString(1,v.getCodigoVendedor()); ps.setString(2,v.getZona()); ps.setBigDecimal(3,v.getComisionPorcentaje()); ps.setLong(4,v.getIdVendedor()); ps.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) { throw new RuntimeException("Error al actualizar vendedor: " + e.getMessage(), e); }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement("DELETE FROM persona WHERE id_persona=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Error al eliminar vendedor: " + e.getMessage(), e); }
    }

    private Vendedor map(ResultSet rs) throws SQLException {
        Vendedor v = new Vendedor();
        v.setIdVendedor(rs.getLong("id_persona"));
        v.setTipoDocumento(rs.getString("tipo_documento")); v.setNumeroDocumento(rs.getString("numero_documento"));
        v.setNombres(rs.getString("nombres")); v.setApellidos(rs.getString("apellidos"));
        v.setTelefono(rs.getString("telefono")); v.setEmail(rs.getString("email")); v.setDireccion(rs.getString("direccion"));
        v.setActivo(rs.getBoolean("activo")); v.setCodigoVendedor(rs.getString("codigo_vendedor"));
        v.setZona(rs.getString("zona")); v.setComisionPorcentaje(rs.getBigDecimal("comision_porcentaje"));
        return v;
    }
}
