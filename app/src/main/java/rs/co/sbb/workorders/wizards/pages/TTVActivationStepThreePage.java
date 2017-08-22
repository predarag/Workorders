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




    public TTVActivationStepThreePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TTVActivationStepThreeFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        // TODO add all item from step3
    }
}
