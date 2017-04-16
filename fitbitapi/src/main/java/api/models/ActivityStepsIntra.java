package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/15/17.
 */

public class ActivityStepsIntra {
    @SerializedName("activities-steps")
    @Expose
    private List<Object> activitySteps = new ArrayList<Object>();

    @SerializedName("activities-steps-intraday")
    @Expose
    private ActivityStepsIntraDay activityStepsIntraDay;

    public List<Object> getActivitySteps() {
        return activitySteps;
    }

    public void setActivitySteps(List<Object> activitySteps) {
        this.activitySteps = activitySteps;
    }

    public ActivityStepsIntraDay getActivityStepsIntraDay() {
        return activityStepsIntraDay;
    }

    public void setActivityStepsIntraDay(ActivityStepsIntraDay activityStepsIntraDay) {
        this.activityStepsIntraDay = activityStepsIntraDay;
    }
}
