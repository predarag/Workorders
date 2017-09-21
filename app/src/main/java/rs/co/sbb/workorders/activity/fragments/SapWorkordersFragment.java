package rs.co.sbb.workorders.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.HomeActivity;
import rs.co.sbb.workorders.activity.ProfileActivity;
import rs.co.sbb.workorders.activity.SearchWorkorderActivity;
import rs.co.sbb.workorders.activity.WorkorderDetailActivity;
import rs.co.sbb.workorders.activity.WorkordersActivity;
import rs.co.sbb.workorders.entity.Workorder;
import rs.co.sbb.workorders.helper.BottomNavigationViewHelper;
import rs.co.sbb.workorders.helper.DividerItemDecoration;
import rs.co.sbb.workorders.helper.RecyclerTouchListener;
import rs.co.sbb.workorders.helper.WorkordersAdapter;

import static rs.co.sbb.workorders.activity.WorkordersActivity.WORKORDER_ID;

/**
 * Created by Predrag.Tasic on 9/12/2017.
 */

public class SapWorkordersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private TextView mTextMessage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<String> orders;
    private ListView lstWorkorders;

    private List<Workorder> workorders = new ArrayList<>();
    private WorkordersAdapter workordersAdapter;
    private RecyclerView recyclerView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                    Intent intentHome = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intentHome);

                    break;
                case R.id.item_workorders:
                    //mTextMessage.setText(R.string.title_nalozi);
                    break;
                case R.id.item_search:
                    Intent intentSearch = new Intent(getActivity(), SearchWorkorderActivity.class);
                    startActivity(intentSearch);

                    break;

                case R.id.item_profile:
                    Intent intentProfile = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(intentProfile);

                    break;

            }
            return false;
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_sap_workorders, container, false);

        addMenu(rootView);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_sap_wo);
        workordersAdapter = new WorkordersAdapter(workorders,getActivity());

       // recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(workordersAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Workorder workorder = workorders.get(position);
                Intent i = new Intent(getActivity(), WorkorderDetailActivity.class);

                i.putExtra(WORKORDER_ID, workorder.getWorkorderNo());

                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_sap_wo);
        swipeRefreshLayout.setOnRefreshListener(this);

        attachWorkordersList2();

        return rootView;
    }

    @Override
    public void onRefresh() {
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

    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(0, 0);
    }

    private void addMenu(View rootView) {
        BottomNavigationView navigation = (BottomNavigationView) rootView.findViewById(R.id.navigation_sap_wo);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        BottomNavigationViewHelper.removeShiftMode(navigation);

    }

}
