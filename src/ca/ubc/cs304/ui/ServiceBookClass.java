package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServiceBookClass extends JFrame{
    private String windowOption;
    private TerminalTransactionsDelegate delegate;
    public ServiceBookClass(ServiceOptionWindow serviceOptionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        setTitle("Option Window");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton insertButton = new JButton("Insert Class Booking");
        JButton checkButton = new JButton("Check Class Availability");
        JButton groupButton = new JButton("Members Have Booked All Types of Classes");
        JButton displayButton = new JButton("Display All Bookings");
        JButton cancelButton = new JButton("Cancel");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowOption = e.getActionCommand();
                openNextWindow(windowOption);
            }
        };

        insertButton.addActionListener(buttonListener);
        checkButton.addActionListener(buttonListener);
        groupButton.addActionListener(buttonListener);
        displayButton.addActionListener(buttonListener);
        cancelButton.addActionListener(buttonListener);

        panel.add(insertButton);
        panel.add(checkButton);
        panel.add(groupButton);
        panel.add(displayButton);
        panel.add(cancelButton);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add ActionListener for the Cancel button
        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide the option window
            serviceOptionWindow.setVisible(true); // Show the previous window
        });

        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        add(panel);
    }
    private void openNextWindow(String optionOption){
        switch (optionOption) {
            case "Insert Class Booking":
                ServiceBookClassInsert serviceBookClassInsert = new ServiceBookClassInsert(this, delegate);
                setVisible(false); // Hide the main window
                serviceBookClassInsert.setVisible(true);
                break;
            case "Check Class Availability":
                ServiceBookClassCheck serviceBookClassCheck = new ServiceBookClassCheck(this, delegate);
                setVisible(false); // Hide the main window
                serviceBookClassCheck.setVisible(true);
                break;
            case "Members Have Booked All Types of Classes":
                ServiceBookClassGroup serviceBookClassGroup = new ServiceBookClassGroup(this, delegate);
                setVisible(false);
                serviceBookClassGroup.setVisible(true);
                break;
            case "Display All Bookings":
                ServiceBookClassDisplayAll serviceBookClassDisplayAll = new ServiceBookClassDisplayAll(this, delegate);
                setVisible(false);
                serviceBookClassDisplayAll.setVisible(true);
        }
    }
}
