package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberDelete extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField MIDField;
    private TerminalTransactionsDelegate delegate;
    public MemberDelete(MemberOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel MIDLabel = new JLabel("Enter MID: ");
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
        gb.setConstraints(MIDLabel, c);
        contentPane.add(MIDLabel);

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
        String message = delegate.deleteMember(MIDField.getText());
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}