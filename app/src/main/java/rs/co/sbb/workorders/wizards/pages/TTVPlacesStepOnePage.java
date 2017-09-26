package rs.co.sbb.workorders.wizards.pages;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import rs.co.sbb.workorders.wizards.wizardpager.model.ModelCallbacks;
import rs.co.sbb.workorders.wizards.wizardpager.model.Page;
import rs.co.sbb.workorders.wizards.wizardpager.model.ReviewItem;

/**
 * Created by milos.milic on 8/17/2017.
 */

public class TTVPlacesStepOnePage extends Page {

    public static final String FIRSTNAME_DATA_KEY = "FIRSTNAME_DATA_KEY";
    public static final String LASTNAME_DATA_KEY = "LASTNAME_DATA_KEY";
    public static final String JMBG_DATA_KEY = "JMBG_DATA_KEY";
    public static final String CITY_DATA_KEY = "CITY_DATA_KEY";
    public static final String REGION_DATA_KEY = "REGION_DATA_KEY";
    public static final String STREET_DATA_KEY = "STREET_DATA_KEY";
    public static final String HOUSE_NO_DATA_KEY = "HOUSE_NO_DATA_KEY";
    public static final String SUB_HOUSE_NO_DATA_KEY = "SUB_HOUSE_NO_DATA_KEY";
    public static final String POST_CODE_DATA_KEY = "POST_CODE_DATA_KEY";
    public static final String FIX_NUMBER_DATA_KEY = "FIX_NUMBER_DATA_KEY";
    public static final String MOBILE_NUMBER_DATA_KEY = "MOBILE_NUMBER_DATA_KEY";
    public static final String EMAIL_DATA_KEY = "EMAIL_DATA_KEY";
    public static final String FLOOR_DATA_KEY = "FLOOR_DATA_KEY";
    public static final String ROOM_DATA_KEY = "ROOM_DATA_KEY";
    public static final String BUILDING_TYPE_DATA_KEY = "BUILDING_TYPE_DATA_KEY";


/*    private boolean completedPar = false;

    public boolean isCompletedPar() {
       return completedPar;
    }

   public void setCompletedPar(boolean completedPar) {
        this.completedPar = completedPar;
    }*/

    public TTVPlacesStepOnePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TTVPlacesStepOneFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Ime", mData.getString(FIRSTNAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Prezime", mData.getString(LASTNAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("JMBG", mData.getString(JMBG_DATA_KEY), getKey(), -1));

        // TODO add all items from step1 that can  be eddited
    }

    @Override
    public boolean isCompleted() {
        Log.i("PAGE", "isCompleted");
        return !TextUtils.isEmpty(mData.getString(FIRSTNAME_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(LASTNAME_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(CITY_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(STREET_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(REGION_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(HOUSE_NO_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(POST_CODE_DATA_KEY));


    }

    public TTVPlacesStepOnePage setFirstName(String firstName) {
        mData.putString(FIRSTNAME_DATA_KEY, firstName);
        return this;
    }

    public TTVPlacesStepOnePage setLastName(String lastName) {
        mData.putString(LASTNAME_DATA_KEY, lastName);
        return this;
    }

    public TTVPlacesStepOnePage setJmbg(String jmbg) {
        mData.putString(JMBG_DATA_KEY, jmbg);
        return this;
    }
}
