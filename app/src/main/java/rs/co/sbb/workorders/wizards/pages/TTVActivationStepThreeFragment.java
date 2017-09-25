package rs.co.sbb.workorders.wizards.pages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.WizardActivity;
import rs.co.sbb.workorders.activity.ocr.IntentIntegrator;
import rs.co.sbb.workorders.activity.ocr.IntentResult;
import rs.co.sbb.workorders.entity.sap.CheckEquipmentResponse;
import rs.co.sbb.workorders.enums.EStatusCode;
import rs.co.sbb.workorders.enums.ETotalTvScanType;
import rs.co.sbb.workorders.utils.SaveSharedPreference;
import rs.co.sbb.workorders.utils.Utils;
import rs.co.sbb.workorders.wizards.wizardpager.ui.PageFragmentCallbacks;
import rs.co.sbb.workorders.ws.config.MobAppIntegrationConfig;
import rs.co.sbb.workorders.ws.impl.MobAppIntegrationServiceImpl;

/**
 * Created by milos.milic on 8/21/2017.
 */

public class TTVActivationStepThreeFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_KEY = "TTV_STEP_THREE_KEY";

    private static final String TAG = "TTVWStepThreeFrag";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private TTVActivationStepThreePage mPage;

    private EditText etSerial1;
    private EditText etSerial2;
    private EditText etSerial3;
    private EditText etSerial4;

    private EditText etMac1;
    private EditText etMac2;
    private EditText etMac3;
    private EditText etMac4;

    private ImageButton imgBtnScanSerial1;
    private ImageButton imgBtnScanSerial2;
    private ImageButton imgBtnScanSerial3;
    private ImageButton imgBtnScanSerial4;

    private ImageButton imgBtnScanBox1;
    private ImageButton imgBtnScanBox2;
    private ImageButton imgBtnScanBox3;
    private ImageButton imgBtnScanBox4;

    private CardView cvAdditionalBox1;
    private CardView cvAdditionalBox2;
    private CardView cvAdditionalBox3;

    private FloatingActionButton floatingActionButton;

    private String clickType = "";

    private boolean hasAdditionalBox1 = false;
    private boolean hasAdditionalBox2 = false;
    private boolean hasAdditionalBox3 = false;

    private ImageButton imgBtnCloseCard2;
    private ImageButton imgBtnCloseCard3;
    private ImageButton imgBtnCloseCard4;

    private CheckEquipmentResponse equipmentResponse = null;

    private int selectedDevices;
    private int renderedDevices;

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

        IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
        scanIntegrator.initiateScan();

        Log.i(TAG, "create");

       /* StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/

    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.wizard_step3_fragment_ttv_activation, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.title);
        if (tv != null) {
            tv.setText(mPage.getTitle());
        } else {
            Log.i(TAG, "onCreateView: There is no title.");
        }

        etSerial1 = (EditText) rootView.findViewById(R.id.etTTVSerialNo1);
        etSerial2 = (EditText) rootView.findViewById(R.id.etTTVSerialNo2);
        etSerial3 = (EditText) rootView.findViewById(R.id.etTTVSerialNo3);
        etSerial4 = (EditText) rootView.findViewById(R.id.etTTVSerialNo4);

        etMac1 = (EditText) rootView.findViewById(R.id.etTTVMacNo1);
        etMac2 = (EditText) rootView.findViewById(R.id.etTTVMacNo2);
        etMac3 = (EditText) rootView.findViewById(R.id.etTTVMacNo3);
        etMac4 = (EditText) rootView.findViewById(R.id.etTTVMacNo4);

        imgBtnScanSerial1 = (ImageButton) rootView.findViewById(R.id.btnTTVScanSerial1);
        imgBtnScanSerial2 = (ImageButton) rootView.findViewById(R.id.btnTTVScanSerial2);
        imgBtnScanSerial3 = (ImageButton) rootView.findViewById(R.id.btnTTVScanSerial3);
        imgBtnScanSerial4 = (ImageButton) rootView.findViewById(R.id.btnTTVScanSerial4);

        imgBtnScanBox1 = (ImageButton) rootView.findViewById(R.id.btnScanTtvBox1);
        imgBtnScanBox2 = (ImageButton) rootView.findViewById(R.id.btnScanTtvBox2);
        imgBtnScanBox3 = (ImageButton) rootView.findViewById(R.id.btnScanTtvBox3);
        imgBtnScanBox4 = (ImageButton) rootView.findViewById(R.id.btnScanTtvBox4);

        cvAdditionalBox1 = (CardView) rootView.findViewById(R.id.ttvEquipment1Card2);
        cvAdditionalBox2 = (CardView) rootView.findViewById(R.id.ttvEquipment1Card3);
        cvAdditionalBox3 = (CardView) rootView.findViewById(R.id.ttvEquipment1Card4);

        imgBtnCloseCard2 = (ImageButton) rootView.findViewById(R.id.ibCloseCard2);
        imgBtnCloseCard3 = (ImageButton) rootView.findViewById(R.id.ibCloseCard3);
        imgBtnCloseCard4 = (ImageButton) rootView.findViewById(R.id.ibCloseCard4);

        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fabTtvAddEquipment);

        setOnClickListener();

        //selectedDevices = SaveSharedPreference.getSelectedTtvDevices(getActivity());
        //renderedDevices = SaveSharedPreference.getRenderedTtvDevices(getActivity());

        selectedDevices = ((WizardActivity)getActivity()).getmCurrentPageSequence().get(2).getData().getInt(TTVActivationStepTwoPage.DEVICE_NUMBER_DATA_KEY);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Pozvan sam");
                if (!hasAdditionalBox1) {
                    Log.i(TAG, "Counter 0");
                    cvAdditionalBox1.setVisibility(View.VISIBLE);
                    hasAdditionalBox1 = true;

                } else if (!hasAdditionalBox2) {
                    Log.i(TAG, "Counter 1");
                    cvAdditionalBox2.setVisibility(View.VISIBLE);
                    hasAdditionalBox2 = true;

                } else if (!hasAdditionalBox3) {
                    Log.i(TAG, "Counter 2");
                    cvAdditionalBox3.setVisibility(View.VISIBLE);
                    hasAdditionalBox3 = true;

                } else {
                    Snackbar.make(rootView, getString(R.string.ttv_snack_max_additional_devices), Snackbar.LENGTH_LONG).show();

                }

            }
        });

        renderDeviceInputs();

        return rootView;
    }

    public void renderDeviceInputs() {

        Log.i(TAG, "Selected devices: "+selectedDevices);

        selectedDevices = SaveSharedPreference.getSelectedTtvDevices(getActivity());
        renderedDevices = SaveSharedPreference.getRenderedTtvDevices(getActivity());

        if (selectedDevices == 1) {
            Log.i(TAG, "Counter 1");
            cvAdditionalBox1.setVisibility(View.VISIBLE);
            hasAdditionalBox1 = true;
        }

        if (selectedDevices == 2) {
            Log.i(TAG, "Counter 2");

            cvAdditionalBox1.setVisibility(View.VISIBLE);
            hasAdditionalBox1 = true;

            cvAdditionalBox2.setVisibility(View.VISIBLE);
            hasAdditionalBox2 = true;
        }

        if (selectedDevices == 3) {
            Log.i(TAG, "Counter 3");

            cvAdditionalBox1.setVisibility(View.VISIBLE);
            hasAdditionalBox1 = true;

            cvAdditionalBox2.setVisibility(View.VISIBLE);
            hasAdditionalBox2 = true;

            cvAdditionalBox3.setVisibility(View.VISIBLE);
            hasAdditionalBox3 = true;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.i(TAG, "onAttach");

        Activity activity = null;
        try {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                Log.i(TAG, "onAttach: context is not activity?");
            }

        } catch (TypeNotPresentException ex) {
            Log.i(TAG, "onAttach: " + ex.getStackTrace());
        }

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
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
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    /*@Override
    public void onClick(View v) {

    }*/


    private void setOnClickListener() {

        imgBtnScanSerial1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
                scanIntegrator.initiateScan();
                startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
                clickType = ETotalTvScanType.BUTTON_SERIAL_1.getScanType();
            }
        });

        imgBtnScanSerial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
                scanIntegrator.initiateScan();
                startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
                clickType = ETotalTvScanType.BUTTON_SERIAL_2.getScanType();
            }
        });

        imgBtnScanSerial3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
                scanIntegrator.initiateScan();
                startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
                clickType = ETotalTvScanType.BUTTON_SERIAL_3.getScanType();
            }
        });


        imgBtnScanSerial4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
                scanIntegrator.initiateScan();
                startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
                clickType = ETotalTvScanType.BUTTON_SERIAL_4.getScanType();
            }
        });

        imgBtnScanBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
                scanIntegrator.initiateScan();
                startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
                clickType = ETotalTvScanType.BUTTON_BOX_1.getScanType();
            }
        });


        imgBtnScanBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
                scanIntegrator.initiateScan();
                startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
                clickType = ETotalTvScanType.BUTTON_BOX_2.getScanType();
            }
        });


        imgBtnScanBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
                scanIntegrator.initiateScan();
                startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
                clickType = ETotalTvScanType.BUTTON_BOX_3.getScanType();
            }
        });

        imgBtnScanBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
                scanIntegrator.initiateScan();
                startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
                clickType = ETotalTvScanType.BUTTON_BOX_4.getScanType();
            }
        });


        imgBtnCloseCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasAdditionalBox2 || hasAdditionalBox3) {
                    Snackbar.make(v, getString(R.string.ttv_snack_delete_last_additional_box), Snackbar.LENGTH_LONG).show();
                    return;
                }
                cvAdditionalBox1.setVisibility(View.INVISIBLE);
                hasAdditionalBox1 = false;
                etSerial2.setText(null);
                etMac2.setText(null);
            }
        });


        imgBtnCloseCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasAdditionalBox3) {
                    Snackbar.make(v, getString(R.string.ttv_snack_delete_last_additional_box), Snackbar.LENGTH_LONG).show();
                    return;
                }
                cvAdditionalBox2.setVisibility(View.INVISIBLE);
                hasAdditionalBox2 = false;
                etSerial3.setText(null);
                etMac3.setText(null);
            }
        });

        imgBtnCloseCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvAdditionalBox3.setVisibility(View.INVISIBLE);
                hasAdditionalBox3 = false;
                etSerial4.setText(null);
                etMac4.setText(null);
            }
        });


    }

    //    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Log.i(TAG, "onActivityResult: usao u metodu");
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            Log.i(TAG, "onActivityResult: " + scanContent);
            String scanFormat = scanningResult.getFormatName();


            if (clickType.equals(ETotalTvScanType.BUTTON_SERIAL_1.getScanType())) {
                CheckEquipmentResponse response = checkEquipment(scanContent, etSerial1, TTVActivationStepThreePage.SERIAL_NO1_DATA_KEY, scanContent);


            } else if (clickType.equals(ETotalTvScanType.BUTTON_SERIAL_2.getScanType())) {
                CheckEquipmentResponse response = checkEquipment(scanContent, etSerial2, TTVActivationStepThreePage.SERIAL_NO2_DATA_KEY, scanContent);


            } else if (clickType.equals(ETotalTvScanType.BUTTON_SERIAL_3.getScanType())) {
                CheckEquipmentResponse response = checkEquipment(scanContent, etSerial3, TTVActivationStepThreePage.SERIAL_NO3_DATA_KEY, scanContent);


            } else if (clickType.equals(ETotalTvScanType.BUTTON_SERIAL_4.getScanType())) {
                CheckEquipmentResponse response = checkEquipment(scanContent, etMac1, TTVActivationStepThreePage.MAC1_DATA_KEY, scanContent);


            } else if (clickType.equals(ETotalTvScanType.BUTTON_BOX_1.getScanType())) {
                CheckEquipmentResponse response = checkEquipment(scanContent, etMac1, TTVActivationStepThreePage.MAC1_DATA_KEY, scanContent);


            } else if (clickType.equals(ETotalTvScanType.BUTTON_BOX_2.getScanType())) {
                CheckEquipmentResponse response = checkEquipment(scanContent, etMac2, TTVActivationStepThreePage.MAC2_DATA_KEY, scanContent);


            } else if (clickType.equals(ETotalTvScanType.BUTTON_BOX_3.getScanType())) {
                CheckEquipmentResponse response = checkEquipment(scanContent, etMac3, TTVActivationStepThreePage.MAC3_DATA_KEY, scanContent);


            } else if (clickType.equals(ETotalTvScanType.BUTTON_BOX_4.getScanType())) {
                CheckEquipmentResponse response = checkEquipment(scanContent, etMac4, TTVActivationStepThreePage.MAC4_DATA_KEY, scanContent);

            }


        } else {
            Toast toast = Toast.makeText(this.getContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTTVScanSerial1) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
            startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
            clickType = ETotalTvScanType.BUTTON_SERIAL_1.getScanType();
        }
        if (v.getId() == R.id.btnTTVScanSerial2) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
            startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
            clickType = ETotalTvScanType.BUTTON_SERIAL_2.getScanType();
        }
        if (v.getId() == R.id.btnTTVScanSerial3) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
            startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
            clickType = ETotalTvScanType.BUTTON_SERIAL_3.getScanType();

        }
        if (v.getId() == R.id.btnTTVScanSerial4) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
            startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
            clickType = ETotalTvScanType.BUTTON_SERIAL_4.getScanType();

        }
        if (v.getId() == R.id.btnScanTtvBox1) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
            startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
            clickType = ETotalTvScanType.BUTTON_BOX_1.getScanType();

        }
        if (v.getId() == R.id.btnScanTtvBox2) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
            startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
            clickType = ETotalTvScanType.BUTTON_BOX_2.getScanType();

        }
        if (v.getId() == R.id.btnScanTtvBox3) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
            startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
            clickType = ETotalTvScanType.BUTTON_BOX_3.getScanType();

        }
        if (v.getId() == R.id.btnScanTtvBox4) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity()); //(Activity) this.getContext()
            scanIntegrator.initiateScan();
            startActivityForResult(IntentIntegrator.externalIntent, scanIntegrator.REQUEST_CODE);
            clickType = ETotalTvScanType.BUTTON_BOX_4.getScanType();

        }
    }

    private CheckEquipmentResponse checkEquipment(String serialNo, final EditText et, final String
            dataKey,final String textViewValue) {

        equipmentResponse = null;

        Log.i(TAG, "checkEquipment: " + serialNo);

        MobAppIntegrationServiceImpl service = new MobAppIntegrationServiceImpl(MobAppIntegrationConfig.SAPINTEGRATION_BASE_PATH);

        Call<CheckEquipmentResponse> call = service.checkSapEquipment(serialNo);


        call.enqueue(new Callback<CheckEquipmentResponse>() {
            @Override
            public void onResponse(Call<CheckEquipmentResponse> call, Response<CheckEquipmentResponse> response) {
                if (null != response && !response.isSuccessful() && response.errorBody() != null) {
                    Log.i(TAG, response.code() + "");
                    Log.i(TAG, response.body() + "");
                    //showProgress(false);
                }
                else{
                    Log.i(TAG,"checkEquipment: response nije null");
                    equipmentResponse = response.body();
                    setTextBoxValue(equipmentResponse,et,dataKey,textViewValue);
                }
            }

            @Override
            public void onFailure(Call<CheckEquipmentResponse> call, Throwable t) {
                Log.i(TAG, "ERROR: "+t.getMessage());
                Utils.showDialog(getActivity(),getString(R.string.error), getString(R.string.error_server));
            }
        });

        return equipmentResponse;

    }

    private void setTextBoxValue(CheckEquipmentResponse response, EditText et, String
            dataKey, String textViewValue) {

        Log.i(TAG, "setTextBoxValue");

        if (null != response && !response.getStatus().equals("")) {
            if (response.getStatus().equals("SUCCESS")) {

                Log.i(TAG, "STORAGE LOCATION: " + response.getEquipment().getStorageLocationForSerialNumber());
                Log.i(TAG, "INTERNAL STORAGE LOCATION: " + SaveSharedPreference.getSapStorageLocation(getActivity()));
                if (!response.getEquipment().getStorageLocationForSerialNumber().equals(SaveSharedPreference.getSapStorageLocation(getActivity()))) {
                    Utils.showDialog(getActivity(), getString(R.string.error), "Oprema nije dodeljenja vasem timu!");
                } else {
                    et.setText(textViewValue);
                    mPage.getData().putString(dataKey, textViewValue);
                    mPage.notifyDataChanged();
                }

            } else {
                Utils.showDialog(getActivity(), getString(R.string.error), response.getStatusMessage());
            }
        } else {
            Utils.showDialog(getActivity(), getString(R.string.error), getString(R.string.error_server));
        }
    }
}
