package edu.school21.sockets.repository;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatRoomsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class) // Используем JUnit 5
@ContextConfiguration(classes = TestApplicationConfig.class) // Загружаем конфигурацию
@Transactional // Обеспечиваем откат транзакций после тестов
public class ChatRoomsRepositoryImplTest {

    @Autowired
    private ChatRoomsRepository<ChatRoom> chatRoomsRepository;

    @Test
    public void getAllConnectedUserTest() {
        List<User> connectedUser = chatRoomsRepository.getAllConnectedUser(1L);
        User first = connectedUser.get(2);
        User second = connectedUser.get(0);
        User third = connectedUser.get(1);

        assertEquals(3, connectedUser.size());

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

    @Test
    public void findUserChatRoomsTestCurrentUserId() {
        Long currUserID = 1L;
        List<ChatRoom> chatRooms = chatRoomsRepository.findUserChatRooms(currUserID);
        assertEquals(chatRooms.size(), 2);

        ChatRoom firstChatRoom = chatRooms.get(0);
        assertEquals(firstChatRoom.getId(), 1);
        assertEquals(firstChatRoom.getCreator().getId(), 1);
        assertEquals(firstChatRoom.getCreator().getEmail(), "alice@example.com");
        assertEquals(firstChatRoom.getCreator().getPasswordHash(), "hashed_password_1");
        assertEquals(firstChatRoom.getCreator().getName(), "Alice Smith");
        assertEquals(firstChatRoom.getName(), "General Chat");
        assertEquals(firstChatRoom.getDescription(), "A room for general discussions");
        assertNotNull(firstChatRoom.getCreatedAt());

        ChatRoom secondChatRoom = chatRooms.get(1);
        assertEquals(secondChatRoom.getId(), 2);
        assertEquals(secondChatRoom.getCreator().getId(), 2);
        assertEquals(secondChatRoom.getCreator().getEmail(), "bob@example.com");
        assertEquals(secondChatRoom.getCreator().getPasswordHash(), "hashed_password_2");
        assertEquals(secondChatRoom.getCreator().getName(), "Bob Johnson");
        assertEquals(secondChatRoom.getName(), "Tech Talk");
        assertEquals(secondChatRoom.getDescription(), "A room for tech enthusiasts");
        assertNotNull(secondChatRoom.getCreatedAt());
    }

    @Test
    public void findUserChatRoomsTestErrorUserId() {
        Long errorUserID = 404L;
        List<ChatRoom> chatRooms = chatRoomsRepository.findUserChatRooms(errorUserID);
        assertTrue(chatRooms.isEmpty());

    }

    @Test
    public void addAndRemoveUserToRoomTestCurrentUserId() {
        assertDoesNotThrow(() -> {chatRoomsRepository.addUserToRoom(3L,2L);});
        List<User> connectedUser = chatRoomsRepository.getAllConnectedUser(3L);
        System.out.println(connectedUser);
        assertEquals(connectedUser.size(), 2);
        assertEquals(connectedUser.get(1).getId(), 2);
        assertDoesNotThrow(() -> {chatRoomsRepository.removeUserFromRoom(3L,2L);});
        connectedUser = chatRoomsRepository.getAllConnectedUser(3L);
        assertEquals(connectedUser.size(), 1);
        assertEquals(connectedUser.get(0).getId(), 3);
    }

    @Test
    public void addAndRemoveUserToRoomTestError() {
        assertThrows(DataIntegrityViolationException.class, () -> {chatRoomsRepository.addUserToRoom(3L,404L);});
        assertThrows(DataIntegrityViolationException.class, () -> {chatRoomsRepository.addUserToRoom(3L,3L);});
        assertDoesNotThrow(() -> {chatRoomsRepository.removeUserFromRoom(3L,404L);});
    }

    @Test
    public void getLastVisitRoomTestCurrentUserId() {
        Optional<ChatRoom> chatRoomOpt = chatRoomsRepository.getLastVisitRoom(1L);
        assertTrue(chatRoomOpt.isPresent());
        ChatRoom chatRoom = chatRoomOpt.get();
        assertEquals(chatRoom.getId(), 1);
        assertEquals(chatRoom.getCreator().getId(), 1);
        assertEquals(chatRoom.getCreator().getEmail(), "alice@example.com");
        assertEquals(chatRoom.getCreator().getPasswordHash(), "hashed_password_1");
        assertEquals(chatRoom.getCreator().getName(), "Alice Smith");
        assertEquals(chatRoom.getName(), "General Chat");
        assertEquals(chatRoom.getDescription(), "A room for general discussions");
        assertNotNull(chatRoom.getCreatedAt());
    }



    @Test
    public void getLastVisitRoomTestErrorUserId() {
        Optional<ChatRoom> chatRoomOpt = chatRoomsRepository.getLastVisitRoom(404L);
        assertFalse(chatRoomOpt.isPresent());
    }

    @Test
    public void findByIdTestCurrentId() {
        Optional<ChatRoom> chatRoomOpt = chatRoomsRepository.findById(1L);
        assertTrue(chatRoomOpt.isPresent());
        ChatRoom chatRoom = chatRoomOpt.get();
        assertEquals(chatRoom.getId(), 1);
        assertEquals(chatRoom.getCreator().getId(), 1);
        assertEquals(chatRoom.getCreator().getEmail(), "alice@example.com");
        assertEquals(chatRoom.getCreator().getPasswordHash(), "hashed_password_1");
        assertEquals(chatRoom.getCreator().getName(), "Alice Smith");
        assertEquals(chatRoom.getName(), "General Chat");
        assertEquals(chatRoom.getDescription(), "A room for general discussions");
        assertNotNull(chatRoom.getCreatedAt());
    }

    @Test
    public void findByIdTestErrorId() {
        Optional<ChatRoom> chatRoomOpt = chatRoomsRepository.findById(404L);
        assertFalse(chatRoomOpt.isPresent());
    }

    @Test
    public void saveTest() {
        ChatRoom entity = new ChatRoom();
        entity.setName("Name");
        entity.setDescription("Description");

        User user = new User();
        user.setId(1L);
        entity.setCreator(user);

        chatRoomsRepository.save(entity);

        assertEquals(entity.getId(), 4);
        assertNotNull(entity.getCreatedAt());

        Optional<ChatRoom> chatRoomOpt = chatRoomsRepository.findById(4L);
        assertTrue(chatRoomOpt.isPresent());
        ChatRoom chatRoom = chatRoomOpt.get();
        assertEquals(chatRoom.getId(), 4);
        assertEquals(chatRoom.getCreator().getId(), 1);
        assertEquals(chatRoom.getCreator().getEmail(), "alice@example.com");
        assertEquals(chatRoom.getCreator().getPasswordHash(), "hashed_password_1");
        assertEquals(chatRoom.getCreator().getName(), "Alice Smith");
        assertEquals(chatRoom.getName(), "Name");
        assertEquals(chatRoom.getDescription(), "Description");
        assertNotNull(chatRoom.getCreatedAt());
    }

    @Test
    public void saveTestError() {
        ChatRoom entity = new ChatRoom();
        entity.setName("Name");
        entity.setDescription("Description");

        User user = new User();
        user.setId(404L);
        entity.setCreator(user);

        assertThrows(DataIntegrityViolationException.class, () -> {chatRoomsRepository.save(entity);});

    }

    @Test
    public void updateTest() {

        Optional<ChatRoom> chatRoomOpt = chatRoomsRepository.findById(3L);
        assertTrue(chatRoomOpt.isPresent());
        ChatRoom chatRoom = chatRoomOpt.get();

        chatRoom.setName("NEW name");
        chatRoom.setDescription("NEW Description");
        User user = new User();
        user.setId(1L);
        chatRoom.setCreator(user);
        Timestamp oldTime = chatRoom.getCreatedAt();

        chatRoomsRepository.update(chatRoom);

        assertEquals(chatRoom.getId(), 3);
        assertEquals(chatRoom.getCreator().getId(), 1);
        assertEquals(chatRoom.getName(), "NEW name");
        assertEquals(chatRoom.getDescription(), "NEW Description");
        assertEquals(oldTime, chatRoom.getCreatedAt());
    }

    @Test
    public void updateTestError() {

        Optional<ChatRoom> chatRoomOpt = chatRoomsRepository.findById(3L);
        assertTrue(chatRoomOpt.isPresent());
        ChatRoom chatRoom = chatRoomOpt.get();

        chatRoom.setName("NEW name");
        chatRoom.setDescription("NEW Description");
        User user = new User();
        chatRoom.setCreator(user);

        assertThrows(DataIntegrityViolationException.class, () -> {chatRoomsRepository.update(chatRoom);});
    }

    @Test
    public void deleteTest() {
        chatRoomsRepository.delete(3L);
        Optional<ChatRoom> chatRoomOpt = chatRoomsRepository.findById(3L);
        assertFalse(chatRoomOpt.isPresent());

        chatRoomsRepository.delete(4L);


    }

    @Test
    public void findAllTest() {
        List<ChatRoom> chatRoomsList = chatRoomsRepository.findAll();
        assertEquals(3, chatRoomsList.size());

        ChatRoom firstChatRoom = chatRoomsList.get(0);
        assertEquals(firstChatRoom.getId(), 1);
        assertEquals(firstChatRoom.getCreator().getId(), 1);
        assertEquals(firstChatRoom.getCreator().getEmail(), "alice@example.com");
        assertEquals(firstChatRoom.getCreator().getPasswordHash(), "hashed_password_1");
        assertEquals(firstChatRoom.getCreator().getName(), "Alice Smith");
        assertEquals(firstChatRoom.getName(), "General Chat");
        assertEquals(firstChatRoom.getDescription(), "A room for general discussions");
        assertNotNull(firstChatRoom.getCreatedAt());

        ChatRoom secondChatRoom = chatRoomsList.get(1);
        assertEquals(secondChatRoom.getId(), 2);
        assertEquals(secondChatRoom.getCreator().getId(), 2);
        assertEquals(secondChatRoom.getCreator().getEmail(), "bob@example.com");
        assertEquals(secondChatRoom.getCreator().getPasswordHash(), "hashed_password_2");
        assertEquals(secondChatRoom.getCreator().getName(), "Bob Johnson");
        assertEquals(secondChatRoom.getName(), "Tech Talk");
        assertEquals(secondChatRoom.getDescription(), "A room for tech enthusiasts");
        assertNotNull(secondChatRoom.getCreatedAt());

        ChatRoom thirdChatRoom = chatRoomsList.get(2);
        assertEquals(thirdChatRoom.getId(), 3);
        assertEquals(thirdChatRoom.getCreator().getId(), 3);
        assertEquals(thirdChatRoom.getCreator().getEmail(), "charlie@example.com");
        assertEquals(thirdChatRoom.getCreator().getPasswordHash(), "hashed_password_3");
        assertEquals(thirdChatRoom.getCreator().getName(), "Charlie Davis");
        assertEquals(thirdChatRoom.getName(), "Random");
        assertEquals(thirdChatRoom.getDescription(), "A room for random conversations");
        assertNotNull(thirdChatRoom.getCreatedAt());
    }
}
