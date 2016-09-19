package examples.muhammed.com.socketchatandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.VolleyError;
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
import examples.muhammed.com.socketchatandroid.socket_impl.NetworkFrameWork;
import examples.muhammed.com.socketchatandroid.socket_impl.NetworkOptions;
import examples.muhammed.com.socketchatandroid.socket_impl.SocketImpl;


public class ContactsActivity extends BaseActivity implements ContactsArrayAdapters.OnItemSelectedListener, NetworkFrameWork.OnApiResult {
    private static final int REQUEST_USER_LIST = 123;
    public static final String TO_USER_ID = "to_user_id";
    public static final String TO_USER_NAME = "to_user_name";
    private RecyclerView mContactRecyclerView;
    private NetworkFrameWork networkFrameWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        setUI();
    }

    private void setUI() {
        networkFrameWork = new NetworkFrameWork(this);
        mContactRecyclerView = (RecyclerView) findViewById(R.id.contactListRecyclerView);
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        /**
         * Call API to get data.
         */
        String userId = AppStorage.getUserId(getApplicationContext());
        networkFrameWork.getApi(NetworkOptions.GET_REQUEST, UrlConstants.USER_LIST_REQUEST + userId, null, REQUEST_USER_LIST, this);

        /**
         * Enabling socket
         */

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == BaseActivity.FINISH) {
                setResult(RESULT_OK, data);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        SocketImpl.getInstance(AppStorage.getUserId(this)).updateMyStatus(SocketImpl.EVENT_STATUS_ONLINE, this, AppStorage.getUserId(this));
        super.onResume();
    }

    @Override
    public void onItemSelected(UserDetails details, int position) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(TO_USER_ID, details.getId());
        intent.putExtra(TO_USER_NAME, details.getName());
        startActivityForResult(intent, BaseActivity.FINISH);
    }

    @Override
    protected void onDestroy() {
        SocketImpl.getInstance(AppStorage.getUserId(this)).updateMyStatus(SocketImpl.EVENT_STATUS_OFFLINE, this, AppStorage.getUserId(this));
        super.onDestroy();
    }

    @Override
    protected void onPause() {
//        updateMyStatus(false);
        super.onPause();
    }

    @Override
    public void onResultSucces(JSONObject jsonObject, int id) {
        if (id == REQUEST_USER_LIST) {
            Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
            ContactResponse contactResponse = gson.fromJson(jsonObject.toString(), ContactResponse.class);
            if (contactResponse != null && contactResponse.getServerStatus().getStatus()) {
                ArrayList<UserDetails> userDetails = new ArrayList<>(Arrays.asList(contactResponse.getUserDetails()));
                ContactsArrayAdapters adapters = new ContactsArrayAdapters(this, userDetails);
                adapters.setOnItemSelectedListener(this);
                mContactRecyclerView.setAdapter(adapters);


            }
        }
    }

    @Override
    public void onResultError(VolleyError error) {

    }
}
