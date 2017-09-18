package rs.co.sbb.workorders.entity.totaltv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Predrag.Tasic on 9/12/2017.
 */

public class Device {

    @Expose
    @SerializedName("cardSerialNo")
    private String cardSerialNo;

    @Expose
    @SerializedName("receiverSerialNO")
    private String reciverSerialNo;

    @Expose
    @SerializedName("position")
    private int position;

    @Expose
    @SerializedName("provisioningModel")
    private String provisioningModel;

    @Expose
    @SerializedName("equipmentType")
    private String equipmentType;

    public Device(String cardSerialNo, String reciverSerialNo, int position){
        this.cardSerialNo = cardSerialNo;
        this.reciverSerialNo = reciverSerialNo;
        this.position = position;
    }


    public String getCardSerialNo() {
        return cardSerialNo;
    }

    public void setCardSerialNo(String cardSerialNo) {
        this.cardSerialNo = cardSerialNo;
    }

    public String getReciverSerialNo() {
        return reciverSerialNo;
    }

    public void setReciverSerialNo(String reciverSerialNo) {
        this.reciverSerialNo = reciverSerialNo;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getProvisioningModel() {
        return provisioningModel;
    }

    public void setProvisioningModel(String provisioningModel) {
        this.provisioningModel = provisioningModel;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }
}
