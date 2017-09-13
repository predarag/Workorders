package rs.co.sbb.workorders.entity.product_package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PackageOptionResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("ProductPackageOptions")
    @Expose
    private List<ProductPackageOption> productPackageOptions = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<ProductPackageOption> getProductPackageOptions() {
        return productPackageOptions;
    }

    public void setProductPackageOptions(List<ProductPackageOption> productPackageOptions) {
        this.productPackageOptions = productPackageOptions;
    }

}


