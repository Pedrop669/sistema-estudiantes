package gt.edu.umg.sistema.estudiantes.modelo;

import java.time.LocalDateTime;

public class OrdenDespacho {

    private Long idOrdenDespacho;
    private LocalDateTime fecha;
    private EstadoDespacho estado;
    private String observaciones;

    public OrdenDespacho() {
    }

    public OrdenDespacho(Long idOrdenDespacho, LocalDateTime fecha,
                         EstadoDespacho estado, String observaciones) {
        this.idOrdenDespacho = idOrdenDespacho;
        this.fecha = fecha;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public void cambiarEstado() {
    }

    public Long getIdOrdenDespacho() {
        return idOrdenDespacho;
    }

    public void setIdOrdenDespacho(Long idOrdenDespacho) {
        this.idOrdenDespacho = idOrdenDespacho;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoDespacho getEstado() {
        return estado;
    }

    public void setEstado(EstadoDespacho estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}