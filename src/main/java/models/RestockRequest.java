package models;

import java.time.LocalDateTime;

/**
 * Represents a manual restock request for an ingredient.
 */
public class RestockRequest {

    private final String ingredient;
    private final LocalDateTime requestTime;

    public RestockRequest(String ingredient) {
        this.ingredient = ingredient;
        this.requestTime = LocalDateTime.now();
    }

    public String getIngredient() {
        return ingredient;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    @Override
    public String toString() {
        return "Restock requested for: " + ingredient + " at " + requestTime;
    }
}
