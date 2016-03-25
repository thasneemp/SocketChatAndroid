package examples.muhammed.com.socketchatandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cabot.volleyframework.NetworkManager;
import com.cabot.volleyframework.NetworkOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.constants.AppStorage;
import examples.muhammed.com.socketchatandroid.constants.UrlConstants;
import examples.muhammed.com.socketchatandroid.models.Response;
import examples.muhammed.com.socketchatandroid.views.CButton;
import examples.muhammed.com.socketchatandroid.views.CEditText;
import examples.muhammed.com.socketchatandroid.views.CTextView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, NetworkManager.OnNetWorkListener {
    private static final int LOGIN_REQUEST_ID = 100;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private CEditText mUsernameCEditText;
    private CEditText mPasswordCEditText;
    private CButton mLoginCButton;
    private CTextView mRegisterLinkCTextView;
    private NetworkManager mNetworkManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /**
         * Initiating UI
         */
        setUI();
    }

    private void setUI() {
        mUsernameCEditText = (CEditText) findViewById(R.id.usernameEditText);
        mPasswordCEditText = (CEditText) findViewById(R.id.passwordEditText);
        mLoginCButton = (CButton) findViewById(R.id.loginButton);
        mRegisterLinkCTextView = (CTextView) findViewById(R.id.registerLinkTextView);

        /**
         * Adding clickListener
         */
        mLoginCButton.setOnClickListener(this);
        mRegisterLinkCTextView.setOnClickListener(this);

        mNetworkManager = NetworkManager.getInstance(this);

        mNetworkManager.setOnNetworkListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                JSONObject object = getCredentials();
                if (object != null)
                    mNetworkManager.postJsonRequest(NetworkOptions.POST_REQUEST, UrlConstants.LOGIN_REQUEST, object, LOGIN_REQUEST_ID);
                break;
            case R.id.registerLinkTextView:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    /**
     * Mapping Credentials
     *
     * @return JSONObject
     */
    private JSONObject getCredentials() {
        JSONObject object = new JSONObject();
        String username = mUsernameCEditText.getText().toString();
        String password = mPasswordCEditText.getText().toString();
        try {
            if (username.length() > 0 && password.length() > 0) {
                object.put(USERNAME, username);
                object.put(PASSWORD, password);
            } else {
                Toast.makeText(getApplicationContext(), "Please enter the credentials", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception unused) {
            return null;
        }
        return object;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject object, int type, int requestId) {
        if (type == NetworkOptions.JSON_OBJECT_REQUEST) {
            if (requestId == LOGIN_REQUEST_ID) {
                Gson gson = new Gson();
                Response response = gson.fromJson(object.toString(), Response.class);
                if (response.getStatus()) {
                    AppStorage.saveUserDetails(response.getUserDetails(), getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
