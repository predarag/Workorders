package rs.co.sbb.workorders.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rs.co.sbb.workorders.entity.totaltv.Device;
import rs.co.sbb.workorders.entity.totaltv.OperaterInfo;
import rs.co.sbb.workorders.entity.totaltv.TotalTvActivationHolder;
import rs.co.sbb.workorders.utils.SaveSharedPreference;
import rs.co.sbb.workorders.utils.Utils;
import rs.co.sbb.workorders.wizards.pages.TTVActivationStepThreePage;
import rs.co.sbb.workorders.wizards.pages.TTVActivationStepTwoPage;
import rs.co.sbb.workorders.wizards.pages.TTVPlacesStepOnePage;
import rs.co.sbb.workorders.wizards.wizardpager.model.Page;

/**
 * Created by Predrag.Tasic on 9/12/2017.
 */

public class TotalTvActivationHelper {


    private static final String TAG = "TotalTvActivationHelper";

    public static TotalTvActivationHolder createRequest(List<Page> mCurrentPageSequence, Context context){

        TotalTvActivationHolder request = null;

        if(mCurrentPageSequence != null  && mCurrentPageSequence.size() != 0) {

            request = new TotalTvActivationHolder();

            request = setUserData(mCurrentPageSequence, request, context);

            if(null != mCurrentPageSequence.get(1).getData().getStringArrayList(TTVActivationStepTwoPage.BILLING_PRODUCTS_DATA_KEY)) {
                ArrayList<String> bps = mCurrentPageSequence.get(1).getData().getStringArrayList(TTVActivationStepTwoPage.BILLING_PRODUCTS_DATA_KEY);
                for (String string : bps)
                    Log.i(TAG, "BP: " + string);
                request.setProducts(bps);
            }

            if(null != mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY) &&
                      !TextUtils.isEmpty(mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY).toString())) {
                Log.i(TAG, mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY).toString());
                request.setProductPackageCode(mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY).toString());

            }

            if(null != mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PACKAGE_OPTION_DATA_KEY) &&
                    !TextUtils.isEmpty(mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PACKAGE_OPTION_DATA_KEY).toString())) {
                Log.i(TAG, mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PACKAGE_OPTION_DATA_KEY).toString());
                request.setOptionCode(mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PACKAGE_OPTION_DATA_KEY).toString());

            }

            if(null != mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PACKAGE_OPTION_DURATION_DATA_KEY) &&
                    !TextUtils.isEmpty(mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PACKAGE_OPTION_DURATION_DATA_KEY).toString())) {
                Log.i(TAG, mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PACKAGE_OPTION_DURATION_DATA_KEY).toString());
                request.setOptionDuration(mCurrentPageSequence.get(1).getData().get(TTVActivationStepTwoPage.PACKAGE_OPTION_DURATION_DATA_KEY).toString());

            }


            request.setDevices(setDeviceData(mCurrentPageSequence));

            OperaterInfo operaterInfo = new OperaterInfo();
            operaterInfo.setUsername(SaveSharedPreference.getUser(context));
            operaterInfo.setSapTeamId(SaveSharedPreference.getSapTeamId(context));
            operaterInfo.setTeamId(SaveSharedPreference.getTeamUniqueId(context));
            operaterInfo.setChannel("USER_INIT");

            request.setOperaterInfo(operaterInfo);



        }


        return  request;

    }

    private static List<Device> setDeviceData(List<Page> mCurrentPageSequence) {

        List<Device> devices = new ArrayList<>();

        String serial1 = null;
        String serial2 = null;
        String serial3 = null;
        String serial4 = null;

        String mac1 = null;
        String mac2 = null;
        String mac3 = null;
        String mac4 = null;


        if(null != mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO1_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO1_DATA_KEY).toString()))
            serial1 = mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO1_DATA_KEY).toString();

        if(null != mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO2_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO2_DATA_KEY).toString()))
            serial2 = mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO2_DATA_KEY).toString();

        if(null != mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO3_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO3_DATA_KEY).toString()))
            serial3 = mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO3_DATA_KEY).toString();

        if(null != mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO4_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO4_DATA_KEY).toString()))
            serial4 = mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.SERIAL_NO4_DATA_KEY).toString();

        if(null != mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC1_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC1_DATA_KEY).toString()))
            mac1 = mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC1_DATA_KEY).toString();

        if(null != mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC2_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC2_DATA_KEY).toString()))
            mac2 = mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC2_DATA_KEY).toString();

        if(null != mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC3_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC3_DATA_KEY).toString()))
            mac3 = mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC3_DATA_KEY).toString();


        if(null != mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC4_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC4_DATA_KEY).toString()))
            mac4 = mCurrentPageSequence.get(2).getData().get(TTVActivationStepThreePage.MAC4_DATA_KEY).toString();

        if(null != serial1 && null != mac1)
            devices.add(new Device(serial1, mac1,1));

        if(null != serial2 && null != mac2)
            devices.add(new Device(serial2, mac2,2));

        if(null != serial3 && null != mac3)
            devices.add(new Device(serial3, mac3,3));
        if(null != serial4 && null != mac4)
            devices.add(new Device(serial4, mac4,4));



        return  devices;
    }

    private static TotalTvActivationHolder setUserData(List<Page> mCurrentPageSequence, TotalTvActivationHolder request, Context context) {
        String firstName = null;
        String lastName= null;
        String community = null;
        String settelment = null;
        String street = null;
        String postCode = null;
        String houseNO = null;
        String subNo = "0000";
        String fixNumber = null;
        String mobNumber = null;
        String email = null;
        String floor = "0000";
        String room = "0000";
        String buildingType = "ZG";




        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FIRSTNAME_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FIRSTNAME_DATA_KEY).toString())) {
            Log.i(TAG, mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FIRSTNAME_DATA_KEY).toString());
            firstName = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FIRSTNAME_DATA_KEY).toString().toUpperCase();
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.LASTNAME_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.LASTNAME_DATA_KEY).toString())) {
            Log.i(TAG, mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.LASTNAME_DATA_KEY).toString());
            lastName = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.LASTNAME_DATA_KEY).toString().toUpperCase();
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.COMMUNITY_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.COMMUNITY_DATA_KEY).toString())) {
            community = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.COMMUNITY_DATA_KEY).toString();
            Log.i(TAG, mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.COMMUNITY_DATA_KEY).toString());
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.SETTLEMENT_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.SETTLEMENT_DATA_KEY).toString())) {
            Log.i(TAG, mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.SETTLEMENT_DATA_KEY).toString());
            settelment = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.SETTLEMENT_DATA_KEY).toString();
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.STREET_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.STREET_DATA_KEY).toString()))
            street = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.STREET_DATA_KEY).toString();

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.POST_CODE_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.POST_CODE_DATA_KEY).toString())) {
            Log.i(TAG, mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.POST_CODE_DATA_KEY).toString());
            postCode = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.POST_CODE_DATA_KEY).toString();
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.HOUSE_NO_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.HOUSE_NO_DATA_KEY).toString())) {
            Log.i(TAG, mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.HOUSE_NO_DATA_KEY).toString());
            houseNO = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.HOUSE_NO_DATA_KEY).toString();
            houseNO = Utils.generateHouseNoString(houseNO);
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.SUB_HOUSE_NO_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.SUB_HOUSE_NO_DATA_KEY).toString())) {
            Log.i(TAG, mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.SUB_HOUSE_NO_DATA_KEY).toString());
            subNo = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.SUB_HOUSE_NO_DATA_KEY).toString();
            subNo = Utils.generateHouseNoString(subNo);
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FIX_NUMBER_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FIX_NUMBER_DATA_KEY).toString())) {
            Log.i(TAG, mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FIX_NUMBER_DATA_KEY).toString());
            fixNumber = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FIX_NUMBER_DATA_KEY).toString();
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.MOBILE_NUMBER_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.MOBILE_NUMBER_DATA_KEY).toString()))
            mobNumber = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.MOBILE_NUMBER_DATA_KEY).toString();

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.EMAIL_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.EMAIL_DATA_KEY).toString())) {
            Log.i(TAG,"email: "+mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.EMAIL_DATA_KEY).toString());
            email = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.EMAIL_DATA_KEY).toString().toUpperCase();
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FLOOR_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FLOOR_DATA_KEY).toString())) {
            Log.i(TAG,"floor: "+mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FLOOR_DATA_KEY).toString());
            floor = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.FLOOR_DATA_KEY).toString();
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.ROOM_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.ROOM_DATA_KEY).toString())) {
            Log.i(TAG,"room: "+mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.ROOM_DATA_KEY).toString());
            room = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.ROOM_DATA_KEY).toString();
        }

        if(null != mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.BUILDING_TYPE_DATA_KEY) &&
                !TextUtils.isEmpty(mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.BUILDING_TYPE_DATA_KEY).toString())) {
            Log.i(TAG,"Building Type: "+mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.BUILDING_TYPE_DATA_KEY).toString());
            buildingType = mCurrentPageSequence.get(0).getData().get(TTVPlacesStepOnePage.BUILDING_TYPE_DATA_KEY).toString();
        }


        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setCity("BEOGRAD");
        request.setRegion("BEOGRAD-NOVI BEOGRAD");
        request.setStreet("OMLADINSKIH BRIGADA");
        request.setPostcode(postCode);
        request.setHouseNum(houseNO);
        request.setHouseNum2(subNo);
        request.setPhone1(fixNumber);
        request.setMobilePhone(mobNumber);
        request.setEmail(email);
        request.setFloor(floor);
        request.setRoom(room);
        request.setCountry(SaveSharedPreference.getCountryCode(context));
        request.setBuilding(buildingType);

        return request;
    }


}
