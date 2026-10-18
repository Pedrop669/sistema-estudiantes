
package gt.edu.umg.sistema.estudiantes.vista;

import gt.edu.umg.sistema.estudiantes.controlador.ClienteController;
import gt.edu.umg.sistema.estudiantes.modelo.Cliente;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmCliente extends javax.swing.JInternalFrame {

    private final ClienteController controller = new ClienteController();

    public FrmCliente() {
        initComponents();
        txtId.setEditable(false);
        refrescarTabla();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNit = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        BtnGrabar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Clientes");

        jTable1.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Nombre", "NIT", "Teléfonos"}
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("ID");
        lblNombre.setText("Nombre");
        lblNit.setText("NIT");
        lblTelefono.setText("Teléfonos (separados por coma)");

        BtnGrabar.setText("Grabar");
        BtnGrabar.addActionListener(this::BtnGrabarActionPerformed);

        javax.swing.GroupLayout layout =
            new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING
            ).addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.LEADING
                ).addComponent(jScrollPane1, 650, 650, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING
                    ).addComponent(jLabel1)
                     .addComponent(lblNombre)
                     .addComponent(lblNit)
                     .addComponent(lblTelefono))
                    .addGap(25)
                    .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING
                    ).addComponent(txtId, 100, 100, 100)
                     .addComponent(txtNombre)
                     .addComponent(txtNit)
                     .addComponent(txtTelefono)
                     .addComponent(BtnGrabar, 110, 110, 110))))
                .addGap(20))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE
                ).addComponent(jLabel1).addComponent(txtId,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12)
                .addGroup(layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE
                ).addComponent(lblNombre).addComponent(txtNombre,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12)
                .addGroup(layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE
                ).addComponent(lblNit).addComponent(txtNit,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12)
                .addGroup(layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE
                ).addComponent(lblTelefono).addComponent(txtTelefono,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addComponent(BtnGrabar)
                .addGap(18)
                .addComponent(jScrollPane1, 220, 220, Short.MAX_VALUE)
                .addGap(20)
        );

        pack();
    }

    private void BtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nombre = txtNombre.getText().trim();
            String nit = txtNit.getText().trim();
            String entrada = txtTelefono.getText().trim();

            if (nombre.isEmpty() || nit.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Ingresa el nombre y el NIT.");
                return;
            }

            List<String> telefonos = Arrays.stream(entrada.split(","))
                .map(String::trim)
                .filter(t -> !t.isEmpty())
                .distinct()
                .collect(Collectors.toList());

            Cliente cliente = new Cliente();
            cliente.setTipoDocumento("NIT");
            cliente.setNumeroDocumento(nit);
            cliente.setNombres(nombre);
            cliente.setApellidos("");
            cliente.setCodigoCliente(nit);
            cliente.setLimiteCredito(BigDecimal.ZERO);
            cliente.setActivo(true);
            cliente.setTelefonos(telefonos);
            cliente.setTelefono(
                telefonos.isEmpty() ? "" : telefonos.get(0)
            );

            controller.guardar(cliente);

            JOptionPane.showMessageDialog(this,
                "Cliente y teléfonos guardados correctamente.");

            txtId.setText("");
            txtNombre.setText("");
            txtNit.setText("");
            txtTelefono.setText("");
            refrescarTabla();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refrescarTabla() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Nombre", "NIT", "Teléfonos"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Cliente> clientes = controller.getClientes();

        for (Cliente c : clientes) {
            String telefonos = String.join(", ", c.getTelefonos());

            if (telefonos.isBlank() && c.getTelefono() != null) {
                telefonos = c.getTelefono();
            }

            model.addRow(new Object[] {
                c.getIdCliente(),
                c.getNombres(),
                c.getNumeroDocumento(),
                telefonos
            });
        }

        jTable1.setModel(model);
    }

    private javax.swing.JButton BtnGrabar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblNit;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
}
