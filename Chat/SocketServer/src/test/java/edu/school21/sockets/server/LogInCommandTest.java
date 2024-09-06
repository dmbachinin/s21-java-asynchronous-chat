package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandHandler;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.LogInCommand;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.server.responseGenerator.ResponseGeneratorImpl;
import edu.school21.sockets.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LogInCommandTest {

    @Mock
    private UsersService mockService;

    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private LogInCommand testCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseGenerator = new ResponseGeneratorImpl();
        testCommand = new LogInCommand(mockService, responseGenerator);
    }

    @Test
    public void CurrentTest() {
        User user = new User();
        user.setId(2L);
        when(mockService.logIn("bob@example.com", "hashed_password_2"))
                .thenReturn(Optional.of(user))
        ;

        UserCommand command = new UserCommand();
        command.setCommand("LOG_IN");
        command.addParameter("email", "bob@example.com");
        command.addParameter("password", "hashed_password_2");
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.OK);
        responseCurrent.addData("userId", 2L);
        responseCurrent.addData("name", null);

        assertEquals(responseCurrent, response);

        verify(mockService).logIn("bob@example.com", "hashed_password_2");
    }

    @Test
    public void ErrorEmailTest() {
        when(mockService.logIn("error@example.com", "error"))
                .thenReturn(Optional.empty())
        ;

        UserCommand command = new UserCommand();
        command.setCommand("LOG_IN");
        command.addParameter("email", "error@example.com");
        command.addParameter("password", "error");
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.NOT_FOUND);

        assertEquals(responseCurrent, response);

        verify(mockService).logIn("error@example.com", "error");
    }

    @Test
    public void ErrorNotParametersTest() {

        UserCommand command = new UserCommand();
        command.setCommand("LOG_IN");
        command.addParameter("email", "error@example.com");
        command.addParameter("other", "other");

        ServerResponse response = testCommand.execute(command);

        ServerResponse responseCurrent = new ServerResponse();
        responseCurrent.setCommand(command.getCommand());
        responseCurrent.setStatus(CommandStatus.ERROR);

        assertEquals(responseCurrent, response);

        verify(mockService, times(0)).logIn(any(String.class), any(String.class));
    }
}
