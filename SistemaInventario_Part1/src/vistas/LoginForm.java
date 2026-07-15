package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import patrones.facade.InventarioFacade;

public class LoginForm extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JLabel lblTitulo, lblUsuario, lblPassword;

    public LoginForm() {
        super("CONTROL DE INVENTARIO - INICIAR SESIÓN");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        Color bgColor = new Color(240, 240, 240);
        Color boxColor = Color.WHITE;
        Color borderColor = new Color(210, 210, 210);
        Color btnBgColor = Color.WHITE;

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        lblTitulo = new JLabel("CONTROL DE INVENTARIO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(51, 51, 51));
        lblTitulo.setBounds(20, 20, 360, 30);
        mainPanel.add(lblTitulo);

        JPanel pnlLoginBox = new JPanel();
        pnlLoginBox.setBackground(boxColor);
        pnlLoginBox.setBorder(BorderFactory.createLineBorder(borderColor));
        pnlLoginBox.setLayout(null);
        pnlLoginBox.setBounds(35, 65, 330, 200);
        mainPanel.add(pnlLoginBox);

        lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsuario.setForeground(new Color(51, 51, 51));
        lblUsuario.setBounds(20, 25, 80, 25);
        pnlLoginBox.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtUsuario.setBounds(110, 25, 190, 28);
        pnlLoginBox.add(txtUsuario);

        lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setForeground(new Color(51, 51, 51));
        lblPassword.setBounds(20, 70, 80, 25);
        pnlLoginBox.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPassword.setBounds(110, 70, 190, 28);
        pnlLoginBox.add(txtPassword);

        btnIngresar = new JButton("INGRESAR");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnIngresar.setBackground(btnBgColor);
        btnIngresar.setForeground(new Color(51, 51, 51));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBounds(110, 120, 190, 35);
        pnlLoginBox.add(btnIngresar);

        btnIngresar.addActionListener(this::btnIngresarActionPerformed);

        txtPassword.addActionListener(this::btnIngresarActionPerformed);
    }

    private void btnIngresarActionPerformed(ActionEvent evt) {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el usuario y la contraseña.", 
                "Campos vacíos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            InventarioFacade facade = InventarioFacade.getInstancia();
            boolean exito = facade.login(usuario, password);

            if (exito) {
                new MenuPrincipalForm().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Usuario o contraseña incorrectos.", 
                    "Error de autenticación", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error de conexión: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
