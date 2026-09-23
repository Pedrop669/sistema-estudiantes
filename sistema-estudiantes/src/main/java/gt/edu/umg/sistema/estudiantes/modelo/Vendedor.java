package gt.edu.umg.sistema.estudiantes.modelo;

import java.math.BigDecimal;

public class Vendedor extends Persona {

    private Long idVendedor;
    private String codigoVendedor;
    private String zona;
    private BigDecimal comisionPorcentaje;
    private Boolean activo;

    public Vendedor() {
        super();
    }

    public Vendedor(Long idVendedor, String codigoVendedor,
                    String zona, BigDecimal comisionPorcentaje,
                    Boolean activo) {

        super();
        this.idVendedor = idVendedor;
        this.codigoVendedor = codigoVendedor;
        this.zona = zona;
        this.comisionPorcentaje = comisionPorcentaje;
        this.activo = activo;
    }

    public void asignarZona() {
        // Método definido en el diagrama.
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(String codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public BigDecimal getComisionPorcentaje() {
        return comisionPorcentaje;
    }

    public void setComisionPorcentaje(BigDecimal comisionPorcentaje) {
        this.comisionPorcentaje = comisionPorcentaje;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}