package rs.co.sbb.workorders.wizards.pages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.TotalTvActivationActivity;
import rs.co.sbb.workorders.entity.SerbianAddressObject;
import rs.co.sbb.workorders.wizards.wizardpager.model.Page;
import rs.co.sbb.workorders.wizards.wizardpager.ui.PageFragmentCallbacks;
import rs.co.sbb.workorders.ws.impl.SerbianAddressServiceImpl;

/**
 * Created by milos.milic on 8/17/2017.
 */

public class TTVPlacesStepOneFragment extends Fragment {

    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private TTVPlacesStepOnePage mPage;

//    private TextView mNameView;
//    private TextView mEmailView;

    private Spinner communitySpinner;
    private Spinner settlementSpinner;
    private Spinner streetSpinner;
    private List<SerbianAddressObject> communitiesList = new ArrayList<>();
    private List<SerbianAddressObject> settlmentList = new ArrayList<>();
    private List<SerbianAddressObject> streetList = new ArrayList<>();

    private View activateTotalTvForm;
    private View progressView;


    public static TTVPlacesStepOneFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        TTVPlacesStepOneFragment fragment = new TTVPlacesStepOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TTVPlacesStepOneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (TTVPlacesStepOnePage) mCallbacks.onGetPage(mKey);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wizard_step1_fragment_ttv_activation, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.title);
        if (tv != null) {
            tv.setText(mPage.getTitle());
        }
//        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

