package rs.co.sbb.workorders.entity.totaltv;

/**
 * Created by Predrag.Tasic on 9/18/2017.
 */

public class BuildingType {

    private String code;
    private String name;

    public BuildingType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
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

    @Override
    public String toString(){
        return name;
    }
}
