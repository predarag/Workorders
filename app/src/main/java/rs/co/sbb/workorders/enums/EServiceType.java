package rs.co.sbb.workorders.enums;

/**
 * Created by Predrag.Tasic on 9/11/2017.
 */

public enum EServiceType {

    DTH("DTH");

    private String type;

    EServiceType(String code) {
        this.type = code;
    }

    public String value() {
        return type;
    }

}
