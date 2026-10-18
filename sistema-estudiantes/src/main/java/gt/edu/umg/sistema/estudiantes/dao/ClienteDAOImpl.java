
package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Cliente;
import gt.edu.umg.sistema.estudiantes.util.ConexionBD;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteDAOImpl implements ClienteDAO {

    private static final String SQL_TELEFONOS =
        "SELECT telefono FROM telefono_cliente " +
        "WHERE id_cliente = ? ORDER BY id_telefono";

    private List<String> normalizarTelefonos(Cliente c) {
        Set<String> numeros = new LinkedHashSet<>();

        if (c.getTelefonos() != null) {
            for (String telefono : c.getTelefonos()) {
                if (telefono != null && !telefono.isBlank()) {
                    numeros.add(telefono.trim());
                }
            }
        }

        // Compatibilidad con el campo telefono heredado de Persona.
        if (numeros.isEmpty()
                && c.getTelefono() != null
                && !c.getTelefono().isBlank()) {
            numeros.add(c.getTelefono().trim());
        }

        return new ArrayList<>(numeros);
    }

    private void guardarTelefonos(
            Connection con, long idCliente, List<String> telefonos)
            throws SQLException {

        String sql = "INSERT INTO telefono_cliente " +
                     "(id_cliente, telefono) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (String telefono : telefonos) {
                ps.setLong(1, idCliente);
                ps.setString(2, telefono);
                ps.addBatch();
            }

            if (!telefonos.isEmpty()) {
                ps.executeBatch();
            }
        }
    }

    @Override
    public void guardar(Cliente c) {
        String sqlPersona =
            "INSERT INTO persona " +
            "(tipo_documento, numero_documento, nombres, apellidos, " +
            "telefono, email, direccion, activo) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlCliente =
            "INSERT INTO cliente " +
            "(id_cliente, codigo_cliente, limite_credito) " +
            "VALUES (?, ?, ?)";

        List<String> telefonos = normalizarTelefonos(c);
        String telefonoPrincipal =
            telefonos.isEmpty() ? "" : telefonos.get(0);

        Connection con = null;

        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            long idPersona;

            try (PreparedStatement ps = con.prepareStatement(
                    sqlPersona, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, c.getTipoDocumento());
                ps.setString(2, c.getNumeroDocumento());
                ps.setString(3, c.getNombres());
                ps.setString(4, c.getApellidos());
                ps.setString(5, telefonoPrincipal);
                ps.setString(6, c.getEmail());
                ps.setString(7, c.getDireccion());
                ps.setBoolean(8,
                    c.getActivo() == null || c.getActivo());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException(
                            "No se generó el ID de la persona."
                        );
                    }

                    idPersona = rs.getLong(1);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
                ps.setLong(1, idPersona);
                ps.setString(2, c.getCodigoCliente());
                ps.setBigDecimal(3,
                    c.getLimiteCredito() == null
                        ? BigDecimal.ZERO
                        : c.getLimiteCredito());

                ps.executeUpdate();
            }

            guardarTelefonos(con, idPersona, telefonos);

            con.commit();
            c.setIdCliente(idPersona);

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.addSuppressed(ex);
                }
            }

            throw new RuntimeException(
                "Error al guardar cliente: " + e.getMessage(), e
            );
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    // La conexión se libera al terminar la operación.
                }
            }
        }
    }

    @Override
    public List<Cliente> listar() {
        return buscar(null, null);
    }

    @Override
    public List<Cliente> buscar(String codigo, String nombre) {
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, c.id_cliente, c.codigo_cliente, " +
            "c.limite_credito, " +
            "(SELECT GROUP_CONCAT(tc.telefono ORDER BY tc.id_telefono " +
            "SEPARATOR ', ') FROM telefono_cliente tc " +
            "WHERE tc.id_cliente = c.id_cliente) AS telefonos " +
            "FROM cliente c " +
            "JOIN persona p ON c.id_cliente = p.id_persona " +
            "WHERE 1=1"
        );

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

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al listar clientes: " + e.getMessage(), e
            );
        }

        return lista;
    }

    @Override
    public Cliente buscarPorId(int id) {
        String sql =
            "SELECT p.*, c.id_cliente, c.codigo_cliente, " +
            "c.limite_credito, " +
            "(SELECT GROUP_CONCAT(tc.telefono ORDER BY tc.id_telefono " +
            "SEPARATOR ', ') FROM telefono_cliente tc " +
            "WHERE tc.id_cliente = c.id_cliente) AS telefonos " +
            "FROM cliente c " +
            "JOIN persona p ON c.id_cliente = p.id_persona " +
            "WHERE c.id_cliente = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al buscar cliente: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void actualizar(Cliente c) {
        String sqlPersona =
            "UPDATE persona SET tipo_documento=?, numero_documento=?, " +
            "nombres=?, apellidos=?, telefono=?, email=?, direccion=?, " +
            "activo=? WHERE id_persona=?";

        String sqlCliente =
            "UPDATE cliente SET codigo_cliente=?, limite_credito=? " +
            "WHERE id_cliente=?";

        List<String> telefonos = normalizarTelefonos(c);
        String telefonoPrincipal =
            telefonos.isEmpty() ? "" : telefonos.get(0);

        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement ps = con.prepareStatement(sqlPersona)) {
                    ps.setString(1, c.getTipoDocumento());
                    ps.setString(2, c.getNumeroDocumento());
                    ps.setString(3, c.getNombres());
                    ps.setString(4, c.getApellidos());
                    ps.setString(5, telefonoPrincipal);
                    ps.setString(6, c.getEmail());
                    ps.setString(7, c.getDireccion());
                    ps.setBoolean(8,
                        c.getActivo() == null || c.getActivo());
                    ps.setLong(9, c.getIdCliente());
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
                    ps.setString(1, c.getCodigoCliente());
                    ps.setBigDecimal(2,
                        c.getLimiteCredito() == null
                            ? BigDecimal.ZERO
                            : c.getLimiteCredito());
                    ps.setLong(3, c.getIdCliente());
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM telefono_cliente WHERE id_cliente=?")) {
                    ps.setLong(1, c.getIdCliente());
                    ps.executeUpdate();
                }

                guardarTelefonos(con, c.getIdCliente(), telefonos);
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al actualizar cliente: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM persona WHERE id_persona=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al eliminar cliente: " + e.getMessage(), e
            );
        }
    }

    private Cliente map(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();

        c.setIdCliente(rs.getLong("id_cliente"));
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

        String telefonos = rs.getString("telefonos");

        if (telefonos != null && !telefonos.isBlank()) {
            c.setTelefonos(
                Arrays.stream(telefonos.split(",\\s*"))
                    .filter(t -> !t.isBlank())
                    .collect(Collectors.toList())
            );
        } else if (c.getTelefono() != null
                && !c.getTelefono().isBlank()) {
            c.setTelefonos(List.of(c.getTelefono()));
        }

        return c;
    }
}
