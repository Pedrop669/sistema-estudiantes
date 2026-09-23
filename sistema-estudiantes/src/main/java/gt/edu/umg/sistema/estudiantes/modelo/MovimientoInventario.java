package gt.edu.umg.sistema.estudiantes.modelo;

import java.time.LocalDateTime;

public class MovimientoInventario {

    private Long idMovimiento;
    private LocalDateTime fecha;
    private TipoMovimiento tipo;
    private Long referenciaId;
    private String referenciaTipo;
    private Integer cantidad;
    private Integer stockAnterior;
    private Integer stockNuevo;
    private String observaciones;

    public MovimientoInventario() {
    }

    public MovimientoInventario(Long idMovimiento, LocalDateTime fecha,
                                TipoMovimiento tipo, Long referenciaId,
                                String referenciaTipo, Integer cantidad,
                                Integer stockAnterior, Integer stockNuevo,
                                String observaciones) {
        this.idMovimiento = idMovimiento;
        this.fecha = fecha;
        this.tipo = tipo;
        this.referenciaId = referenciaId;
        this.referenciaTipo = referenciaTipo;
        this.cantidad = cantidad;
        this.stockAnterior = stockAnterior;
        this.stockNuevo = stockNuevo;
        this.observaciones = observaciones;
    }

    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(Integer stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public Integer getStockNuevo() {
        return stockNuevo;
    }

    public void setStockNuevo(Integer stockNuevo) {
        this.stockNuevo = stockNuevo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}