package rs.co.sbb.workorders.entity.product_package;

/**
 * Created by Predrag.Tasic on 9/11/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPackageOption {

    @SerializedName("productPackage")
    @Expose
    private String productPackage;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("allowanceDuration")
    @Expose
    private String allowanceDuration;
    @SerializedName("optionNumber")
    @Expose
    private String optionNumber;
    @SerializedName("durationUnit")
    @Expose
    private String durationUnit;
    @SerializedName("optPriceKey")
    @Expose
    private String optPriceKey;
    @SerializedName("allowancePriceKey")
    @Expose
    private String allowancePriceKey;

    public String getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(String productPackage) {
        this.productPackage = productPackage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllowanceDuration() {
        return allowanceDuration;
    }

    public void setAllowanceDuration(String allowanceDuration) {
        this.allowanceDuration = allowanceDuration;
    }

    public String getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(String optionNumber) {
        this.optionNumber = optionNumber;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public String getOptPriceKey() {
        return optPriceKey;
    }

    public void setOptPriceKey(String optPriceKey) {
        this.optPriceKey = optPriceKey;
    }

    public String getAllowancePriceKey() {
        return allowancePriceKey;
    }

    public void setAllowancePriceKey(String allowancePriceKey) {
        this.allowancePriceKey = allowancePriceKey;
    }

    @Override
    public String toString() {
        return duration + "M - " +description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPackageOption that = (ProductPackageOption) o;

        if (productPackage != null ? !productPackage.equals(that.productPackage) : that.productPackage != null)
            return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (allowanceDuration != null ? !allowanceDuration.equals(that.allowanceDuration) : that.allowanceDuration != null)
            return false;
        if (optionNumber != null ? !optionNumber.equals(that.optionNumber) : that.optionNumber != null)
            return false;
        if (durationUnit != null ? !durationUnit.equals(that.durationUnit) : that.durationUnit != null)
            return false;
        if (optPriceKey != null ? !optPriceKey.equals(that.optPriceKey) : that.optPriceKey != null)
            return false;
        return allowancePriceKey != null ? allowancePriceKey.equals(that.allowancePriceKey) : that.allowancePriceKey == null;

    }

    @Override
    public int hashCode() {
        int result = productPackage != null ? productPackage.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (allowanceDuration != null ? allowanceDuration.hashCode() : 0);
        result = 31 * result + (optionNumber != null ? optionNumber.hashCode() : 0);
        result = 31 * result + (durationUnit != null ? durationUnit.hashCode() : 0);
        result = 31 * result + (optPriceKey != null ? optPriceKey.hashCode() : 0);
        result = 31 * result + (allowancePriceKey != null ? allowancePriceKey.hashCode() : 0);
        return result;
    }
}
