package com.pccontroll.backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum Gender {
    Male,
    Female
}

public class User {
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String parentPhone;
    private String dob;
    private String age;
    private String datetoday;
    private String unit;

    private Gender gender;

    private String contactMessage;

    public User(String contactName, String contactEmail, String contactPhone, String parentPhone, String dob, String age, String unit, Gender gender) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.parentPhone = parentPhone;
        this.dob = dob;
        this.age = age;
        this.datetoday = new Date().toString();
        this.unit = unit;
        this.gender = gender;
    }

    public User (String contactEmail) {
        this.contactEmail = contactEmail;
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
        header.add("gender");
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
        stringUser.add(this.gender.toString());

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

    public String getContactMessage() {
        return contactMessage;
    }

    public void setContactMessage(String contactMessage) {
        this.contactMessage = contactMessage;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return this.getContactName() + " from " + this.getUnit() + " unit" + "\ncontact number: " + this.getContactPhone() + "\nparents contact number: " + this.getParentPhone() + "\nemail: " + this.getContactEmail() + "\ngender: " + this.getGender().toString() +"\nage: " + this.getAge() + "\ntheir special message: " + this.getContactMessage();
    }
}
