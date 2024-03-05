package ca.ubc.cs304.model;

public class EquipmentModel {
    private String EquipID;
    private String EquipName;
    private String EquipStatus;
    private String EngineerName;
    private String EngineerPhone;

    // Constructor
    public EquipmentModel(String EquipID, String EquipName, String EquipStatus, String EngineerName, String EngineerPhone) {
        this.EquipID = EquipID;
        this.EquipName = EquipName;
        this.EquipStatus = EquipStatus;
        this.EngineerName = EngineerName;
        this.EngineerPhone = EngineerPhone;
    }

    // Getters
    public String getEquipID() {
        return EquipID;
    }

    public String getEquipName() {
        return EquipName;
    }

    public String getEquipStatus() {
        return EquipStatus;
    }

    public String getEngineerName() {
        return EngineerName;
    }

    public String getEngineerPhone() {
        return EngineerPhone;
    }

    // Setters
    public void setEquipID(String equipID) {
        EquipID = equipID;
    }

    public void setEquipName(String equipName) {
        EquipName = equipName;
    }

    public void setEquipStatus(String equipStatus) {
        EquipStatus = equipStatus;
    }

    public void setEngineerName(String engineerName) {
        EngineerName = engineerName;
    }

    public void setEngineerPhone(String engineerPhone) {
        EngineerPhone = engineerPhone;
    }

    @Override
    public String toString() {
        return "EquipmentModel{" +
                "EquipID='" + EquipID + '\'' +
                ", EquipName='" + EquipName + '\'' +
                ", EquipStatus='" + EquipStatus + '\'' +
                ", EngineerName='" + EngineerName + '\'' +
                ", EngineerPhone='" + EngineerPhone + '\'' +
                '}';
    }
}
