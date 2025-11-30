package org.example.client.security;

public class RoleAccessManager {

    public static boolean isAdmin(String role) {
        return "ADMIN".equals(role);
    }

    public static boolean isDispatcher(String role) {
        return "DISPATCHER".equals(role);
    }

    public static boolean isEngineer(String role) {
        return "ENGINEER".equals(role);
    }

    public static boolean isViewer(String role) {
        return "VIEWER".equals(role);
    }

    public static boolean canEditAircraft(String role) {
        return isAdmin(role) || isEngineer(role);
    }

    public static boolean canViewAircraft(String role) {
        return true;
    }

    public static boolean canEditFlights(String role) {
        return isAdmin(role) || isDispatcher(role);
    }

    public static boolean canEditMaintenance(String role) {
        return isAdmin(role) || isEngineer(role);
    }

    public static boolean canEditPassengers(String role) {
        return isAdmin(role) || isDispatcher(role);
    }

    public static boolean canEditCrew(String role) {
        return isAdmin(role) || isDispatcher(role);
    }

    public static boolean canManageUsers(String role) {
        return isAdmin(role);
    }
}
