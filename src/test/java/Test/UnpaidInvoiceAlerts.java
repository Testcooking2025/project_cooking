package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class UnpaidInvoiceAlerts {

    static class Invoice {
        String customer;
        double amount;
        String status;

        Invoice(String customer, double amount, String status) {
            this.customer = customer;
            this.amount = amount;
            this.status = status;
        }
    }

    private final List<Invoice> invoices = new ArrayList<>();
    private int alertThreshold = 0;
    private String adminAlertMessage = null;

    @Given("the current invoice list is:")
    public void theCurrentInvoiceListIs(io.cucumber.datatable.DataTable table) {
        invoices.clear();
        for (Map<String, String> row : table.asMaps()) {
            invoices.add(new Invoice(
                    row.get("Customer"),
                    Double.parseDouble(row.get("Amount")),
                    row.get("Status")
            ));
        }
    }

    @And("the system alert threshold is {int}")
    public void theSystemAlertThresholdIs(int threshold) {
        this.alertThreshold = threshold;
    }

    @When("the system checks unpaid invoices")
    public void theSystemChecksUnpaidInvoices() {
        long count = invoices.stream()
                .filter(i -> i.status.equalsIgnoreCase("Unpaid"))
                .count();

        if (count > alertThreshold) {
            adminAlertMessage = count + " unpaid invoices found. Alerting admin!";
        } else {
            adminAlertMessage = null;
        }
    }

    @Then("the admin should be alerted: {string}")
    public void theAdminShouldBeAlerted(String expectedMessage) {
        assertEquals(expectedMessage, adminAlertMessage);
    }

    @Then("no admin alert should be sent")
    public void noAdminAlertShouldBeSent() {
        assertNull(adminAlertMessage, "Admin was alerted when it shouldn't have been.");
    }
}
