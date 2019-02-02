package com.cumonywa.mtaxi.dashboard.model;

public class DriverAvailable {

    Driver driver;

    public DriverAvailable(){

    }

    public DriverAvailable(Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
