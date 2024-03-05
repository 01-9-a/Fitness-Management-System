package ca.ubc.cs304.model;

public class RequireEquipmentModel {
    private String EquipID;
    private String PID;

    // Constructor
    public RequireEquipmentModel(String EquipID, String PID) {
        this.EquipID = EquipID;
        this.PID = PID;
    }

    // Getters
    public String getEquipID() {
        return EquipID;
    }

    public String getPID() {
        return PID;
    }

    // Setters
    public void setEquipID(String equipID) {
        this.EquipID = equipID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    @Override
    public String toString() {
        return "RequireEquipmentModel{" +
                "EquipID='" + EquipID + '\'' +
                ", PID='" + PID + '\'' +
                '}';
    }
}
