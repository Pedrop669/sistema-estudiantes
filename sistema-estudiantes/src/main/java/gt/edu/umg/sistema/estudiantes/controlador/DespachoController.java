package gt.edu.umg.sistema.estudiantes.controlador;

import gt.edu.umg.sistema.estudiantes.dao.DespachoDAOImpl;
import gt.edu.umg.sistema.estudiantes.modelo.DetalleDespacho;
import gt.edu.umg.sistema.estudiantes.modelo.OrdenDespacho;
import java.util.List;

public class DespachoController {

    DespachoDAOImpl dao;

    public DespachoController() {
        dao = new DespachoDAOImpl();
    }

    public void generarDespacho(long idOrdenVenta) {
        dao.generarDespacho(idOrdenVenta);
    }

    public void confirmarDespacho(long idOrdenDespacho) {
        dao.confirmarDespacho(idOrdenDespacho);
    }

    public List<OrdenDespacho> getPendientes() {
        return dao.listarPendientes();
    }

    public List<OrdenDespacho> getDespachos() {
        return dao.listar();
    }

    public OrdenDespacho getDespacho(long id) {
        return dao.buscarPorId(id);
    }

    public OrdenDespacho getDespachoPorOrdenVenta(long idOrdenVenta) {
        return dao.buscarPorOrdenVenta(idOrdenVenta);
    }

    public List<DetalleDespacho> getDetalles(long idOrdenDespacho) {
        return dao.listarDetalles(idOrdenDespacho);
    }
}
