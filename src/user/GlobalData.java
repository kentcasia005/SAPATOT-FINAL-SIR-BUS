package user;

/**
 * GlobalData class to maintain user session and shared data across all windows
 * This prevents data loss when navigating between pages
 */
public class GlobalData {
    
    // Product data (existing)
    public static String productName = "";
    public static double productPrice = 0.0;
    public static String productImagePath; // Siguradua nga "productImagePath" ang ngalan
    
    // User session data (NEW)
    private static int currentUserId = 0;
    private static String currentUserName = "";
    private static String currentUserRole = "";
    
    // Navigation state
    private static user_dashboard mainDashboard = null;
    
    // User session methods (NEW)
    public static void setCurrentUser(int userId, String userName, String role) {
        currentUserId = userId;
        currentUserName = userName;
        currentUserRole = role;
        System.out.println("DEBUG: GlobalData - User set: ID=" + userId + ", Name=" + userName);
    }
    
    public static int getCurrentUserId() {
        return currentUserId;
    }
    
    public static String getCurrentUserName() {
        return currentUserName;
    }
    
    public static String getCurrentUserRole() {
        return currentUserRole;
    }
    
    // Dashboard reference methods (NEW)
    public static void setMainDashboard(user_dashboard dashboard) {
        mainDashboard = dashboard;
    }
    
    public static user_dashboard getMainDashboard() {
        return mainDashboard;
    }
    
    // Session management (NEW)
    public static void clearSession() {
        currentUserId = 0;
        currentUserName = "";
        currentUserRole = "";
        mainDashboard = null;
        System.out.println("DEBUG: GlobalData - Session cleared");
    }
    
    public static boolean hasActiveSession() {
        return currentUserId > 0;
    }
}