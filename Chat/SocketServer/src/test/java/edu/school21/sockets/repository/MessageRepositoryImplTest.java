package edu.school21.sockets.repository;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // Используем JUnit 5
@ContextConfiguration(classes = TestApplicationConfig.class) // Загружаем конфигурацию
@Transactional // Обеспечиваем откат транзакций после тестов
public class MessageRepositoryImplTest {
    @Autowired
    private MessageRepository<Message> messageRepository;

    @Test
    public void findByIdTest() {
        Optional<Message> messageOptional = messageRepository.findById(1L);
        assertTrue(messageOptional.isPresent());

        Message message = messageOptional.get();
        User user = message.getUser();
        ChatRoom chatRoom = message.getRoom();

        assertEquals(message.getId(), 1L);
        assertEquals(message.getContent(), "Hello everyone!");
        assertNotNull(message.getCreatedAt());

        assertEquals(chatRoom.getId(), 1);
        assertNull(chatRoom.getCreator().getId());
        assertEquals(chatRoom.getName(), "General Chat");
        assertEquals(chatRoom.getDescription(), "A room for general discussions");
        assertNotNull(chatRoom.getCreatedAt());

        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "alice@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_1");
        assertEquals(user.getName(), "Alice Smith");

        Optional<Message> messageNotFound = messageRepository.findById(404L);
        assertFalse(messageNotFound.isPresent());
    }

    @Test
    public void updateTest() {
        Optional<Message> messageOptional = messageRepository.findById(5L);
        assertTrue(messageOptional.isPresent());
        Message message = messageOptional.get();
        User user = message.getUser();
        user.setId(1L);
        ChatRoom chatRoom = message.getRoom();
        chatRoom.setId(1L);

        messageRepository.update(message);

        messageOptional = messageRepository.findById(1L);
        assertTrue(messageOptional.isPresent());

        message = messageOptional.get();
        user = message.getUser();
        chatRoom = message.getRoom();

        assertEquals(message.getId(), 1L);
        assertEquals(message.getContent(), "Hello everyone!");
        assertNotNull(message.getCreatedAt());

        assertEquals(chatRoom.getId(), 1);
        assertNull(chatRoom.getCreator().getId());
        assertEquals(chatRoom.getName(), "General Chat");
        assertEquals(chatRoom.getDescription(), "A room for general discussions");
        assertNotNull(chatRoom.getCreatedAt());

        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "alice@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_1");
        assertEquals(user.getName(), "Alice Smith");
    }

    @Test
    public void saveTest() {
        Message message = new Message();
        message.setId(21L);
        message.setContent("HI!!!");
        message.getUser().setId(1L);
        message.getRoom().setId(1L);

        messageRepository.save(message);
        assertEquals(message.getId(), 7L);

        Optional<Message> messageOptional = messageRepository.findById(7L);
        assertTrue(messageOptional.isPresent());

        message = messageOptional.get();
        User user = message.getUser();
        ChatRoom chatRoom = message.getRoom();

        assertEquals(message.getId(), 7L);
        assertEquals(message.getContent(), "HI!!!");
        assertNotNull(message.getCreatedAt());

        assertEquals(chatRoom.getId(), 1);
        assertNull(chatRoom.getCreator().getId());
        assertEquals(chatRoom.getName(), "General Chat");
        assertEquals(chatRoom.getDescription(), "A room for general discussions");
        assertNotNull(chatRoom.getCreatedAt());

        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "alice@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_1");
        assertEquals(user.getName(), "Alice Smith");
    }

    @Test
    public void deleteTest() {
        messageRepository.delete(6L);
        Optional<Message> messageNotFound = messageRepository.findById(6L);
        assertFalse(messageNotFound.isPresent());

        assertDoesNotThrow(() -> {messageRepository.delete(404L);});
    }

    @Test
    public void findAllTest() {
        List<Message> messageList = messageRepository.findAll();
        assertEquals(messageList.size(), 6);

        Message message = messageList.get(0);
        User user = message.getUser();
        ChatRoom chatRoom = message.getRoom();

        assertEquals(chatRoom.getId(), 1);
        assertNull(chatRoom.getCreator().getId());
        assertEquals(chatRoom.getName(), "General Chat");
        assertEquals(chatRoom.getDescription(), "A room for general discussions");
        assertNotNull(chatRoom.getCreatedAt());

        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "alice@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_1");
        assertEquals(user.getName(), "Alice Smith");

        message = messageList.get(5);
        user = message.getUser();
        chatRoom = message.getRoom();

        assertEquals(message.getId(), 6L);
        assertEquals(message.getContent(), "What s up?");
        assertNotNull(message.getCreatedAt());

        assertEquals(chatRoom.getId(), 3);
        assertNull(chatRoom.getCreator().getId());
        assertEquals(chatRoom.getName(), "Random");
        assertEquals(chatRoom.getDescription(), "A room for random conversations");
        assertNotNull(chatRoom.getCreatedAt());

        assertEquals(user.getId(), 3);
        assertEquals(user.getEmail(), "charlie@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_3");
        assertEquals(user.getName(), "Charlie Davis");
    }

    @Test
    public void getMessagesByRoomTest1() {
        List<Message> messageList = messageRepository.getMessagesByRoom(1L, 1, 1);
        assertEquals(messageList.size(), 1);

        Message message = messageList.get(0);
        User user = message.getUser();
        ChatRoom chatRoom = message.getRoom();

        assertEquals(message.getId(), 1L);
        assertEquals(message.getContent(), "Hello everyone!");
        assertNotNull(message.getCreatedAt());

        assertEquals(chatRoom.getId(), 1);
        assertNull(chatRoom.getCreator().getId());
        assertEquals(chatRoom.getName(), "General Chat");
        assertEquals(chatRoom.getDescription(), "A room for general discussions");
        assertNotNull(chatRoom.getCreatedAt());

        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "alice@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_1");
        assertEquals(user.getName(), "Alice Smith");
    }

    @Test
    public void getMessagesByRoomTest2() {
        List<Message> messageList = messageRepository.getMessagesByRoom(1L, 2, 2);
        assertEquals(messageList.size(), 1);

        Message message = messageList.get(0);
        User user = message.getUser();
        ChatRoom chatRoom = message.getRoom();

        assertEquals(message.getId(), 3);
        assertEquals(message.getContent(), "Hey folks!");
        assertNotNull(message.getCreatedAt());

        assertEquals(chatRoom.getId(), 1);
        assertNull(chatRoom.getCreator().getId());
        assertEquals(chatRoom.getName(), "General Chat");
        assertEquals(chatRoom.getDescription(), "A room for general discussions");
        assertNotNull(chatRoom.getCreatedAt());

        assertEquals(user.getId(), 3);
        assertEquals(user.getEmail(), "charlie@example.com");
        assertEquals(user.getPasswordHash(), "hashed_password_3");
        assertEquals(user.getName(), "Charlie Davis");
    }

    @Test
    public void getMessagesByRoomTest3() {
        List<Message> messageList = messageRepository.getMessagesByRoom(1L, 1, 6);
        assertEquals(messageList.size(), 3);

        assertEquals(messageList.get(0).getId(), 1);
        assertEquals(messageList.get(1).getId(), 2);
        assertEquals(messageList.get(2).getId(), 3);

    }

}
