package ca.ubc.cs304.model;

import java.util.Date;

public class PaymentMemberModel {
    private double Amount;
    private Date PaymentDate;

    private String MemberName;

    private String MID;

    private String PID;

    public PaymentMemberModel(String PID, double Amount, Date PaymentDate, String MemberName, String MID) {
        this.Amount = Amount;
        this.PaymentDate = PaymentDate;
        this.MemberName = MemberName;
        this.MID = MID;
        this.PID = PID;
    }

    public double getAmount() {
        return Amount;
    }

    public Date getPaymentDate() {
        return PaymentDate;
    }

    public String getMemberName() {
        return MemberName;
    }

    public String getMID() {
        return MID;
    }

    public String getPID() {
        return PID;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public void setPaymentDate(Date paymentDate) {
        PaymentDate = paymentDate;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    @Override
    public String toString() {
        return "PaymentModelMemberModel{" +
                "PID=" + PID +
                ", Amount=" + Amount +
                ", PaymentDate='" + PaymentDate + '\'' +
                ", MemberName='" + MemberName + '\'' +
                ", MID='" + MID + '\'' +
                '}';
    }


}