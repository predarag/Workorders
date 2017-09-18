package rs.co.sbb.workorders.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.SerbianAddressObject;
import rs.co.sbb.workorders.utils.Utils;
import rs.co.sbb.workorders.ws.impl.SerbianAddressServiceImpl;

public class TotalTvActivationActivity extends AppCompatActivity  {

    private Spinner communitySpinner;
    private Spinner settlementSpinner;
    private Spinner streetSpinner;
    private List<SerbianAddressObject> communitiesList = new ArrayList<>();
    private List<SerbianAddressObject> settlmentList = new ArrayList<>();
    private List<SerbianAddressObject> streetList = new ArrayList<>();

    private View activateTotalTvForm;
    private View progressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_tv_activation);

        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        activateTotalTvForm = (View) findViewById(R.id.total_tv_activation_form);
        progressView = (View) findViewById(R.id.total_tv_activation_progress);

        communitySpinner = (Spinner) findViewById(R.id.spinnerCommunities);
        settlementSpinner = (Spinner) findViewById(R.id.spinnerSettlement);
        streetSpinner = (Spinner) findViewById(R.id.spinnerStreet);

        getAllCommunitys();

        communitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SerbianAddressObject community = (SerbianAddressObject) parent.getSelectedItem();
                communitySpinner.setSelection(position);

                getSettlementByCode(community.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        settlementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SerbianAddressObject settelment = (SerbianAddressObject) parent.getSelectedItem();
                settlementSpinner.setSelection(position);

                getStreetBySettlementCode(settelment.getCode());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        streetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SerbianAddressObject street = (SerbianAddressObject) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void getAllCommunitys(){

        showProgress(true);

        SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

        Call<HashMap<String,String>> call = service.getAllCitys();

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {

                if(null != response && !response.isSuccessful() && response.errorBody() != null){
                    Log.i("communities", response.code()+"");
                    Log.i("communities", response.errorBody()+"");
                    showProgress(false);
                    Utils.showDialog(TotalTvActivationActivity.this,"Greška","Došlo je do greške prilikom povezivanja sa serverom, pokusajte ponovo!");

                }
                else{
                    HashMap<String, String> communities = response.body();

                    Iterator it = communities.entrySet().iterator();
                    while(it.hasNext()){

                        Map.Entry pair = (Map.Entry) it.next();

                        Log.i("communities",pair.getKey()+" "+pair.getValue());
                        communitiesList.add(new SerbianAddressObject(pair.getKey().toString(),
                                                           pair.getValue().toString()));
                    }

                    populateCommunitySpinner(communitiesList);

                }

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                showProgress(false);
                Utils.showDialog(TotalTvActivationActivity.this,"Greška","Došlo je do greške prilikom povezivanja sa serverom, pokusajte ponovo!");
                Log.e("communities",t.getMessage());
            }
        });

    }


    private void getSettlementByCode(String communityCode){

        Log.i("communityCode","USAO SA: "+communityCode);

        showProgress(true);

        settlmentList.clear();

        SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

        Call<HashMap<String,String>> call = service.getRegionByCityCode(communityCode);

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(null != response && !response.isSuccessful() && response.errorBody() != null){
                    Log.i("getSettlementByCode", response.code()+"");
                    Log.i("getSettlementByCode", response.errorBody()+"");

                    showProgress(false);

                    Utils.showDialog(TotalTvActivationActivity.this,"Greška","Došlo je do greške prilikom povezivanja sa serverom, pokusajte ponovo!");
                }
                else{
                    HashMap<String, String> communities = response.body();

                    Iterator it = communities.entrySet().iterator();
                    while(it.hasNext()){

                        Map.Entry pair = (Map.Entry) it.next();

                        Log.i("settelemt",pair.getKey()+" "+pair.getValue());
                        settlmentList.add(new SerbianAddressObject(pair.getKey().toString(),
                                pair.getValue().toString()));
                    }

                    populateSettlementSpiner(settlmentList);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                showProgress(false);
                Utils.showDialog(TotalTvActivationActivity.this,"Greška","Došlo je do greške prilikom povezivanja sa serverom, pokusajte ponovo!");
                Log.e("settelemt",t.getMessage());
            }
        });
    }


    private void getStreetBySettlementCode(String settlementCode){

        Log.i("streets","USAO SA: "+settlementCode);

        showProgress(true);

        streetList.clear();

        SerbianAddressServiceImpl service = new SerbianAddressServiceImpl();

        Call<HashMap<String,String>> call = service.getStreetsByRegionCode(settlementCode);

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(null != response && !response.isSuccessful() && response.errorBody() != null){
                    Log.i("streets", response.code()+"");
                    Log.i("streets", response.errorBody()+"");

                    showProgress(false);

                    Utils.showDialog(TotalTvActivationActivity.this,"Greška","Došlo je do greške prilikom povezivanja sa serverom, pokusajte ponovo!");
                }
                else{
                    HashMap<String, String> communities = response.body();

                    Iterator it = communities.entrySet().iterator();
                    while(it.hasNext()){

                        Map.Entry pair = (Map.Entry) it.next();

                        Log.i("streets",pair.getKey()+" "+pair.getValue());
                        streetList.add(new SerbianAddressObject(pair.getKey().toString(),
                                pair.getValue().toString()));
                    }

                    populateStreetSpiner(streetList);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.e("streets",t.getMessage());
                showProgress(false);
                Utils.showDialog(TotalTvActivationActivity.this,"Greška","Došlo je do greške prilikom povezivanja sa serverom, pokusajte ponovo!");
            }
        });
    }

    private void populateCommunitySpinner(List<SerbianAddressObject> communitiesList){
        ArrayAdapter<SerbianAddressObject> communityAdapter = new ArrayAdapter<SerbianAddressObject>(TotalTvActivationActivity.this,android.R.layout.simple_spinner_item,communitiesList);
        communityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        communitySpinner.setAdapter(communityAdapter);
        showProgress(false);
    }

    private void populateSettlementSpiner(List<SerbianAddressObject> settlmentList){
        ArrayAdapter<SerbianAddressObject> settlementAdapter = new ArrayAdapter<SerbianAddressObject>(TotalTvActivationActivity.this,android.R.layout.simple_spinner_item,settlmentList);
        settlementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        settlementSpinner.setAdapter(settlementAdapter);

        showProgress(false);
    }

    private void populateStreetSpiner(List<SerbianAddressObject> settlmentList){
        ArrayAdapter<SerbianAddressObject> streetAdapter = new ArrayAdapter<SerbianAddressObject>(TotalTvActivationActivity.this,android.R.layout.simple_spinner_item,streetList);
        streetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        streetSpinner.setAdapter(streetAdapter);

        showProgress(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            activateTotalTvForm.setVisibility(show ? View.GONE : View.VISIBLE);
            activateTotalTvForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    activateTotalTvForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            activateTotalTvForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
