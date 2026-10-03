package gt.edu.umg.sistema.estudiantes.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Documento comercial final del flujo de ventas. Se genera automaticamente
 * al confirmar un despacho (ver DespachoDAOImpl.confirmarDespacho).
 *
 * @author Pedro
 */
public class FacturaVenta {

    private Long idFactura;
    private Long idOrdenVenta;
    private Long idOrdenDespacho;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String estado;

    public FacturaVenta() {
    }

    public FacturaVenta(Long idFactura, Long idOrdenVenta, Long idOrdenDespacho,
                   LocalDateTime fecha, BigDecimal total, String estado) {
        this.idFactura = idFactura;
        this.idOrdenVenta = idOrdenVenta;
        this.idOrdenDespacho = idOrdenDespacho;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public Long getIdOrdenVenta() {
        return idOrdenVenta;
    }

    public void setIdOrdenVenta(Long idOrdenVenta) {
        this.idOrdenVenta = idOrdenVenta;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
