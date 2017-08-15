package rs.co.sbb.workorders.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.utils.BottomNavigationViewHelper;

public class ProfileActivity extends AppCompatActivity {


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
}
