package com.vehliclefootmark.reports.fuel;

public class Fuel {

    private int fuelId;
    private long date;
    private String fuelType;
    private String place;
    private String totalFuel;
    private String amount;
    private int userId;

    public Fuel(int fuelId, long date, String fuelType, String place, String totalFuel, String amount, int userId) {
        this.fuelId = fuelId;
        this.date = date;
        this.fuelType = fuelType;
        this.place = place;
        this.totalFuel = totalFuel;
        this.amount = amount;
        this.userId = userId;
    }

    public int getFuelId() {
        return fuelId;
    }

    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTotalFuel() {
        return totalFuel;
    }

    public void setTotalFuel(String totalFuel) {
        this.totalFuel = totalFuel;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
