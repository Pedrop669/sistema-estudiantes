/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.FACTURA;
import gt.edu.umg.sistema.estudiantes.modelo.FacturaDetalle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tablas usadas (según script del usuario):
 *  Factura(numero PK, cliente, fecha DATE, total)
 *  Factura_detalle(id PK, numero_factura FK->Factura.numero, id_producto FK->Producto.idProducto,
 *                   cantidad, precio, subtotal)
 *  Producto(idProducto PK, nombre)
 *
 * Como el detalle no guarda el nombre del producto sino el id_producto,
 * guardar() busca el producto por nombre y si no existe lo crea.
 *
 * fecha se espera en formato yyyy-MM-dd (formato de java.sql.Date.valueOf).
 *
 * @author maorozco
 */
public class FacturaDAOImpl implements FacturaDAO {

    public void guardar(FACTURA factura) {
        String sqlFactura = "INSERT INTO Factura (numero, cliente, fecha, total) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO Factura_detalle (numero_factura, id_producto, cantidad, precio, subtotal) VALUES (?, ?, ?, ?, ?)";
        String sqlBuscarProducto = "SELECT idProducto FROM Producto WHERE nombre = ?";
        String sqlInsertarProducto = "INSERT INTO Producto (nombre) VALUES (?)";

        Connection connection = null;

        try {
            connection = Conexion.getConexion();
            connection.setAutoCommit(false);

            try (PreparedStatement pstmtFactura = connection.prepareStatement(sqlFactura)) {
                pstmtFactura.setInt(1, factura.getNumeroFactura());
                pstmtFactura.setString(2, factura.getCliente());
                pstmtFactura.setDate(3, Date.valueOf(factura.getFecha()));
                pstmtFactura.setDouble(4, factura.getTotal());
                pstmtFactura.executeUpdate();
            }

            try (PreparedStatement pstmtDetalle = connection.prepareStatement(sqlDetalle)) {
                for (FacturaDetalle detalle : factura.getDetalles()) {
                    int idProducto = obtenerOCrearProducto(connection, sqlBuscarProducto, sqlInsertarProducto, detalle.getProducto());

                    pstmtDetalle.setInt(1, factura.getNumeroFactura());
                    pstmtDetalle.setInt(2, idProducto);
                    pstmtDetalle.setInt(3, detalle.getCantidad());
                    pstmtDetalle.setDouble(4, detalle.getPrecio());
                    pstmtDetalle.setDouble(5, detalle.getSubtotal());
                    pstmtDetalle.addBatch();
                }
                pstmtDetalle.executeBatch();
            }

            connection.commit();
            System.out.println("Factura guardada correctamente en la base de datos");

        } catch (SQLException | IllegalArgumentException exception) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            exception.printStackTrace();
            throw new RuntimeException("No se pudo guardar la factura: " + exception.getMessage(), exception);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    private int obtenerOCrearProducto(Connection connection, String sqlBuscar, String sqlInsertar, String nombreProducto) throws SQLException {
        try (PreparedStatement pstmtBuscar = connection.prepareStatement(sqlBuscar)) {
            pstmtBuscar.setString(1, nombreProducto);
            try (ResultSet rs = pstmtBuscar.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idProducto");
                }
            }
        }

        try (PreparedStatement pstmtInsertar = connection.prepareStatement(sqlInsertar, Statement.RETURN_GENERATED_KEYS)) {
            pstmtInsertar.setString(1, nombreProducto);
            pstmtInsertar.executeUpdate();
            try (ResultSet generatedKeys = pstmtInsertar.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        throw new SQLException("No se pudo obtener ni crear el producto: " + nombreProducto);
    }

    @Override
    public List<FACTURA> listar() {
        List<FACTURA> lista = new ArrayList<>();
        String sqlFacturas = "SELECT numero, cliente, fecha, total FROM Factura";
        String sqlDetalles = "SELECT p.nombre AS producto, fd.cantidad, fd.precio, fd.subtotal "
                + "FROM Factura_detalle fd "
                + "JOIN Producto p ON p.idProducto = fd.id_producto "
                + "WHERE fd.numero_factura = ?";

        try (Connection connection = Conexion.getConexion();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlFacturas)) {

            while (rs.next()) {
                FACTURA factura = new FACTURA();
                factura.setNumeroFactura(rs.getInt("numero"));
                factura.setCliente(rs.getString("cliente"));
                Date fecha = rs.getDate("fecha");
                factura.setFecha(fecha != null ? fecha.toString() : null);
                factura.setTotal(rs.getDouble("total"));

                try (PreparedStatement pstmtDetalle = connection.prepareStatement(sqlDetalles)) {
                    pstmtDetalle.setInt(1, factura.getNumeroFactura());
                    try (ResultSet rsDetalle = pstmtDetalle.executeQuery()) {
                        while (rsDetalle.next()) {
                            FacturaDetalle detalle = new FacturaDetalle();
                            detalle.setProducto(rsDetalle.getString("producto"));
                            detalle.setCantidad(rsDetalle.getInt("cantidad"));
                            detalle.setPrecio(rsDetalle.getDouble("precio"));
                            factura.getDetalles().add(detalle);
                        }
                    }
                }

                lista.add(factura);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return lista;
    }

    @Override
    public void actualizar(FACTURA factura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(int numeroFactura) {
        String sqlDetalle = "DELETE FROM Factura_detalle WHERE numero_factura = ?";
        String sqlFactura = "DELETE FROM Factura WHERE numero = ?";

        try (Connection connection = Conexion.getConexion()) {
            connection.setAutoCommit(false);

            try (PreparedStatement pstmtDetalle = connection.prepareStatement(sqlDetalle)) {
                pstmtDetalle.setInt(1, numeroFactura);
                pstmtDetalle.executeUpdate();
            }

            try (PreparedStatement pstmtFactura = connection.prepareStatement(sqlFactura)) {
                pstmtFactura.setInt(1, numeroFactura);
                pstmtFactura.executeUpdate();
            }

            connection.commit();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
