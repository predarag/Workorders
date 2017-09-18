package rs.co.sbb.workorders.wizards.pages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.SerbianAddressObject;
import rs.co.sbb.workorders.entity.totaltv.BuildingType;
import rs.co.sbb.workorders.utils.Utils;
import rs.co.sbb.workorders.wizards.wizardpager.ui.PageFragmentCallbacks;
import rs.co.sbb.workorders.ws.impl.SerbianAddressServiceImpl;

/**
 * Created by milos.milic on 8/17/2017.
 */

public class TTVPlacesStepOneFragment extends Fragment {

    public static final String ARG_KEY = "TTV_STEP_ONE_KEY";

    private static final String TAG = "TTVWStepOneFrag";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private TTVPlacesStepOnePage mPage;



    private List<SerbianAddressObject> communitiesList = new ArrayList<>();
    private List<SerbianAddressObject> settlmentList = new ArrayList<>();
    private List<SerbianAddressObject> streetList = new ArrayList<>();

    private View activateTotalTvForm;
    private View progressView;


    private AutoCompleteTextView tvCommunity;
    private AutoCompleteTextView tvSettelment;
    private AutoCompleteTextView tvStreet;
    private AutoCompleteTextView tvBuildingType;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etJmbg;
    private EditText etHouseNumber;
    private EditText etSubNumber;
    private EditText etPostCode;
    private EditText etFixNumber;
    private EditText etMobNumber;
    private EditText etEmail;
    private EditText etRoom;
    private EditText etFloor;


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
        } else {
            Log.i(TAG, "onCreateView: There is no title.");
        }

        etFirstName = (EditText) rootView.findViewById(R.id.etTtvFirstName);
        etLastName = (EditText) rootView.findViewById(R.id.etTtvLastName);
        etJmbg = (EditText) rootView.findViewById(R.id.etJТtvJmbg);
        etPostCode = (EditText) rootView.findViewById(R.id.etTtvPostCode);
        etHouseNumber = (EditText) rootView.findViewById(R.id.etTtvHouseNo);
        etSubNumber = (EditText) rootView.findViewById(R.id.etTtvHouseNo2);
        etMobNumber = (EditText) rootView.findViewById(R.id.etTtvMobile);
        etFixNumber = (EditText) rootView.findViewById(R.id.etTtvFixNumber);
        etEmail = (EditText) rootView.findViewById(R.id.etTtvEmail);
        etRoom = (EditText) rootView.findViewById(R.id.etTtvRoom);
        etFloor = (EditText) rootView.findViewById(R.id.etTtvFloor);

        etFirstName.setText(mPage.getData().getString(TTVPlacesStepOnePage.FIRSTNAME_DATA_KEY));
        etLastName.setText(mPage.getData().getString(TTVPlacesStepOnePage.LASTNAME_DATA_KEY));
        etJmbg.setText(mPage.getData().getString(TTVPlacesStepOnePage.JMBG_DATA_KEY));

        //addTextChangeListeners();

        activateTotalTvForm = rootView.findViewById(R.id.total_tv_activation_form);
        progressView = rootView.findViewById(R.id.total_tv_activation_progress);


        tvCommunity = (AutoCompleteTextView) rootView.findViewById(R.id.tvТtvCommunity);
        tvSettelment = (AutoCompleteTextView) rootView.findViewById(R.id.tvTtvSettelemnt);
        tvStreet = (AutoCompleteTextView) rootView.findViewById(R.id.tvTtvStreet);
        tvBuildingType = (AutoCompleteTextView) rootView.findViewById(R.id.tvTtvBuildingType);

        getCitys();


        tvCommunity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvCommunity.showDropDown();
                tvCommunity.requestFocus();
                return false;
            }

        });

        tvCommunity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SerbianAddressObject community = (SerbianAddressObject) parent.getAdapter().getItem(position);
                //tvCommunity.setSelection(position);

                getRegionByCode(community.getCode());
            }
        });


        tvSettelment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvSettelment.showDropDown();
                tvSettelment.requestFocus();
                return false;
            }
        });


        tvSettelment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SerbianAddressObject settelment = (SerbianAddressObject) parent.getAdapter().getItem(position);
                // tvSettelment.setSelection(position);

                getStreetByRegionCode(settelment.getCode());

            }

        });


        tvStreet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvStreet.showDropDown();
                tvStreet.requestFocus();
                return false;
            }
        });


        tvStreet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SerbianAddressObject street = (SerbianAddressObject) parent.getAdapter().getItem(position);

            }


        });

        populateBuildingTypes();

        tvBuildingType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvBuildingType.showDropDown();
                tvBuildingType.requestFocus();
                return false;
            }
        });
        tvBuildingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BuildingType buildingType = (BuildingType) parent.getAdapter().getItem(position);

                Log.i(TAG,"ITEM: "+buildingType.getCode());

                mPage.getData().putString(TTVPlacesStepOnePage.BUILDING_TYPE_DATA_KEY, buildingType.getCode());
                mPage.notifyDataChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    private void addTextChangeListeners() {
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.FIRSTNAME_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
                Log.i(TAG, mPage.isCompleted() + "");
            }
        });

        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.LASTNAME_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
                Log.i(TAG, mPage.isCompleted() + "");

            }
        });

        etJmbg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.JMBG_DATA_KEY, s != null ? s.toString() : null);
                //mPage.setCompletedPar(true);
                mPage.notifyDataChanged();
                Log.i(TAG, mPage.isCompleted() + "");

            }
        });
        tvCommunity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.COMMUNITY_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        tvSettelment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.SETTLEMENT_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        tvStreet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.STREET_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        etHouseNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.HOUSE_NO_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        etSubNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.SUB_HOUSE_NO_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        etPostCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.POST_CODE_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        etFixNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.FIX_NUMBER_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        etMobNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());
                mPage.getData().putString(TTVPlacesStepOnePage.MOBILE_NUMBER_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(TTVPlacesStepOnePage.EMAIL_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        etRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(TTVPlacesStepOnePage.ROOM_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        etFloor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(TTVPlacesStepOnePage.FLOOR_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addTextChangeListeners();

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (etFirstName != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void getCitys() {


        Log.i(TAG, "getCitys");

        Activity activity = getActivity();

        if (isAdded() && activity != null) {

            showProgress(true);

            SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

            Call<HashMap<String, String>> call = service.getAllCitys();

            call.enqueue(new Callback<HashMap<String, String>>() {
                @Override
                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {

                    if (null != response && !response.isSuccessful() && response.errorBody() != null) {
                        Log.d("communities", response.code() + "");
                        Log.d("communities", response.errorBody() + "");
                        showProgress(false);
                        try {
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.e("communities", e.getMessage());
                            showProgress(false);
                            e.printStackTrace();
                        }
                    } else {
                        HashMap<String, String> communities = response.body();

                        Iterator it = communities.entrySet().iterator();
                        while (it.hasNext()) {

                            Map.Entry pair = (Map.Entry) it.next();

                            Log.d("communities", pair.getKey() + " " + pair.getValue());
                            communitiesList.add(new SerbianAddressObject(pair.getKey().toString(),
                                    pair.getValue().toString()));
                        }

                        sortData(communitiesList);

                        populateCitySpinner(communitiesList);

                    }

                }

                @Override
                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                    showProgress(false);
                    Toast.makeText(getActivity(), "Doslo je do greske: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("communities", t.getMessage());
                }
            });
        }

    }


    private void getRegionByCode(String cityCode) {

        Log.i("communityCode", "USAO SA: " + cityCode);

        Activity activity = getActivity();

        if (isAdded() && activity != null) {

            showProgress(true);

            settlmentList.clear();

            SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

            Call<HashMap<String, String>> call = service.getRegionByCityCode(cityCode);

            call.enqueue(new Callback<HashMap<String, String>>() {
                @Override
                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                    if (null != response && !response.isSuccessful() && response.errorBody() != null) {
                        Log.d("getRegionByCode", response.code() + "");
                        Log.d("getRegionByCode", response.errorBody() + "");

                        showProgress(false);

                        try {
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            showProgress(false);
                            Log.e("communities", e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        HashMap<String, String> communities = response.body();

                        Iterator it = communities.entrySet().iterator();
                        while (it.hasNext()) {

                            Map.Entry pair = (Map.Entry) it.next();

                            Log.d("settelemt", pair.getKey() + " " + pair.getValue());
                            settlmentList.add(new SerbianAddressObject(pair.getKey().toString(),
                                    pair.getValue().toString()));
                        }

                        sortData(settlmentList);

                        populateRegionSpinner(settlmentList);
                    }
                }

                @Override
                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                    showProgress(false);
                    Log.e("settelemt", t.getMessage());
                }
            });
        }
    }


    private void getStreetByRegionCode(String regionCode) {

        Log.i("streets", "USAO SA: " + regionCode);

        Activity activity = getActivity();

        if (isAdded() && activity != null) {

            showProgress(true);

            streetList.clear();

            SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

            Call<HashMap<String, String>> call = service.getStreetsByRegionCode(regionCode);

            call.enqueue(new Callback<HashMap<String, String>>() {
                @Override
                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                    if (null != response && !response.isSuccessful() && response.errorBody() != null) {
                        Log.d("streets", response.code() + "");
                        Log.d("streets", response.errorBody() + "");

                        showProgress(false);

                        try {
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.e("streets", e.getMessage());
                            showProgress(false);
                            e.printStackTrace();
                        }
                    } else {
                        HashMap<String, String> communities = response.body();

                        Iterator it = communities.entrySet().iterator();
                        while (it.hasNext()) {

                            Map.Entry pair = (Map.Entry) it.next();

                            Log.d("streets", pair.getKey() + " " + pair.getValue());
                            streetList.add(new SerbianAddressObject(pair.getKey().toString(),
                                    pair.getValue().toString()));
                        }

                        sortData(streetList);

                        populateStreetSpiner(streetList);
                    }
                }

                @Override
                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                    Log.e("streets", t.getMessage());
                    showProgress(false);
                }
            });
        }
    }

    private void populateCitySpinner(List<SerbianAddressObject> communitiesList) {
        Log.i(TAG, "populateCitySpinner");
        ArrayAdapter<SerbianAddressObject> communityAdapter = new ArrayAdapter<SerbianAddressObject>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, communitiesList);

        tvCommunity.setAdapter(communityAdapter);
        showProgress(false);
    }

    private void populateRegionSpinner(List<SerbianAddressObject> settlmentList) {
        Log.i(TAG, "populateRegionSpinner");
        ArrayAdapter<SerbianAddressObject> settlementAdapter = new ArrayAdapter<SerbianAddressObject>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, settlmentList);

        tvSettelment.setAdapter(settlementAdapter);

        showProgress(false);
    }

    private void populateStreetSpiner(List<SerbianAddressObject> settlmentList) {
        Log.i(TAG, "populateStreetSpiner");
        ArrayAdapter<SerbianAddressObject> streetAdapter = new ArrayAdapter<SerbianAddressObject>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, streetList);

        tvStreet.setAdapter(streetAdapter);

        showProgress(false);
    }

    private void populateBuildingTypes(){
        Log.i(TAG, "populateStreetSpiner");
        ArrayAdapter<BuildingType> streetAdapter = new ArrayAdapter<BuildingType>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, Utils.getBuildingTypes());

        tvBuildingType.setAdapter(streetAdapter);
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


    private void sortData(List<SerbianAddressObject> addressObjects) {
        Collections.sort(addressObjects, new Comparator<SerbianAddressObject>() {
            @Override
            public int compare(SerbianAddressObject o1, SerbianAddressObject o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

}
