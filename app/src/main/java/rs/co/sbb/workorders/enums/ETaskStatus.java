package rs.co.sbb.workorders.enums;

/**
 * Created by Predrag.Tasic on 9/21/2017.
 */

public enum ETaskStatus {

    COMPLETED("COMPLETED"),
    IN_PROCESS("IN PROCESS");

    private String status;

    ETaskStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }


}
