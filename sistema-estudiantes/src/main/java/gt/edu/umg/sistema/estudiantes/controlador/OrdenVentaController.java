package gt.edu.umg.sistema.estudiantes.controlador;

import gt.edu.umg.sistema.estudiantes.dao.OrdenVentaDAOImpl;
import gt.edu.umg.sistema.estudiantes.modelo.OrdenVenta;
import gt.edu.umg.sistema.estudiantes.modelo.DetalleOrdenVenta;
import java.util.List;

public class OrdenVentaController {

    OrdenVentaDAOImpl dao;

    public OrdenVentaController() {
        dao = new OrdenVentaDAOImpl();
    }

    public void guardar(OrdenVenta orden, List<DetalleOrdenVenta> detalles) {
        dao.guardar(orden, detalles);
    }

    public List<OrdenVenta> getOrdenes() {
        return dao.listar();
    }

    public OrdenVenta getOrden(int id) {
        return dao.buscarPorId(id);
    }

    public List<DetalleOrdenVenta> getDetalles(int idOrden) {
        return dao.listarDetalles(idOrden);
    }
}
