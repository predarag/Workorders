package rs.co.sbb.workorders.entity.totaltv;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalTvActivationHolder {

    @SerializedName("contractNo")
    @Expose
    private String contractNo;
    @SerializedName("products")
    @Expose
    private List<String> products = null;
    @SerializedName("productPackageCode")
    @Expose
    private String productPackageCode;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("houseNum")
    @Expose
    private String houseNum;
    @SerializedName("houseNum2")
    @Expose
    private String houseNum2;
    @SerializedName("floor")
    @Expose
    private String floor;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("building")
    @Expose
    private String building;
    @SerializedName("phone1")
    @Expose
    private String phone1;
    @SerializedName("phone2")
    @Expose
    private String phone2;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("taxnum")
    @Expose
    private String taxnum;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @Expose
    @SerializedName("devices")
    private List<Device> devices;
    @Expose
    @SerializedName("operaterInfo")
    private OperaterInfo operaterInfo;


    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getProductPackageCode() {
        return productPackageCode;
    }

    public void setProductPackageCode(String productPackageCode) {
        this.productPackageCode = productPackageCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getHouseNum2() {
        return houseNum2;
    }

    public void setHouseNum2(String houseNum2) {
        this.houseNum2 = houseNum2;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxnum() {
        return taxnum;
    }

    public void setTaxnum(String taxnum) {
        this.taxnum = taxnum;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public OperaterInfo getOperaterInfo() {
        return operaterInfo;
    }

    public void setOperaterInfo(OperaterInfo operaterInfo) {
        this.operaterInfo = operaterInfo;
    }
}