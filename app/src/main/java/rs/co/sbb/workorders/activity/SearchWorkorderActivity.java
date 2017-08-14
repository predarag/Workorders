package rs.co.sbb.workorders.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.utils.BottomNavigationViewHelper;

public class SearchWorkorderActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case  R.id.item_home:
                    Intent intentHome = new Intent(SearchWorkorderActivity.this,HomeActivity.class);
                    startActivity(intentHome);

                    break;
                case R.id.item_workorders:
                    Intent intentMain = new Intent(SearchWorkorderActivity.this,WorkordersActivity.class);
                    startActivity(intentMain);

                    break;
                case R.id.item_search:
                    break;

                case R.id.item_profile:

                    Intent intentProfile = new Intent(SearchWorkorderActivity.this,ProfileActivity.class);
                    startActivity(intentProfile);

                    break;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_workorder);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        addMenu();

    }

    private void addMenu() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_search);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        BottomNavigationViewHelper.removeShiftMode(navigation);

    }

    @Override
    protected void onPause(){
        super.onPause();
        overridePendingTransition(0,0);
    }
}
