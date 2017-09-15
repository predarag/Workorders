package rs.co.sbb.workorders.entity.product_package;

/**
 * Created by Predrag.Tasic on 9/11/2017.
 */

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPackage implements Serializable{

    @SerializedName("productPackageName")
    @Expose
    private String productPackageName;
    @SerializedName("productPackageFamilyId")
    @Expose
    private String productPackageFamilyId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("productPackageCode")
    @Expose
    private String productPackageCode;
    @SerializedName("validTo")
    @Expose
    private String validTo;
    @SerializedName("RatePlans")
    @Expose
    private List<RatePlan> ratePlans = null;

    public String getProductPackageName() {
        return productPackageName;
    }

    public void setProductPackageName(String productPackageName) {
        this.productPackageName = productPackageName;
    }

    public String getProductPackageFamilyId() {
        return productPackageFamilyId;
    }

    public void setProductPackageFamilyId(String productPackageFamilyId) {
        this.productPackageFamilyId = productPackageFamilyId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductPackageCode() {
        return productPackageCode;
    }

    public void setProductPackageCode(String productPackageCode) {
        this.productPackageCode = productPackageCode;
    }

    public Object getValidTo() {
        return validTo;
    }

    public void setValidTod(String validTo) {
        this.validTo = validTo;
    }


    @Override
    public String toString() {
        return productPackageName;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPackage that = (ProductPackage) o;

        if (productPackageName != null ? !productPackageName.equals(that.productPackageName) : that.productPackageName != null)
            return false;
        if (productPackageFamilyId != null ? !productPackageFamilyId.equals(that.productPackageFamilyId) : that.productPackageFamilyId != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (productPackageCode != null ? !productPackageCode.equals(that.productPackageCode) : that.productPackageCode != null)
            return false;
        if (validTo != null ? !validTo.equals(that.validTo) : that.validTo != null)
            return false;

        return ratePlans != null ? ratePlans.equals(that.ratePlans) : that.ratePlans == null;

    }

    @Override
    public int hashCode() {
        int result = productPackageName != null ? productPackageName.hashCode() : 0;
        result = 31 * result + (productPackageFamilyId != null ? productPackageFamilyId.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (productPackageCode != null ? productPackageCode.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        result = 31 * result + (ratePlans != null ? ratePlans.hashCode() : 0);
        return result;
    }


    public List<RatePlan> getRatePlans() {
        return ratePlans;
    }

    public void setRatePlans(List<RatePlan> ratePlans) {
        this.ratePlans = ratePlans;
    }

}