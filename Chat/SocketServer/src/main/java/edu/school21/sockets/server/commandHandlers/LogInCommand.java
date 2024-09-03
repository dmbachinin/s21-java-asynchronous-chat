package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.models.User;
import edu.school21.sockets.server.responseGenerator.MessageComposer;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.server.responseGenerator.responses.ErrorResponse;
import edu.school21.sockets.server.responseGenerator.responses.UserInfoResponse;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class LogInCommand implements CommandHandler {

    private final UsersService usersService;
    private final ResponseGenerator responseGenerator;


    @Autowired
    public LogInCommand(UsersService usersService, ResponseGenerator responseGenerator) {
        this.usersService = usersService;
        this.responseGenerator = responseGenerator;
    }

    @Override
    public String execute(UserCommand command) {
        Map<String, Object> parameters = command.getParameters();
        if (checkParameters(parameters)) {
            return responseGenerator.generateResponseError("Ошибка запроса");
        }
        String email = (String) parameters.get("email");
        String password = (String) parameters.get("password");
        Optional<User> userOptional = usersService.logIn(email, password);
        CommandStatus commandStatus = userOptional.isPresent() ? CommandStatus.OK : CommandStatus.NOT_FOUND;
        return responseGenerator.generateResponse(commandStatus, userOptional.orElseGet(User::new));
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        return !parameters.containsKey("email") || !parameters.containsKey("password");
    }
}
