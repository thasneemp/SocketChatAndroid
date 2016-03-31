package examples.muhammed.com.socketchatandroid.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.cabot.volleyframework.NetworkOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.adapters.ChatAdapter;
import examples.muhammed.com.socketchatandroid.constants.APIConstants;
import examples.muhammed.com.socketchatandroid.constants.AppStorage;
import examples.muhammed.com.socketchatandroid.constants.UrlConstants;
import examples.muhammed.com.socketchatandroid.date_manipulation.DateImpl;
import examples.muhammed.com.socketchatandroid.models.ChatModel;
import examples.muhammed.com.socketchatandroid.models.CommonResponse;
import examples.muhammed.com.socketchatandroid.models.UserDetails;
import examples.muhammed.com.socketchatandroid.socket_impl.SocketImpl;
import examples.muhammed.com.socketchatandroid.views.CButton;
import examples.muhammed.com.socketchatandroid.views.CEditText;


public class ChatActivity extends BaseActivity implements View.OnClickListener, SocketImpl.MessageStatusListener, View.OnFocusChangeListener {
    private static final int REQUEST_GET_STATUS = 1213;
    private ListView mChatListView;
    private CEditText mMessageCEditText;
    private ChatAdapter mChatAdapter;
    private SocketImpl socket;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        setUI();

    }

    private void setUI() {
        mChatListView = (ListView) findViewById(R.id.chatListView);
        mMessageCEditText = (CEditText) findViewById(R.id.chatEditText);
        CButton mSendCButton = (CButton) findViewById(R.id.sendMessageButton);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getIntent().getStringExtra(ContactsActivity.TO_USER_NAME));
        setSupportActionBar(mToolbar);
        customizeToolBar();


        /**
         * Set onclick listener
         */
        mSendCButton.setOnClickListener(this);
        settingAdapter();

        /**
         * Enabling Chat Socket
         */
        socket = SocketImpl.getInstance(AppStorage.getUserId(this));


        mMessageCEditText.setOnFocusChangeListener(this);
        mSendCButton.setOnFocusChangeListener(this);
    }

    private void customizeToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void settingAdapter() {
        ArrayList<ChatModel> mChtModels = new ArrayList<>();
        mChatAdapter = new ChatAdapter(this, mChtModels);
        mChatListView.setAdapter(mChatAdapter);
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onSuccess(JSONObject object, int type, int requestId) {
        if (type == NetworkOptions.JSON_OBJECT_REQUEST && requestId == REQUEST_GET_STATUS) {
            Gson gson = new Gson();
            CommonResponse response = gson.fromJson(object.toString(), CommonResponse.class);
            if (response.getServerStatus().getStatus()) {
                UserDetails userDetails = response.getUserDetails();
                onStatusChangedListener(userDetails.getId(), userDetails.isStatus(), SocketImpl.EVENT_STATUS_ONLINE);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                if (mMessageCEditText.getText().toString().length() > 0) {
                    JSONObject object = getMessageBody(getIntent().getStringExtra(ContactsActivity.TO_USER_ID));
                    if (object != null) {
                        socket.sendMessage(object);
                    }
                    socket.updateMyStatus(SocketImpl.EVENT_STATUS_ONLINE, this, getToUserId());
                    mMessageCEditText.clearFocus();
                    sendToUi(mMessageCEditText.getText().toString(), System.currentTimeMillis() + "", true);
                }
                break;
            default:
                break;
        }
    }

    private void sendToUi(String s, String time, boolean isMine) {
        ChatModel model = new ChatModel(s, DateImpl.getFormattedTime(time), isMine);

        mChatAdapter.addMessage(model);
        mChatListView.setSelection(mChatAdapter.getCount() - 1);
        mMessageCEditText.getText().clear();
    }

    private JSONObject getMessageBody(String toUserId) {
        JSONObject object = new JSONObject();
        try {
            object.put(APIConstants.MESSAGE, mMessageCEditText.getText().toString());
            object.put(APIConstants.TIME, System.currentTimeMillis() + "");
            object.put(APIConstants.TO_ID, toUserId);
            object.put(APIConstants.MY_ID, AppStorage.getUserId(this));
            return object;
        } catch (JSONException jsonObject) {
            return null;
        }
    }

    @Override
    public void onMessageSuccess(final JSONObject model) {
        try {
            if (!(model.getString(APIConstants.MY_ID).equalsIgnoreCase(AppStorage.getUserId(this)))) {
                if (model.getString(APIConstants.MY_ID).equalsIgnoreCase(getToUserId())) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sendToUi(model.getString(APIConstants.MESSAGE), model.getString(APIConstants.TIME), false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getToUserId() {
        return getIntent().getStringExtra(ContactsActivity.TO_USER_ID);
    }

    @Override
    public void onStatusChangedListener(final String my_id, final boolean isOnline, final String statusType) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (my_id.equalsIgnoreCase(getToUserId())) {
                    if (isOnline) {
                        mToolbar.setSubtitle(getString(R.string.online));
                    }
                    if (statusType != null && statusType.equalsIgnoreCase(SocketImpl.EVENT_STATUS_TYPING)) {
                        mToolbar.setSubtitle(getString(R.string.typing));
                    } else if (!isOnline) {
                        mToolbar.setSubtitle(getString(R.string.empty));
                    }
                }
            }
        });

    }


    @Override
    protected void onResume() {
        socket.setOnMessageStatusListener(this);
        socket.updateMyStatus(SocketImpl.EVENT_STATUS_ONLINE, this, getToUserId());
        /**
         * Get Current user status
         */
        getNetworkManager().postJsonRequest(NetworkOptions.GET_REQUEST, UrlConstants.GET_STATUS + getToUserId(), null, REQUEST_GET_STATUS);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AppStorage.setUserLoggedStatus(false, this);
                setResult(RESULT_OK, getIntent());
                finish();
                break;
            case R.id.clearchat:
                mChatAdapter.clear();
                mChatAdapter.notifyDataSetChanged();
                break;
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.chatEditText:
                if (hasFocus) {
                    socket.updateMyStatus(SocketImpl.EVENT_STATUS_TYPING, getApplicationContext(), getToUserId());
                } else {
                    socket.updateMyStatus(SocketImpl.EVENT_STATUS_ONLINE, getApplicationContext(), getToUserId());
                }
                break;
            default:
                socket.updateMyStatus(SocketImpl.EVENT_STATUS_ONLINE, getApplicationContext(), getToUserId());
        }
    }

}
