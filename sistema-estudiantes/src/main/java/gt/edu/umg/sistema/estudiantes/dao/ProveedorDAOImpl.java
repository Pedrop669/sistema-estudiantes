package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Proveedor;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAOImpl implements ProveedorDAO {

    @Override
    public void guardar(Proveedor p) {
        String sql = "INSERT INTO proveedor (nit, nombre_comercial, contacto, telefono, email, direccion, activo) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,p.getNit()); ps.setString(2,p.getNombreComercial()); ps.setString(3,p.getContacto());
            ps.setString(4,p.getTelefono()); ps.setString(5,p.getEmail()); ps.setString(6,p.getDireccion());
            ps.setBoolean(7,p.getActivo()!=null?p.getActivo():true);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) p.setIdProveedor(rs.getLong(1)); }
        } catch (SQLException e) { throw new RuntimeException("Error al guardar proveedor: " + e.getMessage(), e); }
    }

    @Override public List<Proveedor> listar() { return buscar(null,null); }

    @Override
    public List<Proveedor> buscar(String nit, String nombre) {
        StringBuilder sql = new StringBuilder("SELECT * FROM proveedor WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (nit!=null&&!nit.isBlank()) { sql.append(" AND nit LIKE ?"); params.add("%"+nit.trim()+"%"); }
        if (nombre!=null&&!nombre.isBlank()) { sql.append(" AND nombre_comercial LIKE ?"); params.add("%"+nombre.trim()+"%"); }
        sql.append(" ORDER BY id_proveedor");
        List<Proveedor> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i=0;i<params.size();i++) ps.setObject(i+1,params.get(i));
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) lista.add(map(rs)); }
        } catch (SQLException e) { throw new RuntimeException("Error al listar proveedores: "+e.getMessage(),e); }
        return lista;
    }

    @Override
    public Proveedor buscarPorId(int id) {
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement("SELECT * FROM proveedor WHERE id_proveedor=?")) {
            ps.setInt(1,id); try (ResultSet rs=ps.executeQuery()) { return rs.next()?map(rs):null; }
        } catch (SQLException e) { throw new RuntimeException("Error al buscar proveedor: "+e.getMessage(),e); }
    }

    @Override
    public void actualizar(Proveedor p) {
        String sql = "UPDATE proveedor SET nit=?,nombre_comercial=?,contacto=?,telefono=?,email=?,direccion=?,activo=? WHERE id_proveedor=?";
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,p.getNit()); ps.setString(2,p.getNombreComercial()); ps.setString(3,p.getContacto());
            ps.setString(4,p.getTelefono()); ps.setString(5,p.getEmail()); ps.setString(6,p.getDireccion());
            ps.setBoolean(7,p.getActivo()!=null?p.getActivo():true); ps.setLong(8,p.getIdProveedor()); ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Error al actualizar proveedor: "+e.getMessage(),e); }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement("DELETE FROM proveedor WHERE id_proveedor=?")) {
            ps.setInt(1,id); ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Error al eliminar proveedor: "+e.getMessage(),e); }
    }

    private Proveedor map(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();
        p.setIdProveedor(rs.getLong("id_proveedor")); p.setNit(rs.getString("nit"));
        p.setNombreComercial(rs.getString("nombre_comercial")); p.setContacto(rs.getString("contacto"));
        p.setTelefono(rs.getString("telefono")); p.setEmail(rs.getString("email"));
        p.setDireccion(rs.getString("direccion")); p.setActivo(rs.getBoolean("activo"));
        return p;
    }
}
