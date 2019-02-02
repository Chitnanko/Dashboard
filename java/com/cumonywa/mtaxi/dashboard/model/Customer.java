package com.cumonywa.mtaxi.dashboard.model;

public class Customer {
    String name;
    String gmail;
    String phno;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public Customer(String name, String gmail, String phno) {

        this.name = name;
        this.gmail = gmail;
        this.phno = phno;
    }
}
