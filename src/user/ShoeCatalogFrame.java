package user;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import static sun.security.jgss.GSSUtil.login;


/**
 * User catalog frame: purple sidebar, Filter, Search, table of shoes.
 * Click row to open checkout.
 */
public class ShoeCatalogFrame extends javax.swing.JFrame {

    private final int userId;
    private final String category;
    private List<ShoeData.ShoeItem> currentShoes;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterCombo;
    private JTextField searchField;

    public ShoeCatalogFrame(int userId, String category) {
        this.userId = userId;
        this.category = category == null ? "all" : category;
        this.currentShoes = ShoeData.getShoesByCategory(this.category);
        buildUI();
        loadTable();
    }

    public int getUserId() {
        return userId;
    }

    private void buildUI() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sapatot - Shoes");
        getContentPane().setLayout(new BorderLayout());

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(java.awt.Color.DARK_GRAY);

        JPanel sidebar = new JPanel();
        sidebar.setBackground(ShoeData.getSidebarColor());
        sidebar.setPreferredSize(new Dimension(180, 600));
        sidebar.setLayout(new GridLayout(0, 1, 0, 8));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        Font sideFont = new Font("Arial", Font.PLAIN, 16);
        addSideLabel(sidebar, "Dashboard", sideFont, () -> openCatalog("all"));
        addSideLabel(sidebar, "Kids", sideFont, () -> openCatalog("kids"));
        addSideLabel(sidebar, "Men", sideFont, () -> openCatalog("men"));
        addSideLabel(sidebar, "Sale", sideFont, () -> openCatalog("sale"));
        addSideLabel(sidebar, "SNKRS", sideFont, () -> openCatalog("snkrs"));
        addSideLabel(sidebar, "Profile", sideFont, this::openProfile);
        addSideLabel(sidebar, "Logout", sideFont, this::doLogout);

        main.add(sidebar, BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout(0, 10));
        right.setBackground(java.awt.Color.DARK_GRAY);
        right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        topBar.setBackground(java.awt.Color.DARK_GRAY);
        JLabel filterLbl = new JLabel("Filter:");
        filterLbl.setForeground(java.awt.Color.WHITE);
        filterLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        topBar.add(filterLbl);
        filterCombo = new JComboBox<>(new String[] { "All Shoes", "Kids", "Men", "Sale", "SNKRS" });
        filterCombo.setPreferredSize(new Dimension(120, 28));
        filterCombo.addActionListener(e -> applyFilterAndSearch());
        topBar.add(filterCombo);
        JLabel searchLbl = new JLabel("Search:");
        searchLbl.setForeground(java.awt.Color.WHITE);
        searchLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        topBar.add(searchLbl);
        searchField = new JTextField(25);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applyFilterAndSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applyFilterAndSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applyFilterAndSearch(); }
        });
        topBar.add(searchField);
        right.add(topBar, BorderLayout.NORTH);

        String[] cols = { "ID", "Photo", "Shoe Name", "Brand", "Size", "Price (₱)" };
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public Class<?> getColumnClass(int c) {
                if (c == 1) return ImageIcon.class;
                if (c == 5) return Double.class;
                return String.class;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(56);
        table.setSelectionBackground(new java.awt.Color(100, 100, 150));
        table.setSelectionForeground(java.awt.Color.WHITE);
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);
        TableColumn photoCol = table.getColumnModel().getColumn(1);
        photoCol.setPreferredWidth(70);
        photoCol.setMinWidth(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(90);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int modelRow = table.convertRowIndexToModel(row);
                    if (modelRow < currentShoes.size()) {
                        openCheckout(currentShoes.get(modelRow));
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(800, 400));
        right.add(scroll, BorderLayout.CENTER);
        main.add(right, BorderLayout.CENTER);
        getContentPane().add(main, BorderLayout.CENTER);

        setFilterToCategory(this.category);
        pack();
        setLocationRelativeTo(null);
    }

    private void addSideLabel(JPanel sidebar, String text, Font font, Runnable action) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(java.awt.Color.WHITE);
        lbl.setFont(font);
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.run();
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lbl.setFont(font.deriveFont(Font.BOLD));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lbl.setFont(font);
            }
        });
        sidebar.add(lbl);
    }

    private void setFilterToCategory(String cat) {
        if ("kids".equalsIgnoreCase(cat)) filterCombo.setSelectedIndex(1);
        else if ("men".equalsIgnoreCase(cat)) filterCombo.setSelectedIndex(2);
        else if ("sale".equalsIgnoreCase(cat)) filterCombo.setSelectedIndex(3);
        else if ("snkrs".equalsIgnoreCase(cat)) filterCombo.setSelectedIndex(4);
        else filterCombo.setSelectedIndex(0);
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for (ShoeData.ShoeItem s : currentShoes) {
            ImageIcon icon = null;
            if (s.imagePath != null && !s.imagePath.isEmpty()) {
                try {
                    java.io.File f = new java.io.File(s.imagePath);
                    if (f.exists()) {
                        icon = new ImageIcon(new ImageIcon(s.imagePath).getImage().getScaledInstance(48, 48, java.awt.Image.SCALE_SMOOTH));
                    }
                } catch (Exception ignored) { }
            }
            if (icon == null) {
                icon = new ImageIcon();
            }
            tableModel.addRow(new Object[] { s.id, icon, s.name, s.brand, s.size, s.price });
        }
    }

    private void applyFilterAndSearch() {
        int idx = filterCombo.getSelectedIndex();
        String cat = idx == 0 ? "all" : (idx == 1 ? "kids" : (idx == 2 ? "men" : (idx == 3 ? "sale" : "snkrs")));
        currentShoes = ShoeData.getShoesByCategory(cat);
        loadTable();
        String q = searchField.getText().trim();
        if (!q.isEmpty()) {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            table.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(q)));
        } else {
            table.setRowSorter(null);
        }
    }

    private void openCheckout(ShoeData.ShoeItem item) {
        GlobalData.productName = item.name;
        GlobalData.productPrice = item.price;
        GlobalData.productImagePath = item.imagePath;
        checkout ch = new checkout();
        ch.setVisible(true);
        dispose();
    }

    private void openCatalog(String cat) {
        ShoeCatalogFrame f = new ShoeCatalogFrame(userId, cat);
        f.setVisible(true);
        dispose();
    }

    private void openProfile() {
        profile p = new profile(userId);
        p.setVisible(true);
        dispose();
    }

   private void doLogout() {
    int a = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to log out?", "Logout", 
            javax.swing.JOptionPane.YES_NO_OPTION);
            
    if (a == javax.swing.JOptionPane.YES_OPTION) {
        try {
            // Dili na kini mag-error basta naa na ang login.java sa 'user' package
            login loginPage = new login(); 
            loginPage.setLocationRelativeTo(null);
            loginPage.setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            System.out.println("Error opening login: " + ex.getMessage());
        }
    }

    }
}
