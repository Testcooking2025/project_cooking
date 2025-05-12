package Test;

import io.cucumber.java.en.*;
import models.InventoryItem;
import views.InventoryView;

import java.util.*;

public class InventoryViewTest {

    private final InventoryView view = new InventoryView();
    private final List<InventoryItem> items = new ArrayList<>();

    @Given("the following inventory items exist:")
    public void theFollowingInventoryItemsExist(io.cucumber.datatable.DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            items.add(new InventoryItem(
                    row.get("Name"),
                    Integer.parseInt(row.get("Quantity"))
            ));
        }
    }

    @Then("the system displays the inventory list")
    public void theSystemDisplaysTheInventoryList() {
        view.displayInventory(items);
    }
}
