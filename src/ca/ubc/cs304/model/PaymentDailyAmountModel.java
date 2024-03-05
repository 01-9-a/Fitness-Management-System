package ca.ubc.cs304.model;

import java.util.Date;

public class PaymentDailyAmountModel {
    private double TotalAmount;
    private Date PaymentDate;

    public PaymentDailyAmountModel(double TotalAmount, Date PaymentDate) {
        this.TotalAmount = TotalAmount;
        this.PaymentDate = PaymentDate;
    }

    //getters
    public double getTotalAmount() {
        return TotalAmount;
    }

    public Date getPaymentDate() {
        return PaymentDate;
    }

    //setters

    public void setTotalAmount(double TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public void setPaymentDate(Date PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    @Override
    public String toString() {
        return "PaymentDailyAmountModel{" +
                "TotalAmount=" + TotalAmount +
                ", PaymentDate=" + PaymentDate +
                '}';
    }
}
