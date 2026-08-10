package gt.edu.umg.sistema.estudiantes.modelo;

public class FacturaDetalle {

    private String producto;
    private int cantidad;
    private double precio;
    private double subtotal;

    public FacturaDetalle() {
    }

    public FacturaDetalle(String producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        calcularSubtotal();
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void calcularSubtotal() {
        subtotal = cantidad * precio;
    }
}