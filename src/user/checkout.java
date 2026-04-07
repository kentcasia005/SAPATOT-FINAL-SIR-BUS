/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import config.config;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import user.CartStore;

/**
 *
 * @author PC-001
 */
public class checkout extends javax.swing.JFrame {

    private int id;

    /**
     * Creates new form checkout
     */
   public checkout() {
    initComponents();
    this.setLocationRelativeTo(null); // Para mo-center ang window
    
    
    // Set default values for order summary
    lbl_item.setText("Order Summary");
    lbl_price.setText("");
    jLabel24.setText("₱0.00");
    jLabel20.setText("₱0.00");
    
    // Clear image display
    image_label.setIcon(null);
}
    
    public checkout(int userId) {
        this.id = userId;
        System.out.println("DEBUG: Checkout constructor received user ID: " + userId);
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Set default values for order summary
        lbl_item.setText("Order Summary");
        lbl_price.setText("");
        jLabel24.setText("₱0.00");
        jLabel20.setText("₱0.00");
        
        // Clear image display
        image_label.setIcon(null);
    }
    
    public void setOrderData(double subtotal, double total) {
        // Display cart items in table (this will also calculate and update totals)
        displayCartItems();
        
        // Clear single product display (since we're showing cart)
        lbl_item.setText("Order Summary");
        lbl_price.setText("");
        image_label.setIcon(null);
        
        // Add window focus listener to refresh when window gains focus
        this.addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                refreshOrderData();
            }
        });
    }
    
    private void displayCartItems() {
        try {
            
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing data
            
            // Check if cart has items
            if (CartStore.items == null || CartStore.items.isEmpty()) {
                ImageIcon defaultIcon = createDefaultIcon();
                model.addRow(new Object[]{defaultIcon, "No items in cart", "₱0.00"});
                return;
            }
            
            double grandTotal = 0.0;
            
            // Add cart items to table with 3 columns including images
            for (CartStore.CartItem item : CartStore.items) {
                try {
                    String itemDisplay = item.item + " (Qty: " + item.qty + ")";
                    String totalDisplay = "₱" + String.format("%.2f", item.price * item.qty);
                    
                    // Try to get image for this product
                    javax.swing.ImageIcon productIcon = getProductImage(item.item);
                    if (productIcon == null) {
                        productIcon = createDefaultIcon();
                    }
                    
                    // Add row with: IMAGE, PRODUCT (QUANTITY), TOTAL PRICE
                    model.addRow(new Object[]{productIcon, itemDisplay, totalDisplay});
                    
                    // Add to grand total
                    grandTotal += (item.price * item.qty);
                    
                } catch (Exception itemError) {
                    System.err.println("Error adding item " + item.item + ": " + itemError.getMessage());
                    // Add item without image if there's an error
                    ImageIcon defaultIcon = createDefaultIcon();
                    model.addRow(new Object[]{defaultIcon, item.item + " (Qty: " + item.qty + ")", "₱" + String.format("%.2f", item.price * item.qty)});
                    grandTotal += (item.price * item.qty);
                }
            }
            
            // Update the total labels
            jLabel24.setText("₱" + String.format("%.2f", grandTotal));
            jLabel20.setText("₱" + String.format("%.2f", grandTotal));
            
        } catch (Exception e) {
            System.err.println("Error displaying cart items: " + e.getMessage());
            e.printStackTrace();
            // Show error message in table
            try {
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                ImageIcon defaultIcon = createDefaultIcon();
                model.addRow(new Object[]{defaultIcon, "Error loading cart items", "₱0.00"});
            } catch (Exception ex) {
                System.err.println("Critical error: " + ex.getMessage());
            }
        }
    }
    
    private javax.swing.ImageIcon getProductImage(String productName) {
        System.out.println("=== Trying to load image for: " + productName + " ===");
        
        // Try multiple possible paths and formats
        String[] possiblePaths = {
            // Try relative path first (most reliable)
            "src/image/" + productName.toLowerCase().replace(" ", "_") + ".jpg",
            "src/image/" + productName.toLowerCase().replace(" ", "_") + ".png",
            "src/image/" + productName.toLowerCase().replace(" ", "") + ".jpg",
            "src/image/" + productName.toLowerCase().replace(" ", "") + ".png",
            // Try absolute paths
            "C:/Users/PC-001/Videos/Sapatot/src/image/" + productName.toLowerCase().replace(" ", "_") + ".jpg",
            "C:/Users/PC-001/Videos/Sapatot/src/image/" + productName.toLowerCase().replace(" ", "_") + ".png",
            "C:/Users/PC-001/Videos/Sapatot/src/image/" + productName.toLowerCase().replace(" ", "") + ".jpg",
            "C:/Users/PC-001/Videos/Sapatot/src/image/" + productName.toLowerCase().replace(" ", "") + ".png",
            // Try Pictures path
            "C:/Users/PC-001/Pictures/Sapatot/src/image/" + productName.toLowerCase().replace(" ", "_") + ".jpg",
            "C:/Users/PC-001/Pictures/Sapatot/src/image/" + productName.toLowerCase().replace(" ", "_") + ".png"
        };
        
        for (int i = 0; i < possiblePaths.length; i++) {
            String imagePath = possiblePaths[i];
            try {
                System.out.println("[" + (i+1) + "] Trying path: " + imagePath);
                
                // Check if file exists first
                java.io.File file = new java.io.File(imagePath);
                if (file.exists()) {
                    System.out.println("    File exists: " + file.getAbsolutePath());
                    ImageIcon icon = new ImageIcon(imagePath);
                    System.out.println("    Icon loaded: " + (icon.getIconWidth() > 0 ? "YES" : "NO"));
                    System.out.println("    Icon width: " + icon.getIconWidth() + ", height: " + icon.getIconHeight());
                    
                    if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                        // Scale image to fit table
                        Image scaledImg = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        System.out.println("    ✓ Successfully loaded and scaled image for: " + productName);
                        return new ImageIcon(scaledImg);
                    }
                } else {
                    System.out.println("    ✗ File does not exist: " + file.getAbsolutePath());
                }
            } catch (Exception e) {
                System.out.println("    ✗ Failed to load from " + imagePath + ": " + e.getMessage());
                // Continue to next path
            }
        }
        
        System.out.println("=== No image found for: " + productName + ", using default icon ===");
        return createDefaultIcon();
    }
    
    private javax.swing.ImageIcon createDefaultIcon() {
        try {
            // Create a simple default icon with text
            BufferedImage defaultImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g2d = defaultImage.createGraphics();
            
            // Background
            g2d.setColor(java.awt.Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, 50, 50);
            
            // Border
            g2d.setColor(java.awt.Color.DARK_GRAY);
            g2d.drawRect(0, 0, 49, 49);
            
            // Text
            g2d.setColor(java.awt.Color.BLACK);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 8));
            g2d.drawString("No Img", 2, 28);
            
            g2d.dispose();
            System.out.println("Created default icon");
            return new ImageIcon(defaultImage);
        } catch (Exception e) {
            System.err.println("Error creating default icon: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        image_label = new javax.swing.JLabel();
        lbl_price = new javax.swing.JLabel();
        lbl_item = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC-001\\Videos\\Sapatot\\src\\image\\jords (1).jpg")); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 230, 80));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setText("Premium");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 90, 20));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel15.setText("Kids");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 50, 20));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel16.setText("Sale");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel16MouseExited(evt);
            }
        });
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 30, 50, 20));

        jLabel17.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel17.setText("SNKRS");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel17MouseExited(evt);
            }
        });
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 30, 80, 20));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel12.setText("Men");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 40, -1));

        jLabel33.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel33.setText("New & Featured");
        jLabel33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel33MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel33MouseExited(evt);
            }
        });
        jPanel3.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 140, 20));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1450, 80));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("SHIPPING ADDRESS");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Last Name:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 80, 10));

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 140, 40));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setOpaque(true);
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, 10, 640));

        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 350, 40));

        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 170, 40));

        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 350, 40));

        jTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 200, 40));

        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 170, 40));

        jTextField7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 350, 40));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("ORDER SUMMARY");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 170, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Apt/House Number, Street Name:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 240, 10));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("ZIP:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 80, 10));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("First Name:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 80, 10));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Phone Number:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 120, 10));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Payment Method:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 140, 20));

        jRadioButton1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jRadioButton1.setText("Credit/Debit");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 560, 200, 30));

        jRadioButton2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jRadioButton2.setText("Cash on Delivery");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 480, 200, 30));

        jRadioButton3.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jRadioButton3.setText("Gcash");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 200, 30));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Address:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 80, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel18.setText("CUSTOMER INFO");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 160, -1));

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Subtotal:");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 80, -1));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, 130, 30));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Free");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, 60, -1));

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Total:");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, -1, -1));

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Delivery/Shipping: ");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, -1, -1));

        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 60, 130, 30));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 380, 10));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, 380, 10));

        image_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(image_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, 190, -1));

        lbl_price.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(lbl_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, 120, 0));

        lbl_item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(lbl_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 202, 120, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "IMAGE", "PRODUCT", "PRICE"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, 360, 100));

        jButton1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton1.setText("CONTINUE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 530, 310, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 780, 640));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1096, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // PARA SA PREMIUM PAGE
        // Pulihan ang 'premium_page' sa saktong ngalan sa imong JFrame class
        premium prem = new premium();
        prem.setVisible(true);
        prem.setLocationRelativeTo(null);
        this.dispose(); // I-close ang dashboard
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        jLabel11.setForeground(java.awt.Color.BLACK);
        jLabel11.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        jLabel11.setForeground(new java.awt.Color(00, 00, 00));
        jLabel11.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // PARA SA KIDS PAGE
        kids_shoes kids = new kids_shoes();
        kids.setVisible(true);
        kids.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        jLabel15.setForeground(java.awt.Color.BLACK);
        jLabel15.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        jLabel15.setForeground(new java.awt.Color(00, 00, 00));
        jLabel15.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel15MouseExited

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // PARA SA SALE PAGE
        sale_shoes sale = new sale_shoes();
        sale.setVisible(true);
        sale.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseEntered
        jLabel16.setForeground(java.awt.Color.BLACK);
        jLabel16.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel16MouseEntered

    private void jLabel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseExited
        jLabel16.setForeground(new java.awt.Color(00, 00, 00));
        jLabel16.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel16MouseExited

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // PARA SA SNKRS PAGE
        snkrs_shoes snkrs = new snkrs_shoes();
        snkrs.setVisible(true);
        snkrs.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseEntered
        jLabel17.setForeground(java.awt.Color.BLACK);
        jLabel17.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel17MouseEntered

    private void jLabel17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseExited
        jLabel17.setForeground(new java.awt.Color(00, 00, 00));
        jLabel17.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel17MouseExited

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // PARA SA MEN PAGE
        men_shoes men = new men_shoes();
        men.setVisible(true);
        men.setLocationRelativeTo(null);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        jLabel12.setForeground(java.awt.Color.BLACK);
        jLabel12.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        jLabel12.setForeground(new java.awt.Color(00, 00, 00));
        jLabel12.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel12MouseExited

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // 1. Paghimo og instance sa imong user_dashboard frame
        user_dashboard home = new user_dashboard();

        // 2. I-center ang dashboard para nindot tan-awon
        home.setLocationRelativeTo(null);

        // 3. I-show ang user_dashboard
        home.setVisible(true);

        // 4. I-close kining current nga premium frame
        this.dispose();
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel33MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseEntered
        jLabel33.setForeground(java.awt.Color.BLACK);
        jLabel33.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Himoon natong Bold
    }//GEN-LAST:event_jLabel33MouseEntered

    private void jLabel33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseExited
        jLabel33.setForeground(new java.awt.Color(00, 00, 00));
        jLabel33.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18)); // Balik sa Plain
    }//GEN-LAST:event_jLabel33MouseExited

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // Customer name field - validate input
        String name = jTextField1.getText().trim();
        if (name.isEmpty() || name.equals("Customer Name")) {
            jTextField1.setText("Customer Name");
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private boolean validateCheckoutForm() {
        // Validate all required fields
        String customerName = jTextField1.getText().trim();
        String address = jTextField2.getText().trim();
        String phone = jTextField3.getText().trim();
        String email = jTextField5.getText().trim();
        String city = jTextField6.getText().trim();
        
        // Check required fields
        if (customerName.isEmpty() || customerName.equals("Customer Name")) {
            JOptionPane.showMessageDialog(this, "Please enter your name");
            jTextField1.requestFocus();
            return false;
        }
        
        if (address.isEmpty() || address.equals("Address")) {
            JOptionPane.showMessageDialog(this, "Please enter your address");
            jTextField2.requestFocus();
            return false;
        }
        
        if (phone.isEmpty() || phone.equals("Phone Number")) {
            JOptionPane.showMessageDialog(this, "Please enter your phone number");
            jTextField3.requestFocus();
            return false;
        }
        
        if (city.isEmpty() || city.equals("City")) {
            JOptionPane.showMessageDialog(this, "Please enter your city");
            jTextField6.requestFocus();
            return false;
        }
        
        // Validate email format (optional field)
        if (!email.isEmpty() && !email.equals("Email")) {
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address");
                jTextField5.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    
    private String getCustomerDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Customer: ").append(jTextField1.getText().trim()).append("\n");
        details.append("Address: ").append(jTextField2.getText().trim()).append("\n");
        details.append("Phone: ").append(jTextField3.getText().trim()).append("\n");
        details.append("City: ").append(jTextField6.getText().trim()).append("\n");
        
        String email = jTextField5.getText().trim();
        if (!email.isEmpty() && !email.equals("Email")) {
            details.append("Email: ").append(email).append("\n");
        }
        
        String postalCode = jTextField4.getText().trim();
        if (!postalCode.isEmpty() && !postalCode.equals("Postal Code")) {
            details.append("Postal Code: ").append(postalCode).append("\n");
        }
        
        String notes = jTextField7.getText().trim();
        if (!notes.isEmpty() && !notes.equals("Additional Notes")) {
            details.append("Notes: ").append(notes).append("\n");
        }
        
        return details.toString();
    }

    private void refreshOrderData() {
        // Refresh the order display when cart changes
        try {
            double subtotal = CartStore.getSubtotal();
            double total = CartStore.getTotal();
            
            jLabel24.setText("₱" + String.format("%.2f", subtotal));
            jLabel20.setText("₱" + String.format("%.2f", total));
            
            displayCartItems();
        } catch (Exception e) {
            System.err.println("Error refreshing order data: " + e.getMessage());
        }
    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // Address field - validate input
        String address = jTextField2.getText().trim();
        if (address.isEmpty() || address.equals("Address")) {
            jTextField2.setText("Address");
        }
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // Phone number field - validate input
        String phone = jTextField3.getText().trim();
        if (phone.isEmpty() || phone.equals("Phone Number")) {
            jTextField3.setText("Phone Number");
        }
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // Email field - validate input
        String email = jTextField5.getText().trim();
        if (email.isEmpty() || email.equals("Email")) {
            jTextField5.setText("Email");
        }
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // City field - validate input
        String city = jTextField6.getText().trim();
        if (city.isEmpty() || city.equals("City")) {
            jTextField6.setText("City");
        }
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // Postal code field - validate input
        String postalCode = jTextField4.getText().trim();
        if (postalCode.isEmpty() || postalCode.equals("Postal Code")) {
            jTextField4.setText("Postal Code");
        }
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // Notes/Additional info field - validate input
        String notes = jTextField7.getText().trim();
        if (notes.isEmpty() || notes.equals("Additional Notes")) {
            jTextField7.setText("Additional Notes");
        }
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // Credit/Debit card selected
        System.out.println("Payment method: Credit/Debit");
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // Cash on Delivery selected
        System.out.println("Payment method: Cash on Delivery");
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // GCash selected
        System.out.println("Payment method: GCash");
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Validate all required fields first
        String customerName = jTextField1.getText().trim();
        String address = jTextField2.getText().trim();
        String phone = jTextField3.getText().trim();
        String city = jTextField6.getText().trim();
        
        // Check required fields
        if (customerName.isEmpty() || customerName.equals("Customer Name")) {
            JOptionPane.showMessageDialog(this, "Please enter your name");
            jTextField1.requestFocus();
            return;
        }
        
        if (address.isEmpty() || address.equals("Address")) {
            JOptionPane.showMessageDialog(this, "Please enter your address");
            jTextField2.requestFocus();
            return;
        }
        
        if (phone.isEmpty() || phone.equals("Phone Number")) {
            JOptionPane.showMessageDialog(this, "Please enter your phone number");
            jTextField3.requestFocus();
            return;
        }
        
        if (city.isEmpty() || city.equals("City")) {
            JOptionPane.showMessageDialog(this, "Please enter your city");
            jTextField6.requestFocus();
            return;
        }
        
        // Check payment method
        String method = "";
        if (jRadioButton2.isSelected()) {
            method = "Cash on Delivery";
        } else if (jRadioButton3.isSelected()) {
            method = "GCash";
        } else if (jRadioButton1.isSelected()) {
            method = "Credit/Debit";
        }

        if (method.equals("")) {
            JOptionPane.showMessageDialog(this, "Choose Payment to Proceed.");
            return;
        }
        
        // Check if cart has items
        if (CartStore.items == null || CartStore.items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!");
            return;
        }
        
        // Create order summary
        StringBuilder orderSummary = new StringBuilder("Order Placed Successfully!\n\n");
        orderSummary.append("Customer: ").append(customerName).append("\n");
        orderSummary.append("Address: ").append(address).append("\n");
        orderSummary.append("Phone: ").append(phone).append("\n");
        orderSummary.append("City: ").append(city).append("\n");
        orderSummary.append("Payment Method: ").append(method).append("\n\n");
        orderSummary.append("Order Items:\n");
        
        double totalAmount = 0.0;
        
        for (CartStore.CartItem item : CartStore.items) {
            orderSummary.append("• ").append(item.item)
                        .append(" (Qty: ").append(item.qty).append(")")
                        .append(" - ₱").append(String.format("%.2f", item.price * item.qty)).append("\n");
            totalAmount += (item.price * item.qty);
        }
        
        orderSummary.append("\nTotal Amount: ₱").append(String.format("%.2f", totalAmount));
                             
        JOptionPane.showMessageDialog(this, orderSummary.toString());
        
        // Save all cart items to database for admin to see in saleslogs
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/PC-001/Videos/Sapatot/sapatotDB.db")) {
            
            // Debug: Check user ID
            System.out.println("DEBUG: Checkout user ID = " + this.id);
            
            // Check if u_id column exists in tbl_sales, add it if it doesn't
            try {
                java.sql.Statement stmt = con.createStatement();
                stmt.execute("ALTER TABLE tbl_sales ADD COLUMN u_id INTEGER DEFAULT 0");
                System.out.println("Added u_id column to tbl_sales");
            } catch (Exception e) {
                // Column probably already exists
                System.out.println("u_id column already exists or other error: " + e.getMessage());
            }
            
            String sql = "INSERT INTO tbl_sales (u_id, customer_name, address, phone, city, item_name, price, qty, total_amount, payment_method, sale_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            for (CartStore.CartItem item : CartStore.items) {
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    System.out.println("DEBUG: Saving item for user ID: " + this.id + ", item: " + item.item);
                    pst.setInt(1, this.id); // User ID
                    pst.setString(2, customerName); 
                    pst.setString(3, address);
                    pst.setString(4, phone);
                    pst.setString(5, city);
                    pst.setString(6, item.item);
                    pst.setDouble(7, item.price);
                    pst.setInt(8, item.qty); 
                    pst.setDouble(9, item.price * item.qty);
                    pst.setString(10, method);
                    pst.setString(11, java.time.LocalDate.now().toString());

                    pst.executeUpdate();
                    System.out.println("DEBUG: Item saved successfully!");
                }
            }
            
            System.out.println("Order saved successfully!");
            JOptionPane.showMessageDialog(this, "Order Placed Successfully!\nThank you for your purchase!");
            
            // Generate and show receipt with your design
            new receipt(customerName, address, phone, city, method, totalAmount, CartStore.items).setVisible(true);
            
            // Clear cart after successful order
            CartStore.clear();
            
            // Close checkout and return to main
            this.dispose();
            
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
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
            java.util.logging.Logger.getLogger(checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new checkout().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel image_label;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel lbl_item;
    private javax.swing.JLabel lbl_price;
    // End of variables declaration//GEN-END:variables
}
