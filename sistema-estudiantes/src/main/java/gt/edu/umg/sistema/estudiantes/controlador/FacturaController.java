package gt.edu.umg.sistema.estudiantes.controlador;

import gt.edu.umg.sistema.estudiantes.dao.FacturaDAOImpl;
import gt.edu.umg.sistema.estudiantes.modelo.FacturaVenta;
import java.util.List;

/**
 * @author Pedro
 */
public class FacturaController {

    FacturaDAOImpl dao;

    public FacturaController() {
        dao = new FacturaDAOImpl();
    }

    public List<FacturaVenta> getFacturas() {
        return dao.listar();
    }

    public FacturaVenta getFactura(long id) {
        return dao.buscarPorId(id);
    }

    public FacturaVenta getFacturaPorOrdenVenta(long idOrdenVenta) {
        return dao.buscarPorOrdenVenta(idOrdenVenta);
    }
}
