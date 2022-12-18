package edu.sdccd.cisc191.template;

import java.util.List;

// Callback interface to handle search results
public interface EmployeeSearchCallback {
    void onResult(List<Employee> result);
}
