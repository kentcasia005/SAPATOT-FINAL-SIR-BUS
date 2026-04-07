package user;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CartStore {
    static final List<CartItem> items = new ArrayList<>();

    public static void add(String item, double price, int qty) {
        items.add(new CartItem(item, price, qty));
    }

    public static void fillTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (CartItem ci : items) {
            model.addRow(new Object[]{ci.item, ci.price, ci.qty, ci.price * ci.qty});
        }
    }

    public static double getSubtotal() {
        double subtotal = 0.0;
        for (CartItem ci : items) {
            subtotal += ci.price * ci.qty;
        }
        return subtotal;
    }

    public static double getTotal() {
        // You can add tax or other calculations here if needed
        return getSubtotal();
    }

    public static void clear() {
        items.clear();
    }
    
    public static void updateQuantity(String itemName, int newQty) {
        for (CartItem item : items) {
            if (item.item.equals(itemName)) {
                // Remove old item and add new one with updated quantity
                items.remove(item);
                items.add(new CartItem(itemName, item.price, newQty));
                break;
            }
        }
    }
    
    public static void removeItem(String itemName) {
        items.removeIf(item -> item.item.equals(itemName));
    }

    public static class CartItem {
        public final String item;
        public final double price;
        public final int qty;

        public CartItem(String item, double price, int qty) {
            this.item = item;
            this.price = price;
            this.qty = qty;
        }
    }
}
