package com.example.androidgrouptask;

public class routeTimes {

    String routeName;
    String  Time;

    // main and default constructor

    public routeTimes(String routeName, String Time) {
        this.routeName = routeName;
        this.Time = Time;
    }

    // getters and setters
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
}
