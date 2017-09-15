package rs.co.sbb.workorders.entity.sap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Predrag.Tasic on 9/14/2017.
 */

public class CheckEquipmentResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("GetEquipment")
    @Expose
    private Equipment equipment;

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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
