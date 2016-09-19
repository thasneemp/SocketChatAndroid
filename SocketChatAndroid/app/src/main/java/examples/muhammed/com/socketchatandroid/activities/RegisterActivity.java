package examples.muhammed.com.socketchatandroid.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.constants.APIConstants;
import examples.muhammed.com.socketchatandroid.constants.UrlConstants;
import examples.muhammed.com.socketchatandroid.socket_impl.NetworkFrameWork;
import examples.muhammed.com.socketchatandroid.socket_impl.NetworkOptions;
import examples.muhammed.com.socketchatandroid.views.CButton;
import examples.muhammed.com.socketchatandroid.views.CEditText;
import examples.muhammed.com.socketchatandroid.views.CTextView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, NetworkFrameWork.OnApiResult {
    private static final int REGISTER_REQUEST_ID = 200;
    private CEditText mNameCEditText;
    private CEditText mUserNameCEditText;
    private CEditText mPasswordCEditText;
    private NetworkFrameWork networkFrameWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUI();
    }

    private void setUI() {
        mNameCEditText = (CEditText) findViewById(R.id.nameEditText);
        mUserNameCEditText = (CEditText) findViewById(R.id.usernameEditText);
        mPasswordCEditText = (CEditText) findViewById(R.id.passwordEditText);
        CButton mRegisterCButton = (CButton) findViewById(R.id.registerButton);
        CTextView mLinkTextView = (CTextView) findViewById(R.id.alreadyRegLinkTextView);


        /**
         * Adding Click Listener
         */
        mRegisterCButton.setOnClickListener(this);
        mLinkTextView.setOnClickListener(this);

        networkFrameWork = new NetworkFrameWork(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                JSONObject object = getFieldValues();
                if (object != null) {
                    networkFrameWork.getApi(NetworkOptions.POST_REQUEST, UrlConstants.REGISTER_REQUEST, object, REGISTER_REQUEST_ID, this);

                }
                break;
            case R.id.registerLinkTextView:
                break;
        }
    }

    private JSONObject getFieldValues() {
        JSONObject object = new JSONObject();
        try {
            String userName = mUserNameCEditText.getText().toString();
            String name = mNameCEditText.getText().toString();
            String password = mPasswordCEditText.getText().toString();

            if (userName.length() == 0) {
                Toast.makeText(getApplicationContext(), getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
            } else if (name.length() == 0) {
                Toast.makeText(getApplicationContext(), getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
            } else if (password.length() == 0) {
                Toast.makeText(getApplicationContext(), getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
            } else {
                object.put(APIConstants.USERNAME, userName);
                object.put(APIConstants.PASSWORD, password);
                object.put(APIConstants.NAME, name);
                return object;
            }


        } catch (JSONException e) {
            return null;
        }
        return null;
    }


    @Override
    public void onResultSucces(JSONObject object, int requestId) {
        if (requestId == REGISTER_REQUEST_ID) {
            Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResultError(VolleyError error) {

    }
}
