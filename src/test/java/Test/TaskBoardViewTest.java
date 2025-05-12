package Test;

import io.cucumber.java.en.*;
import models.KitchenTask;
import views.TaskBoardView;

import java.util.*;

public class TaskBoardViewTest {

    private final TaskBoardView view = new TaskBoardView();
    private final List<KitchenTask> tasks = new ArrayList<>();

    @Given("the following kitchen tasks exist:")
    public void theFollowingKitchenTasksExist(io.cucumber.datatable.DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            tasks.add(new KitchenTask(
                    row.get("Task"),
                    row.get("Staff"),
                    row.get("Status")
            ));
        }
    }

    @Then("the system displays the kitchen task board")
    public void theSystemDisplaysTheKitchenTaskBoard() {
        view.displayTasks(tasks);
    }
}
