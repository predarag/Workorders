package rs.co.sbb.workorders.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.LoginRequest;
import rs.co.sbb.workorders.entity.LoginResponse;
import rs.co.sbb.workorders.utils.Utils;
import rs.co.sbb.workorders.ws.impl.ExternalAuthServiceImpl;
import rs.co.sbb.workorders.ws.impl.TokenServiceImpl;


public class LoginActivity extends AppCompatActivity  {


    private UserLoginTask mAuthTask = null;

    // UI references.
    private TextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        // Set up the login form.
        mUsernameView = (TextView) findViewById(R.id.username);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

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

    }

    @Override
    protected void onResume(){
        super.onResume();
        //checkPlayServices();
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
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {

            showProgress(true);

            TokenServiceImpl tokenService = new TokenServiceImpl();

            String token = FirebaseInstanceId.getInstance().getToken();
            Log.i("LOGIN",token);

            login(username,password,focusView);

            /*Intent i = new Intent(LoginActivity.this,HomeActivity.class);
            LoginActivity.this.startActivity(i);*/
            //finish();
           /* if(null != token && !token.equals("")) {

                Call<TokenResponse> call = tokenService.token(username,token);
                call.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        showProgress(false);
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        LoginActivity.this.startActivity(i);
                        Log.i("LOGIN","OK");
                        //finish();
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        Log.i("LOGIN","ERROR"+t.getMessage());
                        showProgress(false);
                    }
                });

            }*/

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
                    Log.i("login", response.code()+"");
                    Log.i("login", response.errorBody()+"");

                    showProgress(false);

                    try {
                        Toast.makeText(LoginActivity.this,response.errorBody().string(),Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e("streets",e.getMessage());
                        showProgress(false);
                        e.printStackTrace();
                    }
                }
                else{
                    LoginResponse loginResponse = response.body();
                    Log.i("login",loginResponse.toString());
                    showProgress(false);
                    View view = null;
                    if(null != loginResponse.getStatus() && !loginResponse.getStatus().equals("OK")){
                        if(null!= loginResponse.getStatusMessage()){
                            switch (loginResponse.getStatusMessage()) {
                                case "User is not active":
                                    mPasswordView.setError("Korisnik nije aktivan");
                                    view = mPasswordView;
                                    break;
                                case "User doesn't exist":
                                    mPasswordView.setError("Neispravno korisnicko ime ili lozinka");
                                    view = mPasswordView;
                                    break;
                            }
                        }
                    }

                    else{
                        if(loginResponse.getUser().getIsActive().equals("1")){
                            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                            LoginActivity.this.startActivity(i);
                            finish();
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("login",t.getMessage());
                t.printStackTrace();

            }
        });

    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            String token = FirebaseInstanceId.getInstance().getToken();
            if(null != token && !token.equals("")) {
                //Toast.makeText(getApplicationContext(), "NULLLLL", Toast.LENGTH_SHORT).show();
                Log.i("TOKEN", token);
            }
            else {
                //Toast.makeText(getApplicationContext(),"NIJEE NULLLLL",Toast.LENGTH_SHORT).show();
                Log.i("TOKEN", "NULL je");
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
