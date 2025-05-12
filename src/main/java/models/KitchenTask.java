package models;

public class KitchenTask {

    private final String name;
    private final String assignedStaff;
    private String status;

    public KitchenTask(String name, String assignedStaff) {
        this.name = name;
        this.assignedStaff = assignedStaff;
        this.status = "Pending";
    }

    // ✅ Constructor المطلوب لتغطية test
    public KitchenTask(String name, String assignedStaff, String status) {
        this.name = name;
        this.assignedStaff = assignedStaff;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getAssignedStaff() {
        return assignedStaff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
