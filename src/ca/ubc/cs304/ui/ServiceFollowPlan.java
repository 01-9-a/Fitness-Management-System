package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.FollowPlanModel;

import javax.swing.*;
import java.awt.*;

public class ServiceFollowPlan extends JFrame{
    private TerminalTransactionsDelegate delegate;
    public ServiceFollowPlan(ServiceOptionWindow optionWindow, TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        FollowPlanModel[] model = delegate.getAllFollowPlanInfo();
        Object[][] data = new Object[model.length][2];
        for (int i = 0; i < model.length; i++) {
            data[i][0] = model[i].getMID();
            data[i][1] = model[i].getPID();
        }

        setTitle("All Follow Plan Information");
        JPanel contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);

        JButton cancelButton = new JButton("Cancel");

        // Create column names
        String[] columnNames = {"Member ID", "Plan ID"};

        // Create the JTable
        JTable table = new JTable(data, columnNames);

        // Add the table to a scroll pane to enable scrolling if needed
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the center of the content pane
        contentPane.add(scrollPane, BorderLayout.CENTER);

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
