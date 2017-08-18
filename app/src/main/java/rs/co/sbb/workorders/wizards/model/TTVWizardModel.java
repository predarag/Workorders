package rs.co.sbb.workorders.wizards.model;

import android.content.Context;

import rs.co.sbb.workorders.wizards.pages.TTVPlacesStepOnePage;
import rs.co.sbb.workorders.wizards.wizardpager.model.AbstractWizardModel;
import rs.co.sbb.workorders.wizards.wizardpager.model.MultipleFixedChoicePage;
import rs.co.sbb.workorders.wizards.wizardpager.model.PageList;
import rs.co.sbb.workorders.wizards.wizardpager.model.TextPage;

/**
 * Created by milos.milic on 8/17/2017.
 */

public class TTVWizardModel extends AbstractWizardModel {

    public TTVWizardModel(Context context) { super(context);}

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new TTVPlacesStepOnePage(this, "Place and address").setRequired(false),
                new MultipleFixedChoicePage(this, "Test 2").setChoices(
                        "Prvi", "Drugi", "Treci", "Cetvrto", "Peti",
                        "Sesti").setRequired(false),
                new TextPage(this, "Comments").setRequired(false)

        );
    }


}
