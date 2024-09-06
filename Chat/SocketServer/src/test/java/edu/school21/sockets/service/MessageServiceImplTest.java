package edu.school21.sockets.service;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.services.MessageService;
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
public class MessageServiceImplTest {
    @Autowired
    private MessageService messageService;

    @Test
    public void getMessageByIdTestCurrent() {
        Optional<Message> messageOptional = messageService.getMessageById(2L);
        assertTrue(messageOptional.isPresent());
        Message message = messageOptional.get();

        assertEquals(message.getId(), 2L);
        assertEquals(message.getUser().getId(), 2L);
        assertEquals(message.getRoom().getId(), 1L);
    }

    @Test
    public void getMessageByIdTestError() {
        Optional<Message> messageOptional = messageService.getMessageById(404L);
        assertFalse(messageOptional.isPresent());
        messageOptional = messageService.getMessageById(null);
        assertFalse(messageOptional.isPresent());
    }

    @Test
    public void sendMessageTestCurrent() {
        Optional<Message> messageOptional = messageService.sendMessage(1L,2L,"HI!!!");
        assertTrue(messageOptional.isPresent());
        Message message = messageOptional.get();
        assertNotNull(message.getUser().getId());
        assertEquals(message.getUser().getId(), 2L);
        assertEquals(message.getRoom().getId(), 1L);
        assertEquals(message.getContent(), "HI!!!");
    }

    @Test
    public void sendMessageTestError() {
        Optional<Message> messageOptional = messageService.sendMessage(404L,2L,"HI!!!");
        assertFalse(messageOptional.isPresent());
        messageOptional = messageService.sendMessage(1L,404L,"HI!!!");
        assertFalse(messageOptional.isPresent());
        messageOptional = messageService.sendMessage(404L,404L,"HI!!!");
        assertFalse(messageOptional.isPresent());
        messageOptional = messageService.sendMessage(1L,2L,null);
        assertFalse(messageOptional.isPresent());
        messageOptional = messageService.sendMessage(null,2L,"HI!!!");
        assertFalse(messageOptional.isPresent());
        messageOptional = messageService.sendMessage(1L,null,"HI!!!");
        assertFalse(messageOptional.isPresent());
    }

    @Test
    public void getMessagesByRoomTestCurrent() {
        List<Message> messageList = messageService.getMessagesByRoom(2L, 1, 1);
        assertEquals(messageList.size(), 1);
        Message message = messageList.get(0);
        assertEquals(message.getId(), 4L);
        assertEquals(message.getUser().getId(), 1L);
        assertEquals(message.getRoom().getId(), 2L);
        assertEquals(message.getContent(), "Anyone here into Java?");
    }

    @Test
    public void getMessagesByRoomTestError() {
        List<Message> messageList = messageService.getMessagesByRoom(404L, 1, 1);
        assertTrue(messageList.isEmpty());
        messageList = messageService.getMessagesByRoom(null, 1, 1);
        assertTrue(messageList.isEmpty());
        messageList = messageService.getMessagesByRoom(2L, 0, 1);
        assertTrue(messageList.isEmpty());
        messageList = messageService.getMessagesByRoom(2L, 1, 0);
        assertTrue(messageList.isEmpty());
    }
}