//        rootView.getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        activateTotalTvForm = (View) rootView.findViewById(R.id.total_tv_activation_form);
        progressView = (View) rootView.findViewById(R.id.total_tv_activation_progress);

        communitySpinner = (Spinner) rootView.findViewById(R.id.spinnerCommunities);
        settlementSpinner = (Spinner) rootView.findViewById(R.id.spinnerSettlement);
        streetSpinner = (Spinner) rootView.findViewById(R.id.spinnerStreet);

        getAllCommunitys();

        communitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SerbianAddressObject community = (SerbianAddressObject) parent.getSelectedItem();
                communitySpinner.setSelection(position);

                getSettlementByCode(community.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        settlementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SerbianAddressObject settelment = (SerbianAddressObject) parent.getSelectedItem();
                settlementSpinner.setSelection(position);

                getStreetBySettlementCode(settelment.getCode());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        streetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SerbianAddressObject street = (SerbianAddressObject) parent.getSelectedItem();
                mPage.setCompletedPar(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*mNameView = ((TextView) rootView.findViewById(R.id.your_name));
        mNameView.setText(mPage.getData().getString(TTVPlacesStepOnePage.NAME_DATA_KEY));

        mEmailView = ((TextView) rootView.findViewById(R.id.your_email));
        mEmailView.setText(mPage.getData().getString(TTVPlacesStepOnePage.EMAIL_DATA_KEY));*/
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*mNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(TTVPlacesStepOnePage.NAME_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });*/

        /*mEmailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(TTVPlacesStepOnePage.EMAIL_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });*/
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

    private void getAllCommunitys(){

        showProgress(true);

        SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

        Call<HashMap<String,String>> call = service.getAllCommunitys();

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {

                if(null != response && !response.isSuccessful() && response.errorBody() != null){
                    Log.i("communities", response.code()+"");
                    Log.i("communities", response.errorBody()+"");
                    showProgress(false);
                    try {
                        Toast.makeText(getActivity(),response.errorBody().string(),Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e("communities",e.getMessage());
                        showProgress(false);
                        e.printStackTrace();
                    }
                }
                else{
                    HashMap<String, String> communities = response.body();

                    Iterator it = communities.entrySet().iterator();
                    while(it.hasNext()){

                        Map.Entry pair = (Map.Entry) it.next();

                        Log.i("communities",pair.getKey()+" "+pair.getValue());
                        communitiesList.add(new SerbianAddressObject(pair.getKey().toString(),
                                pair.getValue().toString()));
                    }

                    populateCommunitySpinner(communitiesList);

                }

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                showProgress(false);
                Toast.makeText(getActivity(),"Doslo je do greske: "+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("communities",t.getMessage());
            }
        });

    }


    private void getSettlementByCode(String communityCode){

        Log.i("communityCode","USAO SA: "+communityCode);

        showProgress(true);

        settlmentList.clear();

        SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

        Call<HashMap<String,String>> call = service.getSettlementByCommunityCode(communityCode);

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(null != response && !response.isSuccessful() && response.errorBody() != null){
                    Log.i("getSettlementByCode", response.code()+"");
                    Log.i("getSettlementByCode", response.errorBody()+"");

                    showProgress(false);

                    try {
                        Toast.makeText(getActivity(),response.errorBody().string(),Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        showProgress(false);
                        Log.e("communities",e.getMessage());
                        e.printStackTrace();
                    }
                }
                else{
                    HashMap<String, String> communities = response.body();

                    Iterator it = communities.entrySet().iterator();
                    while(it.hasNext()){

                        Map.Entry pair = (Map.Entry) it.next();

                        Log.i("settelemt",pair.getKey()+" "+pair.getValue());
                        settlmentList.add(new SerbianAddressObject(pair.getKey().toString(),
                                pair.getValue().toString()));
                    }

                    populateSettlementSpiner(settlmentList);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                showProgress(false);
                Log.e("settelemt",t.getMessage());
            }
        });
    }


    private void getStreetBySettlementCode(String settlementCode){

        Log.i("streets","USAO SA: "+settlementCode);

        showProgress(true);

        streetList.clear();

        SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

        Call<HashMap<String,String>> call = service.getStreetsBySettlementCode(settlementCode);

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(null != response && !response.isSuccessful() && response.errorBody() != null){
                    Log.i("streets", response.code()+"");
                    Log.i("streets", response.errorBody()+"");

                    showProgress(false);

                    try {
                        Toast.makeText(getActivity(),response.errorBody().string(),Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e("streets",e.getMessage());
                        showProgress(false);
                        e.printStackTrace();
                    }
                }
                else{
                    HashMap<String, String> communities = response.body();

                    Iterator it = communities.entrySet().iterator();
                    while(it.hasNext()){

                        Map.Entry pair = (Map.Entry) it.next();

                        Log.i("streets",pair.getKey()+" "+pair.getValue());
                        streetList.add(new SerbianAddressObject(pair.getKey().toString(),
                                pair.getValue().toString()));
                    }

                    populateStreetSpiner(streetList);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.e("streets",t.getMessage());
                showProgress(false);
            }
        });
    }

    private void populateCommunitySpinner(List<SerbianAddressObject> communitiesList){
        ArrayAdapter<SerbianAddressObject> communityAdapter = new ArrayAdapter<SerbianAddressObject>(getActivity(),android.R.layout.simple_spinner_item,communitiesList);
        communityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        communitySpinner.setAdapter(communityAdapter);
        showProgress(false);
    }

    private void populateSettlementSpiner(List<SerbianAddressObject> settlmentList){
        ArrayAdapter<SerbianAddressObject> settlementAdapter = new ArrayAdapter<SerbianAddressObject>(getActivity(),android.R.layout.simple_spinner_item,settlmentList);
        settlementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        settlementSpinner.setAdapter(settlementAdapter);

        showProgress(false);
    }

    private void populateStreetSpiner(List<SerbianAddressObject> settlmentList){
        ArrayAdapter<SerbianAddressObject> streetAdapter = new ArrayAdapter<SerbianAddressObject>(getActivity(),android.R.layout.simple_spinner_item,streetList);
        streetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        streetSpinner.setAdapter(streetAdapter);

        showProgress(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            activateTotalTvForm.setVisibility(show ? View.GONE : View.VISIBLE);
            activateTotalTvForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    activateTotalTvForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            activateTotalTvForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
