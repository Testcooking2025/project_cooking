package Test;

import io.cucumber.java.en.*;
import models.Invoice;
import views.InvoiceView;

import java.util.*;

public class InvoiceViewTest {

    private final InvoiceView view = new InvoiceView();
    private final List<Invoice> invoices = new ArrayList<>();

    @Given("the following invoices are loaded for display:")
    public void givenInvoices(io.cucumber.datatable.DataTable table) {
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
    }
}
