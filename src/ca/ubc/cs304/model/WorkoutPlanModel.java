package ca.ubc.cs304.model;

import java.util.Date;

public class WorkoutPlanModel {
    private String PID;
    private String Description;
    private float PlanFee;
    private String DifficultyLevel;
    private Date PlanSD;
    private Date PlanED;
    private String IID;

    // Constructor
    public WorkoutPlanModel(String PID, String Description, float PlanFee, String DifficultyLevel, Date PlanSD, Date PlanED, String IID) {
        this.PID = PID;
        this.Description = Description;
        this.PlanFee = PlanFee;
        this.DifficultyLevel = DifficultyLevel;
        this.PlanSD = PlanSD;
        this.PlanED = PlanED;
        this.IID = IID;
    }

    // Getters
    public String getPID() {
        return PID;
    }

    public String getDescription() {
        return Description;
    }

    public float getPlanFee() {
        return PlanFee;
    }

    public String getDifficultyLevel() {
        return DifficultyLevel;
    }

    public Date getPlanSD() {
        return PlanSD;
    }

    public Date getPlanED() {
        return PlanED;
    }

    public String getIID() {
        return IID;
    }

    // Setters
    public void setPID(String PID) {
        this.PID = PID;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPlanFee(float planFee) {
        PlanFee = planFee;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        DifficultyLevel = difficultyLevel;
    }

    public void setPlanSD(Date planSD) {
        PlanSD = planSD;
    }

    public void setPlanED(Date planED) {
        PlanED = planED;
    }

    public void setIID(String IID) {
        this.IID = IID;
    }

    @Override
    public String toString() {
        return "WorkoutPlanModel{" +
                "PID='" + PID + '\'' +
                ", Description='" + Description + '\'' +
                ", PlanFee=" + PlanFee +
                ", DifficultyLevel='" + DifficultyLevel + '\'' +
                ", PlanSD=" + PlanSD +
                ", PlanED=" + PlanED +
                ", IID='" + IID + '\'' +
                '}';
    }
}
