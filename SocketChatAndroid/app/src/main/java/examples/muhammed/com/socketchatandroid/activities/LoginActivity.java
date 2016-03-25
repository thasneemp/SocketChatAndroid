package examples.muhammed.com.socketchatandroid.activities;

import android.os.Bundle;
import android.view.View;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.views.CButton;
import examples.muhammed.com.socketchatandroid.views.CEditText;
import examples.muhammed.com.socketchatandroid.views.CTextView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private CEditText mUsernameCEditText;
    private CEditText mPasswordCEditText;
    private CButton mLoginCButton;
    private CTextView mRegisterLinkCTextView;


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:

                break;
            case R.id.registerLinkTextView:
                break;
        }
    }
}
