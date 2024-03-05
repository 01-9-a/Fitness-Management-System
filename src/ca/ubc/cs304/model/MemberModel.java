package ca.ubc.cs304.model;

import java.util.Date;

public class MemberModel {
    private String MID;
    private String MemberName;
    private String Address;
    private String MemberPhone;
    private Date MembershipSD;
    private Date MembershipED;
    private String LID;

    public MemberModel(String MID, String MemberName, String Address, String MemberPhone, Date MembershipSD, Date MembershipED, String LID) {
        this.MID = MID;
        this.MemberName = MemberName;
        this.Address = Address;
        this.MemberPhone = MemberPhone;
        this.MembershipSD = MembershipSD;
        this.MembershipED = MembershipED;
        this.LID = LID;
    }

    // Getters
    public String getMID() {
        return MID;
    }

    public String getMemberName() {
        return MemberName;
    }

    public String getAddress() {
        return Address;
    }

    public String getMemberPhone() {
        return MemberPhone;
    }

    public Date getMembershipSD() {
        return MembershipSD;
    }

    public Date getMembershipED() {
        return MembershipED;
    }

    public String getLID() {
        return LID;
    }

    // Setters
    public void setMID(String MID) {
        this.MID = MID;
    }

    public void setMemberName(String memberName) {
        this.MemberName = memberName;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setMemberPhone(String memberPhone) {
        this.MemberPhone = memberPhone;
    }

    public void setMembershipSD(Date membershipSD) {
        this.MembershipSD = membershipSD;
    }

    public void setMembershipED(Date membershipED) {
        this.MembershipED = membershipED;
    }

    public void setLID(String LID) {
        this.LID = LID;
    }

    @Override
    public String toString() {
        return "MemberModel{" +
                "MID='" + MID + '\'' +
                ", MemberName='" + MemberName + '\'' +
                ", Address='" + Address + '\'' +
                ", MemberPhone='" + MemberPhone + '\'' +
                ", MembershipSD=" + MembershipSD +
                ", MembershipED=" + MembershipED +
                ", LID='" + LID + '\'' +
                '}';
    }
}
