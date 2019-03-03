package com.rocketechit.officemanagementapp.JavaClass;

public class EventClass {

    private String eventTitle;
    private String eventDetails;
    private String date;
    private String year;
    private String month;
    private String day;
    private String userID;

    public EventClass() {
    }

    public EventClass(String eventTitle, String eventDetails, String date, String year,
                      String month, String day,String userID) {
        this.eventTitle = eventTitle;
        this.eventDetails = eventDetails;
        this.date = date;
        this.year = year;
        this.month = month;
        this.day = day;
        this.userID=userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
