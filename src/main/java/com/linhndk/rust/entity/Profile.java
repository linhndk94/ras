package com.linhndk.rust.entity;

import com.linhndk.rust.types.FireMode;

import java.util.List;

public class Profile {

    private String profileName;
    private FireMode fireMode;
    private int rpm;
    private int rmin;
    private int rmax;
    private int steps;
    private double sensitivityMultiplier;
    private List<Coordinate> pattern;
    private long timeBetweenShot;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public FireMode getFireMode() {
        return fireMode;
    }

    public void setFireMode(FireMode fireMode) {
        this.fireMode = fireMode;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
        this.timeBetweenShot = (long) Math.ceil(60 * 1000 / rpm);
    }

    public double getSensitivityMultiplier() {
        return sensitivityMultiplier;
    }

    public void setSensitivityMultiplier(double sensitivityMultiplier) {
        this.sensitivityMultiplier = sensitivityMultiplier;
    }

    public List<Coordinate> getPattern() {
        return pattern;
    }

    public void setPattern(List<Coordinate> pattern) {
        this.pattern = pattern;
    }

    public int getRmin() {
        return rmin;
    }

    public void setRmin(int rmin) {
        this.rmin = rmin;
    }

    public int getRmax() {
        return rmax;
    }

    public void setRmax(int rmax) {
        this.rmax = rmax;
    }

    public long getTimeBetweenShot() {
        return timeBetweenShot;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return this.profileName;
    }
}
