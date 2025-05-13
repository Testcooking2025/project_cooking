package Test;

import io.cucumber.java.en.*;
import models.Invoice;
import views.InvoiceView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceViewTest {

    private final InvoiceView view = new InvoiceView();
    private final List<Invoice> invoices = new ArrayList<>();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public InvoiceViewTest() {

        System.setOut(new PrintStream(outContent));
    }

    @Given("the following invoices are loaded for display:")
    public void givenInvoices(io.cucumber.datatable.DataTable table) {
        invoices.clear();
        for (Map<String, String> row : table.asMaps()) {
            invoices.add(new Invoice(
                    row.get("Customer"),
                    Double.parseDouble(row.get("Amount")),
                    row.get("Status")));
        }
    }

    @Then("the system displays all invoices")
    public void theSystemDisplaysAllInvoices() {
        view.displayInvoices(invoices);

        String output = outContent.toString();

        assertTrue(output.contains("--- Invoices ---"));

        for (Invoice invoice : invoices) {
            String expected = String.format("%s - %.2f - %s",
                    invoice.getCustomer(),
                    invoice.getAmount(),
                    invoice.getStatus());
            assertTrue(output.contains(expected));
        }

        outContent.reset();
    }
}
