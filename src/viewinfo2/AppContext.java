package viewinfo2;

import java.util.List;
import java.util.Map;
import tableview2.VehicleCSV;

public class AppContext {

    private static AppContext instance;

    private static String loggedInUserId;
    private static String accessLevel; // Add access level variable

    public static Map<String, List<VehicleCSV>> getCarPlateToVehicleMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    AppContext() {
        // Private constructor to prevent instantiation
    }

    public static synchronized AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public static String getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    // Add a method to get the access level
    public static String getAccessLevel() {
        return accessLevel;
    }

    // Add a method to set the access level
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    // Add other application-wide state or settings as needed

}
