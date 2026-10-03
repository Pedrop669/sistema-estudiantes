package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.modelo.FacturaVenta;
import java.util.List;

/**
 * Solo consultas: la factura se genera automaticamente dentro de
 * DespachoDAOImpl.confirmarDespacho, no se inserta desde aqui.
 *
 * @author Antonio
 */
public interface FacturaDAO {

    List<FacturaVenta> listar();

    FacturaVenta buscarPorId(long id);

    FacturaVenta buscarPorOrdenVenta(long idOrdenVenta);
}
