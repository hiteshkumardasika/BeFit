package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/15/17.
 */

public class ActivityCaloriesIntra {
    @SerializedName("activities-calories")
    @Expose
    private List<Object> activityCalories = new ArrayList<Object>();

    @SerializedName("activities-calories-intraday")
    @Expose
    private ActivityStepsIntraDay activityCaloriesIntraDay;

    public List<Object> getActivityCalories() {
        return activityCalories;
    }

    public void setActivityCalories(List<Object> activityCalories) {
        this.activityCalories = activityCalories;
    }

    public ActivityStepsIntraDay getActivityCaloriesIntraDay() {
        return activityCaloriesIntraDay;
    }

    public void setActivityCaloriesIntraDay(ActivityStepsIntraDay activityCaloriesIntraDay) {
        this.activityCaloriesIntraDay = activityCaloriesIntraDay;
    }
}
