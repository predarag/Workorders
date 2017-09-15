package rs.co.sbb.workorders.entity.sap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Predrag.Tasic on 9/14/2017.
 */

public class Equipment {
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("mac1")
    @Expose
    private String mac1;
    @SerializedName("equipmentType")
    @Expose
    private String equipmentType;
    @SerializedName("mac2")
    @Expose
    private String mac2;
    @SerializedName("materialNumber")
    @Expose
    private String materialNumber;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("mtaMac")
    @Expose
    private String mtaMac;
    @SerializedName("erMac")
    @Expose
    private String erMac;
    @SerializedName("stbMac")
    @Expose
    private String stbMac;
    @SerializedName("cmMac")
    @Expose
    private String cmMac;
    @SerializedName("chipId")
    @Expose
    private String chipId;
    @SerializedName("onufsn")
    @Expose
    private String onufsn;
    @SerializedName("provisioningModel")
    @Expose
    private String provisioningModel;
    @SerializedName("materialDescription")
    @Expose
    private String materialDescription;
    @SerializedName("unitOfMeasurement")
    @Expose
    private String unitOfMeasurement;
    @SerializedName("plant")
    @Expose
    private String plant;
    @SerializedName("storageLocationForSerialNumber")
    @Expose
    private String storageLocationForSerialNumber;
    @SerializedName("valuationTypeForSerialNumber")
    @Expose
    private String valuationTypeForSerialNumber;
    @SerializedName("serviceTeamForStorageLocation")
    @Expose
    private String serviceTeamForStorageLocation;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMac1() {
        return mac1;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getMac2() {
        return mac2;
    }

    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMtaMac() {
        return mtaMac;
    }

    public void setMtaMac(String mtaMac) {
        this.mtaMac = mtaMac;
    }

    public String getErMac() {
        return erMac;
    }

    public void setErMac(String erMac) {
        this.erMac = erMac;
    }

    public String getStbMac() {
        return stbMac;
    }

    public void setStbMac(String stbMac) {
        this.stbMac = stbMac;
    }

    public String getCmMac() {
        return cmMac;
    }

    public void setCmMac(String cmMac) {
        this.cmMac = cmMac;
    }

    public String getChipId() {
        return chipId;
    }

    public void setChipId(String chipId) {
        this.chipId = chipId;
    }

    public String getOnufsn() {
        return onufsn;
    }

    public void setOnufsn(String onufsn) {
        this.onufsn = onufsn;
    }

    public String getProvisioningModel() {
        return provisioningModel;
    }

    public void setProvisioningModel(String provisioningModel) {
        this.provisioningModel = provisioningModel;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getStorageLocationForSerialNumber() {
        return storageLocationForSerialNumber;
    }

    public void setStorageLocationForSerialNumber(String storageLocationForSerialNumber) {
        this.storageLocationForSerialNumber = storageLocationForSerialNumber;
    }

    public String getValuationTypeForSerialNumber() {
        return valuationTypeForSerialNumber;
    }

    public void setValuationTypeForSerialNumber(String valuationTypeForSerialNumber) {
        this.valuationTypeForSerialNumber = valuationTypeForSerialNumber;
    }

    public String getServiceTeamForStorageLocation() {
        return serviceTeamForStorageLocation;
    }

    public void setServiceTeamForStorageLocation(String serviceTeamForStorageLocation) {
        this.serviceTeamForStorageLocation = serviceTeamForStorageLocation;
    }

}
