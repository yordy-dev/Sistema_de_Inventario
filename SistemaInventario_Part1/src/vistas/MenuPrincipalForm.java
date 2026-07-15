package vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelos.Categoria;
import modelos.DetalleEntrada;
import modelos.DetalleSalida;
import modelos.Entrada;
import modelos.Producto;
import modelos.Salida;
import modelos.Usuario;
import modelos.Inventario;
import patrones.facade.InventarioFacade;
import patrones.proxy.SecurityProxy;
import patrones.observer.IStockObserver;
import patrones.observer.NotificacionService;

public class MenuPrincipalForm extends JFrame implements IStockObserver {
    private InventarioFacade facade;
    private SecurityProxy securityProxy;

    private JPanel pnlContentArea;
    private CardLayout cardLayout;

    private JTextField txtProdBuscar, txtProdCodigo, txtProdNombre, txtProdMarca, txtProdModelo, txtProdPrecio, txtProdStockMinimo;
    private JComboBox<CategoriaComboItem> cboProdCategoria;
    private JTable tblProductos;
    private DefaultTableModel modelTableProductos;
    private Producto productoSeleccionado = null;

    private JTextField txtCatNombre;
    private JTable tblCategorias;
    private DefaultTableModel modelTableCategorias;
    private Categoria categoriaSeleccionada = null;

    private JRadioButton rbtnEntrada, rbtnSalida;
    private JTextField txtMovObservacion, txtMovCantidad, txtMovPrecio;
    private JComboBox<ProductoComboItem> cboMovProductos;
    private JTable tblMovDetalles;
    private DefaultTableModel modelTableMovDetalles;
    private JLabel lblMovTotal;
    private List<TempDetailItem> listaMovDetallesTemp = new ArrayList<>();

    private JTable tblReporte;
    private DefaultTableModel modelTableReporte;
    private JLabel lblRepTotalItems, lblRepBajoStock;

