package edu.school21.sockets.server;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.CreateRoomCommand;
import edu.school21.sockets.server.commandHandlers.SendMessageCommand;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.server.responseGenerator.ResponseGeneratorImpl;
import edu.school21.sockets.services.ChatRoomService;
import edu.school21.sockets.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SendMessageCommandTest {

    @Mock
    private MessageService mockService;

    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private SendMessageCommand testCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseGenerator = new ResponseGeneratorImpl();
        testCommand = new SendMessageCommand(mockService, responseGenerator);
    }

    @Test
    public void CurrentTest() {
        Message message = new Message();
        when(mockService.sendMessage(2L, 2L, "message"))
                .thenReturn(Optional.of(message))
        ;

        UserCommand command = new UserCommand();
        command.setCommand("SEND_MESSAGE");
        command.addParameter("roomId", 2);
        command.addParameter("senderId", 2);
        command.addParameter("content", "message");
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.OK);

        assertEquals(responseCurrent, response);

        verify(mockService).sendMessage(2L, 2L, "message");
    }

    @Test
    public void ErrorCreatorIdTest() {
        when(mockService.sendMessage(404L, 2L, "message"))
                .thenReturn(Optional.empty())
        ;

        UserCommand command = new UserCommand();
        command.setCommand("SEND_MESSAGE");
        command.addParameter("roomId", 404);
        command.addParameter("senderId", 2);
        command.addParameter("content", "message");
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService).sendMessage(404L, 2L, "message");
    }
}
