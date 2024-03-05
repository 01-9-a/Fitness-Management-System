package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServiceOptionWindow extends JFrame{
    private String optionWindowOption;
    private TerminalTransactionsDelegate delegate;
    public ServiceOptionWindow(MainWindow mainWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        setTitle("Option Window");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton bookClassButton = new JButton("Book Class");
        JButton followPlanButton = new JButton("Follow Plan");
        JButton requireEquipButton = new JButton("Require Equipment");
        JButton cancelButton = new JButton("Cancel");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionWindowOption = e.getActionCommand();
                openNextWindow(optionWindowOption);
            }
        };

        bookClassButton.addActionListener(buttonListener);
        followPlanButton.addActionListener(buttonListener);
        requireEquipButton.addActionListener(buttonListener);
        cancelButton.addActionListener(buttonListener);

        panel.add(bookClassButton);
        panel.add(followPlanButton);
        panel.add(requireEquipButton);
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
            case "Book Class":
                ServiceBookClass serviceBookClass = new ServiceBookClass(this, delegate);
                setVisible(false); // Hide the main window
                serviceBookClass.setVisible(true);
                break;
            case "Follow Plan":
                ServiceFollowPlan serviceFollowPlan = new ServiceFollowPlan(this, delegate);
                setVisible(false); // Hide the main window
                serviceFollowPlan.setVisible(true);
                break;
            case "Require Equipment":
                ServiceRequireEquip serviceRequireEquip = new ServiceRequireEquip(this, delegate);
                setVisible(false); // Hide the main window
                serviceRequireEquip.setVisible(true);
                break;
        }
    }
}
