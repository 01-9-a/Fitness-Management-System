package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.LockerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LockerDisplayRequired extends JFrame implements ActionListener{
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField LsizeField;
    private JTextField LstatusField;
    private JTextField feeField;
    private TerminalTransactionsDelegate delegate;
    public LockerDisplayRequired(LockerOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel LsizeLabel = new JLabel("Enter locker size: ");
        JLabel LstatusLabel = new JLabel("Enter locker status: ");
        JLabel feeLabel = new JLabel("Enter locker fee: ");
        LsizeField = new JTextField(TEXT_FIELD_WIDTH);
        LstatusField = new JTextField(TEXT_FIELD_WIDTH);
        feeField = new JTextField(TEXT_FIELD_WIDTH);
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the Lsize label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(LsizeLabel, c);
        contentPane.add(LsizeLabel);

        // place the text field for the Lsize
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(LsizeField, c);
        contentPane.add(LsizeField);

        // place the Lstatus label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(LstatusLabel, c);
        contentPane.add(LstatusLabel);

        // place the text field for the Lstatus
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(LstatusField, c);
        contentPane.add(LstatusField);

        // place the fee label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(feeLabel, c);
        contentPane.add(feeLabel);

        // place the text field for the fee
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(feeField, c);
        contentPane.add(feeField);

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
        LsizeField.requestFocus();

        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide this window
            optionWindow.setVisible(true); // Show the previous window
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double fee;
        if(feeField.getText().isEmpty()) {
            fee = -1;
        }else {
            try {
                fee = Double.parseDouble(feeField.getText());
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
                throw new RuntimeException(ex);
            }
        }

        LockerModel[] model = delegate.getSingleLockerInfo(LsizeField.getText(), LstatusField.getText(), fee);
        if (model.length == 0) {
            JOptionPane.showMessageDialog(this, "Locker does not exist!");
        } else {
            JFrame frame = new JFrame("Locker Information");

            Object[][] data = new Object[model.length][4];
            for (int i = 0; i < model.length; i++) {
                data[i][0] = model[i].getID();
                data[i][1] = model[i].getSize();
                data[i][2] = model[i].getStatus();
                data[i][3] = model[i].getLockerHourlyFee();
            }

            // Create column names
            String[] columnNames = {"Locker ID", "Locker Size", "Locker Status", "Locker Hourly Fee"};

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
            frame.setSize(500, 200);

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
}
