package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;

public class PaymentAverageAmount extends JFrame {
    private TerminalTransactionsDelegate delegate;
    public PaymentAverageAmount(PaymentOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        double num = delegate.getAvgDailyAmount();

        setTitle("Average Daily Amount");
        JPanel contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);

        // Create and configure a JLabel to display the num value
        JLabel numLabel = new JLabel("Average Daily Amount: " + num);
        numLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the JLabel to the center of the content pane
        contentPane.add(numLabel, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Cancel");

        // Create a panel for the "Cancel" button with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(cancelButton);

        // Add the button panel to the bottom of the content pane
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setSize(400, 200);

        // Center the window
        Dimension d = getToolkit().getScreenSize();
        Rectangle r = getBounds();
        setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide the window
            optionWindow.setVisible(true); // Show the previous window
        });
    }
}
