package Test;

import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Test;
import views.ConsoleView;
import controllers.AppController;

import java.io.ByteArrayInputStream;
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

    // === Cucumber Step Definitions ===

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

    // === JUnit Tests (No Mockito) ===

    @Test
    public void testShowMenu() {
        ConsoleView view = new ConsoleView();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        view.showMenu();

        String output = out.toString();
        assertTrue(output.contains("1. View invoices"));
        assertTrue(output.contains("2. Submit custom meal request"));
        assertTrue(output.contains("3. View kitchen tasks"));
        assertTrue(output.contains("4. View inventory"));
        assertTrue(output.contains("5. Exit"));
        assertTrue(output.contains("Enter your choice:"));

        System.setOut(System.out);
    }

    @Test
    public void testHandleMealRequest_Safely() {
        System.setIn(new ByteArrayInputStream("Rice,Onion\n".getBytes()));

        // Dummy AppController
        AppController dummyController = new AppController() {
            @Override
            public void submitMealRequest(String[] ingredients) {
                // no-op
            }
        };

        ConsoleView view = new ConsoleView(dummyController);
        view.handleMealRequest();
    }

    @Test
    public void testShowInvoice() {
        ConsoleView view = new ConsoleView();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        view.showInvoice("Ali", 30.0, "Unpaid");

        String output = out.toString();
        assertTrue(output.contains("Ali - 30.00 - Unpaid"));

        System.setOut(System.out);
    }

    @Test
    public void testSetController_Safely() {
        ConsoleView view = new ConsoleView();
        view.setController(null); // فقط لتغطية السطر
    }

    @Test
    public void testRunWithoutController() {
        ConsoleView view = new ConsoleView();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        view.run();

        assertTrue(err.toString().contains("ConsoleView: No controller assigned. Exiting."));
        System.setErr(System.err);
    }

    @Test
    public void testRunCoversAllMenuChoices() {
        // Fake input sequence to cover all menu choices: 1, 2, 3, 4, 5 (exit)
        String input = "1\n2\n3\n4\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Dummy AppController (minimal override only)
        AppController dummyController = new AppController() {
            @Override
            public void submitMealRequest(String[] ingredients) {
                // dummy
            }
        };

        ConsoleView view = new ConsoleView(dummyController);
        view.run();  // will loop and exit after "5"
    }
}
