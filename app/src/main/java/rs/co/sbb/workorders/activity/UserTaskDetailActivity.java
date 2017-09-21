package rs.co.sbb.workorders.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.fragments.HumanWorkorderFragment;
import rs.co.sbb.workorders.entity.task.Task;
import rs.co.sbb.workorders.enums.ETaskStatus;

public class UserTaskDetailActivity extends AppCompatActivity {


    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPartnerNo;
    private EditText etProviderOrderNo;
    private EditText etServiceOrderNo;
    private EditText etSapTeamId;
    private EditText etInternalTeamId;
    private EditText etTaskStatus;
    private EditText etBpmId;
    private EditText etTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task_detail);

        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        etFirstName = (EditText) findViewById(R.id.etUsrTaskFirstName);
        etLastName = (EditText) findViewById(R.id.etUsrTaskLastName);
        etPartnerNo = (EditText) findViewById(R.id.etUsrTaskPartnerNo);
        etProviderOrderNo = (EditText) findViewById(R.id.etUsrTaskProviderOrderNo);
        etServiceOrderNo = (EditText) findViewById(R.id.etUsrTaskServiceOrderNo);
        etSapTeamId = (EditText) findViewById(R.id.etUsrTaskSapTeamId);
        etInternalTeamId = (EditText) findViewById(R.id.etUsrTaskInternalTeamId);
        etTaskStatus = (EditText) findViewById(R.id.etUsrTaskStatus);
        etBpmId = (EditText) findViewById(R.id.etUsrTaskBpmId);
        etTaskId = (EditText) findViewById(R.id.etUsrTaskTaskId);

        Intent intent = getIntent();

        Task task = (Task) intent.getSerializableExtra(HumanWorkorderFragment.USER_TASK);

        int processColor = ContextCompat.getColor(this,R.color.total_tv_list_color);
        int processFinishColor = ContextCompat.getColor(this,R.color.task_completed);

        if(task != null){
            etFirstName.setText(task.getFirstName());
            etLastName.setText(task.getLastName());
            etPartnerNo.setText(task.getPartnerNo());
            etProviderOrderNo.setText(task.getProviderOrderId());
            etServiceOrderNo.setText(task.getServiceOrderId());
            etSapTeamId.setText(task.getSapTeamId());
            etInternalTeamId.setText(task.getTeamId());
            etTaskStatus.setText(task.getTaskStatus());
            etBpmId.setText(task.getBpmId());
            etTaskId.setText(task.getTaskId());

            if(task.getTaskStatus().equals(ETaskStatus.IN_PROCESS.getStatus()))
                etTaskStatus.setTextColor(processColor);
            else
                etTaskStatus.setTextColor(processFinishColor);

        }


    }
}

