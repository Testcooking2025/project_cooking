package org.example;

import controllers.AppController;
import models.User;
import services.NotificationService;
import services.UserService;
import views.ConsoleView;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final ConsoleView console = new ConsoleView();
    private static final AppController controller = new AppController();
    private static final NotificationService emailService = new NotificationService();

    public static void main(String[] args) {
        console.showMessage("=== Welcome to Special Cook Console System ===\n");
        controller.setupDemoData();

        while (true) {
            console.showMessage("1) Sign Up\n2) Sign In\n0) Exit");
            System.out.print("Select option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleSignup();
                case "2" -> {
                    if (handleLogin()) {
                        runUserSession();
                    }
                }
                case "0" -> {
                    console.showMessage("Exiting system. Goodbye!");
                    return;
                }
                default -> showError("Invalid option. Try again.");
            }
        }
    }

    private static void handleSignup() {
        console.separator();
        console.showMessage("--- Sign Up ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Role (user/chef/admin): ");
        String role = scanner.nextLine();

        boolean success = userService.signUp(username, email, password, role);
        if (success)
            console.showMessage("✔ Successfully registered!");
        else
            showError("✖ Email already registered.");
    }

    private static boolean handleLogin() {
        console.separator();
        console.showMessage("--- Sign In ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean success = userService.signIn(email, password);
        if (success)
            console.showMessage("✔ Logged in successfully!\n");
        else
            showError("✖ Invalid credentials.\n");

        return success;
    }

    private static void runUserSession() {
        User user = userService.getCurrentUser();

        while (true) {
            console.separator();
            console.showMessage("=== Main Menu (" + user.getRole().toUpperCase() + ") ===");

            switch (user.getRole().toLowerCase()) {
                case "user" -> handleUserActions(user);
                case "chef" -> handleChefActions();
                case "admin" -> handleAdminActions();
                default -> {
                    showError("Unknown role: " + user.getRole());
                    return;
                }
            }
            console.separator();
        }
    }

    private static void handleUserActions(User user) {
        console.showMessage("1) View filtered meals\n2) View invoices\n3) Generate new invoice\n0) Logout");
        System.out.print("Choice: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1" -> console.showList("Filtered Meals",
                    controller.getCustomerProfileManager().getFilteredMeals(user.getUsername()));
            case "2" -> controller.getInvoiceManager().getAllInvoices().forEach(inv ->
                    console.showInvoice(inv.getCustomer(), inv.getAmount(), inv.getStatus()));
            case "3" -> {
                System.out.print("Enter invoice amount: ");
                double amount = Double.parseDouble(scanner.nextLine());
                controller.getInvoiceManager().createInvoice(user.getUsername(), amount);
                console.showMessage("✔ Invoice generated successfully.");
            }
            case "0" -> {
                userService.signOut();
                return;
            }
            default -> showError("Invalid choice.");
        }
    }

    private static void handleChefActions() {
        console.showMessage("1) View tasks\n2) View inventory snapshot\n0) Logout");
        System.out.print("Choice: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1" -> controller.getKitchenTaskManager().getAllTasks().forEach(task ->
                    console.showMessage("Task: " + task.getName() +
                            " | Assigned to: " + task.getAssignedStaff() +
                            " | Status: " + task.getStatus()));
            case "2" -> console.showMap("Inventory Snapshot",
                    controller.getInventoryManager().getInventorySnapshot());
            case "0" -> {
                userService.signOut();
                return;
            }
            default -> showError("Invalid choice.");
        }
    }

    private static void handleAdminActions() {
        console.showMessage("1) View order statistics\n2) View restock alerts\n3) Send restock alert email\n4) View all invoices\n5) View financial summary\n0) Logout");
        System.out.print("Choice: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1" -> console.showMap("Order Statistics",
                    controller.getOrderHistoryManager().getOrderStatistics());
            case "2" -> {
                boolean sent = controller.getSupplierNotificationService().getManualRequestIngredients().contains("Onion");
                console.showMessage("Manual request for Onion sent? " + (sent ? "Yes" : "No"));
            }
            case "3" -> emailService.sendEmail(
                    "jomaamro97@gmail.com",
                    "Restock Alert",
                    "Manual restock request for Onion was triggered by the system.");
            case "4" -> controller.getInvoiceManager().getAllInvoices().forEach(inv ->
                    console.showInvoice(inv.getCustomer(), inv.getAmount(), inv.getStatus()));
            case "5" -> {
                Map<String, Object> summary = controller.getInvoiceManager().getFinancialSummary();
                console.showMessage("=== Financial Summary ===");
                console.showMessage("Total Invoices: " + summary.get("totalInvoices"));
                console.showMessage("Paid Invoices: " + summary.get("paidInvoices"));
                console.showMessage("Unpaid Invoices: " + summary.get("unpaidInvoices"));
                console.showMessage("Total Amount: $" + summary.get("totalAmount"));
            }
            case "0" -> {
                userService.signOut();
                return;
            }
            default -> showError("Invalid choice.");
        }
    }

    private static void showError(String msg) {
        System.out.println("ERROR: " + msg);
    }
}
