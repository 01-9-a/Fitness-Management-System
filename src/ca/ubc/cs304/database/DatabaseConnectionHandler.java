package ca.ubc.cs304.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

import ca.ubc.cs304.model.*;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	//private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final String SUCCESS_TAG = "[SUCCESS]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}


	//---------------------------Locker-----------------------------

	public String deleteLocker(String LID) {
		String result = "";
		try {
			String query = "DELETE FROM Locker WHERE LID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, LID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				result+=(WARNING_TAG + " Locker " + LID + " does not exist!");
			}else{
				result += (SUCCESS_TAG + " Locker " + LID + " has been deleted!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}


	public String insertLocker(LockerModel lockermodel) {
		String result = "";
		try {
			//check if is the LID already used by another locker
			String checkQuery = "SELECT COUNT(*) FROM Locker WHERE LID = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, lockermodel.getID());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same MID
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				result+=(WARNING_TAG + " The Locker ID :" + lockermodel.getID() + " already been occupied by another member.Please choose another ID!");
			} else {
				String query = "INSERT INTO Locker VALUES (?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, lockermodel.getID());
				ps.setString(2, lockermodel.getSize());
				ps.setString(3, lockermodel.getStatus());
				ps.setDouble(4, lockermodel.getLockerHourlyFee());
				ps.executeUpdate();
				connection.commit();

				ps.close();
				result += (SUCCESS_TAG + " Locker " + lockermodel.getID() + " has been inserted!");
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}

	public LockerModel[] getSingleLockerInfo(String Lsize, String Lstatus, double fee) {
		ArrayList<LockerModel> result = new ArrayList<LockerModel>();
		String query = "";
		int row=-1;
		query = "SELECT * FROM Locker WHERE (Lsize = ? OR LockerHourlyFee = ?) OR LockerStatus = ?";
		PreparedStatement ps;
		try {
			if(fee!=-1) {
			 	query = "SELECT * FROM Locker WHERE (Lsize = ? OR LockerHourlyFee = ?) OR LockerStatus = ?";
				ps = connection.prepareStatement(query);
				ps.setString(1, Lsize);
				ps.setDouble(2, fee);
				ps.setString(3, Lstatus);
				row = ps.executeUpdate();
			}else{
				query = "SELECT * FROM Locker WHERE Lsize = ? OR LockerStatus = ?";
				ps = connection.prepareStatement(query);
				ps.setString(1, Lsize);
				ps.setString(2, Lstatus);
				row = ps.executeUpdate();
			}

			if (row == 0) {
				System.out.println(WARNING_TAG + " Locker does not exist!");
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				LockerModel locker = new LockerModel(
						rs.getString("LID"),
						rs.getString("Lsize"),
						rs.getString("LockerStatus"),
						rs.getDouble("LockerHourlyFee")
				);
				result.add(locker);
			}

			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result.toArray(new LockerModel[0]);
	}

	public LockerModel[] getAllLockerInfo() {
		ArrayList<LockerModel> result = new ArrayList<LockerModel>();

		try {
			String query = "SELECT * FROM Locker";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				LockerModel model = new LockerModel(rs.getString("LID"),
						rs.getString("Lsize"),
						rs.getString("LockerStatus"),
						rs.getDouble("LockerHourlyFee"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new LockerModel[result.size()]);
	}

	public String updateLockerStatus(String LID, String Status) {
		String result = "";
		try {
			String query = "UPDATE Locker SET LockerStatus = ? WHERE LID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, Status);
			ps.setString(2, LID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				result+=((WARNING_TAG + " Locker " + LID + " does not exist!"));
			}
			else{
				result += (SUCCESS_TAG + " Locker " + LID + " has been updated!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}

	public LockerModel[] searchAvailableLocker() {
		ArrayList<LockerModel> availableLockers = new ArrayList<>();
		try {
			String query = "SELECT * FROM Locker WHERE LockerStatus = 'available'";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				LockerModel locker = new LockerModel(
						rs.getString("LID"),
						rs.getString("Lsize"),
						rs.getString("LockerStatus"),
						rs.getDouble("LockerHourlyFee")
				);
				availableLockers.add(locker);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return availableLockers.toArray(new LockerModel[0]);
	}


	//---------------------------Member-----------------------------
	public String deleteMember(String MID) {
		String result = "";
		try {
			String query = "DELETE FROM Member WHERE MID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, MID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				result+=(WARNING_TAG + " Member " + MID + " does not exist!");
			}
			else{
				result += (SUCCESS_TAG + " Member " + MID + " has been deleted!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}

	//insert member
	public String insertMember(MemberModel member) {
		String result = "";
		try {
			//check if is the MID already used by another member
			String checkQuery = "SELECT COUNT(*) FROM Member WHERE MID = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, member.getMID());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same MID
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				result+=(WARNING_TAG + " The member ID :" + member.getMID() + " already been occupied by another member.Please choose another ID!");
			} else {

				String query = "INSERT INTO Member (MID, MemberName, Address, MemberPhone, MembershipSD, MembershipED, LID) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(query);

				ps.setString(1, member.getMID());
				ps.setString(2, member.getMemberName());
				ps.setString(3, member.getAddress());
				ps.setString(4, member.getMemberPhone());
				ps.setDate(5, new Date(member.getMembershipSD().getTime()));
				ps.setDate(6, new Date(member.getMembershipED().getTime()));
				ps.setString(7, member.getLID());

				ps.executeUpdate();
				connection.commit();
				ps.close();
				result+=(SUCCESS_TAG + " Member " + member.getMID() + " has been added!");
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}

	//get single member info
	public MemberModel getSingleMemberInfo(String MID) {
		MemberModel member = null;
		try {
			String query = "SELECT * FROM Member WHERE MID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, MID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Member " + MID + " does not exist!");
			}

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				member = new MemberModel(
						rs.getString("MID"),
						rs.getString("MemberName"),
						rs.getString("Address"),
						rs.getString("MemberPhone"),
						rs.getDate("MembershipSD"),
						rs.getDate("MembershipED"),
						rs.getString("LID")
				);
			}

			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return member;
	}

	//get all members info

	public MemberModel[] getAllMemberInfo() {
		ArrayList<MemberModel> result = new ArrayList<>();

		try {
			String query = "SELECT * FROM Member";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				MemberModel member = new MemberModel(
						rs.getString("MID"),
						rs.getString("MemberName"),
						rs.getString("Address"),
						rs.getString("MemberPhone"),
						rs.getDate("MembershipSD"),
						rs.getDate("MembershipED"),
						rs.getString("LID")
				);
				result.add(member);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new MemberModel[0]);
	}

	//update member info based on MID
	public String updateMemberInfo(MemberModel member) {
		String result = "";
		try {
			String query = "UPDATE Member SET MemberName = ?, Address = ?, MemberPhone = ?, MembershipSD = ?, MembershipED = ?, LID = ? WHERE MID = ?";
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, member.getMemberName());
			ps.setString(2, member.getAddress());
			ps.setString(3, member.getMemberPhone());

			// Convert java.util.Date to java.sql.Date
			ps.setDate(4, new java.sql.Date(member.getMembershipSD().getTime()));
			ps.setDate(5, new java.sql.Date(member.getMembershipED().getTime()));

			ps.setString(6, member.getLID());
			ps.setString(7, member.getMID());

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				result+=(WARNING_TAG + " Member " + member.getMID() + " does not exist!");
			}
			else{
				result+=(SUCCESS_TAG + " Member " + member.getMID() + " has been updated!");
			}
			connection.commit();
			ps.close();


		} catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}

	//---------------------------Payment-----------------------------
	//Insert payment
	public String insertPayment(PaymentModel paymentmodel) {
		String result = "";
		try {
			//check if is the PID already used by another payment
			String checkQuery = "SELECT COUNT(*) FROM Payment WHERE PID = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, paymentmodel.getPID());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same PID
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				result+=(WARNING_TAG + " The Payment ID :" + paymentmodel.getPID() + " already been occupied by another payment.Please choose another ID!");
			} else {
				String query = "INSERT INTO Payment VALUES (?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, paymentmodel.getPID());
				ps.setDouble(2, paymentmodel.getAmount());
				ps.setDate(3, new java.sql.Date(paymentmodel.getPaymentDate().getTime()));
				ps.setString(4, paymentmodel.getMID());
				ps.executeUpdate();
				connection.commit();
				ps.close();
				result+=(SUCCESS_TAG + " Payment " + paymentmodel.getPID() + " has been added!");
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}

	//Delete payment
	public String deletePayment(String PID) {
		String result = "";
		try {
			String query = "DELETE FROM Payment WHERE PID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, PID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				result+=(WARNING_TAG + " Payment " + PID + " does not exist!");
			}
			else{
				result+=(SUCCESS_TAG + " Payment " + PID + " has been deleted!");
			}
			connection.commit();
			ps.close();

		} catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}

	//get all payments for a specific day and return member name (JOIN)
	public PaymentMemberModel[] getAllPaymentsInfo(java.sql.Date date) {
		ArrayList<PaymentMemberModel> payments = new ArrayList<>();
		try {
			String query = "SELECT Payment.PID, Payment.Amount, Payment.PaymentDate, Member.MemberName, Member.MID " +
					"FROM Payment INNER JOIN  Member ON(Member.MID = Payment.MID) " +
					"Where PaymentDate = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setDate(1, date);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PaymentMemberModel payment = new PaymentMemberModel(
						rs.getString("PID"),
						rs.getDouble("Amount"),
						rs.getDate("PaymentDate"),
						rs.getString("MemberName"),
						rs.getString("MID")
				);
				payments.add(payment);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return payments.toArray(new PaymentMemberModel[0]);
	}

	/**
	 * Retrieves an array of PaymentDailyAmountModel objects that represent the total daily payment amounts
	 * exceeding a given threshold.
	 *
	 * @param ThresholdAmount the threshold amount for filtering the payment totals
	 * @return an array of PaymentDailyAmountModel objects representing the total daily payment amounts
	 */
	public PaymentDailyAmountModel[] getPaymentDailyTotalAmount(double ThresholdAmount) {
		ArrayList<PaymentDailyAmountModel> payments = new ArrayList<>();
		try {
			String query = "SELECT PaymentDate, SUM(Amount) AS TotalAmount " +
					"FROM Payment " +
					"GROUP BY  PaymentDate HAVING SUM(Amount) > ?" +
					"ORDER BY TotalAmount ASC";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setDouble(1, ThresholdAmount);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PaymentDailyAmountModel payment = new PaymentDailyAmountModel(
						rs.getDouble("TotalAmount"),
						rs.getDate("PaymentDate")
				);
				payments.add(payment);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return payments.toArray(new PaymentDailyAmountModel[0]);
	}

	//get average of daily total payment amount (Nested aggregation
	public double getAvgDailyAmount() {
		double avg=0;
		try {
			String query = "SELECT AVG(DailyAmount) AS AvgDailyAmount " +
					"FROM (SELECT PaymentDate, SUM(Amount) AS DailyAmount  FROM Payment GROUP BY PaymentDate)  ";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				avg = rs.getDouble("AvgDailyAmount");
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return avg;
	}
	//get single payment info based on member ID
	public PaymentModel getSinglePaymentInfo(String PID) {
		PaymentModel payment = null;
		try {
			String query = "SELECT * FROM Payment WHERE PID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, PID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Payment " + PID + " does not exist!");
			}

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				payment = new PaymentModel(
						rs.getString("PID"),
						rs.getDouble("Amount"),
						rs.getDate("PaymentDate"),
						rs.getString("MID")
				);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return payment;
	}

	//update payment info based on PID

	public void updatePaymentInfo(PaymentModel payment) {
		try {
			String query = "UPDATE Payment SET Amount = ?, PaymentDate = ?, MID = ? WHERE PID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setDouble(1, payment.getAmount());
			ps.setDate(2, new java.sql.Date(payment.getPaymentDate().getTime()));
			ps.setString(3, payment.getMID());
			ps.setString(4, payment.getPID());

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Payment " + payment.getPID() + " does not exist!");
			}

			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	//---------------------------Instructor-----------------------------
	//Insert instructor info
	public void insertInstructor(InstructorModel instructormodel) {
		try {
			//check if is the IID already used by another instructor
			String checkQuery = "SELECT COUNT(*) FROM Instructor WHERE IID = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, instructormodel.getIID());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same IID
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				System.out.println(WARNING_TAG + " The Instructor ID :" + instructormodel.getIID() + " already been occupied by another instructor.Please choose another ID!");
			} else {
				String query = "INSERT INTO Locker VALUES (?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, instructormodel.getIID());
				ps.setString(2, instructormodel.getInstructorName());
				ps.setString(3, instructormodel.getInstructorPhone());
				ps.setString(4, instructormodel.getExpertise());
				ps.setDouble(5, instructormodel.getInstructorHourlyFee());

				ps.executeUpdate();
				connection.commit();
				ps.close();
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//Delete instructor by IID
	public void deleteInstructor(String IID) {
		try {
			String query = "DELETE FROM Payment WHERE PID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, IID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Instructor " + IID + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//list all instructor infomation
	public InstructorModel[] getAllInstructorInfo() {
		ArrayList<InstructorModel> instructors = new ArrayList<>();
		try {
			String query = "SELECT * FROM Instructor";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				InstructorModel instructor = new InstructorModel(
						rs.getString("IID"),
						rs.getString("InstructorName"),
						rs.getString("InstructorPhone#"),
						rs.getString("Expertise"),
						rs.getDouble("InstructorHourlyFee")
				);
				instructors.add(instructor);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return instructors.toArray(new InstructorModel[0]);
	}

	//get single instructor info
	public InstructorModel getSingleInstructorInfo(String IID) {
		InstructorModel instructor = null;
		try {
			String query = "SELECT * FROM Instructor WHERE IID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, IID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Instructor " + IID + " does not exist!");
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				instructor = new InstructorModel(
						rs.getString("IID"),
						rs.getString("InstructorName"),
						rs.getString("InstructorPhone#"),
						rs.getString("Expertise"),
						rs.getDouble("InstructorHourlyFee")
				);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return instructor;
	}

	//update instructor info based on IID

	public void updateInstructorInfo(InstructorModel instructor) {
		try {
			String query = "UPDATE Instructor SET InstructorName = ?, InstructorPhone# = ?, Expertise = ?, InstructorHourlyFee = ? WHERE IID = ?";
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, instructor.getInstructorName());
			ps.setString(2, instructor.getInstructorPhone());
			ps.setString(3, instructor.getExpertise());
			ps.setDouble(4, instructor.getInstructorHourlyFee());
			ps.setString(5, instructor.getIID());

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Instructor " + instructor.getIID() + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}



	//---------------------------Class-----------------------------
	//insert class
	public void insertClass(ClassModel classmodel) {
		try {
			//check if is the primary key of class already used by another class
			String checkQuery = "SELECT COUNT(*) FROM Class	 WHERE IID = ? AND ClassDate = ? AND ClassName = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, classmodel.getIID());
			checkStmt.setDate(2, new Date(classmodel.getClassDate().getTime()));
			checkStmt.setString(3, classmodel.getClassName());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same {IID,ClassDate,ClassName}
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				System.out.println(WARNING_TAG + " The Class with IID " + classmodel.getIID()+" on " + classmodel.getClassDate()+" "+classmodel.getClassName() + " already been occupied by another class.Please choose another key!");
			} else {
				String query = "INSERT INTO Class (IID, Room#, ClassName, ClassDate, MaxCapacity, Duration) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, classmodel.getIID());
				ps.setInt(2, classmodel.getRoomNumber());
				ps.setString(3, classmodel.getClassName());
				ps.setDate(4, new Date(classmodel.getClassDate().getTime()));
				ps.setInt(5, classmodel.getMaxCapacity());
				ps.setInt(6, classmodel.getDuration());
				ps.executeUpdate();
				connection.commit();
				ps.close();
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//Delete Class
	public void deleteClass(String IID, Date ClassDate, String ClassName) {
		try {
			String query = "DELETE FROM Class WHERE IID = ?AND ClassDate = ? AND ClassName = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, IID);
			ps.setDate(2, ClassDate);
			ps.setString(3, ClassName);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Class with IID: " + IID + " and ClassDate: " + ClassDate + " and ClassName: " + ClassName + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//get all class info
	public ClassModel[] getAllClassInfo() {
		ArrayList<ClassModel> classes = new ArrayList<>();
		try {
			String query = "SELECT * FROM Class";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClassModel classModel = new ClassModel(
						rs.getString("IID"),
						rs.getInt("Room#"),
						rs.getString("ClassName"),
						rs.getDate("ClassDate"),
						rs.getInt("MaxCapacity"),
						rs.getInt("Duration")
				);
				classes.add(classModel);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return classes.toArray(new ClassModel[0]);
	}

	//get all class info by date
	public ClassModel[] getAllClassesByDate(java.sql.Date date) {
		ArrayList<ClassModel> classes = new ArrayList<>();
		try {
			String query = "SELECT * FROM Class WHERE ClassDate = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setDate(1, date);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClassModel classModel = new ClassModel(
						rs.getString("IID"),
						rs.getInt("Room#"),
						rs.getString("ClassName"),
						rs.getDate("ClassDate"),
						rs.getInt("MaxCapacity"),
						rs.getInt("Duration")
				);
				classes.add(classModel);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return classes.toArray(new ClassModel[0]);
	}

	//search class by class name

	public ClassModel[] searchClassByClassName(String className) {
		ArrayList<ClassModel> classes = new ArrayList<>();
		try {
			String query = "SELECT * FROM Class WHERE ClassName = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, className);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClassModel classModel = new ClassModel(
						rs.getString("IID"),
						rs.getInt("Room#"),
						rs.getString("ClassName"),
						rs.getDate("ClassDate"),
						rs.getInt("MaxCapacity"),
						rs.getInt("Duration")
				);
				classes.add(classModel);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return classes.toArray(new ClassModel[0]);
	}


	//update class info based on key

	public void updateClassInfo(ClassModel class1) {
		try {
			String query = "UPDATE Class SET Room# = ?, MaxCapacity = ?, Duration = ? WHERE IID = ? AND ClassDate = ? AND ClassName = ?";
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setInt(1, class1.getRoomNumber());
			ps.setInt(2, class1.getMaxCapacity());
			ps.setInt(3, class1.getDuration());

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Class with IID: " + class1.getIID() + " and ClassDate: " + class1.getClassDate() + " and ClassName: " + class1.getClassName() + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	//---------------------------BookClass-----------------------------
	//insert new class booking based on existed member, instructor and class {name, date}
	public String insertNewClassBooking(BookClassModel bookClassModel) {
		String result = "";
		try{
			//check the existence of class
			String checkQuery1 = "SELECT COUNT(*) FROM Class WHERE IID = ? AND ClassDate = ? AND ClassName = ?";
			PreparedStatement checkStmt1 = connection.prepareStatement(checkQuery1);
			checkStmt1.setString(1, bookClassModel.getIID());
			checkStmt1.setDate(2, new Date(bookClassModel.getClassDate().getTime()));
			checkStmt1.setString(3, bookClassModel.getClassName());
			ResultSet checkRs1 = checkStmt1.executeQuery();
			int count1 = -1;
			if (checkRs1.next()) {
				count1 = checkRs1.getInt(1);
			}
			if (count1 == 0) {
				result+= (WARNING_TAG + " Class does not exist!");
			}
			//check the existence of member
			String checkQuery2 = "SELECT COUNT(*) FROM Member WHERE MID = ?";
			PreparedStatement checkStmt2 = connection.prepareStatement(checkQuery2);
			checkStmt2.setString(1, bookClassModel.getMID());
			ResultSet checkRs2 = checkStmt2.executeQuery();
			int count2 = -1;
			if (checkRs2.next()) {
				count2 = checkRs2.getInt(1);
			}
			if (count2 == 0) {
				result+= (WARNING_TAG + " Member does not exist!");
			}
			if(count1 == 1 && count2 == 1) {
				//check if the booking already exists
				String checkQuery3 = "SELECT COUNT(*) FROM BookClass WHERE MID = ? AND IID = ? AND ClassName = ? AND ClassDate = ?";
				PreparedStatement checkStmt3 = connection.prepareStatement(checkQuery3);
				checkStmt3.setString(1, bookClassModel.getMID());
				checkStmt3.setString(2, bookClassModel.getIID());
				checkStmt3.setString(3, bookClassModel.getClassName());
				checkStmt3.setDate(4, new Date(bookClassModel.getClassDate().getTime()));
				ResultSet checkRs3 = checkStmt3.executeQuery();
				int count3 = -1;
				if (checkRs3.next()) {
					count3 = checkRs3.getInt(1);
				}
				if (count3 != 0) {
					result+= (WARNING_TAG + " Booking already exists!");
				}
				else {
					//check avalability of the class
					BookingModel[] bookings = getAvailableSlots();
					for (int i = 0; i < bookings.length; i++) {
						if (bookClassModel.getClassName().equals(bookings[i].getClassName()) && bookClassModel.getClassDate().equals(bookings[i].getClassDate())) {
							if (bookings[i].getNumberOfAvailableSpots() == 0) {
								result+= (WARNING_TAG + " Class is full!");
								return result;
							}
						}
					}
					String query = "INSERT INTO BookClass (MID, IID, ClassName, ClassDate) VALUES (?, ?, ?, ?)";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, bookClassModel.getMID());
					ps.setString(2, bookClassModel.getIID());
					ps.setString(3, bookClassModel.getClassName());
					ps.setDate(4, new Date(bookClassModel.getClassDate().getTime()));
					ps.executeQuery();
					connection.commit();
					ps.close();
					result+=( " Class booking successful!");
				}
				//close all connections
				checkRs1.close();
				checkStmt1.close();
				checkRs2.close();
				checkStmt2.close();
				checkRs3.close();
				checkStmt3.close();
			}

		}catch (SQLException e) {
			result+=(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result;
	}
	//get number of available slots for each class (Aggregation with GroupBy)
	public BookingModel[] getAvailableSlots() {
		ArrayList<BookingModel> bookings = new ArrayList<>();
		try {
			//get number of members that have booked for each class and the capacity for each class
			String query1 = "SELECT COUNT(MID) AS booked," +
					"BookClass.ClassName,BookClass.ClassDate, BookClass.IID, Class.MaxCapacity " +
					"FROM BookClass LEFT JOIN Class " +
					"ON BookClass.ClassName = Class.ClassName AND " +
					"BookClass.ClassDate = Class.ClassDate AND " +
					"BookClass.IID = Class.IID " +
					"GROUP BY BookClass.ClassName, BookClass.ClassDate, BookClass.IID, Class.MaxCapacity";

			PreparedStatement Stmt1 = connection.prepareStatement(query1);
			ResultSet rs = Stmt1.executeQuery();
			while(rs.next()){
				BookingModel booking= new BookingModel(rs.getString("IID"),
						rs.getString("ClassName"),
						rs.getDate("ClassDate"),
						rs.getInt("MaxCapacity")-rs.getInt("booked"));
				bookings.add(booking);
			}
			rs.close();
			Stmt1.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return bookings.toArray(new BookingModel[0]);
	}

	//find members who booked all types of class (By class name) (Division)
	public MemberModel[] findMembersWhoBookedAllTypesOfClass() {
		ArrayList<MemberModel> member = new ArrayList<>();
		try {
			String query = "SELECT Member.MID, Member.MemberName, Member.Address, Member.MemberPhone, Member.MembershipSD, Member.MembershipED, Member.LID " +
					"FROM Member " +
					"WHERE NOT EXISTS (SELECT Class.ClassName FROM Class " +
					"WHERE NOT EXISTS " +
					"(SELECT BookClass.ClassName " +
					"FROM BookClass " +
					"WHERE BookClass.MID = Member.MID AND Class.ClassName = BookClass.ClassName))";

			PreparedStatement ps = connection.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				MemberModel model = new MemberModel(rs.getString("MID"),
						rs.getString("MemberName"),
						rs.getString("Address"),
						rs.getString("MemberPhone"),
						rs.getDate("MembershipSD"),
						rs.getDate("MembershipED"),
						rs.getString("LID"));
				member.add(model);
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return member.toArray(new MemberModel[0]);
	}

	//get all booking information
	public BookClassModel[] getAllBookings() {
		ArrayList<BookClassModel> booking = new ArrayList<>();
		try {
			String query = "SELECT * FROM BookClass";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				BookClassModel model = new BookClassModel(rs.getString("MID"),
						rs.getString("IID"),
						rs.getString("ClassName"),
						rs.getDate("ClassDate"));
				booking.add(model);
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return booking.toArray(new BookClassModel[0]);
	}


	//---------------------------Engineer-----------------------------
	//get all info of engineers
	public EngineerModel[] getAllEngineers() {

		ArrayList<EngineerModel> engineer = new ArrayList<>();
		try {
			String query = "SELECT * FROM Engineer";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				EngineerModel model = new EngineerModel(rs.getString("EngineerName"),
						rs.getString("EngineerPhone"),
						rs.getString("EngineerAddress"));
				engineer.add(model);
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return engineer.toArray(new EngineerModel[0]);
	}

	//---------------------------Equipment-----------------------------

	//insert new equipment
	public void insertEquipment(EquipmentModel equipment) {
		try {
			//check if is the EquipID already used by another equipment
			String checkQuery = "SELECT COUNT(*) FROM Equipment WHERE EquipID = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, equipment.getEquipID());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same EquipID
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				System.out.println(WARNING_TAG + " The Equipment ID :" + equipment.getEquipID() + " already been occupied by another equipment.Please choose another ID!");
			} else {

				String query = "INSERT INTO Equipment (EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(query);

				ps.setString(1, equipment.getEquipID());
				ps.setString(2, equipment.getEquipName());
				ps.setString(3, equipment.getEquipStatus());
				ps.setString(4, equipment.getEngineerName());
				ps.setString(5, equipment.getEngineerPhone());

				ps.executeUpdate();
				connection.commit();
				ps.close();
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//delete equipment
	public void deleteEquipment(String EquipID) {
		try {
			String query = "DELETE FROM Equipment WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, EquipID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + EquipID + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//get all equipment info
	public EquipmentModel[] getAllEquipmentInfo() {
		ArrayList<EquipmentModel> result = new ArrayList<>();

		try {
			String query = "SELECT * FROM Equipment";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				EquipmentModel equipment = new EquipmentModel(
						rs.getString("EquipID"),
						rs.getString("EquipName"),
						rs.getString("EquipStatus"),
						rs.getString("EngineerName"),
						rs.getString("EngineerPhone#")
				);
				result.add(equipment);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new EquipmentModel[0]);
	}

	//get single equipment info
	public EquipmentModel getSingleEquipmentInfo(String EquipID) {
		EquipmentModel equipment = null;
		try {
			String query = "SELECT * FROM Equipment WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, EquipID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + EquipID + " does not exist!");
			}

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				equipment = new EquipmentModel(
						rs.getString("EquipID"),
						rs.getString("EquipName"),
						rs.getString("EquipStatus"),
						rs.getString("EngineerName"),
						rs.getString("EngineerPhone")
				);
			}

			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return equipment;
	}

	//update equipment info based on EquipID
	public void updateEquipmentInfo(EquipmentModel equipment) {
		try {
			String query = "UPDATE Equipment SET EquipName = ?, EquipStatus = ?, EngineerName = ?, EngineerPhone# = ? WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, equipment.getEquipName());
			ps.setString(2, equipment.getEquipStatus());
			ps.setString(3, equipment.getEngineerName());
			ps.setString(4, equipment.getEngineerPhone());
			ps.setString(5, equipment.getEquipID());

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + equipment.getEquipID() + " does not exist!");
			}

			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//---------------------------Cardio-----------------------------

	//insert new cardio equipment
	public void insertCardio(CardioModel cardio) {
		try {
			//check if is the EquipID already used by another cardio
			String checkQuery = "SELECT COUNT(*) FROM Cardio WHERE EquipID = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, cardio.getEquipID());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same EquipID
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				System.out.println(WARNING_TAG + " The Equipment ID :" + cardio.getEquipID() + " already been occupied by another equipment.Please choose another ID!");
			} else {

				String query = "INSERT INTO Cardio (EquipID, SpeedRange) VALUES (?, ?)";
				PreparedStatement ps = connection.prepareStatement(query);

				ps.setString(1, cardio.getEquipID());
				ps.setInt(2, cardio.getSpeedRange());

				ps.executeUpdate();
				connection.commit();
				ps.close();
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//delete cardio equipment
	public void deleteCardio(String EquipID) {
		try {
			String query = "DELETE FROM Cardio WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, EquipID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + EquipID + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//get all cardio equipment's EquipID and EquipName
	public EquipIDAndNameModel[] getAllCardioInfo() {
		ArrayList<EquipIDAndNameModel> result = new ArrayList<>();

		try {
			String query = "SELECT E.EquipID, E.EquipName FROM Equipment E, Cardio C WHERE E.EquipID = C.EquipID";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				EquipIDAndNameModel cardio = new EquipIDAndNameModel(
						rs.getString("EquipID"),
						rs.getString("EquipName")
				);
				result.add(cardio);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new EquipIDAndNameModel[0]);
	}

	//get single cardio equipment info
	public CardioModel getSingleCardioInfo(String EquipID) {
		CardioModel cardio = null;
		try {
			String query = "SELECT * FROM Cardio WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, EquipID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + EquipID + " does not exist!");
			}

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				cardio = new CardioModel(
						rs.getString("EquipID"),
						rs.getInt("SpeedRange")
				);
			}

			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return cardio;
	}

	//update cardio equipment info based on EquipID
	public void updateCardioInfo(CardioModel cardio) {
		try {
			String query = "UPDATE Cardio SET SpeedRange = ? WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setInt(1, cardio.getSpeedRange());
			ps.setString(2, cardio.getEquipID());

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + cardio.getEquipID() + " does not exist!");
			}

			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//---------------------------Strength-----------------------------

	//insert new strength equipment
	public void insertStrength(StrengthModel strength) {
		try {
			//check if is the EquipID already used by another cardio
			String checkQuery = "SELECT COUNT(*) FROM Strength WHERE EquipID = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, strength.getEquipID());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same EquipID
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				System.out.println(WARNING_TAG + " The Equipment ID :" + strength.getEquipID() + " already been occupied by another equipment.Please choose another ID!");
			} else {

				String query = "INSERT INTO Strength (EquipID, WeightRange) VALUES (?, ?)";
				PreparedStatement ps = connection.prepareStatement(query);

				ps.setString(1, strength.getEquipID());
				ps.setInt(2, strength.getWeightRange());

				ps.executeUpdate();
				connection.commit();
				ps.close();
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//delete strength equipment
	public void deleteStrength(String EquipID) {
		try {
			String query = "DELETE FROM Strength WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, EquipID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + EquipID + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//get all strength equipment's EquipID and EquipName
	public EquipIDAndNameModel[] getAllStrengthInfo() {
		ArrayList<EquipIDAndNameModel> result = new ArrayList<>();

		try {
			String query = "SELECT E.EquipID, E.EquipName FROM Equipment E, Strength S WHERE E.EquipID = S.EquipID";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				EquipIDAndNameModel strength = new EquipIDAndNameModel(
						rs.getString("EquipID"),
						rs.getString("EquipName")
				);
				result.add(strength);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new EquipIDAndNameModel[0]);
	}

	//get single strength equipment info
	public StrengthModel getSingleStrengthInfo(String EquipID) {
		StrengthModel strength = null;
		try {
			String query = "SELECT * FROM Strength WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, EquipID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + EquipID + " does not exist!");
			}

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				strength = new StrengthModel(
						rs.getString("EquipID"),
						rs.getInt("WeightRange")
				);
			}

			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return strength;
	}

	//update strength equipment info based on EquipID
	public void updateStrengthInfo(StrengthModel strength) {
		try {
			String query = "UPDATE Strength SET WeightRange = ? WHERE EquipID = ?";
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setInt(1, strength.getWeightRange());
			ps.setString(2, strength.getEquipID());

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Equipment " + strength.getEquipID() + " does not exist!");
			}

			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//---------------------------UseEquipment-----------------------------

	//---------------------------WorkoutPlan-----------------------------
	//insert new plan
	public void insertWorkoutPlan(WorkoutPlanModel wp) {
		try {
			//check if is the EquipID already used by another cardio
			String checkQuery = "SELECT COUNT(*) FROM WorkoutPlan WHERE PID = ?";
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
			checkStmt.setString(1, wp.getPID());
			ResultSet rs = checkStmt.executeQuery();
			//count the number of rows with the same PID
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				System.out.println(WARNING_TAG + " The Workout Plan ID :" + wp.getPID() + " already been occupied by another plan.Please choose another ID!");
			} else {

				String query = "INSERT INTO WorkoutPlan (PID, wDescription, PlanFee, DifficultyLevel, PlanSD, PlanED, IID) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(query);

				ps.setString(1, wp.getPID());
				ps.setString(2, wp.getDescription());
				ps.setDouble(3, wp.getPlanFee());
				ps.setString(4, wp.getDifficultyLevel());
				ps.setDate(5, new java.sql.Date(wp.getPlanSD().getTime()));
				ps.setDate(6, new java.sql.Date(wp.getPlanED().getTime()));
				ps.setString(7, wp.getIID());

				ps.executeUpdate();
				connection.commit();
				ps.close();
			}
			checkStmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//delete plan
	public void deleteWorkoutPlan(String PID) {
		try {
			String query = "DELETE FROM WorkoutPlan WHERE PID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, PID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Workout Plan " + PID + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//get all plan info
	public WorkoutPlanModel[] getAllWorkoutPlanInfo() {
		ArrayList<WorkoutPlanModel> result = new ArrayList<>();

		try {
			String query = "SELECT * FROM WorkoutPlan";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				WorkoutPlanModel wp = new WorkoutPlanModel(
						rs.getString("PID"),
						rs.getString("wDescription"),
						rs.getFloat("PlanFee"),
						rs.getString("DifficultyLevel"),
						rs.getDate("PlanSD"),
						rs.getDate("PlanED"),
						rs.getString("IID")
				);
				result.add(wp);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new WorkoutPlanModel[0]);
	}

	//get single plan info
	public WorkoutPlanModel getSingleWorkoutPlanInfo(String PID) {
		WorkoutPlanModel wp = null;
		try {
			String query = "SELECT * FROM WorkoutPlan WHERE PID = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, PID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Workout Plan " + PID + " does not exist!");
			}

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				wp = new WorkoutPlanModel(
						rs.getString("PID"),
						rs.getString("wDescription"),
						rs.getFloat("PlanFee"),
						rs.getString("DifficultyLevel"),
						rs.getDate("PlanSD"),
						rs.getDate("PlanED"),
						rs.getString("IID")
				);
			}

			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return wp;
	}

	//update plan info
	public void updateWorkoutPlanInfo(WorkoutPlanModel wp) {
		try {
			String query = "UPDATE WorkoutPlan SET wDescription = ?, PlanFee = ?, DifficultyLevel = ?, PlanSD = ?, PlanED = ?, IID = ? WHERE PID = ?";
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, wp.getDescription());
			ps.setFloat(2, wp.getPlanFee());
			ps.setString(3, wp.getDifficultyLevel());
			ps.setDate(4, new java.sql.Date(wp.getPlanSD().getTime()));
			ps.setDate(5, new java.sql.Date(wp.getPlanED().getTime()));
			ps.setString(6, wp.getIID());
			ps.setString(7, wp.getPID());

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " WorkoutPlan " + wp.getPID() + " does not exist!");
			}

			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// Search Workout Plan based on difficulty level
	public WorkoutPlanModel[] searchWorkoutPlan(String difficultyLevel) {
		ArrayList<WorkoutPlanModel> result = new ArrayList<>();

		try {
			String query = "SELECT * FROM WorkoutPlan WHERE DifficultyLevel LIKE '" + difficultyLevel + "\'";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();


			while (rs.next()) {
				WorkoutPlanModel wp = new WorkoutPlanModel(
						rs.getString("PID"),
						rs.getString("wDescription"),
						rs.getFloat("PlanFee"),
						rs.getString("DifficultyLevel"),
						rs.getDate("PlanSD"),
						rs.getDate("PlanED"),
						rs.getString("IID")
				);
				result.add(wp);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new WorkoutPlanModel[0]);
	}

	//---------------------------FollowPlan-----------------------------
	public FollowPlanModel[] getAllFollowPlanInfo() {
		ArrayList<FollowPlanModel> result = new ArrayList<>();

		try {
			String query = "SELECT * FROM FollowPlan";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				FollowPlanModel fp = new FollowPlanModel(
						rs.getString("MID"),
						rs.getString("PID")
				);
				result.add(fp);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new FollowPlanModel[0]);
	}

	//---------------------------RequireEquipment-----------------------------
	// Get all require equipment grouped by plan
	public RequireEquipmentModel[] getAllRequireEquipmentGroupedByPlan() {
		ArrayList<RequireEquipmentModel> result = new ArrayList<>();

		try {
			String query = "SELECT * FROM RequireEquipment ORDER BY PID";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				RequireEquipmentModel re = new RequireEquipmentModel(
						rs.getString("EquipID"),
						rs.getString("PID")
				);
				result.add(re);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new RequireEquipmentModel[0]);
	}



	/**
	 * Logs in to the Oracle database with the given username and password.
	 *
	 * @param username The username to log in with.
	 * @param password The password to log in with.
	 * @return True if the login was successful, false otherwise.
	 */
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}



	private void rollbackConnection() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
}