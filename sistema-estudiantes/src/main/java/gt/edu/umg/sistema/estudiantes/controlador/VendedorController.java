package gt.edu.umg.sistema.estudiantes.controlador;

import gt.edu.umg.sistema.estudiantes.dao.VendedorDAOImpl;
import gt.edu.umg.sistema.estudiantes.modelo.Vendedor;
import java.util.List;

public class VendedorController {

    VendedorDAOImpl dao;

    public VendedorController() {
        dao = new VendedorDAOImpl();
    }

    public void guardar(Vendedor vendedor) {
        dao.guardar(vendedor);
    }

    public List<Vendedor> getVendedores() {
        return dao.listar();
    }

    public List<Vendedor> buscar(String codigo, String nombre) {
        return dao.buscar(codigo, nombre);
    }

    public Vendedor getVendedor(int id) {
        return dao.buscarPorId(id);
    }

    public void actualizar(Vendedor vendedor) {
        dao.actualizar(vendedor);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}
