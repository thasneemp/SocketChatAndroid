package examples.muhammed.com.socketchatandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.cabot.volleyframework.NetworkManager;

import org.json.JSONObject;

/**
 * Created by thasneem on 25/3/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements NetworkManager.OnNetWorkListener {
    private NetworkManager mNetworkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mNetworkManager = NetworkManager.getInstance(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        mNetworkManager.setOnNetworkListener(this);
        super.onResume();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        onError(error);
    }

    @Override
    public void onResponse(JSONObject object, int type, int requestId) {
        onSuccess(object, type, requestId);
    }

    public abstract void onError(VolleyError error);

    public abstract void onSuccess(JSONObject object, int type, int requestId);

    public NetworkManager getNetworkManager() {
        return mNetworkManager;
    }
}
