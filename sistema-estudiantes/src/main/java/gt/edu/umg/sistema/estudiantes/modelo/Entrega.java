package gt.edu.umg.sistema.estudiantes.modelo;

import java.time.LocalDateTime;

public class Entrega {

    private Long idEntrega;
    private LocalDateTime fecha;
    private EstadoEntrega estado;
    private String ubicacion;
    private String observaciones;

    public Entrega() {
    }

    public Entrega(Long idEntrega, LocalDateTime fecha,
                   EstadoEntrega estado, String ubicacion,
                   String observaciones) {
        this.idEntrega = idEntrega;
        this.fecha = fecha;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.observaciones = observaciones;
    }

    public void confirmarEntrega() {
    }

    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoEntrega getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntrega estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
