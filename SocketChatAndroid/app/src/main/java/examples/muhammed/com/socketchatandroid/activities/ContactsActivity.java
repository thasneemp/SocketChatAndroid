package examples.muhammed.com.socketchatandroid.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cabot.volleyframework.NetworkOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.adapters.ContactsArrayAdapters;
import examples.muhammed.com.socketchatandroid.constants.AppStorage;
import examples.muhammed.com.socketchatandroid.constants.UrlConstants;
import examples.muhammed.com.socketchatandroid.models.ContactResponse;
import examples.muhammed.com.socketchatandroid.models.UserDetails;

/**
 * Created by thasneem on 26/3/16.
 */
public class ContactsActivity extends BaseActivity {
    private static final int REQUEST_USER_LIST = 123;
    private RecyclerView mContactRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        setUI();
    }

    private void setUI() {
        mContactRecyclerView = (RecyclerView) findViewById(R.id.contactListRecyclerView);
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        /**
         * Call API to get data.
         */
        String userId = AppStorage.getUserId(getApplicationContext());
        getNetworkManager().postJsonRequest(NetworkOptions.GET_REQUEST, UrlConstants.USER_LIST_REQUEST + userId, null, REQUEST_USER_LIST);
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onSuccess(JSONObject object, int type, int requestId) {
        if (type == NetworkOptions.JSON_OBJECT_REQUEST && requestId == REQUEST_USER_LIST) {
            Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
            ContactResponse contactResponse = gson.fromJson(object.toString(), ContactResponse.class);
            if (contactResponse != null && contactResponse.getServerStatus().getStatus()) {
                ArrayList<UserDetails> userDetails = new ArrayList<>(Arrays.asList(contactResponse.getUserDetails()));
                mContactRecyclerView.setAdapter(new ContactsArrayAdapters(this, userDetails));
            }
        }
    }
}
