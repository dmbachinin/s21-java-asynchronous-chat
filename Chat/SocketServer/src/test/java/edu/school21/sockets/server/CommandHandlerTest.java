package edu.school21.sockets.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.CommandProcessor.CommandProcessor;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.services.ChatRoomService;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    public void LOG_INTest() throws JsonProcessingException {
//        User user = new User();
//        user.setId(2L);
//        when(mockUsersService.logIn("bob@example.com", "hashed_password_2"))
//                .thenReturn(Optional.of(user))
//        ;
//
//        UserCommand command = new UserCommand();
//        command.setCommand("LOG_IN");
//        command.addParameter("email", "bob@example.com");
//        command.addParameter("password", "hashed_password_2");
//        command.addParameter("other", "other");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(command);
//
//        ServerResponse response = commandHandler.handeCommand(json);
//
//        ServerResponse responseCurrent = new ServerResponse();
//        responseCurrent.setStatus(CommandStatus.OK);
//        responseCurrent.addData("id", 2L);
//
//        assertEquals(response, responseCurrent);
    }
}
