package examples.muhammed.com.socketchatandroid.models;

/**
 * Created by thasneem on 26/3/16.
 */
public class UserDetails {
    private String username;

    private String _id;

    private String name;
    private boolean status;
    private String status_type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStatusType() {
        return status_type;
    }

    public void setStatusType(String status_type) {
        this.status_type = status_type;
    }
}
