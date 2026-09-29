package gt.edu.umg.sistema.estudiantes.controlador;

import gt.edu.umg.sistema.estudiantes.dao.ProveedorDAOImpl;
import gt.edu.umg.sistema.estudiantes.modelo.Proveedor;
import java.util.List;

public class ProveedorController {

    ProveedorDAOImpl dao;

    public ProveedorController() {
        dao = new ProveedorDAOImpl();
    }

    public void guardar(Proveedor proveedor) {
        dao.guardar(proveedor);
    }

    public List<Proveedor> getProveedores() {
        return dao.listar();
    }

    public List<Proveedor> buscar(String nit, String nombre) {
        return dao.buscar(nit, nombre);
    }

    public Proveedor getProveedor(int id) {
        return dao.buscarPorId(id);
    }

    public void actualizar(Proveedor proveedor) {
        dao.actualizar(proveedor);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}
