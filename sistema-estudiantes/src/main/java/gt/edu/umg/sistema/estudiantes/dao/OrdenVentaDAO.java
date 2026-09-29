package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.OrdenVenta;
import gt.edu.umg.sistema.estudiantes.modelo.DetalleOrdenVenta;
import java.util.List;

public interface OrdenVentaDAO {
    void guardar(OrdenVenta orden, List<DetalleOrdenVenta> detalles);
    List<OrdenVenta> listar();
    OrdenVenta buscarPorId(int id);
    List<DetalleOrdenVenta> listarDetalles(int idOrden);
}
