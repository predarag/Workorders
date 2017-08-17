package rs.co.sbb.workorders.wizards.wizardpager.model;

import android.support.v4.app.Fragment;

import rs.co.sbb.workorders.wizards.wizardpager.ui.NumberFragment;

public class NumberPage extends TextPage {

	public NumberPage(ModelCallbacks callbacks, String title) {
		super(callbacks, title);
	}

	@Override
	public Fragment createFragment() {
		return NumberFragment.create(getKey());
	}

}
