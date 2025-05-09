package models;

public class User {
    private final String username;
    private final String email;
    private final String password;
    private final String role;

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; } // سيتم تحسين الأمان لاحقًا
    public String getRole() { return role; }
}
