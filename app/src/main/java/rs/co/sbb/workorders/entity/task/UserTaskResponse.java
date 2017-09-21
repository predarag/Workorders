package rs.co.sbb.workorders.entity.task;

/**
 * Created by Predrag.Tasic on 9/21/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTaskResponse {

    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = null;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}