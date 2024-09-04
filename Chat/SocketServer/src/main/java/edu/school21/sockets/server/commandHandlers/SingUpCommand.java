package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.models.User;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class SingUpCommand implements CommandHandler {

    private final UsersService usersService;
    private final ResponseGenerator responseGenerator;


    @Autowired
    public SingUpCommand(UsersService usersService, ResponseGenerator responseGenerator) {
        this.usersService = usersService;
        this.responseGenerator = responseGenerator;
    }

    @Override
    public ServerResponse execute(UserCommand command) {
        Map<String, Object> parameters = command.getParameters();
        if (checkParameters(parameters)) {
            return responseGenerator.generateResponseError("Ошибка запроса");
        }
        String email = (String) parameters.get("email");
        String password = (String) parameters.get("password");
        String name = (String) parameters.get("name");
        Optional<User> userOptional = usersService.signUp(email, name, password);
        CommandStatus commandStatus = userOptional.isPresent() ? CommandStatus.OK : CommandStatus.ERROR;
        return responseGenerator.generateResponse(commandStatus, userOptional.orElseGet(User::new));
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        return !parameters.containsKey("email") ||
                !parameters.containsKey("password") ||
                !parameters.containsKey("name");
    }
}
