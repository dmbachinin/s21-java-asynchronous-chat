package edu.school21.sockets.server;

import edu.school21.sockets.server.CommandProcessor.CommandProcessor;
import edu.school21.sockets.services.ChatRoomService;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CommandHandlerTest {
    @Mock
    private UsersService mockUsersService;

    @Mock
    private ChatRoomService mockChatRoomService;

    @Mock
    private MessageService mockMessageService;

    @InjectMocks
    private CommandProcessor commandHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void handeCommandTest() {
        commandHandler.handeCommand("{\n" +
                "  \"command\": \"GET\",\n" +
                "  \"parameters\": {\n" +
                "    \"message\" : \"Hello!\",\n" +
                "    \"fromId\" : 4,\n" +
                "    \"roomId\": 10\n" +
                "  }\n" +
                "}");
    }
}
