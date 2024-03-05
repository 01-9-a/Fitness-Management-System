package ca.ubc.cs304.ui;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.MemberModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberAdd extends JFrame implements ActionListener{
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField MIDField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField LIDField;
    private JTextField SDField;
    private JTextField EDField;
    private TerminalTransactionsDelegate delegate;
    public MemberAdd(MemberOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel MIDLabel = new JLabel("Enter member ID: ");
        JLabel nameLabel = new JLabel("Enter name: ");
        JLabel phoneLabel= new JLabel("Enter phone number: ");
        JLabel addressLabel= new JLabel("Enter address: ");
        JLabel LIDLabel= new JLabel("Enter locker ID: ");
        JLabel SDLabel= new JLabel("Enter membership start date (YYYY-MM-DD): ");
        JLabel EDLabel= new JLabel("Enter membership end date (YYYY-MM-DD): ");

        MIDField = new JTextField(TEXT_FIELD_WIDTH);
        nameField = new JTextField(TEXT_FIELD_WIDTH);
        phoneField = new JTextField(TEXT_FIELD_WIDTH);
        addressField = new JTextField(TEXT_FIELD_WIDTH);
        LIDField = new JTextField(TEXT_FIELD_WIDTH);
        SDField = new JTextField(TEXT_FIELD_WIDTH);
        EDField = new JTextField(TEXT_FIELD_WIDTH);

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel contentPanel = new JPanel();
        this.setContentPane(contentPanel);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPanel.setLayout(gb);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the MID label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(MIDLabel, c);
        contentPanel.add(MIDLabel);

        // place the text field for the MID
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(MIDField, c);
        contentPanel.add(MIDField);

        // place the name label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(nameLabel, c);
        contentPanel.add(nameLabel);

        // place the text field for the name
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(nameField, c);
        contentPanel.add(nameField);

        // place the phone label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(phoneLabel, c);
        contentPanel.add(phoneLabel);

        // place the text field for the phone
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(phoneField, c);
        contentPanel.add(phoneField);

        // place the address label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(addressLabel, c);
        contentPanel.add(addressLabel);

        // place the text field for the address
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(addressField, c);
        contentPanel.add(addressField);

        // place the LID label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(LIDLabel, c);
        contentPanel.add(LIDLabel);

        // place the text field for the LID
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(LIDField, c);
        contentPanel.add(LIDField);

        // place the SD label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(SDLabel, c);
        contentPanel.add(SDLabel);

        // place the text field for the SD
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(SDField, c);
        contentPanel.add(SDField);

        // place the ED label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(EDLabel, c);
        contentPanel.add(EDLabel);

        // place the text field for the ED
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(EDField, c);
        contentPanel.add(EDField);

        // place the submit button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(submitButton, c);
        contentPanel.add(submitButton);

        // place the cancel button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(cancelButton, c);
        contentPanel.add(cancelButton);

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
        MIDField.requestFocus();

        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide this window
            optionWindow.setVisible(true); // Show the previous window
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String sd = SDField.getText();
        String ed = EDField.getText();
        Date dateSD, dateED;

        try {
            dateSD = formatter.parse(sd);
            dateED = formatter.parse(ed);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid date in the correct format!");
            throw new RuntimeException(ex);
        }

        MemberModel model = new MemberModel(MIDField.getText(), nameField.getText(), addressField.getText(), phoneField.getText(), dateSD, dateED, LIDField.getText());
        String message = delegate.insertMember(model);
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}