package examples.muhammed.com.socketchatandroid.socket_impl;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import examples.muhammed.com.socketchatandroid.constants.APIConstants;
import examples.muhammed.com.socketchatandroid.constants.AppStorage;
import examples.muhammed.com.socketchatandroid.constants.UrlConstants;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by thasneem on 24/3/16.
 */
public class SocketImpl {
    private static final java.lang.String EVENT_STATUS = "status";
    private static final String EVENT_STATUS_CALLBACK = "statuscallback";
    public static final String EVENT_STATUS_TYPING = "status_typing";
    public static final String EVENT_STATUS_ONLINE = "status_online";
    public static final String EVENT_STATUS_OFFLINE = "status_offline";
    private static final String EVENT_JOIN = "join";

    private Socket socket;
    private MessageStatusListener onMessageStatusListener;
    private static SocketImpl socketImpl;

    private SocketImpl(String userId) {
        try {
            this.socket = IO.socket(UrlConstants.SOCKET_URL);
            socket.connect();
            Log.d("SOCKET", "connected");


            socket.on(Socket.EVENT_MESSAGE, messageListener);

            socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET", args[0].toString());
                }
            });

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET", "Connect : ");
                }
            });
            socket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET", "Connecting :");
                }
            });
            socket.on(SocketImpl.EVENT_STATUS_CALLBACK, statusListener);
            socket.emit(SocketImpl.EVENT_JOIN, userId);
        } catch (URISyntaxException e) {
            Log.d("SOCKET", "Attempting...!");
            socket.connect();
            e.printStackTrace();
        }
    }


    public static SocketImpl getInstance(String userId) {
        return socketImpl == null ? socketImpl = new SocketImpl(userId) : socketImpl;
    }


    /**
     * Message Listener
     */
    Emitter.Listener messageListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            sendToUi(args[0].toString());
        }
    };

    /**
     * Status Listener
     */
    Emitter.Listener statusListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            if (onMessageStatusListener != null) {
                try {
                    JSONObject object = new JSONObject(args[0].toString());
                    onMessageStatusListener.onStatusChangedListener(object.getString("my_id"), object.getBoolean("status"), object.getString(APIConstants.STATUS_TYPE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void sendToUi(String s) {
        Log.d("SOCKET", s);
        if (onMessageStatusListener != null) {
            try {
                JSONObject object = new JSONObject(s);
                onMessageStatusListener.onMessageSuccess(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public void updateMyStatus(String statusType, Context context, String toId) {
        /**
         * Update my Status
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(APIConstants.STATUS, statusType.equalsIgnoreCase(EVENT_STATUS_ONLINE) || statusType.equalsIgnoreCase(EVENT_STATUS_TYPING) ? true : false);
            jsonObject.put(APIConstants.STATUS_TYPE, statusType);
            jsonObject.put(APIConstants.MY_ID, AppStorage.getUserId(context));
            jsonObject.put(APIConstants.TO_ID, toId);
            sendStatus(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setOnMessageStatusListener(MessageStatusListener onMessageStatusListener) {
        this.onMessageStatusListener = onMessageStatusListener;
    }

    public void sendMessage(JSONObject message) {
        socket.emit(Socket.EVENT_MESSAGE, message);
    }

    private void sendStatus(JSONObject message) {
        socket.emit(SocketImpl.EVENT_STATUS, message);
    }

    public interface MessageStatusListener {
        void onMessageSuccess(JSONObject model);

        void onStatusChangedListener(String my_id, boolean isOnline, String statusType);
    }

    public void disconnectSocket() {
        socket.disconnect();
    }
}
