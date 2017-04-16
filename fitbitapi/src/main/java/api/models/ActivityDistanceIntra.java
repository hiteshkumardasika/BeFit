package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/15/17.
 */

public class ActivityDistanceIntra {
    @SerializedName("activities-distance")
    @Expose
    private List<Object> activityDistances = new ArrayList<Object>();

    @SerializedName("activities-distance-intraday")
    @Expose
    private ActivityStepsIntraDay activityDistanceIntraDay;

    public List<Object> getActivityDistances() {
        return activityDistances;
    }

    public void setActivityDistances(List<Object> activityDistances) {
        this.activityDistances = activityDistances;
    }

    public ActivityStepsIntraDay getActivityDistanceIntraDay() {
        return activityDistanceIntraDay;
    }

    public void setActivityDistanceIntraDay(ActivityStepsIntraDay activityDistanceIntraDay) {
        this.activityDistanceIntraDay = activityDistanceIntraDay;
    }
}
