package com.example.root.befit.reward;

/**
 * Created by jeffrey on 15/04/17.
 */

public class Reward {
    private String name;
    private int progressPoints;
    private int targetPoints;
    private String image;

    public Reward(String name, int progressPoints, int targetPoints, String image) {
        this.name = name;
        this.progressPoints = progressPoints;
        this.targetPoints = targetPoints;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgressPoints() {
        return progressPoints;
    }

    public void setProgressPoints(int progressPoints) {
        this.progressPoints = progressPoints;
    }

    public int getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(int targetPoints) {
        this.targetPoints = targetPoints;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
