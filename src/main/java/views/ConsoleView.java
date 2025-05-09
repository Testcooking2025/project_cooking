package views;

import java.util.List;
import java.util.Map;

public class ConsoleView {

    public void showMessage(String message) {
        System.out.println("[INFO] " + message);
    }

    public void showError(String error) {
        System.out.println("[ERROR] " + error);
    }

    public void showList(String title, List<String> items) {
        System.out.println("\n== " + title + " ==");
        for (String item : items) {
            System.out.println("- " + item);
        }
    }

    public void showInvoice(String customer, double amount, String status) {
        System.out.println("\n--- Invoice ---");
        System.out.println("Customer: " + customer);
        System.out.println("Amount  : $" + amount);
        System.out.println("Status  : " + status);
    }

    public void showMap(String title, Map<String, Integer> data) {
        System.out.println("\n== " + title + " ==");
        data.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public void separator() {
        System.out.println("---------------------------");
    }
}
