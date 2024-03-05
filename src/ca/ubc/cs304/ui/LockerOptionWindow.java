package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LockerOptionWindow extends JFrame {
    private String optionWindowOption;
    private TerminalTransactionsDelegate delegate;
    public LockerOptionWindow(MainWindow mainWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        setTitle("Option Window");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton searchAvailableLockersButton = new JButton("Search Available Lockers");
        JButton updateLockerStatusButton = new JButton("Update Locker Status");
        JButton displayLockersInformationButton = new JButton("Display Lockers Information");
        JButton displaySingleLockerInformationButton = new JButton("Display Required Locker Information");
        JButton cancelButton = new JButton("Cancel");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionWindowOption = e.getActionCommand();
                openNextWindow(optionWindowOption);
            }
        };

        searchAvailableLockersButton.addActionListener(buttonListener);
        updateLockerStatusButton.addActionListener(buttonListener);
        displayLockersInformationButton.addActionListener(buttonListener);
        displaySingleLockerInformationButton.addActionListener(buttonListener);

        panel.add(searchAvailableLockersButton);
        panel.add(updateLockerStatusButton);
        panel.add(displayLockersInformationButton);
        panel.add(displaySingleLockerInformationButton);
        panel.add(cancelButton);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add ActionListener for the Cancel button
        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide the option window
            mainWindow.setVisible(true); // Show the main window
        });

        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        add(panel);
    }
    private void openNextWindow(String optionOption){
        switch (optionOption) {
            case "Search Available Lockers":
                LockerSearchAvailable searchAvailableLockers = new LockerSearchAvailable(this, delegate);
                setVisible(false); // Hide the main window
                searchAvailableLockers.setVisible(true);
                break;
            case "Update Locker Status":
                LockerUpdateStatus updateLockerStatus = new LockerUpdateStatus(this, delegate);
                setVisible(false); // Hide the main window
                updateLockerStatus.setVisible(true);
                break;
            case "Display Lockers Information":
                LockerDisplayAll displayAllLockers = new LockerDisplayAll(this, delegate);
                setVisible(false); // Hide the main window
                displayAllLockers.setVisible(true);
                break;
            case "Display Required Locker Information":
                LockerDisplayRequired displaySingleLocker = new LockerDisplayRequired(this, delegate);
                setVisible(false); // Hide the main window
                displaySingleLocker.setVisible(true);
                break;
        }
    }
}