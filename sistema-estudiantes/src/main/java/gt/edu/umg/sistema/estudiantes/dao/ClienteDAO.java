package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Cliente;
import java.util.List;

public interface ClienteDAO {
    void guardar(Cliente cliente);
    List<Cliente> listar();
    void actualizar(Cliente cliente);
    void eliminar(int id);
    Cliente buscarPorId(int id);
    List<Cliente> buscar(String codigo, String nombre);
}
