package com.example.root.befit.activites;

public class BeFitActivites{

    public float bike, run, walk, cal, sum; //Current value
    protected final String TAG = getClass().getSimpleName();

    public BeFitActivites(float bike, float run, float walk, float cal){
        this.bike = bike;
        this.run = run;
        this.walk = walk;
        this.cal = cal;
        this.sum = bike + run + walk + cal;
    }

    public void updateSum(){
        sum = bike + run + walk + cal;
    }

}