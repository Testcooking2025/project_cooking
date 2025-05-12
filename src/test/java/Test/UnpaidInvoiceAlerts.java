package Test;

import io.cucumber.java.en.*;
import services.InvoiceManager;
import services.InvoiceAlertService;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.datatable.DataTable;

import java.util.*;

public class UnpaidInvoiceAlerts {

    private final InvoiceManager invoiceManager = new InvoiceManager();
    private final InvoiceAlertService alertService = new InvoiceAlertService(invoiceManager);
    private String alertMessage;

    @Given("the current invoice list is:")
    public void theCurrentInvoiceListIs(DataTable table) {
        invoiceManager.clearInvoices();
        for (Map<String, String> row : table.asMaps()) {
            String customer = row.get("Customer");
            double amount = Double.parseDouble(row.get("Amount"));
            String status = row.get("Status");

            invoiceManager.generateInvoice(customer, amount);
            if (!"Unpaid".equalsIgnoreCase(status)) {
                invoiceManager.updateInvoiceStatus(customer, status);
            }
        }
    }

    @And("the system alert threshold is {int}")
    public void theSystemAlertThresholdIs(int threshold) {
        alertService.setAlertThreshold(threshold);
    }

    @When("the system checks unpaid invoices")
    public void theSystemChecksUnpaidInvoices() {
        alertMessage = alertService.checkForUnpaidInvoiceAlerts();
    }

    @Then("the admin should be alerted: {string}")
    public void theAdminShouldBeAlerted(String expectedMessage) {
        assertEquals(expectedMessage, alertMessage);
    }

    @Then("no admin alert should be sent")
    public void noAdminAlertShouldBeSent() {
        assertNull(alertMessage, "Unexpected admin alert: " + alertMessage);
    }
}
