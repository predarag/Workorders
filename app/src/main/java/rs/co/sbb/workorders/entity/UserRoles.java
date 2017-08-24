package rs.co.sbb.workorders.entity;

/**
 * Created by Predrag.Tasic on 8/22/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRoles {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("roles")
    @Expose
    private List<Role> roles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}