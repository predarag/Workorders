package rs.co.sbb.workorders.ws.config;

/**
 * Created by Predrag.Tasic on 8/14/2017.
 */

public class SerbianAddresConfig {

    private static final String HOST = "192.168.2.173";
    private static final String PORT = ":5555";
    public static final String USERNAME = "Administrator";
    public static final String PASSWORD = "manage";

    private static final String BASE_PATH = "/rest/Sbb/SerbianAddressBusinessService/services/pub/sap/restWS/";
    public static final String BASE_URL = "http://"+HOST+PORT+BASE_PATH;



}
