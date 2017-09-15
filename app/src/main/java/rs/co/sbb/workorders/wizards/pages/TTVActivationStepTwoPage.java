package rs.co.sbb.workorders.wizards.pages;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

import rs.co.sbb.workorders.wizards.wizardpager.model.ModelCallbacks;
import rs.co.sbb.workorders.wizards.wizardpager.model.Page;
import rs.co.sbb.workorders.wizards.wizardpager.model.ReviewItem;

/**
 * Created by milos.milic on 8/22/2017.
 */

public class TTVActivationStepTwoPage extends Page {


    public static final String PRODUCT_PACKAGE_DATA_KEY = "PRODUCT_PACKAGE_DATA_KEY";
    public static final String PRODUCT_PACKAGE_NAME_DATA_KEY = "PRODUCT_PACKAGE_NAME_DATA_KEY";
    public static final String PRODUCT_PACKAGE_OBJECT_DATA_KET = "PRODUCT_PACKAGE_OBJECT_DATA_KET";
    public static final String BILLING_PRODUCTS_DATA_KEY = "BILLING_PRODUCTS_DATA_KEY";
    public static final String PACKAGE_OPTION_DATA_KEY = "PACKAGE_OPTION_DATA_KEY";
    public static final String PACKAGE_OPTION_NAME_DATA_KEY = "PACKAGE_OPTION_NAME_DATA_KEY";
    public static final String PACKAGE_OPTION_DURATION_DATA_KEY = "PACKAGE_OPTION_DURATION_DATA_KEY";

    public TTVActivationStepTwoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return  TTVActivationStepTwoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Paket", mData.getString(PRODUCT_PACKAGE_NAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Opcija", mData.getString(PACKAGE_OPTION_NAME_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted(){
        return !TextUtils.isEmpty(mData.getString(PRODUCT_PACKAGE_DATA_KEY));
    }
}
