package admin;

/**
 * Session management for admin. Admin pages (dashboard, saleslogs, shoe_inventory, totalusers)
 * must only be accessible when isLoggedIn is true (set after successful admin login).
 */
public class AdminSession {
    private static boolean loggedIn = false;

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean value) {
        loggedIn = value;
    }

    public static void logout() {
        loggedIn = false;
    }
}
