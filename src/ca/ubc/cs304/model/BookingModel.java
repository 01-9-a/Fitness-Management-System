package ca.ubc.cs304.model;
import java.util.Date;
public class BookingModel {
    private String IID;
    private String className;
    private Date classDate;
    private int numberOfAvailableSpots;
    public BookingModel( String IID, String className, Date classDate, int numberOfAvailableSpots) {
        this.IID = IID;
        this.className = className;
        this.classDate = classDate;
        this.numberOfAvailableSpots = numberOfAvailableSpots;
    }



    public String getIID() {
        return IID;
    }

    public void setIID(String IID) {
        this.IID = IID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }

    public int getNumberOfAvailableSpots() {
        return numberOfAvailableSpots;
    }

    public void setNumberOfAvailableSpots(int numberOfAvailableSpots) {
        this.numberOfAvailableSpots = numberOfAvailableSpots;
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "IID='" + IID + '\'' +
                ", className='" + className + '\'' +
                ", classDate=" + classDate +
                ", numberOfAvailableSpots=" + numberOfAvailableSpots +
                '}';
    }



}