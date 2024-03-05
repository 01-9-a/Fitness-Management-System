package ca.ubc.cs304.model;

public class EquipIDAndNameModel {
    private String EquipID;
    private String EquipName;

    public EquipIDAndNameModel(String EquipID, String EquipName) {
        this.EquipID = EquipID;
        this.EquipName = EquipName;
    }

    // Getters
    public String getEquipID() {
        return EquipID;
    }

    public String getEquipName() {
        return EquipName;
    }

    // Setters
    public void setEquipID(String equipID) {
        EquipID = equipID;
    }

    public void setEquipName(String equipName) {
        EquipName = equipName;
    }

    @Override
    public String toString() {
        return "EquipIDAndNameModel{" +
                "EquipID='" + EquipID + '\'' +
                ", EquipName='" + EquipName + '\'' +
                '}';
    }
}
