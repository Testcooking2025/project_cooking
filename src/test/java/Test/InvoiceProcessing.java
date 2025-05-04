package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class InvoiceProcessing {


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

    private final List<Invoice> invoiceList = new ArrayList<>();
    private String currentCustomer;

    // ========== Scenario 1: Generate invoice ==========
    @Given("a customer named {string} places an order for {string} costing {double}")
    public void customerPlacesOrder(String customerName, String meal, double cost) {
        currentCustomer = customerName;
        invoiceList.add(new Invoice(customerName, cost, "Unpaid"));
    }

    @Then("the system should generate an invoice with amount {double} and status {string}")
    public void systemGeneratesInvoice(double expectedAmount, String expectedStatus) {
        Optional<Invoice> invoice = invoiceList.stream()
                .filter(i -> i.customer.equals(currentCustomer))
                .findFirst();

        assertTrue(invoice.isPresent(), "Invoice not found for customer.");
        assertEquals(expectedAmount, invoice.get().amount);
        assertEquals(expectedStatus, invoice.get().status);
    }

    // ========== Scenario 2: Pay invoice ==========
    @Given("an invoice for {string} with amount {double} and status {string}")
    public void invoiceExists(String customer, double amount, String status) {
        invoiceList.add(new Invoice(customer, amount, status));
    }

    @When("{string} pays the invoice")
    public void customerPaysInvoice(String customer) {
        for (Invoice i : invoiceList) {
            if (i.customer.equals(customer) && i.status.equals("Unpaid")) {
                i.status = "Paid";
            }
        }
    }

    @Then("the invoice for {string} should have status {string}")
    public void verifyInvoiceStatus(String customer, String expectedStatus) {
        Optional<Invoice> invoice = invoiceList.stream()
                .filter(i -> i.customer.equals(customer))
                .findFirst();

        assertTrue(invoice.isPresent(), "Invoice not found.");
        assertEquals(expectedStatus, invoice.get().status);
    }

    // ========== Scenario 3: List unpaid invoices ==========
    @Given("the following invoices exist:")
    public void theFollowingInvoicesExist(io.cucumber.datatable.DataTable table) {
        invoiceList.clear();
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            invoiceList.add(new Invoice(
                    row.get("Customer"),
                    Double.parseDouble(row.get("Amount")),
                    row.get("Status")
            ));
        }
    }

    List<String> unpaidCustomers = new ArrayList<>();

    @When("the system lists unpaid invoices")
    public void systemListsUnpaidInvoices() {
        unpaidCustomers.clear();
        for (Invoice i : invoiceList) {
            if (i.status.equalsIgnoreCase("Unpaid")) {
                unpaidCustomers.add(i.customer);
            }
        }
    }

    @Then("it should show the following customers:")
    public void verifyUnpaidCustomerList(io.cucumber.datatable.DataTable table) {
        List<String> expected = table.asList();
        assertEquals(expected, unpaidCustomers);
    }
}
