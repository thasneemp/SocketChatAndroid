package examples.muhammed.com.socketchatandroid.models;

/**
 * Created by thasneem on 26/3/16.
 */
public class ContactResponse {
    private UserDetails[] userDetails;

    private ServerStatus server_status;

    public UserDetails[] getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails[] userDetails) {
        this.userDetails = userDetails;
    }

    public ServerStatus getServerStatus() {
        return server_status;
    }

    public void setServerStatus(ServerStatus server_status) {
        this.server_status = server_status;
    }

}
