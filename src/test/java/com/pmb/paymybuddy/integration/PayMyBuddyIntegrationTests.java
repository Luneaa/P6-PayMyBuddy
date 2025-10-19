package com.pmb.paymybuddy.integration;

import com.pmb.paymybuddy.P6PayMyBuddyApplication;
import com.pmb.paymybuddy.service.interfaces.ILoginService;
import com.pmb.paymybuddy.service.interfaces.ITransactionService;
import com.pmb.paymybuddy.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Transactional()
@SpringBootTest(classes = P6PayMyBuddyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"/test_script.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(value = {"/script.sql"}, executionPhase = AFTER_TEST_CLASS)
class PayMyBuddyIntegrationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private ITransactionService transactionService;

    @Test
    void basicTransaction() {
        // login
        var user = this.loginService.login("john.doe@gmail.com", "Testtest1234!");

        assertTrue(user.isPresent());
        assertEquals("john", user.get().getUsername());

        // add connection

        var userToAdd = this.userService.getUserByUsername("alain");

        assertTrue(userToAdd.isPresent());

        var john = user.get();
        var newConnections = john.getConnections();
        newConnections.add(userToAdd.get());
        john.setConnections(newConnections);
        this.userService.updateUserConnections(john);

        // Get user again to ensure connection is added
        var johnUpdated = this.userService.getUserByUsername("john");

        assertTrue(johnUpdated.isPresent());
        assertEquals(1, johnUpdated.get().getConnections().stream().filter(c -> c.getUsername().equals("alain")).count());

        // send money
        this.transactionService.addTransaction(john, userToAdd.get(), "Test amount", 42);

        var transactionList = this.transactionService.getTransactions(john.getId());
        var newTransaction = transactionList.stream().filter(t -> t.username.equals("alain")).findFirst();
        assertTrue(newTransaction.isPresent());
        assertEquals("alain", newTransaction.get().username);
        assertEquals("-42.0 €", newTransaction.get().amount);
        assertEquals("Test amount", newTransaction.get().description);
    }

    @Test
    void removeUserConnection() {
        // login
        var user = this.loginService.login("john.doe@gmail.com", "Testtest1234!");

        assertTrue(user.isPresent());
        assertEquals("john", user.get().getUsername());

        // remove connection
        var john = user.get();
        var newConnections = john.getConnections();
        assertEquals(1, john.getConnections().size()); // Check that there is 1 connection

        newConnections.removeFirst();                          // Remove the existing connection
        john.setConnections(newConnections);
        this.userService.updateUserConnections(john);

        // get user to check
        var updatedJohn = this.userService.getUserByUsername("john");
        assertTrue(updatedJohn.isPresent());
        assertEquals(0, (long) updatedJohn.get().getConnections().size()); // No connection still present
    }

    @Test
    void updateProfile() {
        // login
        var user = this.loginService.login("john.doe@gmail.com", "Testtest1234!");

        assertTrue(user.isPresent());
        assertEquals("john", user.get().getUsername());

        // update profile
        var john = user.get();
        john.setEmail("john.doe@yahoo.com");
        john.setPassword("NewPassword1234!");
        this.userService.saveUser(john);

        // Check updated values
        var updatedJohn = this.userService.getUserByUsername("john");
        assertTrue(updatedJohn.isPresent());
        assertEquals("john.doe@yahoo.com", updatedJohn.get().getEmail());
    }
}
