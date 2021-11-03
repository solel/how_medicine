package com.example.how_medicine;

import java.io.Serializable;

public class Time implements Serializable {
    int hour;
    int minute;

    int code;
    String name, day, am_pm;

    public Time(int hour, int minute, int code, String name, String day, String am_pm){
        this.hour = hour;
        this.minute = minute;
        this.code = code;
        this.name = name;
        this.day = day;
        this.am_pm = am_pm;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAm_pm() {
        return am_pm;
    }

    public void setAm_pm(String am_pm) {
        this.am_pm = am_pm;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer("Time{");
        sb.append("hour=").append(hour);
        sb.append(", minute=").append(minute);
        sb.append('}');
        return sb.toString();
    }
}
