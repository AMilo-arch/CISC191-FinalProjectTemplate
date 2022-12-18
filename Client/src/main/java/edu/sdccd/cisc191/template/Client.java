package edu.sdccd.cisc191.template;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;


public class Client {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(

                new Employee("John Smith", 35000),
                new Employee("Jane Doe", 45000),
                new Employee("Bob Johnson", 55000),
                new Employee("Alex Milosevic", 12000),
                new Employee("Jovana Milosevic", 80000),
                new Employee("Guy Zamora", 71000),
                new Employee("James Smithson", 66000),
                new Employee("Brooklyn Kirby", 49500),
                new Employee("Cyrus Burch", 110000)
        );

        // Starting the GUI.
        EmployeeSearchGUI gui = new EmployeeSearchGUI(employees);
        gui.setVisible(true);
    }
}

