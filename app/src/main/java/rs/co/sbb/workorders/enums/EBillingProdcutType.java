package rs.co.sbb.workorders.enums;

/**
 * Created by Predrag.Tasic on 9/11/2017.
 */

public enum EBillingProdcutType {


    INCLUDED("INCLUDED"),
    ADDON("AddOn"),
    TTV_HOME("TotalTV Kuca");

    private String type;

    EBillingProdcutType(String type) {
        this.type = type;
    }

    public String value() {
        return type;
    }

}
