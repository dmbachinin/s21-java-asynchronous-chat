package edu.school21.sockets.server;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.CreateRoomCommand;
import edu.school21.sockets.server.commandHandlers.GetLastVisitRoomCommand;
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

public class GetLastVisitRoomCommandTest {

    @Mock
    private ChatRoomService mockService;

    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private GetLastVisitRoomCommand testCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseGenerator = new ResponseGeneratorImpl();
        testCommand = new GetLastVisitRoomCommand(mockService, responseGenerator);
    }

    @Test
    public void CurrentTest() {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(4L);
        when(mockService.getLastVisitRoom(2L))
                .thenReturn(Optional.of(chatRoom))
        ;

        UserCommand command = new UserCommand();
        command.setCommand("GET_LAST_VISIT_ROOM");
        command.addParameter("userId", 2);

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setStatus(CommandStatus.OK);
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.addData("roomId", 4L);

        assertEquals(responseCurrent, response);

        verify(mockService).getLastVisitRoom(2L);
    }
}
