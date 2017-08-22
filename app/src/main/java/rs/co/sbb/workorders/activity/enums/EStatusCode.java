package rs.co.sbb.workorders.activity.enums;

/**
 * Created by Predrag.Tasic on 8/21/2017.
 */

public enum EStatusCode {
    OK("OK"),
    ERROR("ERROR");

    private String name;

    EStatusCode(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
