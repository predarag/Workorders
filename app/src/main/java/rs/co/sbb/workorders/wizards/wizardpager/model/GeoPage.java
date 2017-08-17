package rs.co.sbb.workorders.wizards.wizardpager.model;

import android.support.v4.app.Fragment;

import rs.co.sbb.workorders.wizards.wizardpager.ui.GeoFragment;

public class GeoPage extends TextPage {

	public GeoPage(ModelCallbacks callbacks, String title) {
		super(callbacks, title);
	}

	@Override
	public Fragment createFragment() {
		return GeoFragment.create(getKey());
	}

	public GeoPage setValue(String value) {
		mData.putString(SIMPLE_DATA_KEY, value);
		return this;
	}
}
