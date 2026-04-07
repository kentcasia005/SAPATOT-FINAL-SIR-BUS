/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import config.config;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author PC-001
 */
public class shoe_inventory extends javax.swing.JFrame {

    private JComboBox<String> cbCategory;
    private JButton btnChooseImage;
    private String selectedImageResourcePath;
    private javax.swing.JTextField txt_price;

    /**
     * Creates new form shoe_inventory
     */
    public shoe_inventory() {
        if (!config.isAdminLoggedIn()) {
            try {
                javax.swing.JOptionPane.showMessageDialog(null, "User Not Found", "Access Denied", javax.swing.JOptionPane.WARNING_MESSAGE);
                Class<?> loginClass = Class.forName("login");
                Object loginInstance = loginClass.getDeclaredConstructor().newInstance();
                loginClass.getMethod("setLocationRelativeTo", java.awt.Component.class).invoke(loginInstance, (java.awt.Component)null);
                loginClass.getMethod("setVisible", boolean.class).invoke(loginInstance, true);
            } catch (Exception e) {
                System.out.println("Login Redirect Error: " + e.getMessage());
            }
            dispose();
            return;
        }
        initComponents();
        setupExtraControls();
        clearDefaultText();
        ensureInventorySchema();
    displayInventory();
    }

    private void clearDefaultText() {
        // Clear any default text from text fields
        if (txt_item != null) txt_item.setText("");
        if (txt_color != null) txt_color.setText("");
        if (txt_size != null) txt_size.setText("");
        if (txt_price != null) txt_price.setText("");
        if (txt_qty != null) txt_qty.setText("");
    }
public void displayInventory() {
    config conf = new config();
    // SQL Query para makuha tanan sapatos
    String sql = "SELECT item_id AS 'ID', item_name AS 'ITEM', color AS 'COLOR', size AS 'SIZE', price AS 'PRICE', qty AS 'QUANTITY', category AS 'CATEGORY', image_path AS 'IMAGE' FROM tbl_inventory";
    conf.displayData(sql, jTable2);
}

private void setupExtraControls() {
    jLabel10.setText("PRICE:");
    jLabel10.setFont(new java.awt.Font("Arial", 1, 18));
    jLabel10.setForeground(new java.awt.Color(255, 255, 255));
    jLabel10.setVisible(true);
    jLabel15.setVisible(false);

    // Add price text field
    txt_price = new javax.swing.JTextField();
    txt_price.setFont(new java.awt.Font("Arial", 0, 14));
    txt_price.setText(""); // Empty so you can input your own price
    jPanel1.add(txt_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, 200, -1));

    cbCategory = new JComboBox<>(new String[]{
            "user_dashboard","kids_shoes","men_shoes","snkrs_shoes","sale_shoes"
    });
    jPanel1.add(cbCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 450, 200, 28));

    jLabel15.setFont(new java.awt.Font("Arial", 1, 18));
    jLabel15.setForeground(new java.awt.Color(255, 255, 255));
    jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel15.setText("CATEGORY:");
    jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 450, 110, -1));

    btnChooseImage = new JButton("Choose Image");
    btnChooseImage.addActionListener(e -> chooseImage());
    jPanel1.add(btnChooseImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 250, 200, 30));
}

private void chooseImage() {
    try {
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File srcFile = chooser.getSelectedFile();
            String fileName = System.currentTimeMillis() + "_" + srcFile.getName();
            String projectDir = System.getProperty("user.dir");
            File destDir = new File(projectDir + File.separator + "src" + File.separator + "image");
            destDir.mkdirs();
            File destFile = new File(destDir, fileName);
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            selectedImageResourcePath = "/image/" + fileName;
            ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            jLabel3.setIcon(new ImageIcon(img));
        }
    } catch (Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Choose Image Failed: " + ex.getMessage());
    }
}

