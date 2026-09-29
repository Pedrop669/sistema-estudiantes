package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Cliente;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void guardar(Cliente c) {
        String sqlPersona = "INSERT INTO persona (tipo_documento, numero_documento, nombres, apellidos, telefono, email, direccion, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (id_cliente, codigo_cliente, limite_credito) VALUES (?, ?, ?)";

        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            // 1. insertar en persona
            try (PreparedStatement ps = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, c.getTipoDocumento());
                ps.setString(2, c.getNumeroDocumento());
                ps.setString(3, c.getNombres());
                ps.setString(4, c.getApellidos());
                ps.setString(5, c.getTelefono());
                ps.setString(6, c.getEmail());
                ps.setString(7, c.getDireccion());
                ps.setBoolean(8, c.getActivo() != null ? c.getActivo() : true);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idPersona = rs.getInt(1);
                        c.setIdCliente((long) idPersona);

                        // 2. insertar en cliente con el mismo id
                        try (PreparedStatement ps2 = con.prepareStatement(sqlCliente)) {
                            ps2.setInt(1, idPersona);
                            ps2.setString(2, c.getCodigoCliente());
                            ps2.setBigDecimal(3, c.getLimiteCredito());
                            ps2.executeUpdate();
                        }
                    }
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) {}
            throw new RuntimeException("Error al guardar cliente: " + e.getMessage(), e);
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException e) {}
        }
    }

    @Override
    public List<Cliente> listar() {
        return buscar(null, null);
    }

    @Override
    public List<Cliente> buscar(String codigo, String nombre) {
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, c.codigo_cliente, c.limite_credito " +
            "FROM cliente c JOIN persona p ON c.id_cliente = p.id_persona WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (codigo != null && !codigo.isBlank()) {
            sql.append(" AND c.codigo_cliente LIKE ?");
            params.add("%" + codigo.trim() + "%");
        }
        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND p.nombres LIKE ?");
            params.add("%" + nombre.trim() + "%");
        }
        sql.append(" ORDER BY p.id_persona");

        List<Cliente> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT p.*, c.codigo_cliente, c.limite_credito " +
                     "FROM cliente c JOIN persona p ON c.id_cliente = p.id_persona " +
                     "WHERE c.id_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Cliente c) {
        String sqlPersona = "UPDATE persona SET tipo_documento=?, numero_documento=?, nombres=?, apellidos=?, telefono=?, email=?, direccion=?, activo=? WHERE id_persona=?";
        String sqlCliente = "UPDATE cliente SET codigo_cliente=?, limite_credito=? WHERE id_cliente=?";
        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(sqlPersona)) {
                ps.setString(1, c.getTipoDocumento());
                ps.setString(2, c.getNumeroDocumento());
                ps.setString(3, c.getNombres());
                ps.setString(4, c.getApellidos());
                ps.setString(5, c.getTelefono());
                ps.setString(6, c.getEmail());
                ps.setString(7, c.getDireccion());
                ps.setBoolean(8, c.getActivo() != null ? c.getActivo() : true);
                ps.setLong(9, c.getIdCliente());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
                ps.setString(1, c.getCodigoCliente());
                ps.setBigDecimal(2, c.getLimiteCredito());
                ps.setLong(3, c.getIdCliente());
                ps.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        // al borrar persona se borra cliente por CASCADE
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement("DELETE FROM persona WHERE id_persona=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente: " + e.getMessage(), e);
        }
    }

    private Cliente map(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getLong("id_persona"));
        c.setTipoDocumento(rs.getString("tipo_documento"));
        c.setNumeroDocumento(rs.getString("numero_documento"));
        c.setNombres(rs.getString("nombres"));
        c.setApellidos(rs.getString("apellidos"));
        c.setTelefono(rs.getString("telefono"));
        c.setEmail(rs.getString("email"));
        c.setDireccion(rs.getString("direccion"));
        c.setActivo(rs.getBoolean("activo"));
        c.setCodigoCliente(rs.getString("codigo_cliente"));
        c.setLimiteCredito(rs.getBigDecimal("limite_credito"));
        return c;
    }
}
