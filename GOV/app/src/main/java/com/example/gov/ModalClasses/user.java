package com.example.gov.ModalClasses;

import java.io.Serializable;

public class user implements Serializable {

    String name;
    String dob;
    String age;
    String phone_number;
    String email;
    String address;
    int type;

    //customer ==0
    //service Provider == 1

    public user(String name, String dob, String age, String phone_number, String email, String address, int type) {
        this.name = name;
        this.dob = dob;
        this.age = age;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.type=type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
