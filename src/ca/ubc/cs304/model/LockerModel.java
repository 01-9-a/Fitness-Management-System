package ca.ubc.cs304.model;

public class LockerModel {
	private String LID;
	private String Lsize;
	private String LockerStatus;
	private double LockerHourlyFee;

	// constructor
	public LockerModel(String LID, String Lsize, String LockerStatus, double LockerHourlyFee) {
		this.LID = LID;
		this.Lsize = Lsize;
		this.LockerStatus = LockerStatus;
		this.LockerHourlyFee = LockerHourlyFee;
	}

	// Getters
	public String getID() {
		return LID;
	}

	public String getSize() {
		return Lsize;
	}

	public String getStatus() {
		return LockerStatus;
	}

	public double getLockerHourlyFee() {
		return LockerHourlyFee;
	}

	// Setters
	public void setLid(String LID) {
		this.LID = LID;
	}

	public void setSize(String Lsize) {
		this.Lsize = Lsize;
	}

	public void setLockerStatus(String LockerStatus) {
		this.LockerStatus = LockerStatus;
	}

	public void setLockerHourlyFee(double lockerHourlyFee) {
		this.LockerHourlyFee = lockerHourlyFee;
	}

	// Override the toString() method
	@Override
	public String toString() {
		return "LockerModel{" +
				"LID='" + LID + '\'' +
				", Lsize='" + Lsize + '\'' +
				", LockerStatus='" + LockerStatus + '\'' +
				", LockerHourlyFee=" + LockerHourlyFee +
				'}';
	}
}

