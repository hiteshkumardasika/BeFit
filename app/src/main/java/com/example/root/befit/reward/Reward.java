package com.example.root.befit.reward;

import java.io.Serializable;

/**
 * Created by jeffrey on 15/04/17.
 */

public class Reward implements Serializable {
    private String name;
    private int progressPoints;
    private int targetPoints;
    private String image;
    private String description;
    private boolean claimed;

    public Reward(String name, int progressPoints, int targetPoints, String image, String description) {
        this.name = name;
        this.progressPoints = progressPoints;
        this.targetPoints = targetPoints;
        this.image = image;
        this.description = description;
        this.claimed =false;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
