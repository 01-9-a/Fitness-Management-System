package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.MainWindow;

import java.sql.Date;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class Gym implements LoginWindowDelegate, TerminalTransactionsDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public Gym() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            MainWindow transaction = new MainWindow(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }


    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Delete branch with given branch ID.
     */
    /*public void deleteBranch(int branchId) {
        dbHandler.deleteBranch(branchId);
    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Update the branch name for a specific ID
     */

    /*public void updateBranch(int branchId, String name) {
        dbHandler.updateBranch(branchId, name);
    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Displays information about varies bank branches.
     */
    /*public void showBranch() {
        BranchModel[] models = dbHandler.getBranchInfo();

        for (int i = 0; i < models.length; i++) {
            BranchModel model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getId());
            System.out.printf("%-20.20s", model.getName());
            if (model.getAddress() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getAddress());
            }
            System.out.printf("%-15.15s", model.getCity());
            if (model.getPhoneNumber() == 0) {
                System.out.printf("%-15.15s", " ");
            } else {
                System.out.printf("%-15.15s", model.getPhoneNumber());
            }

            System.out.println();
        }
    }

    /**
     * TerminalTransactionsDelegate Implementation
     *
     * The TerminalTransaction instance tells us that it is done with what it's
     * doing so we are cleaning up the connection since it's no longer needed.
     */
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    /**
     * TerminalTransactionsDelegate Implementation
     *
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */
    /*public void databaseSetup() {
        dbHandler.databaseSetup();;

    }

    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        Gym gym = new Gym();
        gym.start();
    }

    @Override
    public void updateLockerStatus(String LID, String lockerStatus) {
        dbHandler.updateLockerStatus(LID, lockerStatus);
    }

    @Override
    public LockerModel[] getSingleLockerInfo(String Lsize, String Lstatus, double fee) {
        return dbHandler.getSingleLockerInfo(Lsize, Lstatus, fee);
    }

    @Override
    public LockerModel[] searchAvailableLocker() {
        return dbHandler.searchAvailableLocker();
    }

    @Override
    public LockerModel[] getAllLockerInfo() {
        return dbHandler.getAllLockerInfo();
    }

    @Override
    public String deleteMember(String MID) {
        return dbHandler.deleteMember(MID);
    }

    @Override
    public String insertMember(MemberModel member) {
        return dbHandler.insertMember(member);
    }

    @Override
    public MemberModel getSingleMemberInfo(String MID) {
        return dbHandler.getSingleMemberInfo(MID);
    }

    @Override
    public MemberModel[] getAllMemberInfo() {
        return dbHandler.getAllMemberInfo();
    }

    @Override
    public String updateMemberInfo(MemberModel member) {
        return dbHandler.updateMemberInfo(member);
    }

    @Override
    public EquipIDAndNameModel[] getAllCardioInfo() {
        return dbHandler.getAllCardioInfo();
    }

    @Override
    public EquipIDAndNameModel[] getAllStrengthInfo() {
        return dbHandler.getAllStrengthInfo();
    }

    @Override
    public WorkoutPlanModel[] getAllWorkoutPlanInfo() {
        return dbHandler.getAllWorkoutPlanInfo();
    }

    @Override
    public WorkoutPlanModel[] searchWorkoutPlan(String difficultyLevel) {
        return dbHandler.searchWorkoutPlan(difficultyLevel);
    }

    @Override
    public double getAvgDailyAmount() {
        return dbHandler.getAvgDailyAmount();
    }

    @Override
    public PaymentDailyAmountModel[] getPaymentDailyTotalAmount(double ThresholdAmount) {
        return dbHandler.getPaymentDailyTotalAmount(ThresholdAmount);
    }

    @Override
    public PaymentMemberModel[] getAllPaymentsInfo(java.sql.Date date) {
        return dbHandler.getAllPaymentsInfo(date);
    }

    @Override
    public FollowPlanModel[] getAllFollowPlanInfo() {
        return dbHandler.getAllFollowPlanInfo();
    }

    @Override
    public RequireEquipmentModel[] getAllRequireEquipmentGroupedByPlan() {
        return dbHandler.getAllRequireEquipmentGroupedByPlan();
    }

    @Override
    public String insertNewClassBooking(BookClassModel bookClassModel) {
        return dbHandler.insertNewClassBooking(bookClassModel);
    }

    @Override
    public BookClassModel[] getAllBookings() {
        return dbHandler.getAllBookings();
    }

    @Override
    public MemberModel[] findMembersWhoBookedAllTypesOfClass() {
        return dbHandler.findMembersWhoBookedAllTypesOfClass();
    }

    @Override
    public BookingModel[] getAvailableSlots() {
        return dbHandler.getAvailableSlots();
    }
}