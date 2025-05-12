package Test;

import io.cucumber.java.en.*;
import services.UserService;
import models.User;


import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private final UserService userService = new UserService();
    private boolean signUpResult;
    private boolean signInResult;

    @Given("a user signs up with username {string}, email {string}, password {string}, and role {string}")
    public void userSignsUp(String username, String email, String password, String role) {
        signUpResult = userService.signUp(username, email, password, role);
    }

    @Then("the sign-up should be successful")
    public void theSignUpShouldBeSuccessful() {
        assertTrue(signUpResult, "Sign-up was expected to be successful but failed.");
    }

    @When("the user signs in with email {string} and password {string}")
    public void userSignsIn(String email, String password) {
        signInResult = userService.signIn(email, password);
    }

    @Then("the sign-in should be successful")
    public void theSignInShouldBeSuccessful() {
        assertTrue(signInResult, "Sign-in was expected to be successful but failed.");
    }

    @Then("the system should recognize the user as logged in")
    public void checkLoggedInStatus() {
        assertTrue(userService.isLoggedIn(), "User should be recognized as logged in.");
    }

    @Then("the current user's email should be {string}")
    public void checkCurrentUserEmail(String expectedEmail) {
        User user = userService.getCurrentUser();
        assertNotNull(user, "No user is logged in.");
        assertEquals(expectedEmail, user.getEmail(), "Current user's email mismatch.");
    }

    @When("the user signs out")
    public void userSignsOut() {
        userService.signOut();
    }

    @Then("no user should be logged in")
    public void checkNoUserLoggedIn() {
        assertFalse(userService.isLoggedIn(), "Expected no user to be logged in.");
    }
    @Then("the sign-up should be failed")
    public void theSignUpShouldBeFailed() {
        assertFalse(signUpResult, "Sign-up was expected to fail but it succeeded.");
    }
    @Then("the sign-in should be failed")
    public void theSignInShouldBeFailed() {
        assertFalse(signInResult, "Sign-in was expected to fail but it succeeded.");
    }
}
