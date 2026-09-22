package gt.edu.umg.sistema.estudiantes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
        "jdbc:mysql://localhost:3306/sistema_ventas"
        + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Guatemala";
    private static final String USUARIO = "root";
    private static final String CLAVE   = "";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    /** Metodo rapido para probar que la conexion funciona. */
    public static void main(String[] args) {
        try (Connection con = getConexion()) {
            System.out.println("Conexion exitosa a: " + con.getCatalog());
        } catch (SQLException e) {
            System.out.println("ERROR de conexion: " + e.getMessage());
        }
    }
}
