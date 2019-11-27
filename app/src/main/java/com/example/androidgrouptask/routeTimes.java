package com.example.androidgrouptask;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class routeTimes {

    String route;
    String time;

    // main and default constructor

    public routeTimes() {

    }

    public routeTimes(String route, String time) {
        this.route = route;
        this.time = time;
    }

    // getters and setters
    public String getRoute() {

        return route;
    }

    public void setRouteName(String routeName) {
        this.route = routeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String Time) {
        this.time = Time;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("route", route);
        result.put("time", time);

        return result;
    }
}
