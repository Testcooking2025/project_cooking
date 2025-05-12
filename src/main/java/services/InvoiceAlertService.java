package services;

import java.util.List;

/**
 * Service responsible for alerting administrators when unpaid invoices exceed a defined threshold.
 */
public class InvoiceAlertService {

    private final InvoiceManager invoiceManager;
    private int alertThreshold = 0;

    /**
     * Constructs the alert service with access to the invoice manager.
     *
     * @param invoiceManager The invoice manager to retrieve invoice data from.
     */
    public InvoiceAlertService(InvoiceManager invoiceManager) {
        this.invoiceManager = invoiceManager;
    }

    /**
     * Sets the threshold number of unpaid invoices that will trigger an admin alert.
     *
     * @param threshold The threshold number.
     */
    public void setAlertThreshold(int threshold) {
        this.alertThreshold = threshold;
    }

    /**
     * Checks if the number of unpaid invoices exceeds the threshold.
     * If so, returns an alert message for the administrator.
     *
     * @return Alert message if threshold exceeded, otherwise null.
     */
    public String checkForUnpaidInvoiceAlerts() {
        List<String> unpaid = invoiceManager.getUnpaidCustomerNames();
        int count = unpaid.size();

        if (count > alertThreshold) {
            return count + " unpaid invoices found. Alerting admin!";
        }

        return null;
    }
}
