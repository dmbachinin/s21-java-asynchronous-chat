package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.services.ChatRoomService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class CreateRoomCommand implements CommandHandler {

    private final ChatRoomService chatRoomService;
    private final ResponseGenerator responseGenerator;


    @Autowired
    public CreateRoomCommand(ChatRoomService chatRoomService, ResponseGenerator responseGenerator) {
        this.chatRoomService = chatRoomService;
        this.responseGenerator = responseGenerator;
    }

    @Override
    public ServerResponse execute(UserCommand command) {
        Map<String, Object> parameters = command.getParameters();
        if (checkParameters(parameters)) {
            return responseGenerator.generateResponseError("Ошибка запроса");
        }
        Long id = (Long) parameters.get("userId");
        String name = (String) parameters.get("name");
        String description = (String) parameters.get("description");
        Optional<ChatRoom> optional = chatRoomService.createChatRoom(id, name, description);
        CommandStatus commandStatus = optional.isPresent() ? CommandStatus.OK : CommandStatus.ERROR;
        return responseGenerator.generateResponse(commandStatus, optional.orElseGet(ChatRoom::new));
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        boolean dontHaveSomeParameter =
                !parameters.containsKey("userId") ||
                !parameters.containsKey("name") ||
                !parameters.containsKey("description");
        return dontHaveSomeParameter || !(parameters.get("userId") instanceof Long);
    }
}
