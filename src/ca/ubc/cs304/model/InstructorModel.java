package ca.ubc.cs304.model;

public class InstructorModel {
    private String IID;
    private String InstructorName;
    private String InstructorPhone;
    private String Expertise;
    private double InstructorHourlyFee;

    public InstructorModel(String IID, String InstructorName, String InstructorPhone, String Expertise, double InstructorHourlyFee) {
        this.IID = IID;
        this.InstructorName = InstructorName;
        this.InstructorPhone = InstructorPhone;
        this.Expertise = Expertise;
        this.InstructorHourlyFee = InstructorHourlyFee;
    }

    // Getters
    public String getIID() {
        return IID;
    }

    public String getInstructorName() {
        return InstructorName;
    }

    public String getInstructorPhone() {
        return InstructorPhone;
    }

    public String getExpertise() {
        return Expertise;
    }

    public double getInstructorHourlyFee() {
        return InstructorHourlyFee;
    }

    // Setters
    public void setIID(String IID) {
        this.IID = IID;
    }

    public void setInstructorName(String instructorName) {
        this.InstructorName = instructorName;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.InstructorPhone = instructorPhone;
    }

    public void setExpertise(String expertise) {
        this.Expertise = expertise;
    }

    public void setInstructorHourlyFee(double instructorHourlyFee) {
        this.InstructorHourlyFee = instructorHourlyFee;
    }

    @Override
    public String toString() {
        return "InstructorModel{" +
                "IID='" + IID + '\'' +
                ", InstructorName='" + InstructorName + '\'' +
                ", InstructorPhone='" + InstructorPhone + '\'' +
                ", Expertise='" + Expertise + '\'' +
                ", InstructorHourlyFee=" + InstructorHourlyFee +
                '}';
    }
}
