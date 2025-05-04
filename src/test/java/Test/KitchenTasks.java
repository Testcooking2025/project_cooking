package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class KitchenTasks {

    // Task model
    static class Task {
        String name;
        String staff;
        String status;

        Task(String name, String staff, String status) {
            this.name = name;
            this.staff = staff;
            this.status = status;
        }
    }

    private final List<Task> taskList = new ArrayList<>();

    // ========== Scenario 1: Create & assign task ==========
    @Given("the chef is logged into the system")
    public void chefIsLoggedIn() {
        // Simulated login
    }

    @When("the chef creates a task named {string} assigned to {string}")
    public void chefCreatesTask(String taskName, String staff) {
        taskList.add(new Task(taskName, staff, "Pending"));
    }

    @Then("the system should list the task {string} assigned to {string} with status {string}")
    public void systemShouldListTask(String expectedTask, String expectedStaff, String expectedStatus) {
        boolean found = taskList.stream().anyMatch(task ->
                task.name.equals(expectedTask) &&
                        task.staff.equals(expectedStaff) &&
                        task.status.equals(expectedStatus)
        );
        assertTrue(found, "Task not found or data mismatch.");
    }

    // ========== Scenario 2: Start task ==========
    @Given("a task {string} is assigned to {string} with status {string}")
    public void taskExists(String taskName, String staff, String status) {
        taskList.add(new Task(taskName, staff, status));
    }

    @When("{string} starts the task")
    public void staffStartsTask(String staff) {
        for (Task task : taskList) {
            if (task.staff.equals(staff) && task.status.equals("Pending")) {
                task.status = "In Progress";
            }
        }
    }

    @Then("the task {string} should have status {string}")
    public void verifyTaskStatus(String taskName, String expectedStatus) {
        Optional<Task> match = taskList.stream().filter(t -> t.name.equals(taskName)).findFirst();
        assertTrue(match.isPresent(), "Task not found.");
        assertEquals(expectedStatus, match.get().status);
    }

    // ========== Scenario 3: Complete task ==========
    @When("{string} marks the task as complete")
    public void staffCompletesTask(String staff) {
        for (Task task : taskList) {
            if (task.staff.equals(staff) && task.status.equals("In Progress")) {
                task.status = "Completed";
            }
        }
    }

    // ========== Scenario 4: View all tasks ==========
    @Given("the following tasks exist:")
    public void theFollowingTasksExist(io.cucumber.datatable.DataTable table) {
        taskList.clear();
        List<Map<String, String>> rows = table.asMaps();
        for (Map<String, String> row : rows) {
            taskList.add(new Task(
                    row.get("Task"),
                    row.get("Staff"),
                    row.get("Status")
            ));
        }
    }

    @When("the chef views the task list")
    public void chefViewsTaskList() {
        // no action needed
    }

    @Then("the system should display the tasks with their assigned staff and statuses")
    public void systemShouldDisplayTasks() {
        assertFalse(taskList.isEmpty(), "Task list is empty.");
        for (Task task : taskList) {
            System.out.println(task.name + " - " + task.staff + " - " + task.status);
        }
    }
}
