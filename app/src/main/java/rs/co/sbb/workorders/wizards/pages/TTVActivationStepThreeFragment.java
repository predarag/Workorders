package rs.co.sbb.workorders.wizards.pages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.ocr.IntentIntegrator;
import rs.co.sbb.workorders.activity.ocr.IntentResult;
import rs.co.sbb.workorders.wizards.wizardpager.ui.PageFragmentCallbacks;

/**
 * Created by milos.milic on 8/21/2017.
 */

public class TTVActivationStepThreeFragment extends Fragment {

    private static final String ARG_KEY = "key";

    private static final String TAG = "TTVWStepThreeFrag";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private TTVActivationStepThreePage mPage;

    private EditText etSerial;

    private ImageButton imgBtnScan;

    private String clickType = "";

    public static TTVActivationStepThreeFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        TTVActivationStepThreeFragment fragment = new TTVActivationStepThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TTVActivationStepThreeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (TTVActivationStepThreePage) mCallbacks.onGetPage(mKey);

    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wizard_step3_fragment_ttv_activation, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.title);
        if (tv != null) {
            tv.setText(mPage.getTitle());
        } else {
            Log.i(TAG, "onCreateView: There is no title.");
        }

        etSerial = (EditText) rootView.findViewById(R.id.etTTVSerialNo);
        imgBtnScan = (ImageButton) rootView.findViewById(R.id.btnTTVScanSerial);
        imgBtnScan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                onClickTTVOcrButton(view);
            }
        });

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
            Log.i(TAG, "onAttach: "+ex.getStackTrace());
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

    /*@Override
    public void onClick(View v) {

    }*/

    private void onClickTTVOcrButton(View v) {
        if (v.getId() == R.id.btnTTVScanSerial) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
//            startActivityForResult();
            clickType = "serial";
        }
//        super.onActivityResult();
    }

//    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode,resultCode,intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Log.i(TAG, "onActivityResult: usao u metodu");
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            Log.i(TAG, "onActivityResult: "+scanContent);
            String scanFormat = scanningResult.getFormatName();
            //formatTxt.setText("FORMAT: " + scanFormat);
            //formatTxt.setText("FORMAT: " + scanFormat);
//            if(clickType.equals("serial"))
                etSerial.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(this.getContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
