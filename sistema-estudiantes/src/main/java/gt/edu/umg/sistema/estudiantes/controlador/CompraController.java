package gt.edu.umg.sistema.estudiantes.controlador;

import gt.edu.umg.sistema.estudiantes.dao.CompraDAOImpl;
import gt.edu.umg.sistema.estudiantes.modelo.Compra;
import gt.edu.umg.sistema.estudiantes.modelo.DetalleCompra;
import java.util.List;

public class CompraController {

    CompraDAOImpl dao;

    public CompraController() {
        dao = new CompraDAOImpl();
    }

    public void guardar(Compra compra, List<DetalleCompra> detalles) {
        dao.guardar(compra, detalles);
    }

    public List<Compra> getCompras() {
        return dao.listar();
    }

    public Compra getCompra(int id){
        return dao.buscarPorId(id);
    }

    public List<DetalleCompra> getDetalles(int idCompra) {
        return dao.listarDetalles(idCompra);
    }
}
