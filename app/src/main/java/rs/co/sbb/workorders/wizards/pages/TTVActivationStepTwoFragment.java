package rs.co.sbb.workorders.wizards.pages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.SerbianAddressObject;
import rs.co.sbb.workorders.entity.product_package.BillingProduct;
import rs.co.sbb.workorders.entity.product_package.PackageOptionResponse;
import rs.co.sbb.workorders.entity.product_package.ProductPackage;
import rs.co.sbb.workorders.entity.product_package.ProductPackageOption;
import rs.co.sbb.workorders.entity.product_package.ProductPackageResponse;
import rs.co.sbb.workorders.entity.product_package.RatePlan;
import rs.co.sbb.workorders.enums.EBillingProdcutType;
import rs.co.sbb.workorders.enums.ECountryCode;
import rs.co.sbb.workorders.enums.EServiceType;
import rs.co.sbb.workorders.enums.EStatusCode;
import rs.co.sbb.workorders.wizards.wizardpager.ui.PageFragmentCallbacks;
import rs.co.sbb.workorders.ws.config.MobAppIntegrationConfig;
import rs.co.sbb.workorders.ws.impl.MobAppIntegrationServiceImpl;

/**
 * Created by milos.milic on 8/22/2017.
 */

public class TTVActivationStepTwoFragment extends Fragment {

    public static final String ARG_KEY = "TTV_STEP_TWO_KEY";

    private static final String TAG = "TTVWStepTwoFrag";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private TTVActivationStepTwoPage mPage;

    private AutoCompleteTextView packagesSpinner;
    private AutoCompleteTextView optionsSpinner;



    private List<String> packages = new ArrayList<String>();
    private LinearLayout layoutTtvCheckBox;
    private CheckBox checkBox;


    private List<String> checkedBp = new ArrayList<String>();

    private List<ProductPackage> productPackages = null;
    private List<ProductPackageOption> productPackageOptions = null;

    private View formTtvPacakges;
    private View progressView;
    private View mainView;

    private ArrayList<String> checkedBillingProducts = new ArrayList<String>();


