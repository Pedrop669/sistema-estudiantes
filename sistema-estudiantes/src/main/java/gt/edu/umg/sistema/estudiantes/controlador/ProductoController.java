package gt.edu.umg.sistema.estudiantes.controlador;

import gt.edu.umg.sistema.estudiantes.dao.ProductoDAOImpl;
import gt.edu.umg.sistema.estudiantes.modelo.Producto;
import gt.edu.umg.sistema.estudiantes.modelo.TipoMovimiento;
import java.util.List;

public class ProductoController {

    ProductoDAOImpl dao;

    public ProductoController() {
        dao = new ProductoDAOImpl();
    }

    public void guardar(Producto producto) {
        dao.guardar(producto);
    }

    public List<Producto> getProductos() {
        return dao.listar();
    }

    public List<Producto> buscar(String codigo, String nombre) {
        return dao.buscar(codigo, nombre);
    }

    public Producto getProducto(int id) {
        return dao.buscarPorId(id);
    }

    public void actualizar(Producto producto) {
        dao.actualizar(producto);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }

    public void ajustarStock(int idProducto, TipoMovimiento tipo, int cantidad, Long referenciaId, String referenciaTipo, String observaciones) {
        dao.ajustarStock(idProducto, tipo, cantidad, referenciaId, referenciaTipo, observaciones);
    }
}
