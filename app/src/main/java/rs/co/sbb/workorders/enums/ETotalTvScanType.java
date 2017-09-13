package rs.co.sbb.workorders.enums;

/**
 * Created by Predrag.Tasic on 9/1/2017.
 */

public enum ETotalTvScanType {

    BUTTON_SERIAL_1("SERIAL1"),
    BUTTON_SERIAL_2("SERIAL2"),
    BUTTON_SERIAL_3("SERIAL3"),
    BUTTON_SERIAL_4("SERIAL4"),
    BUTTON_BOX_1("BOX1"),
    BUTTON_BOX_2("BOX2"),
    BUTTON_BOX_3("BOX3"),
    BUTTON_BOX_4("BOX4");


    private String scanType;

    ETotalTvScanType(String scanType) {
        this.scanType = scanType;
    }


    public String getScanType() {
        return scanType;
    }

}
