package rs.co.sbb.workorders.entity;

/**
 * Created by Predrag.Tasic on 8/17/2017.
 */

public class Workorder {

    private String workorderNo;
    private String workorderType;

    public Workorder(String workorderNo, String workorderType) {
        this.workorderNo = workorderNo;
        this.workorderType = workorderType;
    }

    public String getWorkorderNo() {
        return workorderNo;
    }

    public void setWorkorderNo(String workorderNo) {
        this.workorderNo = workorderNo;
    }

    public String getWorkorderType() {
        return workorderType;
    }

    public void setWorkorderType(String workorderType) {
        this.workorderType = workorderType;
    }
}
