/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import config.config;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Color;

/**
 *
 * @author PC-001
 */
public class listpro extends javax.swing.JFrame {

    private int userId = 0;
    private String keyword = "";
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JSpinner qtySpinner; // Keep for compatibility
    private javax.swing.JTextField qtyField; // New quantity field
    private int id;

    /**
     * Creates new form listpro
     */
    public listpro() {
        initComponents();
        this.id = 0; // Default value
    }

    public listpro(String keyword) {
        this.keyword = keyword == null ? "" : keyword;
        this.id = 0; // Default value
        initComponents();
        wireActions();
        setLocationRelativeTo(null);
        loadTable("");
        setupSearch();
    }

    public listpro(int userId, String keyword) {
        this.keyword = keyword == null ? "" : keyword;
        this.userId = userId;
        this.id = userId; // Set both id fields
        initComponents();
        wireActions();
        setLocationRelativeTo(null);
        loadTable("");
        setupSearch();
    }

    private void wireActions() {
        try {
            if (jButton1 != null) jButton1.addActionListener(e -> addSelectedToCart());
            if (jButton2 != null) jButton2.addActionListener(e -> backToDashboard());
        } catch (Exception ignored) {}
    }
    private void setupSearch() {
        jTextField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadTable(jTextField2.getText().trim());
            }
        });
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showSelectedImage();
        });
        
        // Add quantity selector with custom - + buttons
        javax.swing.JLabel qtyLabel = new javax.swing.JLabel("Quantity:");
        
        // Create custom quantity panel
        javax.swing.JPanel qtyPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        qtyField = new javax.swing.JTextField("1"); // Use class field
        qtyField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyField.setPreferredSize(new java.awt.Dimension(60, 30));
        qtyField.setFont(new java.awt.Font("Arial", 0, 14));
        
        javax.swing.JButton minusBtn = new javax.swing.JButton("-");
        minusBtn.setPreferredSize(new java.awt.Dimension(30, 30));
        minusBtn.setFont(new java.awt.Font("Arial", 0, 16));
        minusBtn.addActionListener(e -> {
            try {
                int current = Integer.parseInt(qtyField.getText());
                if (current > 1) {
                    qtyField.setText(String.valueOf(current - 1));
                }
            } catch (Exception ex) {}
        });
        
        javax.swing.JButton plusBtn = new javax.swing.JButton("+");
        plusBtn.setPreferredSize(new java.awt.Dimension(30, 30));
        plusBtn.setFont(new java.awt.Font("Arial", 0, 16));
        plusBtn.addActionListener(e -> {
            try {
                int current = Integer.parseInt(qtyField.getText());
                if (current < 99) {
                    qtyField.setText(String.valueOf(current + 1));
                }
            } catch (Exception ex) {}
        });
        
        // Assemble quantity panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.add(minusBtn);
        buttonPanel.add(qtyField);
        buttonPanel.add(plusBtn);
        qtyPanel.add(buttonPanel, java.awt.BorderLayout.CENTER);
        
        // Store reference to qty field for later use
        qtySpinner = new javax.swing.JSpinner(); // Dummy to keep existing code working
        
        btnAdd = new javax.swing.JButton("Add to Cart");
        btnAdd.addActionListener(e -> addSelectedToCart());
        btnBack = new javax.swing.JButton("Back");
        btnBack.addActionListener(e -> backToDashboard());
        
        // Add Cart Icon Button
        javax.swing.JButton cartIcon = new javax.swing.JButton("🛒 Cart");
        cartIcon.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
        cartIcon.setBackground(new java.awt.Color(255, 165, 0)); // Orange color
        cartIcon.setForeground(Color.WHITE);
        cartIcon.addActionListener(e -> openCart());
        
        javax.swing.JPanel bottom = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        bottom.add(qtyLabel);
        bottom.add(qtyPanel); // Use custom quantity panel
        bottom.add(btnBack);
        bottom.add(btnAdd);
        bottom.add(cartIcon); // Add cart icon button
        getContentPane().add(bottom, java.awt.BorderLayout.SOUTH);
    }

    private void showSelectedImage() {
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            Object pathObj = jTable1.getValueAt(row, 5); // IMAGE column index
            if (pathObj != null) {
                String imagePath = pathObj.toString();
                System.out.println("Attempting to load image from: " + imagePath);
                try {
                    // Try different path formats
                    String[] possiblePaths = {
                        "/" + imagePath,
                        imagePath,
                        "/image/" + imagePath,
                        "C:/Users/PC-001/Videos/Sapatot/src/" + imagePath
                    };
                    
                    ImageIcon icon = null;
                    for (String path : possiblePaths) {
                        try {
                            if (path.startsWith("C:")) {
                                icon = new ImageIcon(path);
                            } else {
                                java.net.URL url = getClass().getResource(path);
                                if (url != null) {
                                    icon = new ImageIcon(url);
                                }
                            }
                            if (icon != null && icon.getIconWidth() > 0) {
                                break;
                            }
                        } catch (Exception e) {
                            // Continue to next path
                        }
                    }
                    
                    if (icon != null && icon.getIconWidth() > 0) {
                        Image img = icon.getImage().getScaledInstance(360, 360, Image.SCALE_SMOOTH);
                        jLabel1.setIcon(new ImageIcon(img));
                    } else {
                        System.err.println("Image not found at any path for: " + imagePath);
                        jLabel1.setIcon(null);
                    }
                } catch (Exception e) {
                    System.err.println("Error loading image: " + e.getMessage());
                    jLabel1.setIcon(null);
                }
            } else {
                jLabel1.setIcon(null);
            }
        } else {
            jLabel1.setIcon(null);
        }
    }

    private String esc(String s) {
        return s.replace("'", "''").toLowerCase();
    }

    private void loadTable(String extra) {
        String base = "SELECT item_name AS 'ITEM', color AS 'COLOR', size AS 'SIZE', qty AS 'QTY', category AS 'CATEGORY', image_path AS 'IMAGE', price AS 'PRICE' " +
                "FROM tbl_inventory WHERE (lower(item_name) LIKE '%" + esc(keyword) + "%' OR lower(category) LIKE '%" + esc(keyword) + "%')";
        if (extra != null && !extra.isEmpty()) {
            base += " AND (lower(item_name) LIKE '%" + esc(extra) + "%' OR lower(color) LIKE '%" + esc(extra) + "%' OR lower(category) LIKE '%" + esc(extra) + "%')";
        }
        new config().displayData(base, jTable1);
    }

    private void addSelectedToCart() {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a product first.");
            return;
        }
        String item = String.valueOf(jTable1.getValueAt(row, 0));
        int qty = 1;
        try {
            // Get quantity from our custom field
            String qtyText = qtyField.getText();
            System.out.println("Quantity field text: '" + qtyText + "'");
            qty = Integer.parseInt(qtyText);
            if (qty < 1) qty = 1;
            if (qty > 99) qty = 99;
            System.out.println("Final quantity: " + qty);
        } catch (Exception e) {
            System.err.println("Error parsing quantity: " + e.getMessage());
            qty = 1;
        }
        double price = 0.0;
        try {
            // Get actual price from table (column index 6 for PRICE)
            Object priceObj = jTable1.getValueAt(row, 6);
            if (priceObj != null) {
                price = Double.parseDouble(priceObj.toString());
            }
        } catch (Exception e) {
            System.err.println("Error getting price: " + e.getMessage());
            price = 0.0;
        }
        
        CartStore.add(item, price, qty);
        javax.swing.JOptionPane.showMessageDialog(this, qty + " x " + item + " added to cart!");
        // Don't open cart automatically - stay on product page
        // new addtocart(this.id).setVisible(true);
        // dispose();
    }
    
    // Open cart without closing product page
    private void openCart() {
        addtocart cart = new addtocart(this.id);
        cart.setVisible(true);
        // Don't dispose - keep product page open
    }

    private void backToDashboard() {
        if (userId > 0) {
            new user_dashboard(userId).setVisible(true);
        } else {
            new user_dashboard().setVisible(true);
        }
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 390, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ITEM", "COLOR", "SIZE", "QTY"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 500, 520));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 380, 510));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Search:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("ADD TO CART");
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 130, 30));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("BACK");
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 80, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(listpro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(listpro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(listpro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(listpro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new listpro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
