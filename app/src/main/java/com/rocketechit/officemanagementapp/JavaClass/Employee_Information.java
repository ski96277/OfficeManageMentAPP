package com.rocketechit.officemanagementapp.JavaClass;

import java.io.Serializable;

public class Employee_Information implements Serializable {
    String userID_Employee;
    String userID_company;
    String email_Employee;
    String password_Employee;
    String name_Employee;
    String join_Date;
    String imageLink;
    String designation;
    String phone;


    public Employee_Information() {
    }
/*

    public Employee_Information(String userID_Employee, String email_Employee, String password_Employee,
                                String name_Employee, String join_Date, String imageLink,String ) {
        this.userID_Employee = userID_Employee;
        this.email_Employee = email_Employee;
        this.password_Employee = password_Employee;
        this.name_Employee = name_Employee;
        this.join_Date = join_Date;
        this.imageLink=imageLink;
    }
*/

    public Employee_Information(String userID_Employee, String userID_company, String email_Employee, String password_Employee,
                                String name_Employee, String join_Date, String imageLink,String designation,String phone) {
        this.userID_Employee = userID_Employee;
        this.userID_company = userID_company;
        this.email_Employee = email_Employee;
        this.password_Employee = password_Employee;
        this.name_Employee = name_Employee;
        this.join_Date = join_Date;
        this.imageLink = imageLink;
        this.designation=designation;
        this.phone=phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUserID_company() {
        return userID_company;
    }

    public void setUserID_company(String userID_company) {
        this.userID_company = userID_company;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getName_Employee() {
        return name_Employee;
    }

    public void setName_Employee(String name_Employee) {
        this.name_Employee = name_Employee;
    }

    public String getJoin_Date() {
        return join_Date;
    }

    public void setJoin_Date(String join_Date) {
        this.join_Date = join_Date;
    }

    public String getUserID_Employee() {
        return userID_Employee;
    }

    public void setUserID_Employee(String userID_Employee) {
        this.userID_Employee = userID_Employee;
    }

    public String getEmail_Employee() {
        return email_Employee;
    }

    public void setEmail_Employee(String email_Employee) {
        this.email_Employee = email_Employee;
    }

    public String getPassword_Employee() {
        return password_Employee;
    }

    public void setPassword_Employee(String password_Employee) {
        this.password_Employee = password_Employee;
    }
}
