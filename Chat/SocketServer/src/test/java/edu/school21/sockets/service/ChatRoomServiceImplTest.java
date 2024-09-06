package edu.school21.sockets.service;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.ChatRoomService;
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
public class ChatRoomServiceImplTest {

    @Autowired
    private ChatRoomService chatRoomService;

    @Test
    public void createChatRoomTestCurrent() {
        Optional<ChatRoom> chatRoomOptional = chatRoomService.createChatRoom(1L, "NEW ROOM", "MY NEW ROOM");
        assertTrue(chatRoomOptional.isPresent());
        ChatRoom chatRoom = chatRoomOptional.get();

        assertNotNull(chatRoom.getId());
        assertEquals(chatRoom.getCreator().getId(), 1);
        assertEquals(chatRoom.getCreator().getEmail(), "alice@example.com");
        assertEquals(chatRoom.getCreator().getPasswordHash(), "hashed_password_1");
        assertEquals(chatRoom.getCreator().getName(), "Alice Smith");
        assertEquals(chatRoom.getName(), "NEW ROOM");
        assertEquals(chatRoom.getDescription(), "MY NEW ROOM");
        assertNotNull(chatRoom.getCreatedAt());
    }

    @Test
    public void createChatRoomTestError() {
        Optional<ChatRoom> chatRoomOptional = chatRoomService.createChatRoom(404L, "NEW ROOM", "MY NEW ROOM");
        assertFalse(chatRoomOptional.isPresent());
    }

    @Test
    public void deleteChatRoomTestCurrent() {
        assertTrue(chatRoomService.deleteChatRoom(3L));
    }

    @Test
    public void deleteChatRoomTestError() {
        assertFalse(chatRoomService.deleteChatRoom(404L));
    }

    @Test
    public void findChatRoomByIdTestCurrent() {
        Optional<ChatRoom> chatRoomOptional = chatRoomService.findChatRoomById(3L);
        assertTrue(chatRoomOptional.isPresent());
    }

    @Test
    public void findChatRoomByIdTestError() {
        Optional<ChatRoom> chatRoomOptional = chatRoomService.findChatRoomById(404L);
        assertFalse(chatRoomOptional.isPresent());
    }

    @Test
    public void addUserToRoomTestCurrent() {
        boolean isAdd = chatRoomService.addUserToRoom(3L,2L);
        assertTrue(isAdd);
    }

    @Test
    public void addUserToRoomTestError() {
        assertFalse(chatRoomService.addUserToRoom(404L,2L));
        assertFalse(chatRoomService.addUserToRoom(3L,404L));
    }

    @Test
    public void removeUserFromRoomTestCurrent() {
        boolean isAdd = chatRoomService.removeUserFromRoom(3L,3L);
        assertTrue(isAdd);
    }

    @Test
    public void removeUserFromRoomTestError() {
        assertFalse(chatRoomService.removeUserFromRoom(404L,2L));
        assertFalse(chatRoomService.removeUserFromRoom(3L,404L));
        assertFalse(chatRoomService.removeUserFromRoom(3L,2L));
    }

    @Test
    public void findUserChatRoomsTestCurrent() {
        List<ChatRoom> chatRoomList = chatRoomService.findUserChatRooms(3L);
        assertEquals(chatRoomList.size(), 2);
        assertEquals(chatRoomList.get(0).getId(), 1);
        assertEquals(chatRoomList.get(1).getId(), 3);

        chatRoomList = chatRoomService.findUserChatRooms(1L);
        assertEquals(chatRoomList.size(), 2);
        assertEquals(chatRoomList.get(0).getId(), 1);
        assertEquals(chatRoomList.get(1).getId(), 2);

        chatRoomList = chatRoomService.findUserChatRooms(2L);
        assertEquals(chatRoomList.size(), 2);
        assertEquals(chatRoomList.get(0).getId(), 1);
        assertEquals(chatRoomList.get(1).getId(), 2);
    }

    @Test
    public void findUserChatRoomsTestError() {
        List<ChatRoom> chatRoomList = chatRoomService.findUserChatRooms(404L);
        assertTrue(chatRoomList.isEmpty());
    }

    @Test
    public void getAllConnectedUserTestCurrent() {
        List<User> userList = chatRoomService.getAllConnectedUser(3L);
        assertEquals(userList.size(), 1);
        assertEquals(userList.get(0).getId(), 3);

        userList = chatRoomService.getAllConnectedUser(1L);
        assertEquals(userList.size(), 3);
        assertEquals(userList.get(0).getId(), 2);
        assertEquals(userList.get(1).getId(), 3);
        assertEquals(userList.get(2).getId(), 1);

        userList = chatRoomService.getAllConnectedUser(2L);
        assertEquals(userList.size(), 2);
        assertEquals(userList.get(0).getId(), 1);
        assertEquals(userList.get(1).getId(), 2);
    }

    @Test
    public void getAllConnectedUserTestError() {
        List<User> userList = chatRoomService.getAllConnectedUser(404L);
        assertTrue(userList.isEmpty());
    }

    @Test
    public void getLastVisitRoomTestCurrent() {

        Optional<ChatRoom> chatRoomOptional = chatRoomService.getLastVisitRoom(1L);
        assertTrue(chatRoomOptional.isPresent());
        assertEquals(chatRoomOptional.get().getId(), 1L);

        chatRoomOptional = chatRoomService.getLastVisitRoom(2L);
        assertTrue(chatRoomOptional.isPresent());
        assertEquals(chatRoomOptional.get().getId(), 2L);

        chatRoomOptional = chatRoomService.getLastVisitRoom(3L);
        assertTrue(chatRoomOptional.isPresent());
        assertEquals(chatRoomOptional.get().getId(), 3L);
    }

    @Test
    public void getLastVisitRoomTestError() {
        Optional<ChatRoom> chatRoomOptional = chatRoomService.getLastVisitRoom(404L);
        assertFalse(chatRoomOptional.isPresent());
    }
}
