package rs.co.sbb.workorders.wizards.pages;


import android.support.v4.app.Fragment;

import java.util.ArrayList;

import rs.co.sbb.workorders.wizards.wizardpager.model.ModelCallbacks;
import rs.co.sbb.workorders.wizards.wizardpager.model.Page;
import rs.co.sbb.workorders.wizards.wizardpager.model.ReviewItem;

/**
 * Created by milos.milic on 8/21/2017.
 */

public class TTVActivationStepThreePage extends Page {

    public static final String SERIAL_NO1_DATA_KEY = "SERIAL_NO1_DATA_KEY";
    public static final String SERIAL_NO2_DATA_KEY = "SERIAL_NO2_DATA_KEY";
    public static final String SERIAL_NO3_DATA_KEY = "SERIAL_NO3_DATA_KEY";
    public static final String SERIAL_NO4_DATA_KEY = "SERIAL_NO4_DATA_KEY";

    public static final String MAC1_DATA_KEY = "MAC1_DATA_KEY";
    public static final String MAC2_DATA_KEY = "MAC2_DATA_KEY";
    public static final String MAC3_DATA_KEY = "MAC3_DATA_KEY";
    public static final String MAC4_DATA_KEY = "MAC4_DATA_KEY";

    public TTVActivationStepThreePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TTVActivationStepThreeFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Serial No", mData.getString(SERIAL_NO1_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Mac", mData.getString(MAC1_DATA_KEY), getKey(), -1));
    }
}
