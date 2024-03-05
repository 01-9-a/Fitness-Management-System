package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkoutPlanOptionWindow extends JFrame{
    private String optionWindowOption;
    private TerminalTransactionsDelegate delegate;
    public WorkoutPlanOptionWindow(MainWindow mainWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        setTitle("Option Window");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton displayButton = new JButton("Display All Plans");
        JButton searchButton = new JButton("Search Plans");
        JButton cancelButton = new JButton("Cancel");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionWindowOption = e.getActionCommand();
                openNextWindow(optionWindowOption);
            }
        };

        displayButton.addActionListener(buttonListener);
        searchButton.addActionListener(buttonListener);

        panel.add(displayButton);
        panel.add(searchButton);
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
            case "Display All Plans":
                WorkoutPlanDisplay workoutPlanDisplay = new WorkoutPlanDisplay(this, delegate);
                setVisible(false); // Hide the main window
                workoutPlanDisplay.setVisible(true);
                break;
            case "Search Plans":
                WorkoutPlanSearch workoutPlanSearch = new WorkoutPlanSearch(this, delegate);
                setVisible(false); // Hide the main window
                workoutPlanSearch.setVisible(true);
                break;
        }
    }
}
