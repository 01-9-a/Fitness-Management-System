package ca.ubc.cs304.model;

import java.util.Date;

public class ClassModel {
    private String IID;
    private int RoomNumber;
    private String ClassName;
    private Date ClassDate;
    private int MaxCapacity;
    private int Duration;

    public ClassModel(String IID, int RoomNumber, String ClassName, Date ClassDate, int MaxCapacity, int Duration) {
        this.IID = IID;
        this.RoomNumber = RoomNumber;
        this.ClassName = ClassName;
        this.ClassDate = ClassDate;
        this.MaxCapacity = MaxCapacity;
        this.Duration = Duration;
    }

    // Getters
    public String getIID() {
        return IID;
    }

    public int getRoomNumber() {
        return RoomNumber;
    }

    public String getClassName() {
        return ClassName;
    }

    public Date getClassDate() {
        return ClassDate;
    }

    public int getMaxCapacity() {
        return MaxCapacity;
    }

    public int getDuration() {
        return Duration;
    }

    // Setters
    public void setIID(String IID) {
        this.IID = IID;
    }

    public void setRoomNumber(int RoomNumber) {
        this.RoomNumber = RoomNumber;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public void setClassDate(Date ClassDate) {
        this.ClassDate = ClassDate;
    }

    public void setMaxCapacity(int MaxCapacity) {
        this.MaxCapacity = MaxCapacity;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "IID='" + IID + '\'' +
                ", RoomNumber=" + RoomNumber +
                ", ClassName='" + ClassName + '\'' +
                ", ClassDate=" + ClassDate +
                ", MaxCapacity=" + MaxCapacity +
                ", Duration=" + Duration +
                '}';
    }
}

