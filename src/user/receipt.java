/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools & Templates
 * and open the template in the editor.
 */
package user;

import user.CartStore;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author PC-001
 */
public class receipt extends javax.swing.JFrame {

    private String customerName;
    private String address;
    private String phone;
    private String city;
    private String paymentMethod;
    private double totalAmount;
    private List<CartStore.CartItem> items;
    private String receiptFilePath;

    /**
     * Creates new form receipt
     */
    public receipt() {
        initComponents();
    }

    public receipt(String customerName, String address, String phone, String city, 
                   String paymentMethod, double totalAmount, List<CartStore.CartItem> items) {
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.items = items;
        
        initComponents();
        populateReceiptData();
        generatePDFReceipt();
        setLocationRelativeTo(null);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Receipt date:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 100, 20));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel2.setText("RECEIPT");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 140, 28));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Customer Town:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 150, 20));

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC-001\\Videos\\Sapatot\\src\\image\\jords (1).jpg")); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 230, 80));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Air Jorgaw");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 45, 100, 28));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Tubod Minglanilla Cebu");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 150, 28));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("6046, Jonard St.");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 150, 28));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 120, 20));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Customer St:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 150, 20));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Billed To");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 150, 28));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Receipt #:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 80, 20));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Total:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 630, 70, 20));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 120, 20));

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, 120, 20));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 630, 120, 20));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 370, 120, 20));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "QTY", "Description", "Unit Price", "Amount"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMinWidth(100);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(3).setMinWidth(100);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 490, 100));

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Customer Name:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 150, 20));

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Subtotal: ");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 550, 60, 20));

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Sales Tax:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 580, 70, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 620, 200, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 622, 200, 0));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 660, 200, 10));

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 120, 20));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, 120, 20));

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 580, 120, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void populateReceiptData() {
        try {
            // Set customer information
            if (customerName != null) jLabel20.setText(customerName);
            if (city != null) jLabel8.setText(city);
            if (address != null) {
                String[] addressParts = address.split(",");
                if (addressParts.length > 0) jLabel14.setText(addressParts[0]); // Street
                if (addressParts.length > 1) jLabel6.setText(addressParts[1]); // Town
            }
            
            // Set receipt number and date
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy");
            java.text.SimpleDateFormat receiptFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
            jLabel13.setText("RCP-" + receiptFormat.format(new Date()));
            jLabel16.setText(dateFormat.format(new Date()));
            
            // Populate table with items
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing data
            
            double subtotal = 0.0;
            for (CartStore.CartItem item : items) {
                double itemTotal = item.price * item.qty;
                model.addRow(new Object[]{
                    item.qty,
                    item.item,
                    String.format("%.2f", item.price),
                    String.format("%.2f", itemTotal)
                });
                subtotal += itemTotal;
            }
            
            // Calculate totals (assuming 12% tax)
            double tax = subtotal * 0.12;
            double grandTotal = subtotal + tax;
            
            // Set totals
            jLabel21.setText(String.format("%.2f", subtotal));
            jLabel22.setText(String.format("%.2f", tax));
            jLabel15.setText(String.format("%.2f", grandTotal));
            
        } catch (Exception e) {
            System.err.println("Error populating receipt data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading receipt data: " + e.getMessage());
        }
    }
    
    private void generatePDFReceipt() {
        try {
            String fileName = "Receipt_" + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf";
            receiptFilePath = System.getProperty("user.home") + "/Desktop/" + fileName;
            
            // Create text and HTML versions
            createTextReceipt();
            createHTMLReceipt();
            
            System.out.println("Receipt files generated: " + receiptFilePath.replace(".pdf", ""));
            
        } catch (Exception e) {
            System.err.println("Error generating receipt: " + e.getMessage());
        }
    }
    
    private void createTextReceipt() {
        try {
            StringBuilder receiptText = new StringBuilder();
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            
            receiptText.append("==================================================\n");
            receiptText.append("              AIR JORGAW\n");
            receiptText.append("              OFFICIAL RECEIPT\n");
            receiptText.append("==================================================\n\n");
            
            receiptText.append("Receipt #: ").append(jLabel13.getText()).append("\n");
            receiptText.append("Date: ").append(dateFormat.format(new Date())).append("\n\n");
            
            receiptText.append("BILLED TO:\n");
            receiptText.append("Name: ").append(customerName).append("\n");
            receiptText.append("Address: ").append(address).append("\n");
            receiptText.append("Phone: ").append(phone).append("\n");
            receiptText.append("City: ").append(city).append("\n\n");
            
            receiptText.append("ORDER DETAILS:\n");
            receiptText.append("--------------------------------------------------\n");
            receiptText.append(String.format("%-5s %-25s %10s %10s\n", "QTY", "DESCRIPTION", "PRICE", "AMOUNT"));
            receiptText.append("--------------------------------------------------\n");
            
            for (CartStore.CartItem item : items) {
                double itemTotal = item.price * item.qty;
                receiptText.append(String.format("%-5d %-25s %10.2f %10.2f\n", 
                    item.qty, item.item, item.price, itemTotal));
            }
            
            receiptText.append("--------------------------------------------------\n");
            receiptText.append(String.format("%-40s %10.2f\n", "SUBTOTAL:", Double.parseDouble(jLabel21.getText())));
            receiptText.append(String.format("%-40s %10.2f\n", "TAX (12%):", Double.parseDouble(jLabel22.getText())));
            receiptText.append("==================================================\n");
            receiptText.append(String.format("%-40s %10.2f\n", "TOTAL:", Double.parseDouble(jLabel15.getText())));
            receiptText.append("==================================================\n\n");
            
            receiptText.append("Payment Method: ").append(paymentMethod).append("\n");
            receiptText.append("THANK YOU FOR YOUR PURCHASE!\n");
            receiptText.append("==================================================\n");
            
            // Write to text file
            String textFilePath = receiptFilePath.replace(".pdf", ".txt");
            try (FileWriter writer = new FileWriter(textFilePath)) {
                writer.write(receiptText.toString());
            }
            
        } catch (IOException e) {
            System.err.println("Error creating text receipt: " + e.getMessage());
        }
    }
    
    private void createHTMLReceipt() {
        try {
            StringBuilder html = new StringBuilder();
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            
            html.append("<!DOCTYPE html>\n");
            html.append("<html>\n");
            html.append("<head>\n");
            html.append("<title>Air Jorgaw - Receipt</title>\n");
            html.append("<style>\n");
            html.append("body { font-family: Arial, sans-serif; margin: 20px; }\n");
            html.append(".header { text-align: center; font-size: 24px; font-weight: bold; margin-bottom: 20px; }\n");
            html.append(".section { margin: 15px 0; }\n");
            html.append("table { width: 100%; border-collapse: collapse; margin: 15px 0; }\n");
            html.append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
            html.append("th { background-color: #f2f2f2; }\n");
            html.append(".total { font-weight: bold; }\n");
            html.append("@media print { body { margin: 10px; } }\n");
            html.append("</style>\n");
            html.append("</head>\n");
            html.append("<body>\n");
            
            html.append("<div class='header'>AIR JORGAW<br>OFFICIAL RECEIPT</div>\n");
            
            html.append("<div class='section'>\n");
            html.append("<strong>Receipt #:</strong> ").append(jLabel13.getText()).append("<br>\n");
            html.append("<strong>Date:</strong> ").append(dateFormat.format(new Date())).append("\n");
            html.append("</div>\n");
            
            html.append("<div class='section'>\n");
            html.append("<h3>BILLED TO:</h3>\n");
            html.append("<strong>Name:</strong> ").append(customerName).append("<br>\n");
            html.append("<strong>Address:</strong> ").append(address).append("<br>\n");
            html.append("<strong>Phone:</strong> ").append(phone).append("<br>\n");
            html.append("<strong>City:</strong> ").append(city).append("\n");
            html.append("</div>\n");
            
            html.append("<div class='section'>\n");
            html.append("<h3>ORDER DETAILS:</h3>\n");
            html.append("<table>\n");
            html.append("<tr><th>QTY</th><th>DESCRIPTION</th><th>PRICE</th><th>AMOUNT</th></tr>\n");
            
            for (CartStore.CartItem item : items) {
                double itemTotal = item.price * item.qty;
                html.append("<tr>\n");
                html.append("<td>").append(item.qty).append("</td>\n");
                html.append("<td>").append(item.item).append("</td>\n");
                html.append("<td>").append(String.format("%.2f", item.price)).append("</td>\n");
                html.append("<td>").append(String.format("%.2f", itemTotal)).append("</td>\n");
                html.append("</tr>\n");
            }
            
            html.append("</table>\n");
            html.append("</div>\n");
            
            html.append("<div class='section'>\n");
            html.append("<table>\n");
            html.append("<tr><td>SUBTOTAL:</td><td>").append(jLabel21.getText()).append("</td></tr>\n");
            html.append("<tr><td>TAX (12%):</td><td>").append(jLabel22.getText()).append("</td></tr>\n");
            html.append("<tr class='total'><td>TOTAL:</td><td>").append(jLabel15.getText()).append("</td></tr>\n");
            html.append("</table>\n");
            html.append("</div>\n");
            
            html.append("<div class='section'>\n");
            html.append("<strong>Payment Method:</strong> ").append(paymentMethod).append("<br>\n");
            html.append("<strong>THANK YOU FOR YOUR PURCHASE!</strong>\n");
            html.append("</div>\n");
            
            html.append("</body>\n");
            html.append("</html>\n");
            
            // Write to HTML file
            String htmlFilePath = receiptFilePath.replace(".pdf", ".html");
            try (FileWriter writer = new FileWriter(htmlFilePath)) {
                writer.write(html.toString());
            }
            
        } catch (IOException e) {
            System.err.println("Error creating HTML receipt: " + e.getMessage());
        }
    }
    
    public void printReceipt() {
        try {
            // Print the HTML version for better formatting
            String htmlFilePath = receiptFilePath.replace(".pdf", ".html");
            File htmlFile = new File(htmlFilePath);
            
            if (htmlFile.exists()) {
                Desktop.getDesktop().print(htmlFile);
                JOptionPane.showMessageDialog(this, "Receipt sent to printer!");
            } else {
                // Fallback to direct printing
                JTextArea printArea = new JTextArea();
                printArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
                printArea.setText(createPrintableText());
                
                boolean printComplete = printArea.print();
                if (printComplete) {
                    JOptionPane.showMessageDialog(this, "Receipt sent to printer!");
                } else {
                    JOptionPane.showMessageDialog(this, "Printing cancelled or failed.");
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Printing error: " + e.getMessage());
        }
    }
    
    public void openReceipt() {
        try {
            // Try to open HTML file first
            String htmlFilePath = receiptFilePath.replace(".pdf", ".html");
            File htmlFile = new File(htmlFilePath);
            
            if (htmlFile.exists()) {
                Desktop.getDesktop().open(htmlFile);
            } else {
                // Fallback to text file
                String textFilePath = receiptFilePath.replace(".pdf", ".txt");
                File textFile = new File(textFilePath);
                
                if (textFile.exists()) {
                    Desktop.getDesktop().open(textFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Receipt file not found.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening receipt: " + e.getMessage());
        }
    }
    
    private String createPrintableText() {
        StringBuilder sb = new StringBuilder();
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        sb.append("==================================================\n");
        sb.append("              AIR JORGAW\n");
        sb.append("              OFFICIAL RECEIPT\n");
        sb.append("==================================================\n\n");
        
        sb.append("Receipt #: ").append(jLabel13.getText()).append("\n");
        sb.append("Date: ").append(dateFormat.format(new Date())).append("\n\n");
        
        sb.append("BILLED TO:\n");
        sb.append("Name: ").append(customerName).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("City: ").append(city).append("\n\n");
        
        sb.append("ORDER DETAILS:\n");
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-5s %-25s %10s %10s\n", "QTY", "DESCRIPTION", "PRICE", "AMOUNT"));
        sb.append("--------------------------------------------------\n");
        
        for (CartStore.CartItem item : items) {
            double itemTotal = item.price * item.qty;
            sb.append(String.format("%-5d %-25s %10.2f %10.2f\n", 
                item.qty, item.item, item.price, itemTotal));
        }
        
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-40s %10.2f\n", "SUBTOTAL:", Double.parseDouble(jLabel21.getText())));
        sb.append(String.format("%-40s %10.2f\n", "TAX (12%):", Double.parseDouble(jLabel22.getText())));
        sb.append("==================================================\n");
        sb.append(String.format("%-40s %10.2f\n", "TOTAL:", Double.parseDouble(jLabel15.getText())));
        sb.append("==================================================\n\n");
        
        sb.append("Payment Method: ").append(paymentMethod).append("\n");
        sb.append("THANK YOU FOR YOUR PURCHASE!\n");
        sb.append("==================================================\n");
        
        return sb.toString();
    }
    
    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {
        printReceipt();
    }
    
    private void jButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {
        openReceipt();
    }
    
    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

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
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new receipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
