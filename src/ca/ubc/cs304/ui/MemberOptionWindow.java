package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberOptionWindow extends JFrame{
    private String optionWindowOption;
    private TerminalTransactionsDelegate delegate;
    public MemberOptionWindow(MainWindow mainWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        setTitle("Option Window");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton deleteMemberButton = new JButton("Delete Member");
        JButton updateMemberButton = new JButton("Update Member");
        JButton addMemberButton = new JButton("Add member");
        JButton displayMembersInformationButton = new JButton("Display Members Information");
        JButton displaySingleMemberInformationButton = new JButton("Display Single Member Information");
        JButton cancelButton = new JButton("Cancel");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionWindowOption = e.getActionCommand();
                openNextWindow(optionWindowOption);
            }
        };

        deleteMemberButton.addActionListener(buttonListener);
        updateMemberButton.addActionListener(buttonListener);
        addMemberButton.addActionListener(buttonListener);
        displayMembersInformationButton.addActionListener(buttonListener);
        displaySingleMemberInformationButton.addActionListener(buttonListener);

        panel.add(deleteMemberButton);
        panel.add(updateMemberButton);
        panel.add(addMemberButton);
        panel.add(displayMembersInformationButton);
        panel.add(displaySingleMemberInformationButton);
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
            case "Delete Member":
                MemberDelete memberDelete = new MemberDelete(this, delegate);
                setVisible(false); // Hide the main window
                memberDelete.setVisible(true);
                break;
            case "Update Member":
                MemberUpdate memberUpdate = new MemberUpdate(this, delegate);
                setVisible(false); // Hide the main window
                memberUpdate.setVisible(true);
                break;
            case "Add member":
                MemberAdd memberAdd = new MemberAdd(this, delegate);
                setVisible(false); // Hide the main window
                memberAdd.setVisible(true);
                break;
            case "Display Members Information":
                MemberDisplayAll memberDisplayAll = new MemberDisplayAll(this, delegate);
                setVisible(false);
                memberDisplayAll.setVisible(true);
                break;
            case "Display Single Member Information":
                MemberDisplaySingle memberDisplaySingle = new MemberDisplaySingle(this, delegate);
                setVisible(false);
                memberDisplaySingle.setVisible(true);
                break;
        }
    }
}
