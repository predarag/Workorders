package rs.co.sbb.workorders.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Predrag.Tasic on 8/10/2017.
 */


public class NotificationTokenRequest {
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("token")
    @Expose
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
