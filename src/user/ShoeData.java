package user;

import config.config;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample shoe data for catalog. Filter by category: "all", "kids", "men", "sale", "snkrs".
 * Loads from tbl_inventory (admin-added products) and merges with static list.
 */
public class ShoeData {
    public static class ShoeItem {
        public final int id;
        public final String name;
        public final String brand;
        public final String size;
        public final double price;
        public final String imagePath;
        public final String category;

        public ShoeItem(int id, String name, String brand, String size, double price, String imagePath, String category) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.size = size;
            this.price = price;
            this.imagePath = imagePath != null ? imagePath : "";
            this.category = category;
        }
    }

    private static final java.awt.Color SIDEBAR_COLOR = new java.awt.Color(75, 0, 130);

    private static final List<ShoeItem> ALL_SHOES = new ArrayList<>();

    static {
        String imgBase = "src/image/";
        ALL_SHOES.add(new ShoeItem(1, "Jordan Trunner", "Nike", "9", 8210, imgBase + "trunner (1).jpg", "snkrs"));
        ALL_SHOES.add(new ShoeItem(2, "Nike PG 1", "Nike", "10", 12210, imgBase + "trunner (1).jpg", "men"));
        ALL_SHOES.add(new ShoeItem(3, "LUKA 77", "Nike", "9", 10210, imgBase + "trunner (1).jpg", "snkrs"));
        ALL_SHOES.add(new ShoeItem(4, "Nike Lebron TR1", "Nike", "10", 9210, imgBase + "trunner (1).jpg", "men"));
        ALL_SHOES.add(new ShoeItem(5, "Nike Kyrie 7", "Nike", "9", 11210, imgBase + "trunner (1).jpg", "snkrs"));
        ALL_SHOES.add(new ShoeItem(6, "Nike Pegasus 39", "Nike", "9", 8210, imgBase + "trunner (1).jpg", "men"));
        ALL_SHOES.add(new ShoeItem(7, "Air Nike 1", "Nike", "8", 4321, imgBase + "trunner (1).jpg", "sale"));
        ALL_SHOES.add(new ShoeItem(8, "Pegasus Turbo 2", "Nike", "10", 9714, imgBase + "trunner (1).jpg", "men"));
        ALL_SHOES.add(new ShoeItem(9, "VANS", "Vans", "9", 8675, imgBase + "trunner (1).jpg", "sale"));
        ALL_SHOES.add(new ShoeItem(10, "SAMBA", "Adidas", "9", 6412, imgBase + "trunner (1).jpg", "sale"));
        ALL_SHOES.add(new ShoeItem(11, "Spide Kids", "Nike", "3", 2210, imgBase + "spi.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(12, "Softbottom", "Nike", "4", 1750, imgBase + "soft.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(13, "Autumn", "Nike", "2", 1265, imgBase + "autom.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(14, "Tsushiki", "Nike", "4", 1259, imgBase + "tsu.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(15, "Faux", "Nike", "3", 810, imgBase + "faux.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(16, "Tanzania", "Nike", "5", 3712, imgBase + "tanzania.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(17, "Converse", "Converse", "7", 5253, imgBase + "conver.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(18, "Fashion", "Nike", "6", 4210, imgBase + "fashion.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(19, "Asics", "Asics", "8", 3560, imgBase + "asics.jpg", "kids"));
        ALL_SHOES.add(new ShoeItem(20, "Fancy", "Nike", "4", 2160, imgBase + "fancy.jpg", "kids"));
    }

    public static List<ShoeItem> getShoesByCategory(String category) {
        List<ShoeItem> fromDb = getShoesFromDatabase(category);
        List<ShoeItem> fromStatic = fromStaticByCategory(category);
        List<ShoeItem> out = new ArrayList<>(fromDb);
        out.addAll(fromStatic);
        return out;
    }

    /** Load products from admin inventory (tbl_inventory). */
    public static List<ShoeItem> getShoesFromDatabase(String category) {
        List<ShoeItem> list = new ArrayList<>();
        Connection conn = null;
        try {
            config.ensureInventoryColumns();
            conn = config.connectDB();
            if (conn == null) return list;
            String sql = "SELECT item_id, item_name, color, size, qty, COALESCE(image_path,'') AS image_path, COALESCE(price,0) AS price, COALESCE(brand,'') AS brand, COALESCE(category,'all') AS category FROM tbl_inventory WHERE qty > 0";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String cat = rs.getString("category");
                    if (category != null && !"all".equalsIgnoreCase(category) && !category.equalsIgnoreCase(cat)) continue;
                    int id = rs.getInt("item_id");
                    String name = rs.getString("item_name");
                    String brand = rs.getString("brand");
                    String size = rs.getString("size");
                    double price = rs.getDouble("price");
                    String imagePath = rs.getString("image_path");
                    list.add(new ShoeItem(id, name, brand, size, price, imagePath, cat));
                }
            }
        } catch (Exception e) {
            System.out.println("ShoeData getShoesFromDatabase: " + e.getMessage());
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception ignored) {}
        }
        return list;
    }

    private static List<ShoeItem> fromStaticByCategory(String category) {
        if (category == null || "all".equalsIgnoreCase(category)) return new ArrayList<>(ALL_SHOES);
        List<ShoeItem> out = new ArrayList<>();
        for (ShoeItem s : ALL_SHOES) {
            if (category.equalsIgnoreCase(s.category)) out.add(s);
        }
        return out;
    }

    public static java.awt.Color getSidebarColor() {
        return SIDEBAR_COLOR;
    }
}
