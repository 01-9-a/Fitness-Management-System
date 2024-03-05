package ca.ubc.cs304.model;

public class UseEquipmentModel {
    private String MID;
    private String EquipID;

    // Constructor
    public UseEquipmentModel(String MID, String EquipID) {
        this.MID = MID;
        this.EquipID = EquipID;
    }

    // Getters
    public String getMID() {
        return MID;
    }

    public String getEquipID() {
        return EquipID;
    }

    // Setters
    public void setMID(String MID) {
        this.MID = MID;
    }

    public void setEquipID(String equipID) {
        EquipID = equipID;
    }

    @Override
    public String toString() {
        return "UseEquipmentModel{" +
                "MID='" + MID + '\'' +
                ", EquipID='" + EquipID + '\'' +
                '}';
    }
}
