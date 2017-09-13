package rs.co.sbb.workorders.helper;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.entity.Workorder;

/**
 * Created by Predrag.Tasic on 8/17/2017.
 */

public class WorkordersAdapter extends RecyclerView.Adapter<WorkordersAdapter.MyViweHolder> {

    private List<Workorder> workorderList;
    private int lastPosition = -1;
    private Context context;
    int color;

    Animation animBlink;


    public class MyViweHolder extends RecyclerView.ViewHolder{
        public TextView workorderType, workorderNo, workorderColot;
        public MyViweHolder(View itemView) {
            super(itemView);
            workorderColot = (TextView) itemView.findViewById(R.id.itemColor);
            workorderType = (TextView) itemView.findViewById(R.id.workorder_type);
            workorderNo = (TextView) itemView.findViewById(R.id.workorder_no);
        }
    }

    public WorkordersAdapter(List<Workorder> workorderList, Context context){
        this.workorderList = workorderList;
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getColor(R.color.total_tv_list_color);
        }

        animBlink = AnimationUtils.loadAnimation(context,
                R.anim.anim_blink);



    }


    @Override
    public MyViweHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.workorder_list_row, parent, false);
        return new MyViweHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViweHolder holder, int position) {
        Workorder workorder = workorderList.get(position);
        holder.workorderNo.setText(workorder.getWorkorderNo());
        holder.workorderType.setText(workorder.getWorkorderType());
        holder.workorderColot.setBackgroundColor(color);

        if(workorder.getWorkorderNo().equals("1233456"))
            holder.workorderColot.setAnimation(animBlink);

        setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return workorderList.size();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


}
