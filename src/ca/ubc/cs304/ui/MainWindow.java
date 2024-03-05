package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.WorkoutPlanModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private String mainWindowOption;
    private TerminalTransactionsDelegate delegate;

    public MainWindow(TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;
        setTitle("Main Window");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JButton memberButton = new JButton("Member");
        JButton paymentButton = new JButton("Payment");
        JButton lockerButton = new JButton("Locker");
        JButton instructorButton = new JButton("Instructor");
        JButton workoutPlanButton = new JButton("Workout Plan");
        JButton classButton = new JButton("Class");
        JButton equipmentButton = new JButton("Equipment");
        JButton engineerButton = new JButton("Engineer");
        JButton serviceButton = new JButton("Service");

        ActionListener buttonListener = e -> {
            mainWindowOption = e.getActionCommand();
            openOptionWindow(mainWindowOption);
        };

        memberButton.addActionListener(buttonListener);
        paymentButton.addActionListener(buttonListener);
        lockerButton.addActionListener(buttonListener);
        instructorButton.addActionListener(buttonListener);
        workoutPlanButton.addActionListener(buttonListener);
        classButton.addActionListener(buttonListener);
        equipmentButton.addActionListener(buttonListener);
        engineerButton.addActionListener(buttonListener);
        serviceButton.addActionListener(buttonListener);

        panel.add(memberButton);
        panel.add(paymentButton);
        panel.add(lockerButton);
        panel.add(instructorButton);
        panel.add(workoutPlanButton);
        panel.add(classButton);
        panel.add(equipmentButton);
        panel.add(engineerButton);
        panel.add(serviceButton);

        add(panel);
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        setVisible(true);
    }

    private void openOptionWindow(String selectedOption) {

        switch (selectedOption) {
            case "Member":
                MemberOptionWindow memberOptionWindow = new MemberOptionWindow(this, delegate);
                setVisible(false);
                memberOptionWindow.setVisible(true);
                break;
            case "Payment":
                PaymentOptionWindow optionWindow = new PaymentOptionWindow(this, delegate);
                setVisible(false);
                optionWindow.setVisible(true);
                break;
            case "Locker":
                LockerOptionWindow lockerOptionWindow = new LockerOptionWindow(this, delegate);
                setVisible(false);
                lockerOptionWindow.setVisible(true);
                break;
                /*
            case "Instructor":
                InstructorOptionWindow optionWindow = new InstructorOptionWindow(this, delegate);
                setVisible(false);
                optionWindow.setVisible(true);
                break;
             */
            case "Workout Plan":
                WorkoutPlanOptionWindow workoutPlanOptionWindow = new WorkoutPlanOptionWindow(this, delegate);
                setVisible(false);
                workoutPlanOptionWindow.setVisible(true);
                break;
                /*
            case "Class":
                ClassOptionWindow optionWindow = new ClassOptionWindow(this, delegate);
                setVisible(false);
                optionWindow.setVisible(true);
                break;
                */
            case "Equipment":
                EquipmentOptionWindow equipmentOptionWindow = new EquipmentOptionWindow(this, delegate);
                setVisible(false);
                equipmentOptionWindow.setVisible(true);
                break;
                /*
            case "Engineer":
                EngineerOptionWindow optionWindow = new EngineerOptionWindow(this, delegate);
                setVisible(false);
                optionWindow.setVisible(true);
                break;
                */
            case "Service":
                ServiceOptionWindow serviceOptionWindow = new ServiceOptionWindow(this, delegate);
                setVisible(false);
                serviceOptionWindow.setVisible(true);
                break;
        }
    }
}