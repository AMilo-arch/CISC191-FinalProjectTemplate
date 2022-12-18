package edu.sdccd.cisc191.template;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;

public class EmployeeSearchTest {
    @Test
    public void testSearchByName() {
        List<Employee> employees = Arrays.asList(
                new Employee("John Smith", 35000),
                new Employee("Jane Doe", 45000),
                new Employee("Bob Johnson", 55000)
        );

        EmployeeSearch search = new EmployeeSearch(employees);

        // Search for the employee, "Smith" and return all entries with "Smith".
        List<Employee> result = search.searchByName("Smith");

        // We know there's only one "Smith" in this list, so our result size should be 1.
        assertEquals(1, result.size());

        // We know "Smith" Should be the first entry, so compare our results with the first entry.
        assertTrue(result.contains(employees.get(0)));

        // Lastly, search for the name XYZ, this result should be empty.
        result = search.searchByName("XYZ");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchBySalary() {
        List<Employee> employees = Arrays.asList(
                new Employee("John Smith", 35000),
                new Employee("Jane Doe", 45000),
                new Employee("Bob Johnson", 55000)
        );

        EmployeeSearch search = new EmployeeSearch(employees);
        // Search for the employee salary,  and return all entries between 40,000 and 50,000.
        List<Employee> result = search.searchBySalary(40000, 50000);

        // We know there's only one employee who makes between 40k and 50k, which is Jane, the result should be 1.
        assertEquals(1, result.size());

        // We know Jane Doe should be our result, so compare Jane Doe against the result.
        assertTrue(result.contains(employees.get(1)));

        // Lastly, search for a salary outside our range, this result should be empty.
        result = search.searchBySalary(10000, 20000);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchByNameAsync() throws InterruptedException {
        List<Employee> employees = Arrays.asList(
                new Employee("John Smith", 35000),
                new Employee("Jane Doe", 45000),
                new Employee("Bob Johnson", 55000)
        );

        EmployeeSearch search = new EmployeeSearch(employees);

        final CountDownLatch latch = new CountDownLatch(1);
        // Start an Async operation for searchByName for "Smith"
        search.searchByNameAsync("Smith", new EmployeeSearchCallback() {
            @Override
            public void onResult(List<Employee> result) {
                // Our result size should be 1, and our results should be "John Smith".
                assertEquals(1, result.size());
                assertTrue(result.contains(employees.get(0)));
                latch.countDown();
            }
        });

        latch.await();
    }
    @Test
    public void testSearchBySalaryAsync() throws InterruptedException {
        List<Employee> employees = Arrays.asList(
                new Employee("John Smith", 35000),
                new Employee("Jane Doe", 45000),
                new Employee("Bob Johnson", 55000)
        );

        EmployeeSearch search = new EmployeeSearch(employees);

        final CountDownLatch latch = new CountDownLatch(1);
        // Start an Async operation for searchBySalary for a salary between 40k and 50k
        search.searchBySalaryAsync(40000, 50000, new EmployeeSearchCallback() {
            @Override
            public void onResult(List<Employee> result) {
                // Our result size should be 1, and our result should be "Jane Doe".
                assertEquals(1, result.size());
                assertTrue(result.contains(employees.get(1)));
                latch.countDown();
            }
        });

        latch.await();
    }
}