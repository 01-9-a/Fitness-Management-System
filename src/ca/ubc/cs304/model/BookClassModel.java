package ca.ubc.cs304.model;

import java.util.Date;

public class BookClassModel {
    private String MID;
    private String IID;
    private String ClassName;
    private Date ClassDate;

    public BookClassModel(String MID, String IID, String ClassName, Date ClassDate) {
        this.MID = MID;
        this.IID = IID;
        this.ClassName = ClassName;
        this.ClassDate = ClassDate;
    }

    // Getters
    public String getMID() {
        return MID;
    }

    public String getIID() {
        return IID;
    }

    public String getClassName() {
        return ClassName;
    }

    public Date getClassDate() {
        return ClassDate;
    }

    // Setters
    public void setMID(String MID) {
        this.MID = MID;
    }

    public void setIID(String IID) {
        this.IID = IID;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public void setClassDate(Date ClassDate) {
        this.ClassDate = ClassDate;
    }

    @Override
    public String toString() {
        return "BookClassModel{" +
                "MID='" + MID + '\'' +
                ", IID='" + IID + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassDate=" + ClassDate +
                '}';
    }
}
