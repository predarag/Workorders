package rs.co.sbb.workorders.enums;

/**
 * Created by Predrag.Tasic on 9/11/2017.
 */

public enum ECountryCode {

    RS("RS"),
    SI("SI");

    private String code;

    ECountryCode(String code) {
        this.code = code;
    }

    public String value() {
        return code;
    }
}
