package services;

import java.util.*;

/**
 * Service responsible for checking if the number of unpaid invoices
 * exceeds a given threshold and triggering an alert if necessary.
 */
public class InvoiceAlertService {

    /**
     * Checks if the number of unpaid invoices exceeds the specified threshold.
     *
     * @param invoiceData A list of invoice entries, where each entry is a map
     *                    containing at least the key "Status" (e.g., "Paid", "Unpaid").
     * @param threshold   The maximum number of unpaid invoices allowed before triggering an alert.
     * @return An alert message if unpaid invoices exceed the threshold, or null if within limits.
     */
    public String checkUnpaidInvoiceAlert(List<Map<String, String>> invoiceData, int threshold) {
        long unpaidCount = invoiceData.stream()
                .filter(row -> "Unpaid".equalsIgnoreCase(row.get("Status")))
                .count();

        if (unpaidCount > threshold) {
            return unpaidCount + " unpaid invoices found. Alerting admin!";
        }

        return null;
    }
}
