package user;

import config.config;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import user.user_dashboard;
import user.user_dashboard;
import user.user_dashboard;
import user.user_dashboard;

public class ProductList extends JFrame {
    private final int userId;
    private final String keyword;
    private final String imagePath;
    private JTable table;
    private JTextField searchField;
    private JLabel imageLabel;

    public ProductList(int userId, String keyword, String imagePath, String title) {
        this.userId = userId;
        this.keyword = keyword;
        this.imagePath = imagePath;
        init();
        setTitle(title);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadTable("");
        loadImage();
    }

    private void init() {
        JPanel root = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Search:");
        searchField = new JTextField();
        top.add(lbl, BorderLayout.WEST);
        top.add(searchField, BorderLayout.CENTER);
        root.add(top, BorderLayout.NORTH);

        table = new JTable();
        table.setRowHeight(28);
        JScrollPane sp = new JScrollPane(table);
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel center = new JPanel(new GridLayout(1, 2));
        center.add(sp);
        center.add(imageLabel);
        root.add(center, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            if (userId > 0) {
                new user_dashboard(userId).setVisible(true);
            } else {
                new user_dashboard().setVisible(true);
            }
            dispose();
        });
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(back);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadTable(searchField.getText().trim());
            }
        });
    }

    private void loadTable(String extra) {
        String base = "SELECT item_name AS 'ITEM', color AS 'COLOR', size AS 'SIZE', qty AS 'QTY' " +
                "FROM tbl_inventory WHERE (lower(item_name) LIKE '%" + escape(keyword.toLowerCase()) + "%' " +
                "OR lower(category) LIKE '%" + escape(keyword.toLowerCase()) + "%')";
        if (extra != null && !extra.isEmpty()) {
            base += " AND (lower(item_name) LIKE '%" + escape(extra.toLowerCase()) + "%' " +
                    "OR lower(color) LIKE '%" + escape(extra.toLowerCase()) + "%' " +
                    "OR lower(category) LIKE '%" + escape(extra.toLowerCase()) + "%')";
        }
        new config().displayData(base, table);
    }

    private void loadImage() {
        if (imagePath == null) {
            imageLabel.setText("No Preview");
            return;
        }
        ImageIcon icon = null;
        try {
            java.net.URL url = getClass().getResource(imagePath);
            if (url != null) icon = new ImageIcon(url);
        } catch (Exception ignored) {}
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(420, 420, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setText("No Preview");
        }
    }

    private String escape(String s) {
        return s.replace("'", "''");
    }
}
