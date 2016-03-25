package examples.muhammed.com.socketchatandroid.constants;

import android.content.Context;
import android.content.SharedPreferences;

import examples.muhammed.com.socketchatandroid.models.UserDetails;

/**
 * Created by thasneem on 25/3/16.
 */
public class AppStorage {

    public static final String USER_DETAILS = "user_details";
    private static final String CHAT_PREFERENCES = "chat_preference";
    private static final String USER_NAME = "username";
    private static final String NAME = "name";
    private static final String USER_ID = "user_id";

    public static void saveUserDetails(UserDetails userDetails, Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(USER_NAME, userDetails.getUsername());
        edit.putString(NAME, userDetails.getName());
        edit.putString(USER_ID, userDetails.get_id());
        edit.apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CHAT_PREFERENCES, Context.MODE_PRIVATE);
        return preferences;
    }


}
