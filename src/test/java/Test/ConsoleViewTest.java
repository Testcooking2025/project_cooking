package Test;

import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Test;
import views.ConsoleView;
import controllers.AppController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        expected.add(""); 
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

    @Test
    public void testRunWithoutControllerPrintsError() {
        ConsoleView view = new ConsoleView(); // controller = null
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        view.run();

        assertTrue(errContent.toString().contains("ConsoleView: No controller assigned. Exiting."));
        System.setErr(System.err); // restore
    }

    @Test
    public void testShowMenuDisplaysOptions() {
        ConsoleView view = new ConsoleView();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        view.showMenu();

        String output = outContent.toString();
        assertTrue(output.contains("===== Special Cook Console Menu ====="));
        assertTrue(output.contains("1. View invoices"));
        assertTrue(output.contains("2. Submit custom meal request"));
        assertTrue(output.contains("3. View kitchen tasks"));
        assertTrue(output.contains("4. View inventory"));
        assertTrue(output.contains("5. Exit"));
        assertTrue(output.contains("Enter your choice:"));

        System.setOut(System.out); // restore
    }

    @Test
    public void testHandleMealRequest() {
        String input = "Rice,Onion\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        AppController mockController = mock(AppController.class);
        ConsoleView view = new ConsoleView(mockController);
        view.handleMealRequest();

        verify(mockController).submitMealRequest(new String[]{"Rice", "Onion"});
    }
}
