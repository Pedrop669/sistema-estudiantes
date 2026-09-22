package gt.edu.umg.sistema.estudiantes.modelo;

import java.time.LocalDateTime;

public class MovimientoInventario {

    private int id;
    private int idProducto;
    private LocalDateTime fecha;
    private TipoMovimiento tipo;
    private Long referenciaId;      // id de la compra/venta/despacho que lo origino
    private String referenciaTipo;  // "COMPRA", "ORDEN_VENTA", etc.
    private int cantidad;
    private int stockAnterior;
    private int stockNuevo;
    private String observaciones;

    public MovimientoInventario() {}

    public MovimientoInventario(int id, int idProducto, LocalDateTime fecha, TipoMovimiento tipo, Long referenciaId, String referenciaTipo, int cantidad, int stockAnterior, int stockNuevo, String observaciones) {
        this.id = id;
        this.idProducto = idProducto;
        this.fecha = fecha;
        this.tipo = tipo;
        this.referenciaId = referenciaId;
        this.referenciaTipo = referenciaTipo;
        this.cantidad = cantidad;
        this.stockAnterior = stockAnterior;
        this.stockNuevo = stockNuevo;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public String getReferenciaTipo() {
        return referenciaTipo;
    }

    public void setReferenciaTipo(String referenciaTipo) {
        this.referenciaTipo = referenciaTipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(int stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public int getStockNuevo() {
        return stockNuevo;
    }

    public void setStockNuevo(int stockNuevo) {
        this.stockNuevo = stockNuevo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
