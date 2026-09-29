package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.Compra;
import gt.edu.umg.sistema.estudiantes.modelo.DetalleCompra;
import java.util.List;

public interface CompraDAO {
    void guardar(Compra compra, List<DetalleCompra> detalles);
    List<Compra> listar();
    Compra buscarPorId(int id);
    List<DetalleCompra> listarDetalles(int idCompra);
}
