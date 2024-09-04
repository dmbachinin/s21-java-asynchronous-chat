package edu.school21.sockets.server;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.CreateRoomCommand;
import edu.school21.sockets.server.commandHandlers.JoinTheRoomCommand;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.server.responseGenerator.ResponseGeneratorImpl;
import edu.school21.sockets.services.ChatRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JoinTheRoomCommandTest {

    @Mock
    private ChatRoomService mockService;

    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private JoinTheRoomCommand testCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseGenerator = new ResponseGeneratorImpl();
        testCommand = new JoinTheRoomCommand(mockService, responseGenerator);
    }

    @Test
    public void CurrentTest() {
        when(mockService.addUserToRoom(2L, 2L))
                .thenReturn(true)
        ;

        UserCommand command = new UserCommand();
        command.setCommand("JOIN_THE_ROOM");
        command.addParameter("roomId", 2L);
        command.addParameter("userId", 2L);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.OK);

        assertEquals(responseCurrent, response);

        verify(mockService).addUserToRoom(2L, 2L);
    }

    @Test
    public void ErrorCreatorIdTest() {
        when(mockService.addUserToRoom(4L, 2L))
                .thenReturn(false)
        ;

        UserCommand command = new UserCommand();
        command.setCommand("JOIN_THE_ROOM");
        command.addParameter("roomId", 4L);
        command.addParameter("userId", 2L);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService).addUserToRoom(4L, 2L);
    }

    @Test
    public void ErrorParametersTest() {
        UserCommand command = new UserCommand();
        command.setCommand("JOIN_THE_ROOM");
        command.addParameter("roomId", "4L");
        command.addParameter("userId", "2L");
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService, times(0)).addUserToRoom(
                any(Long.class), any(Long.class));
    }
}
