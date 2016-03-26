package examples.muhammed.com.socketchatandroid.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cabot.volleyframework.NetworkOptions;

import org.json.JSONException;
import org.json.JSONObject;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.constants.APIConstants;
import examples.muhammed.com.socketchatandroid.constants.UrlConstants;
import examples.muhammed.com.socketchatandroid.views.CButton;
import examples.muhammed.com.socketchatandroid.views.CEditText;
import examples.muhammed.com.socketchatandroid.views.CTextView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final int REGISTER_REQUEST_ID = 200;
    private CEditText mNameCEditText;
    private CEditText mUserNameCEditText;
    private CEditText mPasswordCEditText;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                JSONObject object = getFieldValues();
                if (object != null) {
                    getNetworkManager().postJsonRequest(NetworkOptions.POST_REQUEST, UrlConstants.REGISTER_REQUEST, object, REGISTER_REQUEST_ID);
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
    public void onError(VolleyError error) {

    }

    @Override
    public void onSuccess(JSONObject object, int type, int requestId) {
        if (type == NetworkOptions.JSON_OBJECT_REQUEST && requestId == REGISTER_REQUEST_ID) {
            Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
