package rs.co.sbb.workorders.entity.totaltv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Predrag.Tasic on 9/12/2017.
 */

public class OperaterInfo {

    @Expose
    @SerializedName("teamId")
    private String teamId;
    @Expose
    @SerializedName("sapTeamId")
    private String sapTeamId;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("channel")
    private String channel;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSapTeamId() {
        return sapTeamId;
    }

    public void setSapTeamId(String sapTeamId) {
        this.sapTeamId = sapTeamId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
