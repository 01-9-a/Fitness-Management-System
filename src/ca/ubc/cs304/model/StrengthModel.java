package ca.ubc.cs304.model;

public class StrengthModel {
    private String EquipID;
    private int WeightRange;

    // Constructor
    public StrengthModel(String EquipID, int WeightRange) {
        this.EquipID = EquipID;
        this.WeightRange = WeightRange;
    }

    // Getters
    public String getEquipID() {
        return EquipID;
    }

    public int getWeightRange() {
        return WeightRange;
    }

    // Setters
    public void setEquipID(String equipID) {
        EquipID = equipID;
    }

    public void setWeightRange(int weightRange) {
        WeightRange = weightRange;
    }

    @Override
    public String toString() {
        return "StrengthModel{" +
                "EquipID='" + EquipID + '\'' +
                ", WeightRange=" + WeightRange +
                '}';
    }
}
