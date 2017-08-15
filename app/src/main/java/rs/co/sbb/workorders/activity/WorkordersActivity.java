package rs.co.sbb.workorders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.utils.BottomNavigationViewHelper;

public class WorkordersActivity extends AppCompatActivity {

    private TextView mTextMessage;

    public static final String WORKORDER_ID = "WORKORDER_ID";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                    Intent intentHome = new Intent(WorkordersActivity.this, HomeActivity.class);
                    startActivity(intentHome);

                    break;
                case R.id.item_workorders:
                    //mTextMessage.setText(R.string.title_nalozi);
                    break;
                case R.id.item_search:
                    Intent intentSearch = new Intent(WorkordersActivity.this, SearchWorkorderActivity.class);
                    startActivity(intentSearch);

                    break;

                case R.id.item_profile:
                    Intent intentProfile = new Intent(WorkordersActivity.this, ProfileActivity.class);
                    startActivity(intentProfile);

                    break;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        addMenu();

        attachWorkordersList();

    }

    private void attachWorkordersList() {
        ListView lstWorkorders = (ListView) findViewById(R.id.lstWorkorders);

        final ArrayList<String> orders = new ArrayList<String>();

        orders.add("111112313124");
        orders.add("77777777777");
        orders.add("431687699");
        orders.add("515151551515");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orders);

        lstWorkorders.setAdapter(arrayAdapter);

        lstWorkorders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String order = orders.get(position);

                Intent i = new Intent(WorkordersActivity.this, WorkorderDetailActivity.class);

                i.putExtra(WORKORDER_ID, order);

                startActivity(i);

            }
        });
    }

    private void addMenu() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
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
}
