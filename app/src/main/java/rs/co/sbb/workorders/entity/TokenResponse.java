package rs.co.sbb.workorders.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Predrag.Tasic on 8/10/2017.
 */


public class TokenResponse {
    @SerializedName("user")
    @Expose
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @SerializedName("tokne")
    @Expose
    private String token;
}
