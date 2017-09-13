package rs.co.sbb.workorders.entity.product_package;

/**
 * Created by Predrag.Tasic on 9/11/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingProduct {

    @SerializedName("billingProductCode")
    @Expose
    private String billingProductCode;
    @SerializedName("billingProductName")
    @Expose
    private String billingProductName;
    @SerializedName("billingProductType")
    @Expose
    private String billingProductType;
    @SerializedName("mappingType")
    @Expose
    private String mappingType;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("serviceType")
    @Expose
    private String serviceType;
    @SerializedName("activationSelfcare")
    @Expose
    private String activationSelfcare;

    public String getBillingProductCode() {
        return billingProductCode;
    }

    public void setBillingProductCode(String billingProductCode) {
        this.billingProductCode = billingProductCode;
    }

    public String getBillingProductName() {
        return billingProductName;
    }

    public void setBillingProductName(String billingProductName) {
        this.billingProductName = billingProductName;
    }

    public String getBillingProductType() {
        return billingProductType;
    }

    public void setBillingProductType(String billingProductType) {
        this.billingProductType = billingProductType;
    }

    public String getMappingType() {
        return mappingType;
    }

    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getActivationSelfcare() {
        return activationSelfcare;
    }

    public void setActivationSelfcare(String activationSelfcare) {
        this.activationSelfcare = activationSelfcare;
    }

}