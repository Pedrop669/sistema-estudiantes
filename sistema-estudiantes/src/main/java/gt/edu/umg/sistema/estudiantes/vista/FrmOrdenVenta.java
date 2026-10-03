package gt.edu.umg.sistema.estudiantes.vista;

import gt.edu.umg.sistema.estudiantes.controlador.ClienteController;
import gt.edu.umg.sistema.estudiantes.controlador.DespachoController;
import gt.edu.umg.sistema.estudiantes.controlador.OrdenVentaController;
import gt.edu.umg.sistema.estudiantes.controlador.ProductoController;
import gt.edu.umg.sistema.estudiantes.controlador.VendedorController;
import gt.edu.umg.sistema.estudiantes.modelo.Cliente;
import gt.edu.umg.sistema.estudiantes.modelo.DetalleOrdenVenta;
import gt.edu.umg.sistema.estudiantes.modelo.EstadoOrdenVenta;
import gt.edu.umg.sistema.estudiantes.modelo.OrdenVenta;
import gt.edu.umg.sistema.estudiantes.modelo.Producto;
import gt.edu.umg.sistema.estudiantes.modelo.Vendedor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario de registro de Orden de Venta. Permite elegir cliente y
 * vendedor, agregar varios productos con cantidad, calcular el total y
 * guardar la venta.
 *
 * @author Daniel
 */
