package edu.sdccd.cisc191.template;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeSearch {
    // List to store all employees
    private final List<Employee> employees;
    // Executor service to manage asynchronous searches
    private final ExecutorService executor;

    // Constructor to initialize list of employees and executor service
    public EmployeeSearch(List<Employee> employees) {
        this.employees = employees;
        this.executor = Executors.newFixedThreadPool(10);
    }

    // Method to search employees by name and sort results by name
    public List<Employee> searchByName(String name) {
        // Use Stream API to filter employees by name and sort results by name
        return employees.stream()                                                                       // Creates a stream to begin filtering our employees for a given name.
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))                    // Filter out names that do not contain the name we're looking for.
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))                               // Sort the names from alphabetically.
                .collect(Collectors.toList());                                                          // Collect our results and return it as a list.
    }

    // Method to search employees by salary and sort results by salary
    public List<Employee> searchBySalary(int min, int max) {
        // Use Stream API to filter employees by salary and sort results by salary
        return employees.stream()                                                       // Creates a stream to gather a list of employees based on a salary given a min and max.
                .filter(e -> e.getSalary() >= min && e.getSalary() <= max)              // Filter out employees based on their salary within our provided min-max range.
                .sorted((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()))    // Sort the employees from the lowest salary to highest.
                .collect(Collectors.toList());                                          // Collect our results and return it as a list.
    }

    // Method to search employees by name asynchronously.
    public void searchByNameAsync(String name, EmployeeSearchCallback employeeSearchCallback) {
        // Submit search task to executor service
        executor.submit(() -> employeeSearchCallback.onResult(searchByName(name)));               // Begin an asynchronous operation on searchByName, and notify the provided
                                                                                                  // callback when the results are complete
    }

    // Method to search employees by salary asynchronously
    public void searchBySalaryAsync(int min, int max, EmployeeSearchCallback employeeSearchCallback) {
        // Submit search task to executor service
        executor.submit(() -> employeeSearchCallback.onResult(searchBySalary(min, max)));         // Begin an asynchronous operation on searchBySalary, and notify the
                                                                                                  // callback when the results are complete.
    }
}
