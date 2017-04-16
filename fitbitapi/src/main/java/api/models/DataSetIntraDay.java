package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 4/15/17.
 */

public class DataSetIntraDay {
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
