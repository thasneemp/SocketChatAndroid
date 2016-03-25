package examples.muhammed.com.socketchatandroid.models;

/**
 * Created by thasneem on 25/3/16.
 */
public class UserDetails {
    private String username;

    private String _id;

    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [username = " + username + ", _id = " + _id + ", name = " + name + "]";
    }
}
