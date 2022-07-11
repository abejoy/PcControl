package com.pccontroll.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String parentPhone;
    private String dob;
    private String age;
    private String datetoday;
    private String unit;

    public User(String contactName, String contactEmail, String contactPhone, String parentPhone, String dob, String unit) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.parentPhone = parentPhone;
        this.dob = dob;
        this.age = this.calculateAge(dob);
        this.datetoday = new Date().toString();
        this.unit = unit;
    }

    public User (String contactEmail) {
        this.contactEmail = contactEmail;
    }

    private String calculateAge(String dob) {
        return "24";
    }

    private static List<String> getHeader() {
        List<String> header = new ArrayList<>();
        header.add("name");
        header.add("email");
        header.add("phone");
        header.add("parentPhone");
        header.add("dob");
        header.add("age");
        header.add("datetoday");
        header.add("unit");
        return header;
    }

    public List<String> getArrayUser() {
        if (contactEmail =="header"){
            return getHeader();
        }
        List<String> stringUser = new ArrayList<>();
        stringUser.add(this.contactName);
        stringUser.add(this.contactEmail);
        stringUser.add(this.contactPhone);
        stringUser.add(this.parentPhone);
        stringUser.add(this.dob);
        stringUser.add(this.age);
        stringUser.add(this.datetoday);
        stringUser.add(this.unit);

        return stringUser;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDatetoday() {
        return datetoday;
    }

    public void setDatetoday(String datetoday) {
        this.datetoday = datetoday;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
