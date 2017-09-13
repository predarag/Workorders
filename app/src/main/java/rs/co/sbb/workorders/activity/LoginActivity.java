package rs.co.sbb.workorders.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.CompanyCode;
import rs.co.sbb.workorders.enums.EStatusCode;
import rs.co.sbb.workorders.enums.EUserStatus;
import rs.co.sbb.workorders.entity.LoginRequest;
import rs.co.sbb.workorders.entity.LoginResponse;
import rs.co.sbb.workorders.helper.PermissionHelper;
import rs.co.sbb.workorders.utils.SaveSharedPreference;
import rs.co.sbb.workorders.utils.Utils;
import rs.co.sbb.workorders.ws.impl.ExternalAuthServiceImpl;


public class LoginActivity extends AppCompatActivity  {


    // UI references.
    private TextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private AutoCompleteTextView tvLoginCompanyPicker;

    private static final String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        // Set up the login form.
        mUsernameView = (TextView) findViewById(R.id.username);

        PermissionHelper permissionHelper = new PermissionHelper(this);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionHelper.showPhoneStatePermission();
        }

        mPasswordView = (EditText) findViewById(R.id.password);

        tvLoginCompanyPicker = (AutoCompleteTextView) findViewById(R.id.tvLoginCompanyPicker);

        tvLoginCompanyPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvLoginCompanyPicker.showDropDown();
                tvLoginCompanyPicker.requestFocus();
                return false;
            }
        });

        tvLoginCompanyPicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompanyCode country = (CompanyCode) parent.getAdapter().getItem(position);

                if(country.getCode().equals("SI")){
                    Locale locale = new Locale("si");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
                }
                else{
                    Locale locale = new Locale("");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
                }
                SaveSharedPreference.setCountryCode(LoginActivity.this,country.getCode());

            }
        });

        ArrayAdapter<CompanyCode> companyPickerAdapter = new ArrayAdapter<CompanyCode>(this,android.R.layout.simple_dropdown_item_1line,getCompanyCodes());

        tvLoginCompanyPicker.setAdapter(companyPickerAdapter);

        if(null != SaveSharedPreference.getSessionToken(this) && !SaveSharedPreference.getSessionToken(this).equals("")){
            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
            LoginActivity.this.startActivity(i);
            finish();
            return;
        }
        else{
            attemptLogin();
        }

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Toast.makeText(this, Utils.getIPAddress(true),Toast.LENGTH_LONG).show();

        Log.i(TAG, "SERIAL: " + Build.SERIAL);
        Log.i(TAG,"MODEL: " + Build.MODEL);
        Log.i(TAG,"ID: " + Build.ID);
        Log.i(TAG,"Manufacture: " + Build.MANUFACTURER);
        Log.i(TAG,"brand: " + Build.BRAND);
        Log.i(TAG,"type: " + Build.TYPE);
        Log.i(TAG,"user: " + Build.USER);
        Log.i(TAG,"BASE: " + Build.VERSION_CODES.BASE);
        Log.i(TAG,"INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.i(TAG,"SDK  " + Build.VERSION.SDK);
        Log.i(TAG,"BOARD: " + Build.BOARD);
        Log.i(TAG,"BRAND " + Build.BRAND);
        Log.i(TAG,"HOST " + Build.HOST);
        Log.i(TAG,"FINGERPRINT: "+Build.FINGERPRINT);
        Log.i(TAG,"Version Code: " + Build.VERSION.RELEASE);
        Log.i(TAG,"HARDWARE: " + Build.HARDWARE);


    }


    @Override
    protected void onResume(){
        super.onResume();
    }


    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
           // mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            //mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {

            showProgress(true);

            String token = FirebaseInstanceId.getInstance().getToken();
            Log.i(TAG,token == null ? "" : token);
            Utils.setNotificationToken(username,token);

            login(username,password,focusView);


        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return null != username && !username.equals("");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void login(String username, String password, View focusView){

        ExternalAuthServiceImpl service = new ExternalAuthServiceImpl();

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        Call<LoginResponse> call = service.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(null != response && !response.isSuccessful() && response.errorBody() != null){
                    Log.i(TAG, response.code()+"");
                    Log.i(TAG, response.errorBody()+"");

                    showProgress(false);
                    Snackbar.make(LoginActivity.this.mLoginFormView,getString(R.string.error_server),Snackbar.LENGTH_LONG).show();
                    //Utils.showDialog(LoginActivity.this,getString(R.string.error),getString(R.string.error_server));
                                    }
                else{
                    LoginResponse loginResponse = response.body();
                    Log.i(TAG,loginResponse.toString());
                    showProgress(false);
                    View view = null;
                    if(null != loginResponse.getStatus() && !loginResponse.getStatus().equals(EStatusCode.OK.value())){
                        if(null!= loginResponse.getStatusMessage()){
                            switch (loginResponse.getStatusMessage()) {
                                case "User is not active":

                                    //Utils.showDialog(LoginActivity.this,"",getString(R.string.error_user_not_exist));
                                    Snackbar.make(LoginActivity.this.mLoginFormView,getString(R.string.error_user_not_exist),Snackbar.LENGTH_LONG).show();
                                    break;
                                case "User doesn't exist":

                                    //Utils.showDialog(LoginActivity.this,"",getString(R.string.error_invalid_username_password));
                                    Snackbar.make(LoginActivity.this.mLoginFormView,getString(R.string.error_invalid_username_password),Snackbar.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    }

                    else{
                        if(loginResponse.getUser().getIsActive().equals(EUserStatus.ACTIVE.getStatus())){
                            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                            LoginActivity.this.startActivity(i);

                            String sessionToken = "";

                            if(null != loginResponse.getSessionToken() && !loginResponse.getSessionToken().equals("")){
                                sessionToken = loginResponse.getSessionToken();
                                Log.i(TAG,"SESSION_TOKEN: "+sessionToken);
                                Utils.setUserPreference(LoginActivity.this,loginResponse.getUser(),sessionToken);
                            }

                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showProgress(false);
                Log.i(TAG,t.getMessage());
                Snackbar.make(LoginActivity.this.mLoginFormView,getString(R.string.error_server),Snackbar.LENGTH_LONG).show();
                //Utils.showDialog(LoginActivity.this,getString(R.string.error),getString(R.string.error_server));
                t.printStackTrace();

            }
        });

    }



    @Override
    public void onBackPressed() {
    }

    private List<CompanyCode> getCompanyCodes(){

        List<CompanyCode> countrys = new ArrayList<>();

        for(Map.Entry<String,String> codes : Utils.getCountryCodes().entrySet()){

            String code = codes.getKey();
            String name = codes.getValue();

            countrys.add(new CompanyCode(code,name));

        }

        return countrys;
    }

}

