package com.cumonywa.mtaxi.dashboard.model;

import android.widget.BaseAdapter;

public class ActiveJob  {
    String DId,DName,DPhone,DCarNo,CName,CPhone;

    public ActiveJob(){

    }

    public ActiveJob(String DId, String DName, String DPhone, String DCarNo, String CName, String CPhone) {
        this.DId = DId;
        this.DName = DName;
        this.DPhone = DPhone;
        this.DCarNo = DCarNo;
        this.CName = CName;
        this.CPhone = CPhone;
    }

    public String getDName() {
        return DName;
    }

    public void setDName(String DName) {
        this.DName = DName;
    }

    public String getDPhone() {
        return DPhone;
    }

    public void setDPhone(String DPhone) {
        this.DPhone = DPhone;
    }

    public String getDCarNo() {
        return DCarNo;
    }

    public void setDCarNo(String DCarNo) {
        this.DCarNo = DCarNo;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getCPhone() {
        return CPhone;
    }

    public void setCPhone(String CPhone) {
        this.CPhone = CPhone;
    }

    public String getDId() {
        return DId;
    }

    public void setDId(String DId) {
        this.DId = DId;
    }
}
