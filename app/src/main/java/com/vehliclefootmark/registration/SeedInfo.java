package com.vehliclefootmark.registration;

public class SeedInfo {

    private int seedInfoId;
    private String name;

    public SeedInfo(int seedInfoId, String name) {
        this.seedInfoId = seedInfoId;
        this.name = name;
    }

    public int getSeedInfoId() {
        return seedInfoId;
    }

    public void setSeedInfoId(int seedInfoId) {
        this.seedInfoId = seedInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
