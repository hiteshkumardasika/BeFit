package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/15/17.
 */

public class ActivityStepsIntraDay {
    @SerializedName("dataset")
    @Expose
    private List<DataSetIntraDay> dataSetIntraDays = new ArrayList<DataSetIntraDay>();

    @SerializedName("dataSetInterval")
    @Expose
    private int dataSetInterval;

    @SerializedName("dataSetType")
    @Expose
    private int dataSetType;

    public List<DataSetIntraDay> getDataSetIntraDays() {
        return dataSetIntraDays;
    }

    public void setDataSetIntraDays(List<DataSetIntraDay> dataSetIntraDays) {
        this.dataSetIntraDays = dataSetIntraDays;
    }

    public int getDataSetInterval() {
        return dataSetInterval;
    }

    public void setDataSetInterval(int dataSetInterval) {
        this.dataSetInterval = dataSetInterval;
    }

    public int getDataSetType() {
        return dataSetType;
    }

    public void setDataSetType(int dataSetType) {
        this.dataSetType = dataSetType;
    }
}
