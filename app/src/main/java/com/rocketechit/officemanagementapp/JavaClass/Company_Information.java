package com.rocketechit.officemanagementapp.JavaClass;

public class Company_Information {
    String company_name;
    String email;
    String password;
    String phoneNumber;
    String entryTime;
    String exitTime;
    String userID;

    public Company_Information(String company_name, String email, String password,
                               String phoneNumber, String entryTime, String exitTime,String userID) {
        this.company_name = company_name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.userID=userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
