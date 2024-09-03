package edu.school21.sockets.service;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // Используем JUnit 5
@ContextConfiguration(classes = TestApplicationConfig.class) // Загружаем конфигурацию
@Transactional // Обеспечиваем откат транзакций после тестов
public class UsersServiceImplTest {
    @Autowired
    private UsersService usersService;

    @Test
    public void logInTestCurrent() {
        Optional<User> userOptional = usersService.logIn("bob@example.com", "hashed_password_2");
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals(user.getId(), 2L);
        assertEquals(user.getName(), "Bob Johnson");
        assertEquals(user.getEmail(), "bob@example.com");
    }

    @Test
    public void logInTestError() {
        Optional<User> userOptional = usersService.logIn("bob@example.com", "error_password");
        assertFalse(userOptional.isPresent());

        userOptional = usersService.logIn("error@example.com", "hashed_password_2");
        assertFalse(userOptional.isPresent());

        userOptional = usersService.logIn(null, "hashed_password_2");
        assertFalse(userOptional.isPresent());

        userOptional = usersService.logIn("bob@example.com", null);
        assertFalse(userOptional.isPresent());
    }

    @Test
    public void singUpTestCurrent() {
        Optional<User> userOptional = usersService.signUp("new@example.com", "new User","hashed_password_New");
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        Long userId = user.getId();
        assertNotNull(userId);
        assertEquals(user.getName(), "new User");
        assertEquals(user.getEmail(), "new@example.com");
        assertNotNull(user.getPasswordHash());

        userOptional = usersService.logIn("new@example.com", "hashed_password_New");
        assertTrue(userOptional.isPresent());
        user = userOptional.get();
        assertEquals(user.getId(), userId);
        assertEquals(user.getName(), "new User");
        assertEquals(user.getEmail(), "new@example.com");
        assertNotNull(user.getPasswordHash());

        userOptional = usersService.signUp("new2@example.com", null,"hashed_password_2");
        assertTrue(userOptional.isPresent());

    }

    @Test
    public void singUpTestError() {
        Optional<User> userOptional = usersService.signUp("bob@example.com", "Bob Johnson","hashed_password_2");
        assertFalse(userOptional.isPresent());

        userOptional = usersService.signUp("bob@example.com", "newUser","hashed_password_2");
        assertFalse(userOptional.isPresent());

        userOptional = usersService.signUp(null, "newUser","hashed_password_2");
        assertFalse(userOptional.isPresent());

        userOptional = usersService.signUp("new@example.com", "new User",null);
        assertFalse(userOptional.isPresent());
    }

    @Test
    public void findUserByEmailTestCurrent() {
        Optional<User> userOptional = usersService.findUserByEmail("bob@example.com");
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals(user.getId(), 2L);
        assertEquals(user.getName(), "Bob Johnson");
        assertEquals(user.getEmail(), "bob@example.com");
    }

    @Test
    public void findUserByEmailTestError() {
        Optional<User> userOptional = usersService.findUserByEmail("error@example.com");
        assertFalse(userOptional.isPresent());

        userOptional = usersService.findUserByEmail(null);
        assertFalse(userOptional.isPresent());
    }

    @Test
    public void findUserByIdTestCurrent() {
        Optional<User> userOptional = usersService.findUserById(2L);
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals(user.getId(), 2L);
        assertEquals(user.getName(), "Bob Johnson");
        assertEquals(user.getEmail(), "bob@example.com");
    }

    @Test
    public void findUserByIdTestError() {
        Optional<User> userOptional = usersService.findUserById(404L);
        assertFalse(userOptional.isPresent());

        userOptional = usersService.findUserById(null);
        assertFalse(userOptional.isPresent());
    }
}