private void ensureInventorySchema() {
    try (Connection conn = config.connectDB(); Statement st = conn.createStatement()) {
        try { st.executeUpdate("ALTER TABLE tbl_inventory ADD COLUMN category TEXT"); } catch (Exception ignored) {}
        try { st.executeUpdate("ALTER TABLE tbl_inventory ADD COLUMN image_path TEXT"); } catch (Exception ignored) {}
        try { st.executeUpdate("ALTER TABLE tbl_inventory ADD COLUMN price REAL DEFAULT 0.0"); } catch (Exception ignored) {}
    } catch (Exception ignored) {}
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txt_qty = new javax.swing.JTextField();
        txt_item = new javax.swing.JTextField();
        txt_color = new javax.swing.JTextField();
        txt_size = new javax.swing.JTextField();
        txt_search = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_qty1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SHOE INVENTORY");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 320, 70));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TOTAL USERS");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 130, 20));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("USER MANAGEMENT");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
        });
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 190, 20));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SHOE INVENTORY");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 190, 20));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("SALES LOGS");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 130, 20));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 230, 460));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel8.setText("ADMIN DASHBOARD");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 320, 70));

        jLabel2.setBackground(new java.awt.Color(255, 0, 0));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LOGOUT");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.setOpaque(true);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 100, 110, 30));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1300, 10));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 140));

        txt_qty.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_qtyActionPerformed(evt);
            }
        });
        txt_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_qtyKeyReleased(evt);
            }
        });
        jPanel1.add(txt_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 410, 200, -1));

        txt_item.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_itemActionPerformed(evt);
            }
        });
        jPanel1.add(txt_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 200, -1));

        txt_color.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_colorActionPerformed(evt);
            }
        });
        jPanel1.add(txt_color, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 290, 200, -1));

        txt_size.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_size.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sizeActionPerformed(evt);
            }
        });
        jPanel1.add(txt_size, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 330, 200, -1));

        txt_search.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_search.setForeground(new java.awt.Color(102, 102, 102));
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });
        jPanel1.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 450, 230, 30));

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("ADD ITEM");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 100, -1));

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("DELETE ITEM");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 210, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("SIZE:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 330, 90, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("ITEM:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, 100, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "ITEM", "COLOR", "SIZE", "PRICE", "QTY"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 490, 970, 270));

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setOpaque(true);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 290, 200, 150));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("CATEGORY:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 450, 120, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("COLOR:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, 90, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("PRICE:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 120, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("QUANTITY:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, 120, -1));

        txt_qty1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_qty1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_qty1ActionPerformed(evt);
            }
        });
        txt_qty1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_qty1KeyReleased(evt);
            }
        });
        // Remove txt_qty1 from panel to avoid conflict with price field
        // jPanel1.add(txt_qty1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, 200, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 890));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // 1. Paghimo og instance sa imong totalusers frame
        totalusers usersPage = new totalusers();

        // 2. I-center ang frame para nindot tan-awon
        usersPage.setLocationRelativeTo(null);

        // 3. I-show ang totalusers frame
        usersPage.setVisible(true);

        // 4. I-close o i-hide ang current dashboard (Optional)
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        jLabel4.setForeground(java.awt.Color.WHITE);
        jLabel4.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        jLabel5.setForeground(java.awt.Color.WHITE);
        jLabel5.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        jLabel6.setForeground(java.awt.Color.WHITE);
        jLabel6.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        jLabel7.setForeground(java.awt.Color.WHITE);
        jLabel7.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
       try {
            config.clearAdminSession();
            Class<?> loginClass = Class.forName("login");
            Object loginInstance = loginClass.getDeclaredConstructor().newInstance();
            loginClass.getMethod("setLocationRelativeTo", java.awt.Component.class).invoke(loginInstance, (java.awt.Component)null);
            loginClass.getMethod("setVisible", boolean.class).invoke(loginInstance, true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(shoe_inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
          // 1. Paghimo og instance sa imong admin dashboard
    admin_dashboard dash = new admin_dashboard();
    
    // 2. I-center ang dashboard
    dash.setLocationRelativeTo(null);
    
    // 3. I-pakita ang dashboard
    dash.setVisible(true);
    
    // 4. I-close kining totalusers nga frame
    this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
       // 1. Paghimo og object para sa imong saleslogs frame
    saleslogs sales = new saleslogs();
    
    // 2. I-pakita ang sales logs frame
    sales.setVisible(true);
    
    // 3. I-refresh ang data sa table para naay sulod inig abli
    sales.displaySales();
    
    // 4. I-close o i-hide ang current frame (Admin Dashboard)
    this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void txt_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_qtyActionPerformed
        txt_qty.setText("");
    }//GEN-LAST:event_txt_qtyActionPerformed

    private void txt_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qtyKeyReleased
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
        jButton1ActionPerformed(null);
    }//GEN-LAST:event_txt_qtyKeyReleased

    private void txt_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_itemActionPerformed
        txt_item.setText("");
    }//GEN-LAST:event_txt_itemActionPerformed

    private void txt_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_colorActionPerformed
        txt_color.setText("");
    }//GEN-LAST:event_txt_colorActionPerformed

    private void txt_sizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sizeActionPerformed
        txt_size.setText("");
    }//GEN-LAST:event_txt_sizeActionPerformed

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // Siguruha nga jTable1 ang gigamit dili jTable2
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    }//GEN-LAST:event_txt_searchActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        // Gigamit nato ang jTable1 kay mao nay name sa imong variable sa ubos
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTable2.setRowSorter(sorter);

        String query = txt_search.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }//GEN-LAST:event_txt_searchKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        config conf = new config();
        String item = txt_item.getText().trim();
        String color = txt_color.getText().trim();
        String size = txt_size.getText().trim();
        String price = txt_price.getText().trim();
        String qty = txt_qty.getText().trim();

        // Debug output
        System.out.println("=== ADD ITEM DEBUG ===");
        System.out.println("Item: '" + item + "' (empty: " + item.isEmpty() + ")");
        System.out.println("Color: '" + color + "' (empty: " + color.isEmpty() + ")");
        System.out.println("Size: '" + size + "' (empty: " + size.isEmpty() + ")");
        System.out.println("Price: '" + price + "' (empty: " + price.isEmpty() + ")");
        System.out.println("Qty: '" + qty + "' (empty: " + qty.isEmpty() + ")");

        // Simplified validation - just check if fields are empty
        if (item.isEmpty() || color.isEmpty() || size.isEmpty() || price.isEmpty() || qty.isEmpty()) {
            System.out.println("VALIDATION FAILED: Some fields are empty");
            javax.swing.JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            if (item.isEmpty()) txt_item.requestFocus();
            else if (color.isEmpty()) txt_color.requestFocus();
            else if (size.isEmpty()) txt_size.requestFocus();
            else if (price.isEmpty()) txt_price.requestFocus();
            else txt_qty.requestFocus();
        } else {
            try {
                String category = cbCategory != null ? String.valueOf(cbCategory.getSelectedItem()) : "";
                String imgPath = selectedImageResourcePath != null ? selectedImageResourcePath : "";
                String sql = "INSERT INTO tbl_inventory (item_name, color, size, price, qty, category, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                System.out.println("Attempting to add product...");
                System.out.println("SQL: " + sql);
                System.out.println("Values: '" + item + "', '" + color + "', '" + size + "', '" + price + "', '" + qty + "', '" + category + "', '" + imgPath + "'");
                
                conf.addRecord(sql, item, color, size, price, qty, category, imgPath);
                System.out.println("Product added successfully!");

                // Refresh table
                conf.displayData("SELECT item_id AS 'ID', item_name AS 'ITEM', color AS 'COLOR', size AS 'SIZE', price AS 'PRICE', qty AS 'QUANTITY', category AS 'CATEGORY', image_path AS 'IMAGE' FROM tbl_inventory", jTable2);

                // I-blanko ang boxes human mag-add
                txt_item.setText("");
                txt_color.setText("");
                txt_size.setText("");
                txt_price.setText("");
                txt_qty.setText("");
                if (cbCategory != null) cbCategory.setSelectedIndex(0);
                selectedImageResourcePath = null;
                jLabel3.setIcon(null);
                
                javax.swing.JOptionPane.showMessageDialog(this, "Product added successfully!");
            } catch (Exception e) {
                System.err.println("ERROR adding product: " + e.getMessage());
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this, "Error adding product: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = jTable2.getSelectedRow(); // 1. Kuhaon ang gi-select nga row

        if (row == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an item from the menu/table first.");
        } else {
            // 2. Pangutan-on ang user kung sigurado ba gyud siya
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this?", "Warning", javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                config conf = new config();

                // 3. Kuhaon ang ID gikan sa column 0 (item_id)
                String id = jTable2.getValueAt(row, 0).toString();

                // 4. I-execute ang delete record
                String sql = "DELETE FROM tbl_inventory WHERE item_id = ?";
                conf.deleteRecord(sql, id);

                // 5. I-refresh ang table para makita nga wala na ang item
                conf.displayData("SELECT item_id AS 'ID', item_name AS 'Item', color AS 'Color', size AS 'Size', qty AS 'Qty', category AS 'Category', image_path AS 'Image' FROM tbl_inventory", jTable2);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_qty1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_qty1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_qty1ActionPerformed

    private void txt_qty1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qty1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_qty1KeyReleased

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
            java.util.logging.Logger.getLogger(shoe_inventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(shoe_inventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(shoe_inventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(shoe_inventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new shoe_inventory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txt_color;
    private javax.swing.JTextField txt_item;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_qty1;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_size;
    // End of variables declaration//GEN-END:variables

   
    }
