package Test;

import io.cucumber.java.en.*;
import models.Invoice;
import services.InvoiceManager;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceProcessingTest {

    private final InvoiceManager invoiceManager = new InvoiceManager();
    private List<String> unpaidCustomers;
    private Invoice latestInvoice;

    // Scenario 1: Generate invoice after placing order
    @Given("a customer named {string} places an order for {string} costing {double}")
    public void customerPlacesOrder(String customer, String item, double cost) {
        invoiceManager.createInvoice(customer, cost);
        latestInvoice = invoiceManager.getInvoiceByCustomer(customer);
    }

    @Then("the system should generate an invoice with amount {double} and status {string}")
    public void verifyGeneratedInvoice(double amount, String status) {
        assertNotNull(latestInvoice, "Invoice not found.");
        assertEquals(amount, latestInvoice.getAmount(), 0.001);
        assertEquals(status, latestInvoice.getStatus());
    }

    // Scenario 2: Customer pays the invoice
    @Given("an invoice for {string} with amount {double} and status {string}")
    public void createInvoiceForCustomer(String customer, double amount, String status) {
        invoiceManager.createInvoice(customer, amount);
        invoiceManager.updateInvoiceStatus(customer, status);
    }

    @When("{string} pays the invoice")
    public void customerPaysInvoice(String customer) {
        invoiceManager.markAsPaid(customer);
    }

    @Then("the invoice for {string} should have status {string}")
    public void verifyInvoiceStatus(String customer, String expectedStatus) {
        Invoice invoice = invoiceManager.getInvoiceByCustomer(customer);
        assertNotNull(invoice, "Invoice not found for customer: " + customer);
        assertEquals(expectedStatus, invoice.getStatus(), "Invoice status mismatch");
    }

    // Scenario 3: System lists unpaid invoices
    @Given("the following invoices exist:")
    public void theFollowingInvoicesExist(io.cucumber.datatable.DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            String customer = row.get("Customer");
            double amount = Double.parseDouble(row.get("Amount"));
            String status = row.get("Status");

            invoiceManager.createInvoice(customer, amount);
            invoiceManager.updateInvoiceStatus(customer, status);
        }
    }

    @When("the system lists unpaid invoices")
    public void theSystemListsUnpaidInvoices() {
        unpaidCustomers = invoiceManager.getUnpaidCustomerNames();
    }

    @Then("it should show the following customers:")
    public void itShouldShowTheFollowingCustomers(io.cucumber.datatable.DataTable dataTable) {
        List<String> expected = new ArrayList<>(dataTable.asList());
        List<String> actual = new ArrayList<>(unpaidCustomers);
        Collections.sort(expected);
        Collections.sort(actual);
        assertEquals(expected, actual, "Mismatch in unpaid invoice customer list (ignoring order).");
    }
}
