package gt.edu.umg.sistema.estudiantes.vista;

import gt.edu.umg.sistema.estudiantes.controlador.DespachoController;
import gt.edu.umg.sistema.estudiantes.controlador.FacturaController;
import gt.edu.umg.sistema.estudiantes.controlador.OrdenVentaController;
import gt.edu.umg.sistema.estudiantes.modelo.FacturaVenta;
import gt.edu.umg.sistema.estudiantes.modelo.OrdenDespacho;
import gt.edu.umg.sistema.estudiantes.modelo.OrdenVenta;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario de Despachos. Tiene dos partes:
 *
 * 1. Tabla de despachos PENDIENTES con el boton "Confirmar Despacho": al
 *    confirmar, se rebaja el stock de los productos del detalle y se genera
 *    la factura automaticamente (toda la logica vive en
 *    DespachoController/DespachoDAOImpl.confirmarDespacho).
 *
 * 2. Tabla de consulta que muestra la relacion completa
 *    Orden de Venta -> Despacho -> Factura, para poder comprobar el flujo
 *    de principio a fin (requisito de la entrega final).
 *
 * @author Jenny
 */
public class FrmDespacho extends javax.swing.JInternalFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FrmDespacho.class.getName());

    DespachoController despachoController = new DespachoController();
    OrdenVentaController ordenVentaController = new OrdenVentaController();
    FacturaController facturaController = new FacturaController();

    /**
     * Creates new form FrmDespacho
     */
    public FrmDespacho() {
        initComponents();
        cargarPendientes();
        cargarConsulta();
    }

    // ================= carga de datos =================

    private void cargarPendientes() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Despacho");
        model.addColumn("ID Orden Venta");
        model.addColumn("Fecha");
        model.addColumn("Estado");
        model.addColumn("Observaciones");

        List<OrdenDespacho> pendientes = despachoController.getPendientes();
        for (OrdenDespacho d : pendientes) {
            model.addRow(new Object[]{
                d.getIdOrdenDespacho(),
                d.getIdOrdenVenta(),
                d.getFecha(),
                d.getEstado(),
                d.getObservaciones()
            });
        }
        jTable1.setModel(model);
    }

    private void cargarConsulta() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Orden");
        model.addColumn("Estado Orden");
        model.addColumn("ID Despacho");
        model.addColumn("Estado Despacho");
        model.addColumn("ID Factura");
        model.addColumn("Total Factura");

        List<OrdenDespacho> despachos = despachoController.getDespachos();
        for (OrdenDespacho d : despachos) {
            long idOrdenVenta = d.getIdOrdenVenta();

            OrdenVenta ov = ordenVentaController.getOrden((int) idOrdenVenta);
            FacturaVenta factura = facturaController.getFacturaPorOrdenVenta(idOrdenVenta);

            model.addRow(new Object[]{
                idOrdenVenta,
                ov != null ? ov.getEstado() : "?",
                d.getIdOrdenDespacho(),
                d.getEstado(),
                factura != null ? factura.getIdFactura() : "— sin facturar —",
                factura != null ? factura.getTotal() : ""
            });
        }
        jTable2.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPendientes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnConfirmarDespacho = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        lblConsulta = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Despachos");

        lblPendientes.setText("Despachos pendientes");
        lblPendientes.setFont(new java.awt.Font("Tahoma", 1, 13));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID Despacho", "ID Orden Venta", "Fecha", "Estado", "Observaciones"}
        ));
        jScrollPane1.setViewportView(jTable1);

        btnConfirmarDespacho.setText("Confirmar Despacho (rebaja inventario)");
        btnConfirmarDespacho.addActionListener(this::btnConfirmarDespachoActionPerformed);

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(this::btnRefrescarActionPerformed);

        lblConsulta.setText("Consulta: Orden → Despacho → Factura");
        lblConsulta.setFont(new java.awt.Font("Tahoma", 1, 13));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID Orden", "Estado Orden", "ID Despacho", "Estado Despacho", "ID Factura", "Total Factura"}
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPendientes)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConfirmarDespacho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefrescar))
                    .addComponent(lblConsulta)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblPendientes)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmarDespacho)
                    .addComponent(btnRefrescar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblConsulta)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ================= acciones =================

    private void btnConfirmarDespachoActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = jTable1.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un despacho de la tabla.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long idOrdenDespacho = Long.parseLong(jTable1.getValueAt(fila, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Confirmar este despacho?\nEsto rebajará el inventario y generará la factura.",
                "Confirmar despacho", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            despachoController.confirmarDespacho(idOrdenDespacho);

            JOptionPane.showMessageDialog(this,
                    "Despacho confirmado.\nSe rebajó el inventario y se generó la factura.");

            cargarPendientes();
            cargarConsulta();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al confirmar el despacho: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarPendientes();
        cargarConsulta();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmarDespacho;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblConsulta;
    private javax.swing.JLabel lblPendientes;
    // End of variables declaration//GEN-END:variables
}
