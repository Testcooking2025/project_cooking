package Test;

import io.cucumber.java.en.*;
import views.ConsoleView;

import java.util.*;

public class ConsoleViewTest {

    private final ConsoleView console = new ConsoleView();

    @Then("the system shows the message {string}")
    public void theSystemShowsMessage(String msg) {
        console.showMessage(msg);
    }

    @Then("the system shows the following list titled {string}")
    public void theSystemShowsList(String title, io.cucumber.datatable.DataTable table) {
        List<String> items = table.asList();
        console.showList(title, items);
    }

    @Then("the system shows the following map titled {string}")
    public void theSystemShowsMap(String title, io.cucumber.datatable.DataTable table) {
        Map<String, String> map = new HashMap<>();
        for (List<String> row : table.asLists()) {
            map.put(row.get(0), row.get(1));
        }
        console.showMap(title, map);
    }

    @Then("the system prints a separator")
    public void theSystemPrintsASeparator() {
        console.separator();
    }
    @Then("the system shows the following list titled {string}:")
    public void theSystemShowsTheFollowingListTitled(String title, io.cucumber.datatable.DataTable table) {
        List<String> items = table.asList();
        ConsoleView view = new ConsoleView();
        view.showList(title, items);
    }
    @Then("the system shows the following map titled {string}:")
    public void theSystemShowsTheFollowingMapTitled(String title, io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> map = new LinkedHashMap<>();
        for (List<String> row : dataTable.asLists()) {
            map.put(row.get(0), row.get(1));
        }
        ConsoleView view = new ConsoleView();
        view.showMap(title, map);
    }


}
