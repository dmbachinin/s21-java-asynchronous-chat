package edu.school21.sockets.server;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.GetMessagesCommand;
import edu.school21.sockets.server.commandHandlers.SendMessageCommand;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.server.responseGenerator.ResponseGeneratorImpl;
import edu.school21.sockets.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetMessagesCommandTest {

    @Mock
    private MessageService mockService;

    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private GetMessagesCommand testCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseGenerator = new ResponseGeneratorImpl();
        testCommand = new GetMessagesCommand(mockService, responseGenerator);
    }

    @Test
    public void CurrentTest() {
        Message message_1 = new Message();
        message_1.setId(1L);
        message_1.setUser(new User(1L, "User1", null, null));
        message_1.setRoom(new ChatRoom(1L, null, null, null));
        message_1.setContent("HI!!!");

        Message message_2 = new Message();
        message_2.setId(2L);
        message_2.setUser(new User(2L, "User2", null, null));
        message_2.setRoom(new ChatRoom(1L, null, null, null));
        message_2.setContent("Hello!");

        List<Message> messageList = new ArrayList<>();
        messageList.add(message_1);
        messageList.add(message_2);

        when(mockService.getMessagesByRoom(1L, 1, 2))
                .thenReturn(messageList)
        ;

        UserCommand command = new UserCommand();
        command.setCommand("GET_MESSAGE");
        command.addParameter("roomId", 1);
        command.addParameter("page", 1);
        command.addParameter("size", 2);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.OK);

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> message_1_map = new HashMap<>();
        message_1_map.put("roomId", 1L);
        message_1_map.put("senderName", "User1");
        message_1_map.put("content", "HI!!!");
        message_1_map.put("createAt", null);
        Map<String, Object> message_2_map = new HashMap<>();
        message_2_map.put("roomId", 2L);
        message_2_map.put("senderName", "User2");
        message_2_map.put("content", "Hello!");
        message_2_map.put("createAt", null);
        result.add(message_1_map);
        result.add(message_2_map);

        responseCurrent.addData("messages", result);

        assertEquals(responseCurrent, response);

        verify(mockService).getMessagesByRoom(1L, 1, 2);
    }

    @Test
    public void ErrorCreatorIdTest() {
        when(mockService.getMessagesByRoom(404L, 1, 2))
                .thenReturn(new ArrayList<>())
        ;

        UserCommand command = new UserCommand();
        command.setCommand("GET_MESSAGE");
        command.addParameter("roomId", 404);
        command.addParameter("page", 1);
        command.addParameter("size", 2);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService).getMessagesByRoom(404L, 1, 2);
    }
}
