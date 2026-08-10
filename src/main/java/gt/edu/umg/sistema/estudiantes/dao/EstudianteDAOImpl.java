/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maorozco
 */
public class EstudianteDAOImpl implements EstudianteDAO {

    private final List<Estudiante> estudiantes = new ArrayList<>();

    public void guardar(Estudiante estutidante) {
        String sql = "INSERT INTO Estudiante (nombres, apellidos, email, carnet) VALUES (?, ?, ?, ?)";

        try (Connection connection = Conexion.getConexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, estutidante.getNombres());
            pstmt.setString(2, estutidante.getApellidos());
            pstmt.setString(3, estutidante.getEmail());
            pstmt.setString(4, estutidante.getCarnet());

            pstmt.executeUpdate();
            System.out.println("Estudiante guardado correctamente en la base de datos");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<Estudiante> listar() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT id, nombres, apellidos, email, carnet FROM Estudiante";

        try (Connection connection = Conexion.getConexion();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estudiante est = new Estudiante();
                est.setId(rs.getInt("id"));
                est.setNombres(rs.getString("nombres"));
                est.setApellidos(rs.getString("apellidos"));
                est.setEmail(rs.getString("email"));
                est.setCarnet(rs.getString("carnet"));
                lista.add(est);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return lista;
    }

    @Override
    public void actualizar(Estudiante estudiante) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int id) {
        estudiantes.removeIf(estudiante -> estudiante.getId() == id);
    }
    
}
