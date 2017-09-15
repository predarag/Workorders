package rs.co.sbb.workorders.ws.config;

/**
 * Created by Predrag.Tasic on 8/31/2017.
 */

public class MobAppIntegrationConfig {

    private static final String HOST = "192.168.2.170";
    private static final String PORT = ":5555";
    public static final String USERNAME = "Administrator";
    public static final String PASSWORD = "manage";

    public static final String PRODUCT_PACKAGE_BASE_PATH = "/rest/Ug.mobAppIntegration.pub.restResources.productcatalog/";
    public static final String SAPINTEGRATION_BASE_PATH = "/rest/Ug.mobAppIntegration.pub.restResources.sapintegration/";
    public static final String TOTALTV_BASE_PATH = "/rest/Ug.mobAppIntegration.pub.restResources.totaltv/";
    public static final String BASE_URL = "http://"+HOST+PORT;


}
