package ca.ubc.cs304.model;

public class EngineerModel {
    private String EngineerName;
    private String EngineerPhone;
    private String EngineerAddress;

    public EngineerModel(String EngineerName, String EngineerPhone, String EngineerAddress) {
        this.EngineerName = EngineerName;
        this.EngineerPhone = EngineerPhone;
        this.EngineerAddress = EngineerAddress;
    }

    // Getters
    public String getEngineerName() {
        return EngineerName;
    }

    public String getEngineerPhone() {
        return EngineerPhone;
    }

    public String getEngineerAddress() {
        return EngineerAddress;
    }

    // Setters
    public void setEngineerName(String EngineerName) {
        this.EngineerName = EngineerName;
    }

    public void setEngineerPhone(String EngineerPhone) {
        this.EngineerPhone = EngineerPhone;
    }

    public void setEngineerAddress(String EngineerAddress) {
        this.EngineerAddress = EngineerAddress;
    }

    @Override
    public String toString() {
        return "EngineerModel{" +
                "EngineerName='" + EngineerName + '\'' +
                ", EngineerPhone='" + EngineerPhone + '\'' +
                ", EngineerAddress='" + EngineerAddress + '\'' +
                '}';
    }
}
