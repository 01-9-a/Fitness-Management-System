package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.LockerModel;
import ca.ubc.cs304.model.MemberModel;
import ca.ubc.cs304.model.PaymentDailyAmountModel;
import ca.ubc.cs304.model.PaymentModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class PaymentDisplayAllByAmount extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField amountField;
    private TerminalTransactionsDelegate delegate;
    public PaymentDisplayAllByAmount(PaymentOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel amountLabel = new JLabel("Enter amount: ");
        amountField = new JTextField(TEXT_FIELD_WIDTH);
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the amount label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(amountLabel, c);
        contentPane.add(amountLabel);

        // place the text field for the amount
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(amountField, c);
        contentPane.add(amountField);

        // place the submit button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(submitButton, c);
        contentPane.add(submitButton);

        // place the cancel button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(cancelButton, c);
        contentPane.add(cancelButton);

        // register login button with action event handler
        submitButton.addActionListener(this);

        // size the window to obtain the best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // make the window visible
        this.setVisible(true);

        // place the cursor in the text field for the username
        amountField.requestFocus();

        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide this window
            optionWindow.setVisible(true); // Show the previous window
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //verify input
        String input = amountField.getText();
        double amount;

        try {
            amount = Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid amount(int)!");
            throw new RuntimeException(ex);
        }

        PaymentDailyAmountModel[] model = delegate.getPaymentDailyTotalAmount(Double.parseDouble(amountField.getText()));
        Object[][] data = new Object[model.length][2];
        for (int i = 0; i < model.length; i++) {
            data[i][0] = model[i].getTotalAmount();
            data[i][1] = model[i].getPaymentDate();
        }

        JFrame frame = new JFrame("Payments information");

        // Create column names
        String[] columnNames = {"Payment Amount", "Payment Date"};

        // Create the JTable
        JTable table = new JTable(data, columnNames);

        // Add the table to a scroll pane to enable scrolling if needed
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        frame.getContentPane().add(scrollPane);

        // Create a panel for the "Cancel" button with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);

        // Add the button panel to the bottom of the content pane
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set some properties for the frame
        frame.setSize(500, 400);

        // center the frame
        Dimension d = frame.getToolkit().getScreenSize();
        Rectangle r = frame.getBounds();
        frame.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        frame.setVisible(true);

        cancelButton.addActionListener(event -> {
            frame.setVisible(false); // Hide the frame
        });
    }
}
