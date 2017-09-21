package rs.co.sbb.workorders.activity.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.HomeActivity;
import rs.co.sbb.workorders.activity.ProfileActivity;
import rs.co.sbb.workorders.activity.SearchWorkorderActivity;
import rs.co.sbb.workorders.activity.UserTaskDetailActivity;
import rs.co.sbb.workorders.activity.WorkorderDetailActivity;
import rs.co.sbb.workorders.entity.task.Task;
import rs.co.sbb.workorders.entity.task.UserTaskResponse;
import rs.co.sbb.workorders.helper.BottomNavigationViewHelper;
import rs.co.sbb.workorders.helper.DividerItemDecoration;
import rs.co.sbb.workorders.helper.RecyclerTouchListener;
import rs.co.sbb.workorders.helper.UserTasksAdapter;
import rs.co.sbb.workorders.utils.SaveSharedPreference;
import rs.co.sbb.workorders.utils.Utils;
import rs.co.sbb.workorders.ws.config.MobAppIntegrationConfig;
import rs.co.sbb.workorders.ws.impl.MobAppIntegrationServiceImpl;

import static rs.co.sbb.workorders.activity.WorkordersActivity.WORKORDER_ID;


public class HumanWorkorderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private List<Task> userTasks ;
    private UserTasksAdapter userTasksAdapter;
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private final String TAG = "HumanWorkorderFragment";

    public static final String USER_TASK = "USER_TASK";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_human_workorder, container, false);

        addMenu(rootView);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_human_wo);


  /*      if(recyclerView != null)
            recyclerView.setHasFixedSize(true);*/

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Task task = userTasks.get(position);
                Intent i = new Intent(getActivity(), UserTaskDetailActivity.class);

                i.putExtra(USER_TASK, task);

                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_human_wo);
        swipeRefreshLayout.setOnRefreshListener(this);

        getUserTasks();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(0, 0);
    }

    private void addMenu(View rootView) {
        BottomNavigationView navigation = (BottomNavigationView) rootView.findViewById(R.id.navigation_human_wo);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        BottomNavigationViewHelper.removeShiftMode(navigation);

    }

    @Override
    public void onRefresh() {
        getUserTasks();
    }

    private void getUserTasks(){

        Activity activity = getActivity();

        if (isAdded() && activity != null) {

            if(null != userTasks)
                userTasks.clear();

            swipeRefreshLayout.setRefreshing(true);

            MobAppIntegrationServiceImpl service = new MobAppIntegrationServiceImpl(MobAppIntegrationConfig.BPM_BASE_PATH);

            Call<UserTaskResponse> call = service.getUserTasks(SaveSharedPreference.getUser(getActivity()), SaveSharedPreference.getCountryCode(getActivity()));

            call.enqueue(new Callback<UserTaskResponse>() {
                @Override
                public void onResponse(Call<UserTaskResponse> call, Response<UserTaskResponse> response) {
                    if (null != response && !response.isSuccessful() && response.errorBody() != null) {
                        Log.i(TAG, response.code() + "");
                        Log.i(TAG, response.body() + "");
                        swipeRefreshLayout.setRefreshing(false);
                        Utils.showDialog(getActivity(),getActivity().getString(R.string.error), getActivity().getString(R.string.error_server));
                    }
                    else {
                        swipeRefreshLayout.setRefreshing(false);
                        UserTaskResponse taskResponse = response.body();

                        if(taskResponse != null){
                            Log.i(TAG,"taskResponse != null");
                            userTasks = taskResponse.getTasks();
                            userTasksAdapter = new UserTasksAdapter(userTasks,getActivity());
                            recyclerView.setAdapter(userTasksAdapter);

                            Log.i(TAG,"size: "+userTasks.size());
                            userTasksAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserTaskResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                    Utils.showDialog(getActivity(),getActivity().getString(R.string.error), getActivity().getString(R.string.error_server));
                }
            });
        }



    }

}
