package rs.co.sbb.workorders.helper;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.task.Task;
import rs.co.sbb.workorders.enums.ETaskStatus;

/**
 * Created by Predrag.Tasic on 9/21/2017.
 */

public class UserTasksAdapter extends RecyclerView.Adapter<UserTasksAdapter.MyViweHolder> {

    private List<Task> tasks;
    private int lastPosition = -1;
    private Context context;
    int processColor;
    int processFinishColor;
    int midProcessColor;

    Animation animBlink;

    public class MyViweHolder extends RecyclerView.ViewHolder {
        public TextView taskProviderOrderNo, taskProcessStatus, taskProcessColor, taskProcessName;

        public MyViweHolder(View itemView) {
            super(itemView);
            taskProviderOrderNo = (TextView) itemView.findViewById(R.id.tvUserTaskNo);
            taskProcessStatus = (TextView) itemView.findViewById(R.id.tvUserTaskProcessStatus);
            taskProcessColor = (TextView) itemView.findViewById(R.id.tvUserTaskProcessColor);
            taskProcessName = (TextView) itemView.findViewById(R.id.tvUserTaskName);
        }
    }

    public UserTasksAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;

        Log.i("HumanWorkorderFragment","UserTasksAdapter init..");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            processColor = context.getColor(R.color.total_tv_list_color);
            processFinishColor = context.getColor(R.color.task_completed);
            midProcessColor = context.getColor(R.color.task_in_process);
        }

        animBlink = AnimationUtils.loadAnimation(context,
                R.anim.anim_blink);

    }

    @Override
    public UserTasksAdapter.MyViweHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i("HumanWorkorderFragment","onCreateViewHolder init..");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_tasks_list_row, parent, false);
        return new UserTasksAdapter.MyViweHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViweHolder holder, int position) {
        Task task = tasks.get(position);

        Log.i("HumanWorkorderFragment","onBindViewHolder init..");

        Log.i("HumanWorkorderFragment",task.getTaskStatus());
        Log.i("HumanWorkorderFragment",task.getTaskId());

        holder.taskProcessStatus.setText(task.getTaskStatus());
        holder.taskProcessName.setText(task.getFirstName()+" "+task.getLastName());
        if(null != task.getProviderOrderId() && !task.getProviderOrderId().equals("")) {
            holder.taskProviderOrderNo.setText(task.getProviderOrderId());
        }

        if (task.getTaskStatus().equals(ETaskStatus.IN_PROCESS.getStatus())) {
            holder.taskProcessColor.setAnimation(animBlink);
            if(null != task.getProviderOrderId() && !task.getProviderOrderId().equals("")) {
                holder.taskProcessColor.setBackgroundColor(midProcessColor);
            }
            else{
                holder.taskProcessColor.setBackgroundColor(processColor);
            }

        } else {
            holder.taskProcessColor.setBackgroundColor(processFinishColor);
        }

        setAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        Log.i("HumanWorkorderFragment","getCount: "+tasks.size());
        return tasks.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
