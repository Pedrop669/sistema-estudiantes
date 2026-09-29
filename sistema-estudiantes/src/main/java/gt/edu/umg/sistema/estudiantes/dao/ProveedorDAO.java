package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Proveedor;
import java.util.List;

public interface ProveedorDAO {
    void guardar(Proveedor proveedor);
    List<Proveedor> listar();
    void actualizar(Proveedor proveedor);
    void eliminar(int id);
    Proveedor buscarPorId(int id);
    List<Proveedor> buscar(String nit, String nombre);
}
