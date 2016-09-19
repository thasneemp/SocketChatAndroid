package examples.muhammed.com.socketchatandroid.socket_impl;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import examples.muhammed.com.socketchatandroid.activities.BaseActivity;

/**
 * Created by muhammed on 9/19/2016.
 */
public class NetworkFrameWork {
    private Context context;

    public NetworkFrameWork(Context context) {
        setContext(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }


    public void getApi(int requestType, String url, JSONObject object, final int loginRequestId, final OnApiResult onApiResult) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(requestType,
                url, object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (onApiResult != null) {
                            onApiResult.onResultSucces(response, loginRequestId);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (onApiResult != null) {
                    onApiResult.onResultError(error);
                }

            }
        });

        // Adding request to request queue
        BaseActivity.getInstance().addToRequestQueue(jsonObjReq, "" + loginRequestId);
    }

    public interface OnApiResult {
        void onResultSucces(JSONObject jsonObject, int id);

        void onResultError(VolleyError error);
    }
}
