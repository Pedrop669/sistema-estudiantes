package gt.edu.umg.sistema.estudiantes.modelo;

public class DetalleDespacho {

    private Long idDetalleDespacho;
    private Integer cantidad;
    private String observaciones;

    public DetalleDespacho() {
    }

    public DetalleDespacho(Long idDetalleDespacho, Integer cantidad,
                           String observaciones) {
        this.idDetalleDespacho = idDetalleDespacho;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
    }

    public Long getIdDetalleDespacho() {
        return idDetalleDespacho;
    }

    public void setIdDetalleDespacho(Long idDetalleDespacho) {
        this.idDetalleDespacho = idDetalleDespacho;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}