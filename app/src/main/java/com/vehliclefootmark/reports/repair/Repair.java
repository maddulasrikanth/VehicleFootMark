package com.vehliclefootmark.reports.repair;

public class Repair {

    private int repairId;
    private int userId;
    private long repairFoundDate;
    private long dateOfRepair;
    private String problemSummary;
    private String repairSummary;
    private String materialCost;
    private String labourCost;

    public Repair(int repairId, int userId, long repairFoundDate, long dateOfRepair, String problemSummary, String repairSummary, String materialCost, String labourCost) {
        this.repairId = repairId;
        this.userId = userId;
        this.repairFoundDate = repairFoundDate;
        this.dateOfRepair = dateOfRepair;
        this.problemSummary = problemSummary;
        this.repairSummary = repairSummary;
        this.materialCost = materialCost;
        this.labourCost = labourCost;
    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getRepairFoundDate() {
        return repairFoundDate;
    }

    public void setRepairFoundDate(long repairFoundDate) {
        this.repairFoundDate = repairFoundDate;
    }

    public long getDateOfRepair() {
        return dateOfRepair;
    }

    public void setDateOfRepair(long dateOfRepair) {
        this.dateOfRepair = dateOfRepair;
    }

    public String getProblemSummary() {
        return problemSummary;
    }

    public void setProblemSummary(String problemSummary) {
        this.problemSummary = problemSummary;
    }

    public String getRepairSummary() {
        return repairSummary;
    }

    public void setRepairSummary(String repairSummary) {
        this.repairSummary = repairSummary;
    }

    public String getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(String materialCost) {
        this.materialCost = materialCost;
    }

    public String getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(String labourCost) {
        this.labourCost = labourCost;
    }

}

