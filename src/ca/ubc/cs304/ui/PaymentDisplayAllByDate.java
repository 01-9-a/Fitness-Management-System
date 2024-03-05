package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.PaymentMemberModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentDisplayAllByDate extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField dateField;
    private TerminalTransactionsDelegate delegate;
    public PaymentDisplayAllByDate(PaymentOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel dateLabel = new JLabel("Enter date: ");
        dateField = new JTextField(TEXT_FIELD_WIDTH);
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the date label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(dateLabel, c);
        contentPane.add(dateLabel);

        // place the text field for the date
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(dateField, c);
        contentPane.add(dateField);

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
        dateField.requestFocus();

        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide this window
            optionWindow.setVisible(true); // Show the previous window
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //verify input
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateField.getText();
        Date newDate;

        try {
            newDate = formatter.parse(date);

        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());
        PaymentMemberModel[] model = delegate.getAllPaymentsInfo(sqlDate);

        Object[][] data = new Object[model.length][5];
        for (int i = 0; i < model.length; i++) {
            data[i][0] = model[i].getAmount();
            data[i][1] = model[i].getPaymentDate();
            data[i][2] = model[i].getMemberName();
            data[i][3] = model[i].getMID();
            data[i][4] = model[i].getPID();
        }


        JFrame frame = new JFrame("Payments information");

        // Create column names
        String[] columnNames = {"Payment Amount", "Payment Date", "Member Name", "Member ID", "Payment ID"};

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
        frame.setSize(800, 400);

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
