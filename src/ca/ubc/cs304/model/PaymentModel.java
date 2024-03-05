package ca.ubc.cs304.model;

import java.util.Date;

public class PaymentModel {
    private String PID;
    private double Amount;
    private Date PaymentDate;
    private String MID;

    public PaymentModel(String PID, double Amount, Date PaymentDate, String MID) {
        this.PID = PID;
        this.Amount = Amount;
        this.PaymentDate = PaymentDate;
        this.MID = MID;
    }

    // Getters
    public String getPID() {
        return PID;
    }

    public double getAmount() {
        return Amount;
    }

    public Date getPaymentDate() {
        return PaymentDate;
    }

    public String getMID() {
        return MID;
    }

    // Setters
    public void setPID(String PID) {
        this.PID = PID;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public void setPaymentDate(Date PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    @Override
    public String toString() {
        return "LockerModel{" +
                "PID='" + PID + '\'' +
                ", Amount=" + Amount +
                ", PaymentDate=" + PaymentDate +
                ", MID='" + MID + '\'' +
                '}';
    }
}
