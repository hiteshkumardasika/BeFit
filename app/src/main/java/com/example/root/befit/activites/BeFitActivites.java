package com.example.root.befit.activites;

import api.models.DailyActivitySummary;
import api.models.Summary;

public class BeFitActivites{

    public float bike, run, walk, cal, sum; //Current value

    public BeFitActivites(float bike, float run, float walk, float cal, float sum){
        this.bike = bike;
        this.run = run;
        this.walk = walk;
        this.cal = cal;
        this.sum = sum;
    }
}