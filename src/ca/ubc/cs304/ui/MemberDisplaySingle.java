package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.MemberModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberDisplaySingle extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField MIDField;
    private TerminalTransactionsDelegate delegate;
    public MemberDisplaySingle(MemberOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel LIDLabel = new JLabel("Enter MID: ");
        MIDField = new JTextField(TEXT_FIELD_WIDTH);
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the MID label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(LIDLabel, c);
        contentPane.add(LIDLabel);

        // place the text field for the MID
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(MIDField, c);
        contentPane.add(MIDField);

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
        MIDField.requestFocus();

        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide this window
            optionWindow.setVisible(true); // Show the previous window
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MemberModel model = delegate.getSingleMemberInfo(MIDField.getText());
        String modelMID = model.getMID();
        String modelLName = model.getMemberName();
        String modelPhone = model.getMemberPhone();
        String modelAddress = model.getAddress();
        String modelLID = model.getLID();
        String modelSD = String.valueOf(model.getMembershipSD());
        String modelED = String.valueOf(model.getMembershipED());


        JFrame frame = new JFrame("Member Information");

        Object[][] data = {
                {modelMID, modelLName, modelPhone, modelAddress, modelLID, modelSD, modelED}
        };

        // Create column names
        String[] columnNames = {"Member ID", "Name", "Phone Number", "Address", "Locker ID", "Membership Start Date", "Membership End Date"};

        // Create the JTable
        JTable table = new JTable(data, columnNames);

        // Add the table to a scroll pane to enable scrolling if needed
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        frame.getContentPane().add(scrollPane);

        // Set some properties for the frame
        frame.setSize(1200, 200);;

        // center the frame
        Dimension d = frame.getToolkit().getScreenSize();
        Rectangle r = frame.getBounds();
        frame.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        frame.setVisible(true);
    }
}
