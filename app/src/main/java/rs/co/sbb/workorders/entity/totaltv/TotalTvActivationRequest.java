package rs.co.sbb.workorders.entity.totaltv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Predrag.Tasic on 9/13/2017.
 */

public class TotalTvActivationRequest {


    @Expose
    @SerializedName("activationRequest")
    private TotalTvActivationHolder totalTvActivationHolder;

    public TotalTvActivationHolder getTotalTvActivationHolder() {
        return totalTvActivationHolder;
    }

    public void setTotalTvActivationHolder(TotalTvActivationHolder totalTvActivationHolder) {
        this.totalTvActivationHolder = totalTvActivationHolder;
    }
}
