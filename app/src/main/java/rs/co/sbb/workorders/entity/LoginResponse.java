package rs.co.sbb.workorders.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class LoginResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("sessionToken")
    @Expose
    private String sessionToken;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +

                '}';
    }


}

