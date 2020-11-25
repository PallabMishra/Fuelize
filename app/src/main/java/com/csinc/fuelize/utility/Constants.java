package com.csinc.fuelize.utility;

public class Constants {

    public static double latitude;
    public static double longitude;
    public static int speed;
    public static String MASTER_TAG_ID = "C1E21980";
    public static String HW_DISPENSER = "/dev/ttymxc4";   // Port Number for Dispenser
    public static String HW_PRINTER = "/dev/ttymxc3"; // Port Number for Printer
    public static String ORDER_CREATED = "E0001"; //	Order Created
    public static String TRIP_STARTED = "E0002"; //	Trip Start
    public static String REACHED_AT_CUSTOMER = "E0003"; //	Vehicle arrived at site for delivery
    public static String ORDER_DELIVERED = "E0004"; //	Order delivery completed
    public static String ORDER_CANCELLED = "E0005"; //	Order cancelled and return processed
    public static String ORDER_RETURN = "E0006"; //	Order Return Initiated at RO
    public static String ORDER_CANCELLED_ADMIN = "E0008"; //Order cancelled by admin
    public static String ORDER_START = "E0009"; // Order Start
    public static String ORDER_DOWNLOADED = "E00010"; //Order downloaded
    public static String DECANTATION_START = "E00031"; // MDU Decant Start
    public static String DECANTATION_END = "E00032"; // MDU Decant End
    public static String DECANTATION_ERROR = "E00033"; //Decant Error
    public static String DECANTATION_PAUSE = "E00034"; //MDU Decant Pause
    public static String DECANTATION_RESUME = "E00035"; //MDU Decant Resume
    public static String REACHED_AT_CUSTOMER_MANUALLY = "E00041"; // Vehicle arrived at site for delivery Manually
    public static String ORDER_ON_HOLD = "C0001";
    public static String REACHED_AT_RO = "C0002";
    public static String REACHED_AT_RO_MANUALLY = "C0003";

   // private static String BASE_URL = "https://csiecomm.ril.com:5000";   // Development Server
    private static String BASE_URL = "http://157.230.47.85:5000";   // Our local Server
    public static String URL_LOGIN = BASE_URL + "/signin_controller/operatorLogin";
    public static String URL_DASHBOARD = BASE_URL + "/dashboard_controller/driver/dashboard";
    public static String URL_TRIP_START = BASE_URL + "/trip_controller/start";
    public static String URL_TRIP_STATUS_SYNC = BASE_URL + "/trip_controller/trip_status_sync";
    public static String URL_ORDER_LIST = BASE_URL + "/order_controller/get_mdu";
    public static String URL_TRIP_UPDATE = BASE_URL + "/order_controller/update";
    public static String URL_ORDER_DETAILS = BASE_URL + "/order_controller/retrieve";
    public static String URL_MULTI_ORDER_SYNC = BASE_URL + "/order_controller/update_multi";
    public static String URL_TRIP_MULTI_STATUS_SYNC = BASE_URL + "/trip_controller/trip_status_sync_multi";
}
