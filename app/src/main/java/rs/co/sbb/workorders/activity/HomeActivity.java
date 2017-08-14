package rs.co.sbb.workorders.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.utils.BottomNavigationViewHelper;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                   return true;
                case R.id.item_workorders:
                    Intent intentMain = new Intent(HomeActivity.this,WorkordersActivity.class);
                    startActivity(intentMain);

                    return true;
                case R.id.item_search:
                    Intent intentSearch = new Intent(HomeActivity.this,SearchWorkorderActivity.class);
                    startActivity(intentSearch);

                    return true;

                case R.id.item_profile:

                    Intent intentProfile = new Intent(HomeActivity.this,ProfileActivity.class);
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

        addMenu();
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
    protected void onPause(){
        super.onPause();
        overridePendingTransition(0,0);
    }
}
