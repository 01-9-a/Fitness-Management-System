package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.*;

import java.util.Date;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the
 * controller class.
 *
 * TerminalTransactions calls the methods that we have listed below but
 * Gym is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
    //public void databaseSetup();

    //Locker
    public void updateLockerStatus(String LID, String lockerStatus);
    public LockerModel[] getSingleLockerInfo(String Lsize, String Lstatus, double fee);
    LockerModel[] searchAvailableLocker();
    LockerModel[] getAllLockerInfo();

    //Member
    public String deleteMember(String MID);
    public String insertMember(MemberModel member);
    public MemberModel getSingleMemberInfo(String MID);
    public MemberModel[] getAllMemberInfo();
    public String updateMemberInfo(MemberModel member);

    //Equipment
    public EquipIDAndNameModel[] getAllCardioInfo();
    public EquipIDAndNameModel[] getAllStrengthInfo();

    //Workout Plan
    public WorkoutPlanModel[] getAllWorkoutPlanInfo();
    public WorkoutPlanModel[] searchWorkoutPlan(String difficultyLevel);

    //Payment
    public double getAvgDailyAmount();
    public PaymentDailyAmountModel[] getPaymentDailyTotalAmount(double ThresholdAmount);
    public PaymentMemberModel[] getAllPaymentsInfo(java.sql.Date date);
    //Service
    public FollowPlanModel[] getAllFollowPlanInfo();
    public RequireEquipmentModel[] getAllRequireEquipmentGroupedByPlan();
    public String insertNewClassBooking(BookClassModel bookClassModel);
    public BookClassModel[] getAllBookings();
    public MemberModel[] findMembersWhoBookedAllTypesOfClass();
    public BookingModel[] getAvailableSlots();
    //public void terminalTransactionsFinished();
}
