package rs.co.sbb.workorders.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import rs.co.sbb.workorders.R;

public class WorkorderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_detail);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        Intent i = getIntent();
        String wororderNumber = i.getStringExtra(WorkordersActivity.WORKORDER_ID);

        TextView textView = (TextView) findViewById(R.id.tvWorkorderId);
        textView.setText(wororderNumber);
    }

    @Override
    protected void onPause(){
        super.onPause();
        overridePendingTransition(0,0);
    }
}
