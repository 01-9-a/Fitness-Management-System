package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.EquipmentModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EquipmentOptionWindow extends JFrame {
    private String optionWindowOption;
    private TerminalTransactionsDelegate delegate;
    public EquipmentOptionWindow(MainWindow mainWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        setTitle("Option Window");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton cardioButton = new JButton("Cardio");
        JButton strengthButton = new JButton("Strength");
        JButton cancelButton = new JButton("Cancel");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionWindowOption = e.getActionCommand();
                openNextWindow(optionWindowOption);
            }
        };

        cardioButton.addActionListener(buttonListener);
        strengthButton.addActionListener(buttonListener);

        panel.add(cardioButton);
        panel.add(strengthButton);
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
            case "Cardio":
                EquipmentCardio equipmentCardio = new EquipmentCardio(this, delegate);
                setVisible(false); // Hide the main window
                equipmentCardio.setVisible(true);
                break;
            case "Strength":
                EquipmentStrength equipmentStrength = new EquipmentStrength(this, delegate);
                setVisible(false); // Hide the main window
                equipmentStrength.setVisible(true);
                break;
        }
    }
}
