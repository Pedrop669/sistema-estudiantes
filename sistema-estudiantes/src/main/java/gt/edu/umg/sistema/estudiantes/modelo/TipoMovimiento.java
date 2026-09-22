package gt.edu.umg.sistema.estudiantes.modelo;

/** Tipos de movimiento de inventario (enumeracion del diagrama). */
public enum TipoMovimiento {
    COMPRA,             // entra stock
    VENTA,              // sale stock
    DESPACHO,           // sale stock
    DEVOLUCION_VENTA,   // entra stock
    DEVOLUCION_COMPRA,  // sale stock
    AJUSTE              // correccion manual
}
