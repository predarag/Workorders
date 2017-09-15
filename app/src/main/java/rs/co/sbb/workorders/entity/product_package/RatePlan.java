package rs.co.sbb.workorders.entity.product_package;

/**
 * Created by Predrag.Tasic on 9/11/2017.
 */

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatePlan implements Serializable{

    @SerializedName("ratePlanCode")
    @Expose
    private String ratePlanCode;
    @SerializedName("ratePlanName")
    @Expose
    private String ratePlanName;
    @SerializedName("BillingProducts")
    @Expose
    private List<BillingProduct> billingProducts = null;

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getRatePlanName() {
        return ratePlanName;
    }

    public void setRatePlanName(String ratePlanName) {
        this.ratePlanName = ratePlanName;
    }

    public List<BillingProduct> getBillingProducts() {
        return billingProducts;
    }

    public void setBillingProducts(List<BillingProduct> billingProducts) {
        this.billingProducts = billingProducts;
    }

}
