package gt.edu.umg.sistema.estudiantes.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Compra {

    private Long idCompra;
    private LocalDateTime fecha;
    private Proveedor proveedor;
    private EstadoCompra estado;
    private BigDecimal total;

    public Compra() {
    }

    public Compra(Long idCompra, LocalDateTime fecha,
                  Proveedor proveedor, EstadoCompra estado,
                  BigDecimal total) {
        this.idCompra = idCompra;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.estado = estado;
        this.total = total;
    }

    public void agregarDetalle() {
    }

    public void cambiarEstado() {
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}