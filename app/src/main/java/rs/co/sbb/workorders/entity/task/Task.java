package rs.co.sbb.workorders.entity.task;

/**
 * Created by Predrag.Tasic on 9/21/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Task implements Serializable{

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("sapTeamId")
    @Expose
    private String sapTeamId;
    @SerializedName("teamId")
    @Expose
    private String teamId;
    @SerializedName("partnerNo")
    @Expose
    private String partnerNo;
    @SerializedName("providerOrderId")
    @Expose
    private String providerOrderId;
    @SerializedName("serviceOrderId")
    @Expose
    private String serviceOrderId;
    @SerializedName("taskStatus")
    @Expose
    private String taskStatus;
    @SerializedName("bpmId")
    @Expose
    private String bpmId;
    @SerializedName("taskId")
    @Expose
    private String taskId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSapTeamId() {
        return sapTeamId;
    }

    public void setSapTeamId(String sapTeamId) {
        this.sapTeamId = sapTeamId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getProviderOrderId() {
        return providerOrderId;
    }

    public void setProviderOrderId(String providerOrderId) {
        this.providerOrderId = providerOrderId;
    }

    public String getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getBpmId() {
        return bpmId;
    }

    public void setBpmId(String bpmId) {
        this.bpmId = bpmId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}