package com.vehliclefootmark.login;

public class LoginResponseDTO {

    private int userNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String vehicleModel;
    private String vehicleNumber;
    private boolean admin;

    public LoginResponseDTO(int userNumber, String email, String firstName, String lastName, String vehicleModel, String vehicleNumber, boolean admin) {
        this.userNumber = userNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicleModel = vehicleModel;
        this.vehicleNumber = vehicleNumber;
        this.admin = admin;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
