package edu.school21.sockets.repository;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // Используем JUnit 5
@ContextConfiguration(classes = TestApplicationConfig.class) // Загружаем конфигурацию
@Transactional // Обеспечиваем откат транзакций после тестов
public class UsersRepositoryImplTest {
    @Autowired
    private UsersRepository<User> usersRepository;

    @Test
    public void findByIdTest() {
        Optional<User> userOptional = usersRepository.findById(1L);
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();

        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "alice@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_1");
        assertEquals(user.getName(), "Alice Smith");

        Optional<User> userNotFound = usersRepository.findById(404L);
        assertFalse(userNotFound.isPresent());
    }

    @Test
    public void updateTestCurrent() {
        User user = new User();
        user.setId(1L);
        user.setEmail("new EMAIL");
        user.setName("new NAME");
        user.setPasswordHash("new hashed_password");

        usersRepository.update(user);

        Optional<User> userOptional = usersRepository.findById(1L);
        assertTrue(userOptional.isPresent());
        User newUser = userOptional.get();

        assertEquals(newUser.getId(), 1);
        assertEquals(newUser.getEmail(), "new EMAIL");
        assertEquals(newUser.getName(), "new NAME");
        assertEquals(newUser.getPasswordHash(), "new hashed_password");

    }

    @Test
    public void updateTestError() {
        User errorUser = new User();
        errorUser.setId(404L);
        errorUser.setEmail("new EMAIL");
        errorUser.setName("new NAME");
        errorUser.setPasswordHash("new hashed_password");

       usersRepository.update(errorUser);

        Optional<User> userNotFound = usersRepository.findById(404L);
        assertFalse(userNotFound.isPresent());
    }

    @Test
    public void saveTest() {
        User user = new User();
        user.setId(3L);
        user.setEmail("new EMAIL");
        user.setName("new NAME");
        user.setPasswordHash("new hashed_password");

        usersRepository.save(user);

        assertEquals(user.getId(), 4);
        Optional<User> userOptional = usersRepository.findById(4L);
        assertTrue(userOptional.isPresent());

        User newUser = userOptional.get();

        assertEquals(newUser.getId(), 4);
        assertEquals(newUser.getEmail(), "new EMAIL");
        assertEquals(newUser.getName(), "new NAME");
        assertEquals(newUser.getPasswordHash(), "new hashed_password");

    }

    @Test
    public void deleteTestCurrent() {

        usersRepository.delete(2L);

        Optional<User> userNotFound = usersRepository.findById(2L);
        assertFalse(userNotFound.isPresent());
    }

    @Test
    public void findByEmailTest() {
        Optional<User> userOptional = usersRepository.findByEmail("alice@example.com");
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();

        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "alice@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_1");
        assertEquals(user.getName(), "Alice Smith");

        Optional<User> userNotFound = usersRepository.findByEmail("null@null.null");
        assertFalse(userNotFound.isPresent());
    }

    @Test
    public void findAllTest() {
        List<User> userList = usersRepository.findAll();
        assertEquals(userList.size(), 3);

        User first = userList.get(0);
        User second = userList.get(1);
        User third = userList.get(2);

        assertEquals(first.getId(), 1);
        assertEquals(first.getEmail(), "alice@example.com");
        assertEquals(first.getPasswordHash(), "hashed_password_1");
        assertEquals(first.getName(), "Alice Smith");

        assertEquals(second.getId(), 2);
        assertEquals(second.getEmail(), "bob@example.com");
        assertEquals(second.getPasswordHash(), "hashed_password_2");
        assertEquals(second.getName(), "Bob Johnson");

        assertEquals(third.getId(), 3);
        assertEquals(third.getEmail(), "charlie@example.com");
        assertEquals(third.getPasswordHash(), "hashed_password_3");
        assertEquals(third.getName(), "Charlie Davis");
    }

}
