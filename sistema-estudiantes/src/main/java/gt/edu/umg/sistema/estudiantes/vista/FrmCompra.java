package gt.edu.umg.sistema.estudiantes.vista;

import gt.edu.umg.sistema.estudiantes.controlador.CompraController;
import gt.edu.umg.sistema.estudiantes.controlador.ProductoController;
import gt.edu.umg.sistema.estudiantes.controlador.ProveedorController;
import gt.edu.umg.sistema.estudiantes.modelo.Compra;
import gt.edu.umg.sistema.estudiantes.modelo.DetalleCompra;
import gt.edu.umg.sistema.estudiantes.modelo.EstadoCompra;
import gt.edu.umg.sistema.estudiantes.modelo.Producto;
import gt.edu.umg.sistema.estudiantes.modelo.Proveedor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmCompra extends javax.swing.JInternalFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FrmCompra.class.getName());

    ProveedorController proveedorController = new ProveedorController();
    ProductoController productoController = new ProductoController();
    CompraController compraController = new CompraController();

    private List<Proveedor> listaProveedores = new ArrayList<>();
    private List<Producto> listaProductos = new ArrayList<>();

    private List<DetalleCompra> detalles = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

 
    public FrmCompra() {
        initComponents();
        cargarCombos();
        prepararTablaDetalle();
    }


    private void cargarCombos() {
        listaProveedores = proveedorController.getProveedores();
        DefaultComboBoxModel<String> modeloProveedor = new DefaultComboBoxModel<>();
        for (Proveedor p : listaProveedores) {
            modeloProveedor.addElement(p.getNit() + " - " + p.getNombreComercial());
        }
        cmbProveedor.setModel(modeloProveedor);

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblProveedor = new javax.swing.JLabel();
        cmbProveedor = new javax.swing.JComboBox<>();
        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblPrecioUnitario = new javax.swing.JLabel();
        txtPrecioUnitario = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Nueva Compra");

        lblProveedor.setText("Proveedor:");
        lblProducto.setText("Producto:");
        lblCantidad.setText("Cantidad:");
        lblPrecioUnitario.setText("Precio Unit.:");

        btnAgregar.setText("Agregar producto");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Producto", "Cantidad", "Precio", "Subtotal"}
        ));
        jScrollPane1.setViewportView(jTable1);

        lblTotal.setText("Total: Q 0.00");
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14));

        btnGuardar.setText("Guardar Compra");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCantidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPrecioUnitario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal)
                    .addComponent(btnGuardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProveedor)
                    .addComponent(cmbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProducto)
                    .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrecioUnitario)
                    .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotal)
                .addGap(10, 10, 10)
                .addComponent(btnGuardar)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

            BigDecimal precio;
            try {
                precio = new BigDecimal(txtPrecioUnitario.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El precio unitario debe ser numérico.",
                        "Dato inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "El precio unitario debe ser mayor a 0.",
                        "Dato inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto producto = listaProductos.get(idxProducto);
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));

            DetalleCompra detalle = new DetalleCompra();
            detalle.setIdProducto(producto.getIdProducto());
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precio);
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
            txtPrecioUnitario.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int idxProveedor = cmbProveedor.getSelectedIndex();

            if (idxProveedor < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un proveedor.",
                        "Dato faltante", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (detalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Agrega al menos un producto a la compra.",
                        "Carrito vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Proveedor proveedor = listaProveedores.get(idxProveedor);

            Compra compra = new Compra();
            compra.setFecha(LocalDateTime.now());
            compra.setEstado(EstadoCompra.REGISTRADA);
            compra.setTotal(total);
            compra.setIdProveedor(proveedor.getIdProveedor());

            compraController.guardar(compra, detalles);

            JOptionPane.showMessageDialog(this, "Compra registrada con éxito.");

            detalles.clear();
            total = BigDecimal.ZERO;
            lblTotal.setText("Total: Q 0.00");
            ((DefaultTableModel) jTable1.getModel()).setRowCount(0);

            cargarCombos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la compra: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JComboBox<String> cmbProveedor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblPrecioUnitario;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtPrecioUnitario;
    // End of variables declaration//GEN-END:variables
}
