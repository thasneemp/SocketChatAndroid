package examples.muhammed.com.socketchatandroid.models;

/**
 * Created by thasneem on 28/3/16.
 */
public class ChatModel {
    private String message;
    private String time;
    private boolean status;
    private boolean mine;

    public ChatModel() {
    }

    public ChatModel(String message, String time, boolean mine) {
        this.message = message;
        this.time = time;
        this.mine = mine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
