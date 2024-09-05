package edu.school21.sockets.server;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.GetUserRoomsCommand;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetRoomsCommandTest {

    @Mock
    private ChatRoomService mockService;

    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private GetUserRoomsCommand testCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseGenerator = new ResponseGeneratorImpl();
        testCommand = new GetUserRoomsCommand(mockService, responseGenerator);
    }

    @Test
    public void CurrentTest() {
        ChatRoom chatRoom_1 = new ChatRoom();
        chatRoom_1.setId(1L);
        chatRoom_1.setName("1");
        ChatRoom chatRoom_2 = new ChatRoom();
        chatRoom_2.setId(2L);
        chatRoom_2.setName("2");
        ChatRoom chatRoom_3 = new ChatRoom();
        chatRoom_3.setId(3L);
        chatRoom_3.setName("3");
        List<ChatRoom> chatRoomList = new ArrayList<>();
        chatRoomList.add(chatRoom_1);
        chatRoomList.add(chatRoom_2);
        chatRoomList.add(chatRoom_3);
        when(mockService.findUserChatRooms(2L))
                .thenReturn(chatRoomList)
        ;

        UserCommand command = new UserCommand();
        command.setCommand("GET_ROOMS");
        command.addParameter("userId", 2);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.OK);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> chatRoom_1_map = new HashMap<>();
        chatRoom_1_map.put("roomId", 1L);
        chatRoom_1_map.put("name", "1");
        Map<String, Object> chatRoom_2_map = new HashMap<>();
        chatRoom_2_map.put("roomId", 2L);
        chatRoom_2_map.put("name", "2");
        Map<String, Object> chatRoom_3_map = new HashMap<>();
        chatRoom_3_map.put("roomId", 3L);
        chatRoom_3_map.put("name", "3");
        result.add(chatRoom_1_map);
        result.add(chatRoom_2_map);
        result.add(chatRoom_3_map);
        responseCurrent.addData("rooms", result);

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
        command.addParameter("userId", 4);
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService).findUserChatRooms(4L);
    }
}