    public MenuPrincipalForm() {
        super("PANEL DE CONTROL - GESTIÓN DE INVENTARIO");
        this.facade = InventarioFacade.getInstancia();
        this.securityProxy = new SecurityProxy();
        
        NotificacionService.getInstancia().agregarObserver(this);

        initComponents();
        
        listarProductos();
        listarCategorias();
        cargarCategoriasCombo();
        cargarProductosCombo();
        listarReporteProductos();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color bgColorHeader = new Color(240, 240, 240);
        Color bgColorSidebar = new Color(240, 240, 240);
        Color bgColorContent = new Color(245, 245, 245);
        Color borderColor = new Color(210, 210, 210);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(bgColorHeader);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, borderColor));
        pnlHeader.setPreferredSize(new Dimension(getWidth(), 60));

        Usuario u = facade.getUsuarioLogueado();
        String nombreUsuario = (u != null) ? u.getNombre() : "Usuario";
        String rol = (u != null && u.getUsuario().equalsIgnoreCase("admin")) ? "ADMINISTRADOR" : "ALMACENERO/OPERADOR";
        JLabel lblUserInfo = new JLabel("  Usuario: " + nombreUsuario + " (" + rol + ")");
        lblUserInfo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUserInfo.setForeground(Color.BLACK);
        pnlHeader.add(lblUserInfo, BorderLayout.WEST);

        JPanel pnlLogout = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pnlLogout.setOpaque(false);
        JButton btnSignOut = new JButton("Cerrar Sesión");
        btnSignOut.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSignOut.setBackground(Color.WHITE);
        btnSignOut.setFocusPainted(false);
        btnSignOut.addActionListener(e -> cerrarSesion());
        pnlLogout.add(btnSignOut);
        pnlHeader.add(pnlLogout, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBackground(bgColorSidebar);
        pnlSidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, borderColor));
        pnlSidebar.setPreferredSize(new Dimension(160, getHeight()));

        pnlSidebar.add(Box.createVerticalStrut(20));

        JButton btnNavInicio = crearBotonNavegacion("Inicio");
        JButton btnNavProductos = crearBotonNavegacion("Productos");
        JButton btnNavCategorias = crearBotonNavegacion("Categorías");
        JButton btnNavMovimientos = crearBotonNavegacion("Movimientos");
        JButton btnNavReportes = crearBotonNavegacion("Stock Actual");

        pnlSidebar.add(btnNavInicio);
        pnlSidebar.add(Box.createVerticalStrut(10));
        pnlSidebar.add(btnNavProductos);
        pnlSidebar.add(Box.createVerticalStrut(10));
        pnlSidebar.add(btnNavCategorias);
        pnlSidebar.add(Box.createVerticalStrut(10));
        pnlSidebar.add(btnNavMovimientos);
        pnlSidebar.add(Box.createVerticalStrut(10));
        pnlSidebar.add(btnNavReportes);

        add(pnlSidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        pnlContentArea = new JPanel(cardLayout);
        pnlContentArea.setBackground(bgColorContent);

        pnlContentArea.add(crearPanelInicio(), "Inicio");
        pnlContentArea.add(crearPanelProductos(), "Productos");
        pnlContentArea.add(crearPanelCategorias(), "Categorías");
        pnlContentArea.add(crearPanelMovimientos(), "Movimientos");
        pnlContentArea.add(crearPanelReportes(), "Stock Actual");

        add(pnlContentArea, BorderLayout.CENTER);

        btnNavInicio.addActionListener(e -> cardLayout.show(pnlContentArea, "Inicio"));
        btnNavProductos.addActionListener(e -> {
            listarProductos();
            cardLayout.show(pnlContentArea, "Productos");
        });
        btnNavCategorias.addActionListener(e -> {
            listarCategorias();
            cardLayout.show(pnlContentArea, "Categorías");
        });
        btnNavMovimientos.addActionListener(e -> {
            cargarProductosCombo();
            cardLayout.show(pnlContentArea, "Movimientos");
        });
        btnNavReportes.addActionListener(e -> {
            listarReporteProductos();
            cardLayout.show(pnlContentArea, "Stock Actual");
        });

        cardLayout.show(pnlContentArea, "Inicio");
    }

    private JButton crearBotonNavegacion(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setMaximumSize(new Dimension(140, 35));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private void cerrarSesion() {
        NotificacionService.getInstancia().removerObserver(this);
        facade.logout();
        new LoginForm().setVisible(true);
        this.dispose();
    }

    private JPanel crearPanelInicio() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(new Color(245, 245, 245));
        
        JLabel lblWelcome = new JLabel("BIENVENIDO AL SISTEMA DE INVENTARIO");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(new Color(44, 62, 80));

        JLabel lblInstruct = new JLabel("Utilice la barra lateral izquierda para navegar entre los módulos.");
        lblInstruct.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInstruct.setForeground(Color.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 10, 10, 10);
        pnl.add(lblWelcome, gbc);
        gbc.gridy = 1;
        pnl.add(lblInstruct, gbc);

        return pnl;
    }

    private JPanel crearPanelProductos() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBackground(new Color(245, 245, 245));
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);

        JLabel lblTitle = new JLabel("PRODUCTOS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(51, 51, 51));
        pnlTop.add(lblTitle, BorderLayout.WEST);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Buscar: "));
        txtProdBuscar = new JTextField(15);
        pnlSearch.add(txtProdBuscar);
        
        JButton btnRefresh = new JButton("REFRESCAR");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnRefresh.setBackground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> {
            txtProdBuscar.setText("");
            listarProductos();
        });
        pnlSearch.add(btnRefresh);
        
        txtProdBuscar.addActionListener(e -> buscarProducto());
        pnlTop.add(pnlSearch, BorderLayout.EAST);
        pnl.add(pnlTop, BorderLayout.NORTH);

        modelTableProductos = new DefaultTableModel(
            new Object[]{"CÓDIGO", "NOMBRE", "PRECIO COMPRA", "PRECIO VENTA", "MARCA", "ID"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblProductos = new JTable(modelTableProductos);
        
        tblProductos.getColumnModel().getColumn(5).setMinWidth(0);
        tblProductos.getColumnModel().getColumn(5).setMaxWidth(0);
        tblProductos.getColumnModel().getColumn(5).setPreferredWidth(0);

        JScrollPane scrollTable = new JScrollPane(tblProductos);
        pnl.add(scrollTable, BorderLayout.CENTER);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        pnlForm.setPreferredSize(new Dimension(300, getHeight()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblFormTitle = new JLabel("Detalle del Producto");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlForm.add(lblFormTitle, gbc);

        gbc.gridy = 1;
        cboProdCategoria = new JComboBox<>();
        pnlForm.add(cboProdCategoria, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2; gbc.gridx = 0;
        pnlForm.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        txtProdCodigo = new JTextField(12);
        pnlForm.add(txtProdCodigo, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        pnlForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtProdNombre = new JTextField(12);
        pnlForm.add(txtProdNombre, gbc);

        gbc.gridy = 4; gbc.gridx = 0;
        pnlForm.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        txtProdMarca = new JTextField(12);
        pnlForm.add(txtProdMarca, gbc);

        gbc.gridy = 5; gbc.gridx = 0;
        pnlForm.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1;
        txtProdModelo = new JTextField(12);
        pnlForm.add(txtProdModelo, gbc);

        gbc.gridy = 6; gbc.gridx = 0;
        pnlForm.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        txtProdPrecio = new JTextField(12);
        pnlForm.add(txtProdPrecio, gbc);

        gbc.gridy = 7; gbc.gridx = 0;
        pnlForm.add(new JLabel("Stock Mínimo:"), gbc);
        gbc.gridx = 1;
        txtProdStockMinimo = new JTextField(12);
        pnlForm.add(txtProdStockMinimo, gbc);

        Usuario u = facade.getUsuarioLogueado();
        boolean esAdmin = (u != null && u.getUsuario().equalsIgnoreCase("admin"));

        txtProdCodigo.setEditable(esAdmin);
        txtProdNombre.setEditable(esAdmin);
        txtProdMarca.setEditable(esAdmin);
        txtProdModelo.setEditable(esAdmin);
        txtProdPrecio.setEditable(esAdmin);
        txtProdStockMinimo.setEditable(esAdmin);
        cboProdCategoria.setEnabled(esAdmin);

        JPanel pnlBtns = new JPanel(new GridLayout(1, 3, 5, 0));
        pnlBtns.setOpaque(false);
        JButton btnAdd = new JButton("Registrar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Eliminar");
        
        btnAdd.setEnabled(esAdmin);
        btnEdit.setEnabled(esAdmin);
        btnDelete.setEnabled(esAdmin);
        
        Color lightBlueBtn = new Color(240, 240, 240);
        btnAdd.setBackground(lightBlueBtn);
        btnEdit.setBackground(lightBlueBtn);
        btnDelete.setBackground(lightBlueBtn);

        pnlBtns.add(btnAdd);
        pnlBtns.add(btnEdit);
        pnlBtns.add(btnDelete);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        pnlForm.add(pnlBtns, gbc);

        gbc.gridy = 9;
        JButton btnClear = new JButton("LIMPIAR");
        btnClear.setBackground(lightBlueBtn);
        pnlForm.add(btnClear, gbc);

        pnl.add(pnlForm, BorderLayout.EAST);

        btnClear.addActionListener(e -> limpiarCamposProducto());
        btnAdd.addActionListener(e -> guardarProducto(false));
        btnEdit.addActionListener(e -> guardarProducto(true));
        btnDelete.addActionListener(e -> eliminarProducto());
        
        tblProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblProductos.getSelectedRow();
                if (row >= 0) {
                    int id = (int) tblProductos.getValueAt(row, 5);
                    productoSeleccionado = facade.buscarProductoPorId(id);
                    mostrarProductoSeleccionado();
                }
            }
        });

        return pnl;
    }

    private JPanel crearPanelCategorias() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBackground(new Color(245, 245, 245));
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("CATEGORÍAS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pnl.add(lblTitle, BorderLayout.NORTH);

        modelTableCategorias = new DefaultTableModel(new Object[]{"ID Categoria", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCategorias = new JTable(modelTableCategorias);
        pnl.add(new JScrollPane(tblCategorias), BorderLayout.CENTER);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createTitledBorder("Datos de Categoría"));
        pnlForm.setPreferredSize(new Dimension(300, getHeight()));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtCatNombre = new JTextField(12);
        pnlForm.add(txtCatNombre, gbc);

        Usuario u = facade.getUsuarioLogueado();
        boolean esAdmin = (u != null && u.getUsuario().equalsIgnoreCase("admin"));
        
        txtCatNombre.setEditable(esAdmin);

        JPanel pnlBtns = new JPanel(new GridLayout(1, 3, 5, 0));
        pnlBtns.setOpaque(false);
        JButton btnAdd = new JButton("Registrar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Eliminar");
        
        btnAdd.setEnabled(esAdmin);
        btnEdit.setEnabled(esAdmin);
        btnDelete.setEnabled(esAdmin);
        
        btnAdd.setBackground(Color.WHITE);
        btnEdit.setBackground(Color.WHITE);
        btnDelete.setBackground(Color.WHITE);

        pnlBtns.add(btnAdd);
        pnlBtns.add(btnEdit);
        pnlBtns.add(btnDelete);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        pnlForm.add(pnlBtns, gbc);

        pnl.add(pnlForm, BorderLayout.EAST);

        tblCategorias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblCategorias.getSelectedRow();
                if (row >= 0) {
                    int id = (int) tblCategorias.getValueAt(row, 0);
                    categoriaSeleccionada = facade.buscarCategoriaPorId(id);
                    txtCatNombre.setText(categoriaSeleccionada.getNombre());
                }
            }
        });

        btnAdd.addActionListener(e -> guardarCategoria(false));
        btnEdit.addActionListener(e -> guardarCategoria(true));
        btnDelete.addActionListener(e -> eliminarCategoria());

        return pnl;
    }

    private JPanel crearPanelMovimientos() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBackground(new Color(245, 245, 245));
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlTop.setOpaque(false);
        rbtnEntrada = new JRadioButton("Entrada (+ Stock)", true);
        rbtnSalida = new JRadioButton("Salida (- Stock)");
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnEntrada);
        group.add(rbtnSalida);
        pnlTop.add(rbtnEntrada);
        pnlTop.add(rbtnSalida);
        pnl.add(pnlTop, BorderLayout.NORTH);

        JPanel pnlCentral = new JPanel(new BorderLayout(5, 5));
        pnlCentral.setOpaque(false);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Datos del Movimiento"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlForm.add(new JLabel("Observación:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 5;
        txtMovObservacion = new JTextField(30);
        pnlForm.add(txtMovObservacion, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        cboMovProductos = new JComboBox<>();
        pnlForm.add(cboMovProductos, gbc);
        cboMovProductos.addActionListener(e -> {
            ProductoComboItem item = (ProductoComboItem) cboMovProductos.getSelectedItem();
            if (item != null) {
                txtMovPrecio.setText(String.valueOf(item.precio));
            }
        });

        gbc.gridx = 2;
        pnlForm.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 3;
        txtMovCantidad = new JTextField(5);
        pnlForm.add(txtMovCantidad, gbc);

        gbc.gridx = 4;
        pnlForm.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 5;
        txtMovPrecio = new JTextField(6);
        pnlForm.add(txtMovPrecio, gbc);

        gbc.gridx = 6;
        JButton btnAddDetail = new JButton("Agregar");
        btnAddDetail.setBackground(Color.WHITE);
        pnlForm.add(btnAddDetail, gbc);
        btnAddDetail.addActionListener(e -> agregarItemMovimientoTemp());

        pnlCentral.add(pnlForm, BorderLayout.NORTH);

        modelTableMovDetalles = new DefaultTableModel(new Object[]{"ID", "Código", "Nombre", "Cantidad", "Precio", "Subtotal"}, 0);
        tblMovDetalles = new JTable(modelTableMovDetalles);
        pnlCentral.add(new JScrollPane(tblMovDetalles), BorderLayout.CENTER);

        pnl.add(pnlCentral, BorderLayout.CENTER);

        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        pnlFooter.setOpaque(false);
        lblMovTotal = new JLabel("TOTAL: S/ 0.00");
        lblMovTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlFooter.add(lblMovTotal);

        JButton btnQuitar = new JButton("Quitar Item");
        btnQuitar.addActionListener(e -> quitarItemMovimientoTemp());
        pnlFooter.add(btnQuitar);

        JButton btnGuardarMov = new JButton("Registrar Movimiento");
        btnGuardarMov.setBackground(new Color(230, 230, 230));
        pnlFooter.add(btnGuardarMov);
        btnGuardarMov.addActionListener(e -> guardarMovimientoFinal());

        pnl.add(pnlFooter, BorderLayout.SOUTH);

        return pnl;
    }

    private JPanel crearPanelReportes() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBackground(new Color(245, 245, 245));
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        JLabel lblTitle = new JLabel("STOCK ACTUAL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pnlHeader.add(lblTitle, BorderLayout.NORTH);
        pnlHeader.add(new JLabel("Nota: Las filas en ROJO indican productos con stock igual o inferior al mínimo."), BorderLayout.SOUTH);
        pnl.add(pnlHeader, BorderLayout.NORTH);

        modelTableReporte = new DefaultTableModel(
            new Object[]{"ID", "Código", "Nombre", "Stock Actual", "Stock Mínimo", "Precio Unitario", "Valorizado", "Alerta"}, 0
        );
        tblReporte = new JTable(modelTableReporte);
        
        tblReporte.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int stock = (int) table.getValueAt(row, 3);
                int stockMin = (int) table.getValueAt(row, 4);
                if (stock <= stockMin) {
                    c.setBackground(new Color(254, 229, 229));
                    c.setForeground(new Color(192, 57, 43));
                } else {
                    c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                    c.setForeground(isSelected ? table.getSelectionForeground() : Color.BLACK);
                }
                return c;
            }
        });
        pnl.add(new JScrollPane(tblReporte), BorderLayout.CENTER);

        JPanel pnlSummary = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        pnlSummary.setOpaque(false);
        lblRepTotalItems = new JLabel("Total Items: 0");
        lblRepBajoStock = new JLabel("Items bajo stock: 0");
        lblRepBajoStock.setForeground(new Color(192, 57, 43));
        pnlSummary.add(lblRepTotalItems);
        pnlSummary.add(lblRepBajoStock);
        
        Usuario u = facade.getUsuarioLogueado();
        boolean esAdmin = (u != null && u.getUsuario().equalsIgnoreCase("admin"));
        JButton btnExportarPDF = new JButton("Exportar a PDF");
        btnExportarPDF.setBackground(Color.WHITE);
        btnExportarPDF.setFocusPainted(false);
        btnExportarPDF.setEnabled(esAdmin);
        btnExportarPDF.addActionListener(e -> {
            try {
                javax.print.PrintService[] services = java.awt.print.PrinterJob.lookupPrintServices();
                javax.print.PrintService pdfService = null;
                for (javax.print.PrintService service : services) {
                    if (service.getName().toLowerCase().contains("pdf")) {
                        pdfService = service;
                        break;
                    }
                }

                if (pdfService == null) {
                    JOptionPane.showMessageDialog(this, 
                        "No se encontró una impresora PDF (como 'Microsoft Print to PDF') activa en el sistema.", 
                        "Impresora PDF No Encontrada", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
                job.setPrintService(pdfService);

                java.io.File file = new java.io.File("c:/Users/yodrf/Downloads/Reporte_Inventario.pdf");
                
                javax.print.attribute.PrintRequestAttributeSet attr = new javax.print.attribute.HashPrintRequestAttributeSet();
                attr.add(new javax.print.attribute.standard.Destination(file.toURI()));

                java.text.MessageFormat header = new java.text.MessageFormat("Reporte de Inventario y Stock Actual");
                java.text.MessageFormat footer = new java.text.MessageFormat("Página {0}");

                java.awt.print.Printable printable = tblReporte.getPrintable(
                    javax.swing.JTable.PrintMode.FIT_WIDTH, header, footer
                );
                job.setPrintable(printable);

                job.print(attr);

                JOptionPane.showMessageDialog(this, 
                    "¡Reporte PDF generado exitosamente en:\n" + file.getAbsolutePath(), 
                    "Exportación Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al generar el PDF: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        pnlSummary.add(btnExportarPDF);

        pnl.add(pnlSummary, BorderLayout.SOUTH);

        return pnl;
    }

    private void cargarCategoriasCombo() {
        cboProdCategoria.removeAllItems();
        List<Categoria> list = facade.listarCategorias();
        for (Categoria c : list) {
            cboProdCategoria.addItem(new CategoriaComboItem(c.getIdCategoria(), c.getNombre()));
        }
    }

    private void cargarProductosCombo() {
        cboMovProductos.removeAllItems();
        List<Producto> list = facade.listarProductos();
        for (Producto p : list) {
            cboMovProductos.addItem(new ProductoComboItem(p.getIdProducto(), p.getCodigo(), p.getNombre(), p.getPrecio()));
        }
    }

    private void listarProductos() {
        modelTableProductos.setRowCount(0);
        List<Producto> list = facade.listarProductos();
        for (Producto p : list) {
            modelTableProductos.addRow(new Object[]{
                p.getCodigo(), p.getNombre(), p.getPrecio(), p.getPrecio() * 1.2, p.getMarca(), p.getIdProducto()
            });
        }
    }

    private void buscarProducto() {
        String filter = txtProdBuscar.getText().trim();
        if (filter.isEmpty()) {
            listarProductos();
            return;
        }
        modelTableProductos.setRowCount(0);
        List<Producto> list = facade.listarProductos();
        for (Producto p : list) {
            if (p.getNombre().toLowerCase().contains(filter.toLowerCase()) || 
                p.getCodigo().toLowerCase().contains(filter.toLowerCase())) {
                modelTableProductos.addRow(new Object[]{
                    p.getCodigo(), p.getNombre(), p.getPrecio(), p.getPrecio() * 1.2, p.getMarca(), p.getIdProducto()
                });
            }
        }
    }

    private void mostrarProductoSeleccionado() {
        if (productoSeleccionado == null) return;
        txtProdCodigo.setText(productoSeleccionado.getCodigo());
        txtProdNombre.setText(productoSeleccionado.getNombre());
        txtProdMarca.setText(productoSeleccionado.getMarca());
        txtProdModelo.setText(productoSeleccionado.getModelo());
        txtProdPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
        txtProdStockMinimo.setText(String.valueOf(productoSeleccionado.getStockMinimo()));

        for (int i = 0; i < cboProdCategoria.getItemCount(); i++) {
            if (cboProdCategoria.getItemAt(i).id == productoSeleccionado.getIdCategoria()) {
                cboProdCategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limpiarCamposProducto() {
        productoSeleccionado = null;
        txtProdCodigo.setText("");
        txtProdNombre.setText("");
        txtProdMarca.setText("");
        txtProdModelo.setText("");
        txtProdPrecio.setText("");
        txtProdStockMinimo.setText("5");
    }

    private void guardarProducto(boolean esEdicion) {
        String codigo = txtProdCodigo.getText().trim();
        String nombre = txtProdNombre.getText().trim();
        String marca = txtProdMarca.getText().trim();
        String modelo = txtProdModelo.getText().trim();
        String precioStr = txtProdPrecio.getText().trim();
        String stockMinStr = txtProdStockMinimo.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || cboProdCategoria.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int stockMin = Integer.parseInt(stockMinStr);
            CategoriaComboItem catItem = (CategoriaComboItem) cboProdCategoria.getSelectedItem();

            Producto p = esEdicion ? productoSeleccionado : patrones.factory.ProductoFactory.crearProducto();
            if (p == null && esEdicion) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            p.setCodigo(codigo);
            p.setNombre(nombre);
            p.setMarca(marca);
            p.setModelo(modelo);
            p.setPrecio(precio);
            p.setStockMinimo(stockMin);
            p.setIdCategoria(catItem.id);

            boolean exito;
            if (esEdicion) {
                exito = securityProxy.editarProducto(p);
            } else {
                exito = securityProxy.registrarProducto(p);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Producto guardado con éxito.");
                listarProductos();
                limpiarCamposProducto();
            } else {
                JOptionPane.showMessageDialog(this, "Error al procesar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio y stock mínimo deben ser numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Acceso Denegado (Patrón Proxy)", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar " + productoSeleccionado.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean exito = securityProxy.eliminarProducto(productoSeleccionado.getIdProducto());
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado.");
                    listarProductos();
                    limpiarCamposProducto();
                }
            } catch (SecurityException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Acceso Denegado (Patrón Proxy)", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listarCategorias() {
        modelTableCategorias.setRowCount(0);
        List<Categoria> list = facade.listarCategorias();
        for (Categoria c : list) {
            modelTableCategorias.addRow(new Object[]{c.getIdCategoria(), c.getNombre()});
        }
        cargarCategoriasCombo();
    }

    private void guardarCategoria(boolean esEdicion) {
        String nombre = txtCatNombre.getText().trim();
        if (nombre.isEmpty()) return;

        try {
            Categoria c = esEdicion ? categoriaSeleccionada : new Categoria();
            if (c == null && esEdicion) return;
            c.setNombre(nombre);

            boolean exito = esEdicion ? securityProxy.editarCategoria(c) : securityProxy.registrarCategoria(c);
            if (exito) {
                listarCategorias();
                txtCatNombre.setText("");
                categoriaSeleccionada = null;
            }
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Acceso Denegado (Patrón Proxy)", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCategoria() {
        if (categoriaSeleccionada == null) return;
        try {
            boolean exito = securityProxy.eliminarCategoria(categoriaSeleccionada.getIdCategoria());
            if (exito) {
                listarCategorias();
                txtCatNombre.setText("");
                categoriaSeleccionada = null;
            }
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Acceso Denegado (Patrón Proxy)", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarItemMovimientoTemp() {
        ProductoComboItem prod = (ProductoComboItem) cboMovProductos.getSelectedItem();
        String cantStr = txtMovCantidad.getText().trim();
        String precioStr = txtMovPrecio.getText().trim();

        if (prod == null || cantStr.isEmpty() || precioStr.isEmpty()) return;

        try {
            int cantidad = Integer.parseInt(cantStr);
            double precio = Double.parseDouble(precioStr);

            if (rbtnSalida.isSelected()) {
                Producto realProduct = facade.buscarProductoPorId(prod.id);
                int stockDisp = realProduct.getStock();
                for (TempDetailItem temp : listaMovDetallesTemp) {
                    if (temp.idProducto == prod.id) stockDisp -= temp.cantidad;
                }
                if (stockDisp < cantidad) {
                    JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + stockDisp, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            listaMovDetallesTemp.add(new TempDetailItem(prod.id, prod.codigo, prod.nombre, cantidad, precio));
            actualizarTablaMovimientoTemp();
            txtMovCantidad.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void quitarItemMovimientoTemp() {
        int selected = tblMovDetalles.getSelectedRow();
        if (selected >= 0) {
            listaMovDetallesTemp.remove(selected);
            actualizarTablaMovimientoTemp();
        }
    }

    private void actualizarTablaMovimientoTemp() {
        modelTableMovDetalles.setRowCount(0);
        double total = 0;
        for (TempDetailItem item : listaMovDetallesTemp) {
            double sub = item.cantidad * item.precio;
            total += sub;
            modelTableMovDetalles.addRow(new Object[]{item.idProducto, item.codigo, item.nombre, item.cantidad, item.precio, sub});
        }
        lblMovTotal.setText(String.format("TOTAL: S/ %.2f", total));
    }

    private void guardarMovimientoFinal() {
        if (listaMovDetallesTemp.isEmpty()) return;
        String obs = txtMovObservacion.getText().trim();
        boolean exito;

        if (rbtnEntrada.isSelected()) {
            List<DetalleEntrada> detalles = new ArrayList<>();
            for (TempDetailItem temp : listaMovDetallesTemp) {
                detalles.add(new DetalleEntrada(0, 0, temp.idProducto, temp.cantidad, temp.precio));
            }
            exito = facade.registrarEntrada(obs, detalles);
        } else {
            List<DetalleSalida> detalles = new ArrayList<>();
            for (TempDetailItem temp : listaMovDetallesTemp) {
                detalles.add(new DetalleSalida(0, 0, temp.idProducto, temp.cantidad, temp.precio));
            }
            exito = facade.registrarSalida(obs, detalles);
        }

        if (exito) {
            JOptionPane.showMessageDialog(this, "Movimiento registrado con éxito.");
            listaMovDetallesTemp.clear();
            txtMovObservacion.setText("");
            actualizarTablaMovimientoTemp();
            listarProductos();
        }
    }

    private void listarReporteProductos() {
        modelTableReporte.setRowCount(0);
        List<Producto> list = facade.listarProductos();
        int totalItems = 0;
        int bajoStock = 0;

        for (Producto p : list) {
            double valor = p.getStock() * p.getPrecio();
            String alerta = (p.getStock() <= p.getStockMinimo()) ? "⚠️ STOCK BAJO" : "OK";
            modelTableReporte.addRow(new Object[]{
                p.getIdProducto(), p.getCodigo(), p.getNombre(), p.getStock(), p.getStockMinimo(), p.getPrecio(), valor, alerta
            });
            totalItems++;
            if (p.getStock() <= p.getStockMinimo()) bajoStock++;
        }
        lblRepTotalItems.setText("Total Items: " + totalItems);
        lblRepBajoStock.setText("Items bajo stock: " + bajoStock);
    }

    @Override
    public void stockActualizado(Producto producto, int cantidadCambio) {
        listarProductos();
        listarReporteProductos();
        System.out.println("[OBSERVER DASHBOARD] Notificado del producto: " + producto.getNombre() + " (Cambio: " + cantidadCambio + ")");
    }

    private static class CategoriaComboItem {
        int id;
        String nombre;
        CategoriaComboItem(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
        @Override
        public String toString() { return nombre; }
    }

    private static class ProductoComboItem {
        int id;
        String codigo;
        String nombre;
        double precio;
        ProductoComboItem(int id, String codigo, String nombre, double precio) {
            this.id = id;
            this.codigo = codigo;
            this.nombre = nombre;
            this.precio = precio;
        }
        @Override
        public String toString() { return "[" + codigo + "] " + nombre; }
    }

    private static class TempDetailItem {
        int idProducto;
        String codigo;
        String nombre;
        int cantidad;
        double precio;
        TempDetailItem(int idProducto, String codigo, String nombre, int cantidad, double precio) {
            this.idProducto = idProducto;
            this.codigo = codigo;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precio = precio;
        }
    }
}
