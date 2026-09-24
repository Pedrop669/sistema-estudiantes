import java.sql.*;

public class ClienteDAO {

    public boolean insertarCliente(Cliente cliente) {
        String sqlPersona = "INSERT INTO persona (tipo_documento, numero_documento, nombres, apellidos, telefono, email, direccion, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (id_cliente, codigo_cliente, limite_credito) VALUES (?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            conn.setAutoCommit(false); // Iniciar Transacción

            // 1. Insertar en Persona
            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                stmtPersona.setString(1, cliente.getTipoDocumento());
                stmtPersona.setString(2, cliente.getNumeroDocumento());
                stmtPersona.setString(3, cliente.getNombres());
                stmtPersona.setString(4, cliente.getApellidos());
                stmtPersona.setString(5, cliente.getTelefono());
                stmtPersona.setString(6, cliente.getEmail());
                stmtPersona.setString(7, cliente.getDireccion());
                stmtPersona.setBoolean(8, cliente.isActivo());
                
                stmtPersona.executeUpdate();
                
                ResultSet rsKeys = stmtPersona.getGeneratedKeys();
                if (rsKeys.next()) {
                    int idPersona = rsKeys.getInt(1);
                    
                    // 2. Insertar en Cliente usando el ID generado
                    try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
                        stmtCliente.setInt(1, idPersona);
                        stmtCliente.setString(2, cliente.getCodigoCliente());
                        stmtCliente.setBigDecimal(3, cliente.getLimiteCredito());
                        
                        stmtCliente.executeUpdate();
                    }
                } else {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit(); // Confirmar transacción
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}