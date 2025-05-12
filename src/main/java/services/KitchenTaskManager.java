package services;

import models.KitchenTask;

import java.util.*;

/**
 * Manages kitchen tasks and their statuses.
 */
public class KitchenTaskManager {

    private final List<KitchenTask> tasks = new ArrayList<>();

    /**
     * Creates a new kitchen task and assigns it to a staff member.
     *
     * @param name  Name of the task.
     * @param staff Name of the assigned staff member.
     */
    public void createTask(String name, String staff) {
        tasks.add(new KitchenTask(name, staff));
    }

    /**
     * Returns a list of all kitchen tasks.
     *
     * @return List of KitchenTask objects.
     */
    public List<KitchenTask> getAllTasks() {
        return tasks;
    }

    /**
     * Retrieves a task by its name.
     *
     * @param name Name of the task.
     * @return KitchenTask object or null if not found.
     */
    public KitchenTask getTaskByName(String name) {
        for (KitchenTask task : tasks) {
            if (task.getName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    /**
     * Updates the status of a specific task.
     *
     * @param name   Name of the task.
     * @param status New status to apply.
     */
    public void updateTaskStatus(String name, String status) {
        KitchenTask task = getTaskByName(name);
        if (task != null) {
            task.setStatus(status);
        }
    }

    /**
     * Marks the first "Pending" task for a staff member as "In Progress".
     *
     * @param staff Staff name.
     */
    public void startTaskByStaff(String staff) {
        for (KitchenTask task : tasks) {
            if (task.getAssignedStaff().equals(staff) && task.getStatus().equals("Pending")) {
                task.setStatus("In Progress");
                return;
            }
        }
    }

    /**
     * Marks the first "In Progress" task for a staff member as "Completed".
     *
     * @param staff Staff name.
     */
    public void completeTaskByStaff(String staff) {
        for (KitchenTask task : tasks) {
            if (task.getAssignedStaff().equals(staff) && task.getStatus().equals("In Progress")) {
                task.setStatus("Completed");
                return;
            }
        }
    }
}
