package rs.co.sbb.workorders.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.Response;
import rs.co.sbb.workorders.entity.totaltv.TotalTvActivationHolder;
import rs.co.sbb.workorders.entity.totaltv.TotalTvActivationRequest;
import rs.co.sbb.workorders.enums.EStatusCode;
import rs.co.sbb.workorders.helper.TotalTvActivationHelper;
import rs.co.sbb.workorders.wizards.model.TTVWizardModel;
import rs.co.sbb.workorders.wizards.pages.TTVActivationStepThreeFragment;
import rs.co.sbb.workorders.wizards.wizardpager.model.AbstractWizardModel;
import rs.co.sbb.workorders.wizards.wizardpager.model.ModelCallbacks;
import rs.co.sbb.workorders.wizards.wizardpager.model.Page;
import rs.co.sbb.workorders.wizards.wizardpager.ui.PageFragmentCallbacks;
import rs.co.sbb.workorders.wizards.wizardpager.ui.ReviewFragment;
import rs.co.sbb.workorders.wizards.wizardpager.ui.StepPagerStrip;
import rs.co.sbb.workorders.ws.config.MobAppIntegrationConfig;
import rs.co.sbb.workorders.ws.impl.MobAppIntegrationServiceImpl;

/**
 * Created by milos.milic on 8/17/2017.
 */

public class WizardActivity extends AppCompatActivity implements PageFragmentCallbacks, ReviewFragment.Callbacks, ModelCallbacks {

    private static final String TAG = "WizardActivityTTV";
    public static final String ACTIVATION_MESSAGE = "ACTIVATION_MESSAGE";

    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;

    private boolean mEditingAfterReview;

    private AbstractWizardModel mWizardModel = new TTVWizardModel(this);

    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;

    public List<Page> getmCurrentPageSequence() {
        return mCurrentPageSequence;
    }

    public void setmCurrentPageSequence(List<Page> mCurrentPageSequence) {
        this.mCurrentPageSequence = mCurrentPageSequence;
    }

    private Button mPrevButton;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;

    private View ttvWizardWorm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wizard_holder_activity);

        ttvWizardWorm = (View) findViewById(R.id.ttv_wizard_form);

        // TODO detect which fragments to get - TTV or WO

        Log.i(TAG,"onCreate");

        //mWizardModel = new TTVWizardModel(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
//        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);

        mStepPagerStrip
                .setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
                    @Override
                    public void onPageStripSelected(int position) {
                        position = Math.min(mPagerAdapter.getCount() - 1,
                                position);
                        if (mPager.getCurrentItem() != position) {
                            mPager.setCurrentItem(position);
                        }
                    }
                });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mNextButton = (Button) findViewById(R.id.next_button);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                Log.i(TAG,"onPageSelected");
                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });



        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    /* TODO .show dialog throws exception */
                    showDialogQ(WizardActivity.this, "Radni nalog", "Da li ste sigurni da želite da pošaljete radni zadatak.");


                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();

        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    @Override
    public void onPageDataChanged(Page page) {
        Log.i(TAG,"onPageDataChanged");
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }

    @Override
    public void onPageTreeChanged() {
        Log.i(TAG,"onPageTreeChanged");
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 =
        // review
        // step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }
    private boolean recalculateCutOffPage() {
        Log.i(TAG,"updateBottomBar");
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                Log.i(TAG, "page.isRequired() && !page.isCompleted()");
                cutOffPage = i;
                break;
           /* }
            else{
                Log.i(TAG,"ELSE page.isRequired() && !page.isCompleted()");
                updateBottomBar();
            }*/
            }
        }
        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }
        return false;
    }
    public void updateBottomBar() {
        Log.i(TAG,"updateBottomBar");
 /*       int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
//            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
            } else {
                mNextButton.setTextAppearance(R.style.TextAppearanceFinish);
            }
        } else {
            mNextButton.setText(mEditingAfterReview ? R.string.review
                    : R.string.next);
            mNextButton
                    .setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v,
                    true);
//            mNextButton.setTextAppearance(this, v.resourceId);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                mNextButton.setTextAppearance(this, v.resourceId);
            } else {
                mNextButton.setTextAppearance(v.resourceId);
            }
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }
        mPrevButton.setText(R.string.prev);
        mPrevButton
                .setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);

        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isCompleted()) {
                Log.i(TAG, "page.isRequired() && !page.isCompleted()");
                mNextButton.setEnabled(true);
                page.setRequired(false);
                break;
            }
        }*/

        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);

    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String pageKey) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(pageKey)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }
            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: optimize this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }
            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            return Math.min(mCutOffPage + 1, mCurrentPageSequence == null ? 1
                    : mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }


    private void showDialogQ(final Context context, String title, String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);

        dlgAlert.setPositiveButton("Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        TotalTvActivationHolder request = TotalTvActivationHelper.createRequest(mCurrentPageSequence, WizardActivity.this);
                        activateEquipment(request, WizardActivity.this);

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

    private void activateEquipment(TotalTvActivationHolder request, final Context context){

        MobAppIntegrationServiceImpl service = new MobAppIntegrationServiceImpl(MobAppIntegrationConfig.TOTALTV_BASE_PATH);

        TotalTvActivationRequest activationRequest = new TotalTvActivationRequest();
        activationRequest.setTotalTvActivationHolder(request);

        Call<Response> call = service.activateTotalTvEquipment(activationRequest);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(null != response && !response.isSuccessful() && response.errorBody() != null) {
                    Log.i(TAG, response.code() + "");
                    Log.i(TAG, response.errorBody() + "");
                    Snackbar.make(WizardActivity.this.ttvWizardWorm, getString(R.string.error_server), Snackbar.LENGTH_LONG).show();
                }
                else{
                    Response callResponse = response.body();
                    Log.i(TAG, callResponse.getStatus()+" "+callResponse.getStatusMessage());
                    Intent i = new Intent(context,HomeActivity.class);
                    if(null != callResponse.getStatus() && callResponse.getStatus().equals(EStatusCode.OK.value())){
                        i.putExtra(ACTIVATION_MESSAGE,getString(R.string.ttv_message_workorder_activation_sent));
                    }
                    else{
                        i.putExtra(ACTIVATION_MESSAGE,getString(R.string.ttv_message_workorder_activation_failed));
                    }


                    context.startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }


}
