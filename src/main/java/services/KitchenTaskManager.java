package services;

import models.KitchenTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for managing kitchen tasks including assignment, status updates,
 * and task retrieval.
 */
public class KitchenTaskManager {

    private final List<KitchenTask> taskList = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(KitchenTaskManager.class.getName());

    /**
     * Creates a new kitchen task and assigns it to the specified staff member.
     *
     * @param taskName  The name of the task (e.g., "Prepare Salad").
     * @param staffName The staff member assigned to the task.
     */
    public void createTask(String taskName, String staffName) {
        taskList.add(new KitchenTask(taskName, staffName));
    }

    /**
     * Marks the first pending task assigned to the given staff member as "In Progress".
     *
     * @param staffName The name of the staff member starting the task.
     */
    public void startTask(String staffName) {
        for (KitchenTask task : taskList) {
            if (task.getAssignedStaff().equals(staffName) && task.getStatus().equals("Pending")) {
                task.startTask();
            }
        }
    }

    /**
     * Marks the first in-progress task assigned to the given staff member as "Completed".
     *
     * @param staffName The name of the staff member completing the task.
     */
    public void completeTask(String staffName) {
        for (KitchenTask task : taskList) {
            if (task.getAssignedStaff().equals(staffName) && task.getStatus().equals("In Progress")) {
                task.completeTask();
            }
        }
    }

    /**
     * Retrieves all kitchen tasks managed by the system.
     *
     * @return A list of {@link KitchenTask} objects.
     */
    public List<KitchenTask> getAllTasks() {
        return taskList;
    }

    /**
     * Finds a kitchen task by its name.
     *
     * @param taskName The name of the task to find.
     * @return An {@link Optional} containing the task if found.
     */
    public Optional<KitchenTask> findTaskByName(String taskName) {
        return taskList.stream()
                .filter(t -> t.getName().equals(taskName))
                .findFirst();
    }

    /**
     * Loads tasks from a structured table format, optionally overriding their status.
     *
     * @param tableData A list of rows, each containing:
     *                  - Task name
     *                  - Assigned staff
     *                  - Optional status ("Pending", "In Progress", "Completed")
     */
    public void loadTasksFromTable(List<List<String>> tableData) {
        taskList.clear();
        for (List<String> row : tableData) {
            try {
                KitchenTask task = new KitchenTask(row.get(0), row.get(1));
                if (row.size() > 2) {
                    String status = row.get(2);
                    if (status.equals("In Progress")) task.startTask();
                    if (status.equals("Completed")) {
                        task.startTask();
                        task.completeTask();
                    }
                }
                taskList.add(task);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to load task with status override", e);
            }
        }
    }
}
