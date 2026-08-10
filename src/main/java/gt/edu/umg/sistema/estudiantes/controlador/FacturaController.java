/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.estudiantes.controlador;

import gt.edu.umg.sistema.estudiantes.dao.FacturaDAOImpl;
import gt.edu.umg.sistema.estudiantes.modelo.FACTURA;
import java.util.List;

/**
 *
 * @author maorozco
 */
public class FacturaController {

    FacturaDAOImpl dao;

    public FacturaController() {
        dao = new FacturaDAOImpl();
    }

    public void Guardar(FACTURA factura) {
        dao.guardar(factura);
    }

    public List<FACTURA> GetFacturas() {
        return dao.listar();
    }
}
