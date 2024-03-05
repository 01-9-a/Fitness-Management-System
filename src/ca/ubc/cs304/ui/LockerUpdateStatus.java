package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LockerUpdateStatus extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField LIDField;
    private JTextField LockerStatusField;
    private TerminalTransactionsDelegate delegate;
    public LockerUpdateStatus(LockerOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel LIDLabel = new JLabel("Enter LID: ");
        JLabel LockerStatusLabel= new JLabel("Enter locker status: ");

        LIDField = new JTextField(TEXT_FIELD_WIDTH);
        LockerStatusField = new JTextField(TEXT_FIELD_WIDTH);

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the LID label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(LIDLabel, c);
        contentPane.add(LIDLabel);

        // place the text field for the LID
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(LIDField, c);
        contentPane.add(LIDField);


        // place the LockerStatus label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(LockerStatusLabel, c);
        contentPane.add(LockerStatusLabel);

        // place the text field for the LockerStatus
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(LockerStatusField, c);
        contentPane.add(LockerStatusField);

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

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

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
        LIDField.requestFocus();

        cancelButton.addActionListener(e -> {
            setVisible(false);
            optionWindow.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        delegate.updateLockerStatus(LIDField.getText(), LockerStatusField.getText());
    }
}