package examples.muhammed.com.socketchatandroid.constants;

/**
 * Created by thasneem on 25/3/16.
 */
public class UrlConstants {
    //    private static final String IP = "192.168.5.84";
    private static final String IP = "10.0.2.2"; // Android Emulator
    //        private static final String IP = "10.0.3.2"; // Android Genymotion Emulator
    private static final String PORT = "3000/";
    private static final String SERVER_URL = "http://" + IP + ":" + PORT;

    public static final String LOGIN_REQUEST = SERVER_URL + "users/login";
    public static final String REGISTER_REQUEST = SERVER_URL + "users/register";
    public static final String USER_LIST_REQUEST = SERVER_URL + "users/getcontacts?id=";
}
