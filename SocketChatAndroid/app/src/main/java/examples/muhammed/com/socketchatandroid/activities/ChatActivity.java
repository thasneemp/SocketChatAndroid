package examples.muhammed.com.socketchatandroid.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.adapters.ChatAdapter;
import examples.muhammed.com.socketchatandroid.models.ChatModel;
import examples.muhammed.com.socketchatandroid.views.CButton;
import examples.muhammed.com.socketchatandroid.views.CEditText;

/**
 * Created by thasneem on 28/3/16.
 */
public class ChatActivity extends BaseActivity implements View.OnClickListener {
    private ListView mChatListView;
    private CEditText mMessageCEditText;
    private CButton mSendCButton;
    private ArrayList<ChatModel> mChtModels;
    private ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        setUI();

    }

    private void setUI() {
        mChatListView = (ListView) findViewById(R.id.chatListView);
        mMessageCEditText = (CEditText) findViewById(R.id.chatEditText);
        mSendCButton = (CButton) findViewById(R.id.sendMessageButton);


        /**
         * Set onclick listener
         */
        mSendCButton.setOnClickListener(this);
        settingAdapter();
    }

    private void settingAdapter() {
        mChtModels = new ArrayList<>();
        mChatAdapter = new ChatAdapter(this, mChtModels);
        mChatListView.setAdapter(mChatAdapter);
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onSuccess(JSONObject object, int type, int requestId) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                ChatModel model = new ChatModel(mMessageCEditText.getText().toString(), "12/12/2016", true);
                mChatAdapter.addMessage(model);
                mChatListView.setSelection(mChatAdapter.getCount() - 1);
                mMessageCEditText.getText().clear();
                break;
            default:
                break;
        }
    }
}
