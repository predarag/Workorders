package rs.co.sbb.workorders.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.helper.BottomNavigationViewHelper;
import rs.co.sbb.workorders.utils.SaveSharedPreference;
import rs.co.sbb.workorders.utils.Utils;

public class ProfileActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhoneNumber;

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
        showDialog(this,"Odjava","Da li ste sigurni da zelite da se odjavite?");
    }


    public void showDialog(Context context, String title, String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

        dlgAlert.setMessage(title);
        dlgAlert.setTitle(message);

        dlgAlert.setPositiveButton("Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.deleteUserPreference(ProfileActivity.this);
                        Intent i = new Intent(ProfileActivity.this,LoginActivity.class);
                        startActivity(i);
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
}
