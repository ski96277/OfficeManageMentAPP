package com.rocketechit.officemanagementapp.JavaClass;

public class Entry_Exit_Time {

    String entryTime;
    String exitTime;
    String date;

    public Entry_Exit_Time() {
    }

    public Entry_Exit_Time(String exitTime) {
        this.exitTime = exitTime;
    }

    public Entry_Exit_Time(String entryTime, String exitTime, String date) {
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }
}
