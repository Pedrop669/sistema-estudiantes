package gt.edu.umg.sistema.estudiantes.modelo;

import java.math.BigDecimal;

public class Cliente extends Persona {

    private Long idCliente;
    private String codigoCliente;
    private BigDecimal limiteCredito;
    private Boolean activo;

    public Cliente() {
        super();
    }

    public Cliente(Long idCliente, String codigoCliente,
                   BigDecimal limiteCredito, Boolean activo) {

        super();
        this.idCliente = idCliente;
        this.codigoCliente = codigoCliente;
        this.limiteCredito = limiteCredito;
        this.activo = activo;
    }

    public void validarCredito(BigDecimal monto) {
        // Método definido en el diagrama.
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}