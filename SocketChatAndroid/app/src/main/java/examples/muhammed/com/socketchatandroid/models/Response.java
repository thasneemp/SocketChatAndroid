package examples.muhammed.com.socketchatandroid.models;

/**
 * Created by thasneem on 25/3/16.
 */
public class Response {
    private String message;

    private boolean status;

    private UserDetails userDetails;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", status = " + status + ", userDetails = " + userDetails + "]";
    }
}
