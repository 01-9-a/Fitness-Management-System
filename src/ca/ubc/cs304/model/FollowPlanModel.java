package ca.ubc.cs304.model;

public class FollowPlanModel {
    private String MID;
    private String PID;

    // Constructor
    public FollowPlanModel(String MID, String PID) {
        this.MID = MID;
        this.PID = PID;
    }

    // Getters
    public String getMID() {
        return MID;
    }

    public String getPID() {
        return PID;
    }

    // Setters
    public void setMID(String MID) {
        this.MID = MID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    @Override
    public String toString() {
        return "FollowPlanModel{" +
                "MID='" + MID + '\'' +
                ", PID='" + PID + '\'' +
                '}';
    }
}
