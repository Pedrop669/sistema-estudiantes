
package gt.edu.umg.sistema.estudiantes.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {

    private Long idCliente;
    private String codigoCliente;
    private BigDecimal limiteCredito;
    private Boolean activo;
    private List<String> telefonos = new ArrayList<>();

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
        if (monto == null || monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                "El monto debe ser mayor o igual a cero."
            );
        }

        if (limiteCredito != null && monto.compareTo(limiteCredito) > 0) {
            throw new IllegalArgumentException(
                "El monto supera el límite de crédito del cliente."
            );
        }
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

    public List<String> getTelefonos() {
        return new ArrayList<>(telefonos);
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos == null
                ? new ArrayList<>()
                : new ArrayList<>(telefonos);
    }

    public void agregarTelefono(String telefono) {
        if (telefono != null && !telefono.isBlank()) {
            String numero = telefono.trim();

            if (!telefonos.contains(numero)) {
                telefonos.add(numero);
            }
        }
    }
}
