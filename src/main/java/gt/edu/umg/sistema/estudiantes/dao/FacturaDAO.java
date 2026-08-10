/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.FACTURA;
import java.util.List;

/**
 *
 * @author maorozco
 */
public interface FacturaDAO {

    void guardar(FACTURA factura);

    List<FACTURA> listar();

    void actualizar(FACTURA factura);

    void eliminar(int numeroFactura);
}
