package views;

import controllers.AppController;
import java.util.*;

/**
 * A basic console-based view that interacts with the AppController.
 */
public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private AppController controller;

    // ✅ Constructor بدون controller (للاختبار أو تحميل لاحق)
    public ConsoleView() {
        // Empty
    }

    // ✅ Constructor مع controller (للاستخدام الفعلي)
    public ConsoleView(AppController controller) {
        this.controller = controller;
    }

    public void run() {
        if (controller == null) {
            System.err.println("ConsoleView: No controller assigned. Exiting.");
            return;
        }

        controller.setupDemoData(); // optional: load sample data
        boolean exit = false;
        while (!exit) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> controller.displayAllInvoices();
                case "2" -> handleMealRequest();
                case "3" -> controller.displayAllKitchenTasks();
                case "4" -> controller.displayInventoryStatus();
                case "5" -> exit = true;
                default -> showMessage("Invalid choice. Please try again.");
            }
        }
        showMessage("Thank you. Exiting system.");
    }

    private void showMenu() {
        separator();
        System.out.println("===== Special Cook Console Menu =====");
        System.out.println("1. View invoices");
        System.out.println("2. Submit custom meal request");
        System.out.println("3. View kitchen tasks");
        System.out.println("4. View inventory");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleMealRequest() {
        showMessage("Enter ingredients (comma-separated):");
        String[] ingredients = scanner.nextLine().split(",");
        controller.submitMealRequest(ingredients);
    }

    // ====== Common Console Display Helpers ======

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void separator() {
        System.out.println("\n----------------------------------------");
    }

    public void showList(String title, List<String> items) {
        System.out.println("\n" + title);
        for (String item : items) {
            System.out.println("- " + item);
        }
    }

    public void showMap(String title, Map<?, ?> map) {
        System.out.println("\n" + title);
        for (var entry : map.entrySet()) {
            System.out.printf("%s: %s%n", entry.getKey(), entry.getValue());
        }
    }

    public void showInvoice(String customer, double amount, String status) {
        System.out.printf("%s - %.2f - %s%n", customer, amount, status);
    }


    public void setController(AppController controller) {
        this.controller = controller;
    }
}
