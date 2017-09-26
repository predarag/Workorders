package rs.co.sbb.workorders.entity.product_package;

/**
 * Created by Predrag.Tasic on 9/11/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BillingProduct implements Serializable {

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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillingProduct that = (BillingProduct) o;

        if (billingProductCode != null ? !billingProductCode.equals(that.billingProductCode) : that.billingProductCode != null)
            return false;
        if (billingProductName != null ? !billingProductName.equals(that.billingProductName) : that.billingProductName != null)
            return false;
        if (billingProductType != null ? !billingProductType.equals(that.billingProductType) : that.billingProductType != null)
            return false;
        if (mappingType != null ? !mappingType.equals(that.mappingType) : that.mappingType != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (serviceType != null ? !serviceType.equals(that.serviceType) : that.serviceType != null)
            return false;
        return activationSelfcare != null ? activationSelfcare.equals(that.activationSelfcare) : that.activationSelfcare == null;

    }

    @Override
    public int hashCode() {
        int result = billingProductCode != null ? billingProductCode.hashCode() : 0;
        result = 31 * result + (billingProductName != null ? billingProductName.hashCode() : 0);
        result = 31 * result + (billingProductType != null ? billingProductType.hashCode() : 0);
        result = 31 * result + (mappingType != null ? mappingType.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (activationSelfcare != null ? activationSelfcare.hashCode() : 0);
        return result;
    }


}