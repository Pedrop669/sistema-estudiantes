package gt.edu.umg.sistema.estudiantes.modelo; 

import java.util.ArrayList;
import java.util.List;

public class FACTURA{
    private int numeroFactura;
    private String cliente;
    private String fecha; 
    private double total; 
    
    private List<FacturaDetalle> detalles;
    
    public FACTURA(){
        detalles = new ArrayList<>();
    }
    
    public FACTURA(int numeroFactura, String cliente, String fecha){
        this.numeroFactura = numeroFactura; 
        this.cliente = cliente;
        this.fecha = fecha;
        this.detalles = new ArrayList<>();
    }
    
        public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }

    public void agregarDetalle(FacturaDetalle detalle) {
        detalles.add(detalle);
        calcularTotal();
    }

    public void calcularTotal() {
        total = 0;

        for (FacturaDetalle detalle : detalles) {
            total += detalle.getSubtotal();
        }
    }
    
}