    public static TTVActivationStepTwoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        TTVActivationStepTwoFragment fragment = new TTVActivationStepTwoFragment();
        fragment.setArguments(args);
        Log.i(TAG, "create");
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
        Log.i(TAG, "onCreate");



    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wizard_step2_fragment_ttv_activation, container, false);

        Log.i(TAG, "onCreateView");

        mainView = rootView;

        TextView tv = (TextView) rootView.findViewById(R.id.title);
        if (tv != null) {
            tv.setText(mPage.getTitle());
        } else {
            Log.i(TAG, "onCreateView: There is no title.");
        }


        formTtvPacakges = rootView.findViewById(R.id.total_tv_pacakges_form);
        progressView = rootView.findViewById(R.id.total_tv_packages_progress);

        packagesSpinner = (AutoCompleteTextView) rootView.findViewById(R.id.tvTtvPackages);
        optionsSpinner = (AutoCompleteTextView) rootView.findViewById(R.id.tvTtvOption);

        getProductPackages();


        layoutTtvCheckBox = (LinearLayout) rootView.findViewById(R.id.ttvPackagesLayout);

        packagesSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                packagesSpinner.showDropDown();
                packagesSpinner.requestFocus();
                return false;
            }
        });

        optionsSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                optionsSpinner.showDropDown();
                optionsSpinner.requestFocus();
                return false;
            }
        });

        packagesSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductPackage productPackage = (ProductPackage) parent.getAdapter().getItem(position);


                checkedBillingProducts.clear();
                createAddonsCheckBoxes(productPackage.getRatePlans());

                getPackageOptions(productPackage.getProductPackageCode());
                closeKeyboard(getActivity(),packagesSpinner.getWindowToken());

                mPage.getData().putString(TTVActivationStepTwoPage.PRODUCT_PACKAGE_NAME_DATA_KEY, productPackage.getProductPackageName());
                mPage.getData().putSerializable(TTVActivationStepTwoPage.PRODUCT_PACKAGE_OBJECT_DATA_KET,productPackage);
                mPage.getData().putString(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY, productPackage.getProductPackageCode());
                mPage.notifyDataChanged();

            }

        });

        packagesSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY, s != null ? s.toString() : null);
                mPage.getData().putString(TTVActivationStepTwoPage.PRODUCT_PACKAGE_NAME_DATA_KEY, s != null ? s.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        optionsSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                closeKeyboard(getActivity(),optionsSpinner.getWindowToken());
                ProductPackageOption option = (ProductPackageOption) parent.getAdapter().getItem(position);
                mPage.getData().putString(TTVActivationStepTwoPage.PACKAGE_OPTION_DATA_KEY, option.getOptionNumber());
                mPage.getData().putString(TTVActivationStepTwoPage.PACKAGE_OPTION_NAME_DATA_KEY, option.getDescription());
                mPage.getData().putString(TTVActivationStepTwoPage.PACKAGE_OPTION_DURATION_DATA_KEY, option.getDuration());
                mPage.notifyDataChanged();
            }


        });

        if(mPage.getData().getSerializable(TTVActivationStepTwoPage.PRODUCT_PACKAGE_OBJECT_DATA_KET) != null ){
            ProductPackage productPackage = (ProductPackage) mPage.getData().getSerializable(TTVActivationStepTwoPage.PRODUCT_PACKAGE_OBJECT_DATA_KET);

            createAddonsCheckBoxes(productPackage.getRatePlans());
        }

        return rootView;
    }

    private void createAddonsCheckBoxes(List<RatePlan> ratePlanList) {

        layoutTtvCheckBox.removeAllViews();

        for (RatePlan ratePlan : ratePlanList) {

            int i = 0;

            for (final BillingProduct billingProduct : ratePlan.getBillingProducts()) {

                checkBox = new CheckBox(getContext());
                checkBox.setId(i++);
                checkBox.setText(billingProduct.getBillingProductName());
                checkBox.setHighlightColor(getResources().getColor(R.color.colorPrimary));

                if(mPage.getData().getStringArrayList(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY) != null){
                    List<String> checkedBpsTemp = (List<String>) mPage.getData().getStringArrayList(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY);
                    for(String checkedBp : checkedBpsTemp){
                        if(checkedBp.equals(billingProduct.getBillingProductCode()))
                            checkBox.setChecked(true);
                    }
                }

                if (billingProduct.getMappingType().equals(EBillingProdcutType.INCLUDED.value())) {
                    checkBox.setClickable(false);
                    checkBox.setChecked(true);
                } else {
                    checkBox.setClickable(true);
                    checkBox.setChecked(false);

                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Log.i(TAG, buttonView.getText().toString());
                            checkedBillingProducts.add(billingProduct.getBillingProductCode());
                            printAddons(checkedBillingProducts);
                        } else {
                            Log.i(TAG, "unchecked");
                            checkedBillingProducts.remove(billingProduct.getBillingProductCode());
                            printAddons(checkedBillingProducts);
                        }
                    }
                });

                mPage.getData().putStringArrayList(TTVActivationStepTwoPage.PRODUCT_PACKAGE_DATA_KEY, checkedBillingProducts);

                layoutTtvCheckBox.addView(checkBox);
            }

        }
    }


    private void printAddons(List<String> bps) {
        String addons = "";
        for (String string : bps)
            addons += string + " ";

        Log.i(TAG, addons);

    }


    private void setPackagesSpinnerAdapter() {
        if (productPackages != null) {
            ArrayAdapter<ProductPackage> packagesSpinnerAdapter = new ArrayAdapter<ProductPackage>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, productPackages);
            packagesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            packagesSpinner.setAdapter(packagesSpinnerAdapter);
        }
    }

    private void setOptionsSpinnerAdapter() {
        if (productPackageOptions != null) {
            ArrayAdapter<ProductPackageOption> optionSpinnerAdapter = new ArrayAdapter<ProductPackageOption>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, productPackageOptions);
            optionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            optionsSpinner.setAdapter(optionSpinnerAdapter);
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

        }

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach");
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


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            formTtvPacakges.setVisibility(show ? View.GONE : View.VISIBLE);
            formTtvPacakges.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    formTtvPacakges.setVisibility(show ? View.GONE : View.VISIBLE);
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
            formTtvPacakges.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    private void getProductPackages() {

        Log.i(TAG, "getProductPackages");

        Activity activity = getActivity();

        if (isAdded() && activity != null) {

            showProgress(true);

            MobAppIntegrationServiceImpl service = new MobAppIntegrationServiceImpl(MobAppIntegrationConfig.PRODUCT_PACKAGE_BASE_PATH);

            Call<ProductPackageResponse> call = service.getServiceProductPacakges(EServiceType.DTH.value(), ECountryCode.RS.value());

            call.enqueue(new Callback<ProductPackageResponse>() {
                @Override
                public void onResponse(Call<ProductPackageResponse> call, Response<ProductPackageResponse> response) {
                    if (null != response && !response.isSuccessful() && response.errorBody() != null) {
                        Log.i(TAG, response.code() + "");
                        Log.i(TAG, response.body() + "");
                        showProgress(false);
                        Snackbar.make(TTVActivationStepTwoFragment.this.mainView, R.string.error_server, Snackbar.LENGTH_LONG).show();
                    } else {
                        showProgress(false);
                        ProductPackageResponse packageResponse = response.body();

                        if (null != packageResponse) {
                            if (packageResponse.getStatus().equals(EStatusCode.ERROR.value())) {
                                Snackbar.make(TTVActivationStepTwoFragment.this.mainView, packageResponse.getStatusMessage(), Snackbar.LENGTH_LONG).show();

                            } else {
                                productPackages = packageResponse.getProductPackages();
                                setPackagesSpinnerAdapter();
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<ProductPackageResponse> call, Throwable t) {
                    showProgress(false);
                    Snackbar.make(TTVActivationStepTwoFragment.this.mainView, R.string.error_server, Snackbar.LENGTH_LONG).show();
                    Log.i(TAG, t.getMessage());
                }
            });
        }

    }

    public void getPackageOptions(String productPackageCode) {

        Activity activity = getActivity();

        if (isAdded() && activity != null) {

            optionsSpinner.setText("");

            showProgress(true);
            MobAppIntegrationServiceImpl service = new MobAppIntegrationServiceImpl(MobAppIntegrationConfig.SAPINTEGRATION_BASE_PATH);

            Call<PackageOptionResponse> call = service.getProductPackageOptions(productPackageCode, ECountryCode.RS.value());

            call.enqueue(new Callback<PackageOptionResponse>() {
                @Override
                public void onResponse(Call<PackageOptionResponse> call, Response<PackageOptionResponse> response) {
                    if (null != response && !response.isSuccessful() && response.errorBody() != null) {
                        Log.i(TAG, response.code() + "");
                        Log.i(TAG, response.body() + "");
                        showProgress(false);
                        Snackbar.make(TTVActivationStepTwoFragment.this.mainView, R.string.error_server, Snackbar.LENGTH_LONG).show();
                    } else {
                        showProgress(false);
                        PackageOptionResponse optionResponse = response.body();

                        if (null != optionResponse) {
                            if (optionResponse.getStatus().equals(EStatusCode.ERROR.value())) {
                                Snackbar.make(TTVActivationStepTwoFragment.this.mainView, optionResponse.getStatusMessage(), Snackbar.LENGTH_LONG).show();

                            } else {
                                productPackageOptions = optionResponse.getProductPackageOptions();
                                setOptionsSpinnerAdapter();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PackageOptionResponse> call, Throwable t) {
                    showProgress(false);
                    Snackbar.make(TTVActivationStepTwoFragment.this.mainView, R.string.error_server, Snackbar.LENGTH_LONG).show();
                    Log.i(TAG, t.getMessage());
                }
            });
        }

    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }


}
