package Test;

import io.cucumber.java.en.Then;
import views.ConsoleView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleViewTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ConsoleView console = new ConsoleView();

    public ConsoleViewTest() {
        System.setOut(new PrintStream(outContent));
    }

    @Then("the system shows the message {string}")
    public void theSystemShowsMessage(String msg) {
        console.showMessage(msg);
        assertTrue(outContent.toString().contains(msg));
        outContent.reset();
    }

    @Then("the system shows the following list titled {string}:")
    public void theSystemShowsTheFollowingListTitled(String title, io.cucumber.datatable.DataTable table) {
        List<String> items = table.asList();
        console.showList(title, items);

        List<String> expected = new ArrayList<>();
        expected.add(""); // للسطر الفارغ الأول
        expected.add(title);
        for (String item : items) {
            expected.add("- " + item);
        }

        List<String> actual = Arrays.asList(outContent.toString().split("\\r?\\n"));
        assertLinesMatch(expected, actual);
        outContent.reset();
    }

    @Then("the system shows the following map titled {string}:")
    public void theSystemShowsTheFollowingMapTitled(String title, io.cucumber.datatable.DataTable table) {
        Map<String, String> map = new LinkedHashMap<>();
        for (List<String> row : table.asLists()) {
            map.put(row.get(0), row.get(1));
        }
        console.showMap(title, map);

        String output = outContent.toString();
        assertTrue(output.contains(title));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            assertTrue(output.contains(entry.getKey() + ": " + entry.getValue()));
        }
        outContent.reset();
    }

    @Then("the system prints a separator")
    public void theSystemPrintsASeparator() {
        console.separator();
        assertTrue(outContent.toString().contains("----------------------------------------"));
        outContent.reset();
    }
}
