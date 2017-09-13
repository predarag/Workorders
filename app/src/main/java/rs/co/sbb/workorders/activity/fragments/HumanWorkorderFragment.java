package rs.co.sbb.workorders.activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.HomeActivity;
import rs.co.sbb.workorders.activity.ProfileActivity;
import rs.co.sbb.workorders.activity.SearchWorkorderActivity;
import rs.co.sbb.workorders.helper.BottomNavigationViewHelper;


public class HumanWorkorderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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



    public HumanWorkorderFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_human_workorder, container, false);

        addMenu(rootView);

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

}
