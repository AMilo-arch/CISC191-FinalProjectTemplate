package edu.sdccd.cisc191.template;

public class Employee {
    // Employee name and salary
    private final String name;
    private final int salary;

    // Constructor to initialize name and salary
    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }
    // Getter for employee name
    public String getName() {
        return name;
    }

    // Getter for employee salary
    public int getSalary() {
        return salary;
    }
}
