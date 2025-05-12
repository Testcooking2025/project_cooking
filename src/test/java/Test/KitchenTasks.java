package Test;

import io.cucumber.java.en.*;
import services.KitchenTaskManager;
import models.KitchenTask;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class KitchenTasks {

    private final KitchenTaskManager manager = new KitchenTaskManager();
    private List<KitchenTask> visibleTasks = new ArrayList<>();

    @Given("the chef is logged into the system")
    public void chefIsLoggedIn() {
        // Assume chef session (no-op)
    }

    @When("the chef creates a task named {string} assigned to {string}")
    public void chefCreatesTask(String taskName, String staff) {
        manager.createTask(taskName, staff);
    }

    @Then("the system should list the task {string} assigned to {string} with status {string}")
    public void systemShouldListTask(String expectedTask, String expectedStaff, String expectedStatus) {
        List<KitchenTask> tasks = manager.getAllTasks();
        boolean found = tasks.stream().anyMatch(task ->
                task.getName().equals(expectedTask) &&
                        task.getAssignedStaff().equals(expectedStaff) &&
                        task.getStatus().equals(expectedStatus)
        );
        assertTrue(found, "Expected task not found or mismatch.");
    }

    @Given("a task {string} is assigned to {string} with status {string}")
    public void taskExists(String taskName, String staff, String status) {
        manager.createTask(taskName, staff);
        manager.updateTaskStatus(taskName, status);
    }

    @When("{string} starts the task")
    public void staffStartsTask(String staff) {
        manager.startTaskByStaff(staff);
    }

    @When("{string} marks the task as complete")
    public void staffCompletesTask(String staff) {
        manager.completeTaskByStaff(staff);
    }

    @Then("the task {string} should have status {string}")
    public void verifyTaskStatus(String taskName, String expectedStatus) {
        KitchenTask task = manager.getTaskByName(taskName);
        assertNotNull(task, "Task not found.");
        assertEquals(expectedStatus, task.getStatus());
    }

    @Given("the following tasks exist:")
    public void theFollowingTasksExist(io.cucumber.datatable.DataTable table) {
        List<Map<String, String>> rows = table.asMaps();
        for (Map<String, String> row : rows) {
            manager.createTask(row.get("Task"), row.get("Staff"));
            manager.updateTaskStatus(row.get("Task"), row.get("Status"));
        }
    }

    @When("the chef views the task list")
    public void theChefViewsTheTaskList() {
        visibleTasks = manager.getAllTasks();
    }

    @Then("the system should display the tasks with their assigned staff and statuses")
    public void theSystemShouldDisplayTheTasksWithTheirAssignedStaffAndStatuses() {
        assertFalse(visibleTasks.isEmpty(), "No tasks displayed.");
        for (KitchenTask task : visibleTasks) {
            assertNotNull(task.getName(), "Task name is null.");
            assertNotNull(task.getAssignedStaff(), "Assigned staff is null.");
            assertNotNull(task.getStatus(), "Status is null.");
        }
    }
}
