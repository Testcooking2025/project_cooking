package services;

import models.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private final Map<String, User> userStore = new HashMap<>();
    private User currentUser = null;

    public boolean signUp(String username, String email, String password, String role) {
        if (userStore.containsKey(email)) return false;
        userStore.put(email, new User(username, email, password, role));
        return true;
    }

    public boolean signIn(String email, String password) {
        User user = userStore.get(email);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void signOut() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
