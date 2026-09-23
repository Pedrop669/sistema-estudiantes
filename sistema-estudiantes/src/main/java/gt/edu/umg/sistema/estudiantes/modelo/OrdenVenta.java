package gt.edu.umg.sistema.estudiantes.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdenVenta {

    private Long idOrdenVenta;
    private LocalDateTime fecha;
    private EstadoOrdenVenta estado;
    private String observaciones;
    private BigDecimal total;

    public OrdenVenta() {
    }

    public OrdenVenta(Long idOrdenVenta, LocalDateTime fecha,
                      EstadoOrdenVenta estado, String observaciones,
                      BigDecimal total) {

        this.idOrdenVenta = idOrdenVenta;
        this.fecha = fecha;
        this.estado = estado;
        this.observaciones = observaciones;
        this.total = total;
    }

    public void agregarDetalle() {
        // Método definido en el diagrama.
    }

    public void calcularTotal() {
        // Método definido en el diagrama.
    }

    public void cambiarEstado() {
        // Método definido en el diagrama.
    }

    public Long getIdOrdenVenta() {
        return idOrdenVenta;
    }

    public void setIdOrdenVenta(Long idOrdenVenta) {
        this.idOrdenVenta = idOrdenVenta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoOrdenVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrdenVenta estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}