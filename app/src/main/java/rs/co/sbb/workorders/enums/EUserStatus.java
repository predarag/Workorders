package rs.co.sbb.workorders.enums;

/**
 * Created by Predrag.Tasic on 8/21/2017.
 */

public enum EUserStatus {

    ACTIVE("1"),
    INACTIVE("0");

    private String status;

    EUserStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
