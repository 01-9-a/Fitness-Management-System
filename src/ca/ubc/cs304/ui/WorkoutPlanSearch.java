package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.WorkoutPlanModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkoutPlanSearch extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JTextField levelField;
    private TerminalTransactionsDelegate delegate;
    public WorkoutPlanSearch(WorkoutPlanOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel levelLabel = new JLabel("Enter difficulty level: ");
        levelField = new JTextField(TEXT_FIELD_WIDTH);
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the level label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(levelLabel, c);
        contentPane.add(levelLabel);

        // place the text field for the level
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(levelField, c);
        contentPane.add(levelField);

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
        levelField.requestFocus();

        cancelButton.addActionListener(e -> {
            setVisible(false); // Hide this window
            optionWindow.setVisible(true); // Show the previous window
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WorkoutPlanModel[] model = delegate.searchWorkoutPlan(levelField.getText());
        Object[][] data = new Object[model.length][7];
        for (int i = 0; i < model.length; i++) {
            data[i][0] = model[i].getPID();
            data[i][1] = model[i].getDescription();
            data[i][2] = model[i].getPlanFee();
            data[i][3] = model[i].getDifficultyLevel();
            data[i][4] = model[i].getPlanSD();
            data[i][5] = model[i].getPlanED();
            data[i][6] = model[i].getIID();
        }

        JFrame frame = new JFrame("Plan Information");

        // Create column names
        String[] columnNames = {"Plan ID", "Description", "Plan Fee", "Difficulty Level", "Start Date", "End Date", "Instructor ID"};

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
        frame.setSize(1400, 200);

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
