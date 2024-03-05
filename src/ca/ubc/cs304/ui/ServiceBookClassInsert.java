package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BookClassModel;
import ca.ubc.cs304.model.MemberModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceBookClassInsert extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField MIDField;
    private JTextField IIDField;
    private JTextField classNameField;
    private JTextField classDateField;
    private TerminalTransactionsDelegate delegate;
    public ServiceBookClassInsert(ServiceBookClass optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel MIDLabel = new JLabel("Enter member ID: ");
        JLabel IIDLabel = new JLabel("Enter instructor ID: ");
        JLabel classNameLabel= new JLabel("Enter class name: ");
        JLabel classDateLabel= new JLabel("Enter class date (YYYY-MM-DD): ");

        MIDField = new JTextField(TEXT_FIELD_WIDTH);
        IIDField = new JTextField(TEXT_FIELD_WIDTH);
        classNameField = new JTextField(TEXT_FIELD_WIDTH);
        classDateField = new JTextField(TEXT_FIELD_WIDTH);

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

        // place the IID label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(IIDLabel, c);
        contentPanel.add(IIDLabel);

        // place the text field for the IID
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(IIDField, c);
        contentPanel.add(IIDField);

        // place the className label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(classNameLabel, c);
        contentPanel.add(classNameLabel);

        // place the text field for the className
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(classNameField, c);
        contentPanel.add(classNameField);

        // place the classDate label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(classDateLabel, c);
        contentPanel.add(classDateLabel);

        // place the text field for the classDate
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(classDateField, c);
        contentPanel.add(classDateField);

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

        // register submit button with action event handler
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
        String date = classDateField.getText();
        Date dateNew;
        try {
            dateNew = formatter.parse(date);

        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        BookClassModel model = new BookClassModel(MIDField.getText(), IIDField.getText(), classNameField.getText(), dateNew);
        String message = delegate.insertNewClassBooking(model);
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}