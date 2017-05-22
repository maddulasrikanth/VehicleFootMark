package com.vehliclefootmark.login;

public class UserDetails {

    private int userNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String vehicleModel;
    private String vehicleNumber;

    public UserDetails(int userNumber, String email, String firstName, String lastName, String vehicleModel, String vehicleNumber) {
        this.userNumber = userNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicleModel = vehicleModel;
        this.vehicleNumber = vehicleNumber;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

}
