package views;

import models.KitchenTask;

import java.util.List;

public class TaskBoardView {
    public void displayTasks(List<KitchenTask> tasks) {
        System.out.println("Kitchen Task Board:");
        for (KitchenTask task : tasks) {
            System.out.printf("Task: %s | Assigned to: %s | Status: %s%n",
                    task.getName(), task.getAssignedStaff(), task.getStatus());
        }
        System.out.println("---------------");
    }
}
