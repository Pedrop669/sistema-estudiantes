package gt.edu.umg.sistema.estudiantes.modelo;

public class Proveedor {

    private Long idProveedor;
    private String nit;
    private String nombreComercial;
    private String contacto;
    private String telefono;
    private String correoElectronico;
    private String direccion;
    private Boolean activo;

    public Proveedor() {
    }

    public Proveedor(Long idProveedor, String nit, String nombreComercial,
                     String contacto, String telefono,
                     String correoElectronico, String direccion,
                     Boolean activo) {

        this.idProveedor = idProveedor;
        this.nit = nit;
        this.nombreComercial = nombreComercial;
        this.contacto = contacto;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.activo = activo;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}