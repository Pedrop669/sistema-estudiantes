package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.DetalleDespacho;
import gt.edu.umg.sistema.estudiantes.modelo.OrdenDespacho;
import java.util.List;

public interface DespachoDAO {

    void generarDespacho(long idOrdenVenta);

    void confirmarDespacho(long idOrdenDespacho);

    List<OrdenDespacho> listarPendientes();

    List<OrdenDespacho> listar();

    OrdenDespacho buscarPorId(long id);

    List<DetalleDespacho> listarDetalles(long idOrdenDespacho);

    OrdenDespacho buscarPorOrdenVenta(long idOrdenVenta);
}
