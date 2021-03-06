package examples.muhammed.com.socketchatandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.constants.APIConstants;
import examples.muhammed.com.socketchatandroid.constants.AppStorage;
import examples.muhammed.com.socketchatandroid.constants.UrlConstants;
import examples.muhammed.com.socketchatandroid.models.CommonResponse;
import examples.muhammed.com.socketchatandroid.socket_impl.NetworkFrameWork;
import examples.muhammed.com.socketchatandroid.socket_impl.NetworkOptions;
import examples.muhammed.com.socketchatandroid.views.CButton;
import examples.muhammed.com.socketchatandroid.views.CEditText;
import examples.muhammed.com.socketchatandroid.views.CTextView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, NetworkFrameWork.OnApiResult {
    private static final int LOGIN_REQUEST_ID = 100;


    private CEditText mUsernameCEditText;
    private CEditText mPasswordCEditText;
    private NetworkFrameWork networkFrameWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppStorage.isUserLogged(this)) {
            startActivity(new Intent(this, ContactsActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        /**
         * Initiating UI
         */
        setUI();
    }

    private void setUI() {
        mUsernameCEditText = (CEditText) findViewById(R.id.usernameEditText);
        mPasswordCEditText = (CEditText) findViewById(R.id.passwordEditText);
        CButton mLoginCButton = (CButton) findViewById(R.id.loginButton);
        CTextView mRegisterLinkCTextView = (CTextView) findViewById(R.id.registerLinkTextView);

        /**
         * Adding clickListener
         */
        mLoginCButton.setOnClickListener(this);
        mRegisterLinkCTextView.setOnClickListener(this);

        networkFrameWork = new NetworkFrameWork(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                JSONObject object = getCredentials();
                if (object != null)
                    networkFrameWork.getApi(NetworkOptions.POST_REQUEST, UrlConstants.LOGIN_REQUEST, object, LOGIN_REQUEST_ID, this);
//                    getNetworkManager().postJsonRequest(NetworkOptions.POST_REQUEST, UrlConstants.LOGIN_REQUEST, object, LOGIN_REQUEST_ID);
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
                object.put(APIConstants.USERNAME, username);
                object.put(APIConstants.PASSWORD, password);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.please_enter_the_credentials), Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception unused) {
            return null;
        }
        return object;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == BaseActivity.FINISH) {
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onResultSucces(JSONObject jsonObject, int id) {
        if (id == LOGIN_REQUEST_ID) {
            Gson gson = new Gson();
            CommonResponse commonResponse = gson.fromJson(jsonObject.toString(), CommonResponse.class);
            if (commonResponse != null) {
                if (commonResponse.getServerStatus().getStatus()) {
                    AppStorage.saveUserDetails(commonResponse.getUserDetails(), getApplicationContext());
                    startActivityForResult(new Intent(this, ContactsActivity.class), BaseActivity.FINISH);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.newtwork_check), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResultError(VolleyError error) {

    }
}
