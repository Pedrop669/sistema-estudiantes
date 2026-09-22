package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Producto;
import gt.edu.umg.sistema.estudiantes.modelo.TipoMovimiento;
import java.util.List;

public interface ProductoDAO {

    void guardar(Producto producto);
    List<Producto> listar();
    void actualizar(Producto producto);
    void eliminar(int id);

    Producto buscarPorId(int id);

    List<Producto> buscar(String codigo, String nombre);

    void ajustarStock(int idProducto, TipoMovimiento tipo, int cantidad, Long referenciaId, String referenciaTipo, String observaciones);
}
