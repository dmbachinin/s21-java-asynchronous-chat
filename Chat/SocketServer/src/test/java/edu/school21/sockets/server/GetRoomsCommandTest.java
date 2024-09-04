package edu.school21.sockets.server;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.CreateRoomCommand;
import edu.school21.sockets.server.commandHandlers.GetRoomsCommand;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetRoomsCommandTest {

    @Mock
    private ChatRoomService mockService;

    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private GetRoomsCommand testCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseGenerator = new ResponseGeneratorImpl();
        testCommand = new GetRoomsCommand(mockService, responseGenerator);
    }

    @Test
    public void CurrentTest() {
        ChatRoom chatRoom_1 = new ChatRoom(); chatRoom_1.setId(1L);
        ChatRoom chatRoom_2 = new ChatRoom(); chatRoom_2.setId(2L);
        ChatRoom chatRoom_3 = new ChatRoom(); chatRoom_3.setId(3L);
        List<ChatRoom> chatRoomList =  new ArrayList<>();
        chatRoomList.add(chatRoom_1);
        chatRoomList.add(chatRoom_2);
        chatRoomList.add(chatRoom_3);
        when(mockService.findUserChatRooms(2L))
                .thenReturn(chatRoomList)
        ;

        UserCommand command = new UserCommand();
        command.setCommand("GET_ROOMS");
        command.addParameter("userId", 2L);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setStatus(CommandStatus.OK);
        List<Long> resultID =  new ArrayList<>();
        resultID.add(1L);
        resultID.add(2L);
        resultID.add(3L);
        responseCurrent.addData("roomsId", resultID);

        assertEquals(responseCurrent, response);

        verify(mockService).findUserChatRooms(2L);
    }

    @Test
    public void ErrorCreatorIdTest() {
        when(mockService.findUserChatRooms(4L))
                .thenReturn(new ArrayList<>())
        ;

        UserCommand command = new UserCommand();
        command.setCommand("GET_ROOMS");
        command.addParameter("userId", 4L);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService).findUserChatRooms(4L);
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
