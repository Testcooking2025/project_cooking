package models;

/**
 * Represents a kitchen task assigned to a staff member,
 * with a status indicating the progress of the task.
 */
public class KitchenTask {

    private final String name;
    private final String assignedStaff;
    private String status; // "Pending", "In Progress", "Completed"

    /**
     * Constructs a new kitchen task assigned to a specific staff member.
     * The task starts with the status "Pending".
     *
     * @param name          The name or description of the task (e.g., "Prepare Salad").
     * @param assignedStaff The name of the staff member assigned to this task.
     */
    public KitchenTask(String name, String assignedStaff) {
        this.name = name;
        this.assignedStaff = assignedStaff;
        this.status = "Pending";
    }

    /**
     * Returns the name of the task.
     *
     * @return The task name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the assigned staff member.
     *
     * @return The staff member's name.
     */
    public String getAssignedStaff() {
        return assignedStaff;
    }

    /**
     * Returns the current status of the task.
     *
     * @return The task status ("Pending", "In Progress", or "Completed").
     */
    public String getStatus() {
        return status;
    }

    /**
     * Starts the task by changing its status from "Pending" to "In Progress".
     * Does nothing if the task is already in progress or completed.
     */
    public void startTask() {
        if (status.equals("Pending")) {
            status = "In Progress";
        }
    }

    /**
     * Completes the task by changing its status from "In Progress" to "Completed".
     * Does nothing if the task is not currently in progress.
     */
    public void completeTask() {
        if (status.equals("In Progress")) {
            status = "Completed";
        }
    }
}
