package rs.co.sbb.workorders.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.helper.BottomNavigationViewHelper;
import rs.co.sbb.workorders.helper.PermissionHelper;
import rs.co.sbb.workorders.service.LocationService;
import rs.co.sbb.workorders.utils.SaveSharedPreference;
import rs.co.sbb.workorders.utils.Utils;

public class HomeActivity extends AppCompatActivity {


    private static final String TAG = "HomeActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                    return true;
                case R.id.item_workorders:
                    Intent intentMain = new Intent(HomeActivity.this, WorkordersActivity.class);
                    startActivity(intentMain);

                    return true;
                case R.id.item_search:
                    Intent intentSearch = new Intent(HomeActivity.this, SearchWorkorderActivity.class);
                    startActivity(intentSearch);

                    return true;

                case R.id.item_profile:

                    Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intentProfile);

                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        View homeView = findViewById(R.id.content);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionHelper permissionHelper = new PermissionHelper(this);
            permissionHelper.showPhoneStatePermission();
            startService(new Intent(this, LocationService.class));

        }
        else{
            startService(new Intent(this, LocationService.class));
        }


        addMenu();

        displayNotification(getIntent());

        Intent i = getIntent();

        String wizardActivationMessage = i.getStringExtra(WizardActivity.ACTIVATION_MESSAGE);

        if(null != wizardActivationMessage && !wizardActivationMessage.equals("")){
            Snackbar.make(homeView, wizardActivationMessage, Snackbar.LENGTH_LONG).show();
        }

        Utils.deleteDevicesPreference(this);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Bundle bundle = intent.getExtras();
        Log.i(TAG,"onNewIntent");
        if(bundle != null){
            Log.i(TAG,"BUNDLE NIJE NULL");
        }
        else {
            Log.i(TAG,"BUNDLE NULL");
        }
    }

    private void addMenu() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        BottomNavigationViewHelper.removeShiftMode(navigation);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
    }

    public void openTotalTvActivation(View view) {

        Intent intentTotalTvActivation = new Intent(HomeActivity.this, WizardActivity.class);
        startActivity(intentTotalTvActivation);

    }

    public void openOcrView(View view) {
        Intent ocrIntent = new Intent(HomeActivity.this, OcrReaderActivity.class);
        startActivity(ocrIntent);
    }

    /*@Override
    public void onStart(){
        super.onStart();
        Log.i(TAG,"On start..");
        //displayNotification();
    }*/

    /*@Override
    public void onResume(){
        Log.i(TAG,"On resume..");
        super.onResume();
        //displayNotification();
    }*/


    private void displayNotification(Intent intent){

        Bundle extras = getIntent().getExtras();

        String contract = "";
        String partnerNo = "";
        String time = "";

        if(extras != null) {

            contract = extras.getString("contract");
            partnerNo = extras.getString("partnerNo");
            time = extras.getString("time");

            Log.i(TAG, "contract=" + contract);
            Log.i(TAG, "partnerNo=" + partnerNo);
            Log.i(TAG, "time=" + time);

            String dialogMessage = "Ugovor:" + contract + "\n" +
                    "Partner: " + partnerNo + "\n" +
                    "Time: " + time;

            if(null != contract && !contract.equals(""))
                Utils.showDialog(this, "Radni nalog", dialogMessage);

            Utils.deleteNotificationPreference(this);

        }
        else
        {


        }


    }

}
