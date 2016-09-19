package examples.muhammed.com.socketchatandroid.constants;


public class UrlConstants {
    private static final String IP = "192.168.2.73";
    //    private static final String IP = "10.0.2.2"; // Android Emulator
    //            private static final String IP = "10.0.3.2"; // Android Genymotion Emulator
    private static final String PORT = "3000/";
    private static final String SERVER_URL = "http://" + IP + ":" + PORT;

    public static final String LOGIN_REQUEST = SERVER_URL + "users/login";
    public static final String REGISTER_REQUEST = SERVER_URL + "users/register";
    public static final String USER_LIST_REQUEST = SERVER_URL + "users/getcontacts?id=";
    public static final String SOCKET_URL = SERVER_URL;
    public static final String GET_STATUS = SERVER_URL + "users/getstatus?id=";
}
