package services;

import models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Service to manage user authentication and session handling.
 */
public class UserService {

    private final Map<String, User> userStore = new HashMap<>();
    private User currentUser = null;

    /**
     * Registers a new user if the email does not already exist.
     *
     * @param username User's display name
     * @param email    Unique email
     * @param password Account password
     * @param role     User role (admin, customer, etc.)
     * @return true if registration is successful, false if email is taken
     */
    public boolean signUp(String username, String email, String password, String role) {
        if (userStore.containsKey(email)) return false;
        userStore.put(email, new User(username, email, password, role));
        return true;
    }

    /**
     * Authenticates a user by email and password.
     *
     * @param email    User's email
     * @param password Provided password
     * @return true if authenticated successfully
     */
    public boolean signIn(String email, String password) {
        User user = userStore.get(email);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    /**
     * Returns the currently logged-in user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logs out the current user.
     */
    public void signOut() {
        currentUser = null;
    }

    /**
     * Checks if a user is currently logged in.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Clears all registered users (for testing purposes).
     */
    public void clearUsers() {
        userStore.clear();
        currentUser = null;
    }
}