public class FrmOrdenVenta extends javax.swing.JInternalFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FrmOrdenVenta.class.getName());

    ClienteController clienteController = new ClienteController();
    VendedorController vendedorController = new VendedorController();
    ProductoController productoController = new ProductoController();
    OrdenVentaController ordenVentaController = new OrdenVentaController();
    DespachoController despachoController = new DespachoController();

    // listas paralelas a los combos, para mapear el indice seleccionado
    // con el objeto real (Cliente / Vendedor / Producto)
    private List<Cliente> listaClientes = new ArrayList<>();
    private List<Vendedor> listaVendedores = new ArrayList<>();
    private List<Producto> listaProductos = new ArrayList<>();

    // detalle de la venta que se va armando en memoria antes de guardar
    private List<DetalleOrdenVenta> detalles = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    // id de la ultima orden guardada, para poder confirmarla despues
    private Long idUltimaOrdenGuardada = null;

    /**
     * Creates new form FrmOrdenVenta
     */
    public FrmOrdenVenta() {
        initComponents();
        cargarCombos();
        prepararTablaDetalle();
        btnConfirmarOrden.setEnabled(false);
    }

    // ================= carga inicial =================

    private void cargarCombos() {
        // --- clientes ---
        listaClientes = clienteController.getClientes();
        DefaultComboBoxModel<String> modeloCliente = new DefaultComboBoxModel<>();
        for (Cliente c : listaClientes) {
            modeloCliente.addElement(c.getCodigoCliente() + " - " + c.getNombres());
        }
        cmbCliente.setModel(modeloCliente);

        // --- vendedores ---
        listaVendedores = vendedorController.getVendedores();
        DefaultComboBoxModel<String> modeloVendedor = new DefaultComboBoxModel<>();
        for (Vendedor v : listaVendedores) {
            modeloVendedor.addElement(v.getCodigoVendedor() + " - " + v.getNombres());
        }
        cmbVendedor.setModel(modeloVendedor);

        // --- productos ---
        listaProductos = productoController.getProductos();
        DefaultComboBoxModel<String> modeloProducto = new DefaultComboBoxModel<>();
        for (Producto p : listaProductos) {
            modeloProducto.addElement(p.getCodigo() + " - " + p.getNombre()
                    + " (stock: " + p.getStockActual() + ")");
        }
        cmbProducto.setModel(modeloProducto);
    }

    private void prepararTablaDetalle() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Producto");
        model.addColumn("Cantidad");
        model.addColumn("Precio");
        model.addColumn("Subtotal");
        jTable1.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        lblCliente = new javax.swing.JLabel();
        cmbCliente = new javax.swing.JComboBox<>();
        lblVendedor = new javax.swing.JLabel();
        cmbVendedor = new javax.swing.JComboBox<>();
        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnConfirmarOrden = new javax.swing.JButton();
        lblEstadoOrden = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Nueva Orden de Venta");

        lblCliente.setText("Cliente:");
        lblVendedor.setText("Vendedor:");
        lblProducto.setText("Producto:");
        lblCantidad.setText("Cantidad:");

        btnAgregar.setText("Agregar producto");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Producto", "Cantidad", "Precio", "Subtotal"}
        ));
        jScrollPane1.setViewportView(jTable1);

        lblTotal.setText("Total: Q 0.00");
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14));

        btnGuardar.setText("Guardar Venta");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnConfirmarOrden.setText("Confirmar Orden (generar despacho)");
        btnConfirmarOrden.addActionListener(this::btnConfirmarOrdenActionPerformed);

        lblEstadoOrden.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblVendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCantidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmarOrden))
                    .addComponent(lblEstadoOrden))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCliente)
                    .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVendedor)
                    .addComponent(cmbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProducto)
                    .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotal)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnConfirmarOrden))
                .addGap(10, 10, 10)
                .addComponent(lblEstadoOrden)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    // ================= acciones =================

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int idxProducto = cmbProducto.getSelectedIndex();
            if (idxProducto < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto.",
                        "Dato faltante", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.",
                        "Dato inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.",
                        "Dato inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto producto = listaProductos.get(idxProducto);

            if (cantidad > producto.getStockActual()) {
                JOptionPane.showMessageDialog(this,
                        "Stock insuficiente. Disponible: " + producto.getStockActual(),
                        "Stock insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BigDecimal precio = producto.getPrecioVenta();
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));

            DetalleOrdenVenta detalle = new DetalleOrdenVenta();
            detalle.setIdProducto(producto.getIdProducto());
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precio);
            detalle.setDescuento(BigDecimal.ZERO);
            detalle.setSubtotal(subtotal);
            detalles.add(detalle);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{
                producto.getCodigo() + " - " + producto.getNombre(),
                cantidad,
                precio,
                subtotal
            });

            total = total.add(subtotal);
            lblTotal.setText("Total: Q " + total.toPlainString());

            txtCantidad.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int idxCliente = cmbCliente.getSelectedIndex();
            int idxVendedor = cmbVendedor.getSelectedIndex();

            if (idxCliente < 0 || idxVendedor < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona cliente y vendedor.",
                        "Datos faltantes", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (detalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Agrega al menos un producto a la venta.",
                        "Carrito vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Cliente cliente = listaClientes.get(idxCliente);
            Vendedor vendedor = listaVendedores.get(idxVendedor);

            OrdenVenta orden = new OrdenVenta();
            orden.setFecha(LocalDateTime.now());
            orden.setEstado(EstadoOrdenVenta.REGISTRADA);
            orden.setObservaciones("");
            orden.setTotal(total);
            orden.setIdCliente(cliente.getIdCliente());
            orden.setIdVendedor(vendedor.getIdVendedor());

            // guarda la orden + detalles. YA NO toca el inventario aqui:
            // eso pasa hasta que se confirme el despacho.
            ordenVentaController.guardar(orden, detalles);

            idUltimaOrdenGuardada = orden.getIdOrdenVenta();

            JOptionPane.showMessageDialog(this,
                    "Orden de venta #" + idUltimaOrdenGuardada + " registrada.\n"
                    + "Ahora puedes presionar \"Confirmar Orden\" para generar el despacho.");

            lblEstadoOrden.setText("Orden #" + idUltimaOrdenGuardada + " guardada (REGISTRADA) — pendiente de confirmar");
            btnConfirmarOrden.setEnabled(true);
            btnGuardar.setEnabled(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la venta: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnConfirmarOrdenActionPerformed(java.awt.event.ActionEvent evt) {
        if (idUltimaOrdenGuardada == null) {
            JOptionPane.showMessageDialog(this, "Primero guarda una orden de venta.",
                    "Sin orden", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            despachoController.generarDespacho(idUltimaOrdenGuardada);

            JOptionPane.showMessageDialog(this,
                    "Orden confirmada. Se generó la orden de despacho correspondiente.\n"
                    + "Ve al módulo de Despacho para confirmarla y descontar inventario.");

            lblEstadoOrden.setText("Orden #" + idUltimaOrdenGuardada + " APROBADA — despacho generado");
            btnConfirmarOrden.setEnabled(false);

            detalles.clear();
            total = BigDecimal.ZERO;
            lblTotal.setText("Total: Q 0.00");
            ((DefaultTableModel) jTable1.getModel()).setRowCount(0);
            idUltimaOrdenGuardada = null;
            btnGuardar.setEnabled(true);

            // recargar combos por si acaso (el stock aun no cambia aqui,
            // pero mantiene todo consistente)
            cargarCombos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al confirmar la orden: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnConfirmarOrden;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbCliente;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JComboBox<String> cmbVendedor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblEstadoOrden;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration
}
