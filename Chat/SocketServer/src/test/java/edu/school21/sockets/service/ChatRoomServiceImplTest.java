package edu.school21.sockets.service;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.services.ChatRoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@ExtendWith(SpringExtension.class) // Используем JUnit 5
@ContextConfiguration(classes = TestApplicationConfig.class) // Загружаем конфигурацию
@Transactional // Обеспечиваем откат транзакций после тестов
public class ChatRoomServiceImplTest {

    @Autowired
    private ChatRoomService chatRoomService;

    @Test
    public void createChatRoomTest1() {
//        Optional<ChatRoom> chatRoomOptional = chatRoomService.createChatRoom();
    }
}
