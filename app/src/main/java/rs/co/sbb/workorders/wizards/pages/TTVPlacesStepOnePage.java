package rs.co.sbb.workorders.wizards.pages;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

import rs.co.sbb.workorders.wizards.wizardpager.model.ModelCallbacks;
import rs.co.sbb.workorders.wizards.wizardpager.model.Page;
import rs.co.sbb.workorders.wizards.wizardpager.model.ReviewItem;

/**
 * Created by milos.milic on 8/17/2017.
 */

public class TTVPlacesStepOnePage extends Page {

    public static final String NAME_DATA_KEY = "name";
    public static final String EMAIL_DATA_KEY = "email";

    private boolean completedPar = false;

    public boolean isCompletedPar() {
        return completedPar;
    }

    public void setCompletedPar(boolean completedPar) {
        this.completedPar = completedPar;
    }

    public TTVPlacesStepOnePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TTVPlacesStepOneFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Your name", mData.getString(NAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Your email", mData.getString(EMAIL_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
//        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY));
            return completedPar;
    }
}
