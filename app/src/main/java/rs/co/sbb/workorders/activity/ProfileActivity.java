package rs.co.sbb.workorders.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.enums.EStatusCode;
import rs.co.sbb.workorders.entity.LoginRequest;
import rs.co.sbb.workorders.entity.Response;
import rs.co.sbb.workorders.helper.BottomNavigationViewHelper;
import rs.co.sbb.workorders.utils.SaveSharedPreference;
import rs.co.sbb.workorders.utils.Utils;
import rs.co.sbb.workorders.ws.impl.ExternalAuthServiceImpl;

public class ProfileActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhoneNumber;

    private static final String TAG = "PROFILE";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                    Intent intentHome = new Intent(ProfileActivity.this,HomeActivity.class);
                    startActivity(intentHome);

                    return true;
                case R.id.item_workorders:
                    Intent intentMain = new Intent(ProfileActivity.this,WorkordersActivity.class);
                    startActivity(intentMain);

                    return true;
                case R.id.item_search:
                    Intent intentSearch = new Intent(ProfileActivity.this,SearchWorkorderActivity.class);
                    startActivity(intentSearch);

                    return true;

                case R.id.item_profile:

                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        addMenu();

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName =  (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhoneNumber = (EditText) findViewById(R.id.etPhone);

        populateTextViews();
    }

    private void addMenu() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_profle);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        BottomNavigationViewHelper.removeShiftMode(navigation);
    }

    @Override
    protected void onPause(){
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed() {
    }

    private void populateTextViews(){

        etFirstName.setText(SaveSharedPreference.getFIRSTNAME(this));
        etLastName.setText(SaveSharedPreference.getLASTNAME(this));
        etEmail.setText(SaveSharedPreference.getEMAIL(this));
        etPhoneNumber.setText(SaveSharedPreference.getPHONE(this));

    }

    public void logout(View view){
        showDialog(this,"Odjava",getString(R.string.profile_logout_message),view);
    }


    public void showDialog(Context context, String title, String message, final View view){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);

        dlgAlert.setPositiveButton("Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logout(view, SaveSharedPreference.getUser(ProfileActivity.this));

                    }
                });

        dlgAlert.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

    }

    public void logout(final View view,String username){

        ExternalAuthServiceImpl authService = new ExternalAuthServiceImpl();

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);

        Call<Response> call = authService.logout(loginRequest);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(null != response && !response.isSuccessful() && response.errorBody() != null){
                    Log.i(TAG,"ERROR CODE: "+response.code());
                    try {
                        Log.i(TAG,"ERROR BODY: "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Snackbar.make(view,getString(R.string.error_server),Snackbar.LENGTH_LONG).show();
                }
                else {
                    Response logoutResponse = response.body();
                    Log.i(TAG,"Response: "+logoutResponse.getStatus()+" "+logoutResponse.getStatusMessage());
                    if(logoutResponse != null) {
                        if (logoutResponse.getStatus().equals(EStatusCode.OK.value())) {
                            deleteNotificationToken();
                            Utils.deleteUserPreference(ProfileActivity.this);
                            Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            Snackbar.make(view, getString(R.string.error_general), Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Snackbar.make(view,getString(R.string.error_server),Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void deleteNotificationToken(){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.i(TAG,"Deleting notificatoin token...");
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    Log.e(TAG,e.getMessage());
                    e.printStackTrace();
                }
                return  null;
            }
        }.execute();

    }
}
