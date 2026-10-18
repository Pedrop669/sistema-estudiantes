
package gt.edu.umg.sistema.estudiantes.util;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.getConexion()) {

            System.out.println("CONEXION EXITOSA");
            System.out.println("Base de datos: " + conexion.getCatalog());

        } catch (SQLException e) {
            System.out.println("ERROR AL CONECTAR CON MYSQL");
            e.printStackTrace();
        }
    }
}
