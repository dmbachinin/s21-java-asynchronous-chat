package edu.school21.sockets.repository;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatRoomsRepository;
import edu.school21.sockets.repositories.ChatRoomsRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatRoomsRepositoryImplTest {
    private static ChatRoomsRepository<ChatRoom> chatRoomsRepository;
    @BeforeAll
    public static void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        chatRoomsRepository = context.getBean("chatRoomsRepository", ChatRoomsRepository.class);
    }

    @Test
    public void getAllConnectedUserTest() {
        List<User> connectedUser = chatRoomsRepository.getAllConnectedUser(1L);
        User first = connectedUser.get(0);
        User second = connectedUser.get(1);
        User third = connectedUser.get(2);

        assertEquals(3, connectedUser.size());

        assertEquals(first.getId(), 2);
        assertEquals(second.getId(), 3);
        assertEquals(third.getId(), 1);
    }

}
