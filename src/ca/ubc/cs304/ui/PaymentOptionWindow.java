package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentOptionWindow extends JFrame{
    private String optionWindowOption;
    private TerminalTransactionsDelegate delegate;
    public PaymentOptionWindow(MainWindow mainWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        setTitle("Option Window");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton averageAmountButton = new JButton("Display Average Daily Amount");
        JButton byAmountButton = new JButton("Display Payments by Amount");
        JButton byDateButton = new JButton("Display Payments by Date");
        JButton cancelButton = new JButton("Cancel");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionWindowOption = e.getActionCommand();
                openNextWindow(optionWindowOption);
            }
        };

        averageAmountButton.addActionListener(buttonListener);
        byAmountButton.addActionListener(buttonListener);
        byDateButton.addActionListener(buttonListener);

        panel.add(averageAmountButton);
        panel.add(byAmountButton);
        panel.add(byDateButton);
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
            case "Display Average Daily Amount":
                PaymentAverageAmount paymentAverageAmount = new PaymentAverageAmount(this, delegate);
                setVisible(false); // Hide the main window
                paymentAverageAmount.setVisible(true);
                break;
            case "Display Payments by Amount":
                PaymentDisplayAllByAmount paymentDisplayAllByAmount = new PaymentDisplayAllByAmount(this, delegate);
                setVisible(false); // Hide the main window
                paymentDisplayAllByAmount.setVisible(true);
                break;
            case "Display Payments by Date":
                PaymentDisplayAllByDate paymentDisplayAllByDate = new PaymentDisplayAllByDate(this, delegate);
                setVisible(false); // Hide the main window
                paymentDisplayAllByDate.setVisible(true);
                break;
        }
    }
}
