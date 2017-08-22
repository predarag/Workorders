package rs.co.sbb.workorders.wizards.pages;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.wizards.wizardpager.ui.PageFragmentCallbacks;

/**
 * Created by milos.milic on 8/22/2017.
 */

public class TTVActivationStepTwoFragment extends Fragment {

    private static final String ARG_KEY = "key";

    private static final String TAG = "TTVWStepTwoFrag";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private TTVActivationStepTwoPage mPage;

    public static TTVActivationStepTwoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        TTVActivationStepTwoFragment fragment = new TTVActivationStepTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TTVActivationStepTwoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (TTVActivationStepTwoPage) mCallbacks.onGetPage(mKey);

    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wizard_step2_fragment_ttv_activation, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.title);
        if (tv != null) {
            tv.setText(mPage.getTitle());
        } else {
            Log.i(TAG, "onCreateView: There is no title.");
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = null;
        try {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                Log.i(TAG, "onAttach: context is not activity?");
            }

        } catch (TypeNotPresentException ex) {

        }

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
       /* if (mNameView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }*/
    }


    @Override
    public void onResume(){
        super.onResume();
    }


}
