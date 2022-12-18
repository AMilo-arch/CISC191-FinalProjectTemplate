package edu.sdccd.cisc191.template;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EmployeeSearchGUI extends JFrame {
    private final EmployeeSearch search;

    private JTextField nameField;
    private JTextField minSalaryField;
    private JTextField maxSalaryField;
    private JLabel resultsLabel;

    // Constructor that takes in a list of employees.
    public EmployeeSearchGUI(List<Employee> employees) {
        this.search = new EmployeeSearch(employees);
        // Setting up our window and our UI elements.
        setTitle("Employee Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        resultsLabel = new JLabel();
        add(resultsLabel, BorderLayout.CENTER);
        resultsLabel.setText("No Results!");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    // Sets up the UI panel and user input.
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        nameField = new JTextField(20);
        minSalaryField = new JTextField(5);
        maxSalaryField = new JTextField(5);

        JButton nameButton = new JButton("Search by Name");
        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByName();
            }
        });

        JButton salaryButton = new JButton("Search by Salary");
        salaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBySalary();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx = 2;
        panel.add(nameButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Min Salary:"), gbc);
        gbc.gridx = 1;
        panel.add(minSalaryField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Max Salary:"), gbc);
        gbc.gridx = 3;
        panel.add(maxSalaryField, gbc);
        gbc.gridx = 4;
        panel.add(salaryButton, gbc);

        return panel;
    }

    // sets up the logic for when the searchByName button is pressed.
    private void searchByName() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            resultsLabel.setText("Please enter a name.");
            return;
        }

        search.searchByNameAsync(name, new EmployeeSearchCallback() {
            @Override
            public void onResult(List<Employee> result) {
                displayResults(result);
            }
        });
    }

    // sets up the logic for when the searchBySalary button is pressed.
    private void searchBySalary() {
        String minStr = minSalaryField.getText().trim();
        String maxStr = maxSalaryField.getText().trim();
        if (minStr.isEmpty() || maxStr.isEmpty()) {
            resultsLabel.setText("Please enter both a minimum and maximum salary.");
            return;
        }

        int min;
        int max;
        try {
            min = Integer.parseInt(minStr);
            max = Integer.parseInt(maxStr);
        } catch (NumberFormatException e) {
            resultsLabel.setText("Invalid salary.");
            return;
        }

        search.searchBySalaryAsync(min, max, new EmployeeSearchCallback() {
            @Override
            public void onResult(List<Employee> result) {
                displayResults(result);
            }
        });
    }

    // Displays the results to a label.
    private void displayResults(List<Employee> result) {
        if (result.isEmpty()) {
            resultsLabel.setText("No matching employees found.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            for (Employee employee : result) {
                sb.append(employee.getName()).append(": ").append(employee.getSalary()).append("<br>");
            }
            sb.append("</html>");
            resultsLabel.setText(sb.toString());
        }
        pack();
    }
}