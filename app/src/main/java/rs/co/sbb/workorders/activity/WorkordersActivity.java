package rs.co.sbb.workorders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.Workorder;
import rs.co.sbb.workorders.helper.BottomNavigationViewHelper;
import rs.co.sbb.workorders.helper.DividerItemDecoration;
import rs.co.sbb.workorders.helper.RecyclerTouchListener;
import rs.co.sbb.workorders.helper.WorkordersAdapter;

public class WorkordersActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private TextView mTextMessage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<String> orders;
    private ListView lstWorkorders;

    private List<Workorder> workorders = new ArrayList<>();
    private WorkordersAdapter workordersAdapter;
    private RecyclerView recyclerView;

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
        setContentView(R.layout.activity_workorders);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        addMenu();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        workordersAdapter = new WorkordersAdapter(workorders);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(workordersAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Workorder workorder = workorders.get(position);
                Intent i = new Intent(WorkordersActivity.this, WorkorderDetailActivity.class);

                i.putExtra(WORKORDER_ID, workorder.getWorkorderNo());

                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        attachWorkordersList2();


    }

    private void attachWorkordersList2(){

        swipeRefreshLayout.setRefreshing(true);

        //workorders.clear();

        workorders.add(new Workorder("1233456","Nova aktivacija"));
        workorders.add(new Workorder("76539131","Nova aktivacija"));
        workorders.add(new Workorder("22243312","Zamena opreme"));
        workorders.add(new Workorder("98746119","Nova aktivacija"));
        workorders.add(new Workorder("26326391","Zamena opreme"));

        swipeRefreshLayout.setRefreshing(false);

        workordersAdapter.notifyDataSetChanged();
    }

    private void attachWorkordersList() {

        swipeRefreshLayout.setRefreshing(true);
        orders.clear();
        orders.add("111112313124");
        orders.add("77777777777");
        orders.add("431687699");
        orders.add("515151551515");

        swipeRefreshLayout.setRefreshing(false);


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

    @Override
    public void onRefresh() {
        attachWorkordersList2();
    }
}
