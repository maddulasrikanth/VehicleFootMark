package com.vehliclefootmark.reports.service;


public class ServiceDTO {

    private int serviceId;
    private String type;
    private int labourcost;
    private int partscost;
    private int tax;
    private long completedDate;
    private long nextDue;
    private String servicedBy;
    private String comments;
    private int userId;

    public ServiceDTO(int serviceId, String type, int labourcost, int partscost, int tax, long completedDate, long nextDue, String servicedBy, String comments, int userId) {
        this.serviceId = serviceId;
        this.type = type;
        this.labourcost = labourcost;
        this.partscost = partscost;
        this.tax = tax;
        this.completedDate = completedDate;
        this.nextDue = nextDue;
        this.servicedBy = servicedBy;
        this.comments = comments;
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLabourcost() {
        return labourcost;
    }

    public void setLabourcost(int labourcost) {
        this.labourcost = labourcost;
    }

    public int getPartscost() {
        return partscost;
    }

    public void setPartscost(int partscost) {
        this.partscost = partscost;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public long getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(long completedDate) {
        this.completedDate = completedDate;
    }

    public long getNextDue() {
        return nextDue;
    }

    public void setNextDue(long nextDue) {
        this.nextDue = nextDue;
    }

    public String getServicedBy() {
        return servicedBy;
    }

    public void setServicedBy(String servicedBy) {
        this.servicedBy = servicedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}

