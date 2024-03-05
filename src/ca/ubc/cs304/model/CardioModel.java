package ca.ubc.cs304.model;

public class CardioModel {
    private String EquipID;
    private int SpeedRange;

    // Constructor
    public CardioModel(String EquipID, int SpeedRange) {
        this.EquipID = EquipID;
        this.SpeedRange = SpeedRange;
    }

    // Getters
    public String getEquipID() {
        return EquipID;
    }

    public int getSpeedRange() {
        return SpeedRange;
    }

    // Setters
    public void setEquipID(String equipID) {
        EquipID = equipID;
    }

    public void setSpeedRange(int speedRange) {
        SpeedRange = speedRange;
    }

    @Override
    public String toString() {
        return "CardioModel{" +
                "EquipID='" + EquipID + '\'' +
                ", SpeedRange=" + SpeedRange +
                '}';
    }
}
