package rs.co.sbb.workorders.entity;

/**
 * Created by Predrag.Tasic on 9/13/2017.
 */

public class CompanyCode {

    private String code;
    private String name;

    public CompanyCode(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
