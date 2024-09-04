package edu.school21.sockets.server;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.CreateRoomCommand;
import edu.school21.sockets.server.commandHandlers.SingUpCommand;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.server.responseGenerator.ResponseGeneratorImpl;
import edu.school21.sockets.services.ChatRoomService;
import edu.school21.sockets.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateRoomCommandTest {

    @Mock
    private ChatRoomService mockService;

    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private CreateRoomCommand testCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseGenerator = new ResponseGeneratorImpl();
        testCommand = new CreateRoomCommand(mockService, responseGenerator);
    }

    @Test
    public void CurrentTest() {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(4L);
        when(mockService.createChatRoom(2L, "name","description"))
                .thenReturn(Optional.of(chatRoom))
        ;

        UserCommand command = new UserCommand();
        command.setCommand("CREATE_ROOM");
        command.addParameter("name", "name");
        command.addParameter("userId", 2L);
        command.addParameter("description", "description");
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setStatus(CommandStatus.OK);
        responseCurrent.addData("chatId", 4L);

        assertEquals(responseCurrent, response);

        verify(mockService).createChatRoom(2L, "name","description");
    }

    @Test
    public void ErrorCreatorIdTest() {
        when(mockService.createChatRoom(4L, "name","description"))
                .thenReturn(Optional.empty())
        ;

        UserCommand command = new UserCommand();
        command.setCommand("CREATE_ROOM");
        command.addParameter("name", "name");
        command.addParameter("userId", 4L);
        command.addParameter("description", "description");
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService).createChatRoom(4L, "name","description");
    }

    @Test
    public void ErrorParametersTest() {


        UserCommand command = new UserCommand();
        command.setCommand("CREATE_ROOM");
        command.addParameter("name", 33);
        command.addParameter("userId", "123");
        command.addParameter("description", 1L);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService, times(0)).createChatRoom(
                any(Long.class), any(String.class), any(String.class));
    }
}
