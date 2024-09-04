package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class JoinTheRoomCommand implements CommandHandler {

    private final ChatRoomService chatRoomService;
    private final ResponseGenerator responseGenerator;


    @Autowired
    public JoinTheRoomCommand(ChatRoomService chatRoomService, ResponseGenerator responseGenerator) {
        this.chatRoomService = chatRoomService;
        this.responseGenerator = responseGenerator;
    }

    @Override
    public ServerResponse execute(UserCommand command) {
        Map<String, Object> parameters = command.getParameters();
        if (checkParameters(parameters)) {
            return responseGenerator.generateResponseError(command.getCommand(),"Ошибка запроса");
        }
        Long roomId = (Long) parameters.get("roomId");
        Long userID = (Long) parameters.get("userId");
        ServerResponse result;
        if (chatRoomService.addUserToRoom(roomId, userID)) {
            result = responseGenerator.generateResponseMessage(command.getCommand(),"Пользователь добавлен в чат");
        } else {
            result = responseGenerator.generateResponseError(command.getCommand(),"Ошибка при добавлении пользователя");
        }
        return result;
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        boolean dontHaveSomeParameter =
                !parameters.containsKey("userId") ||
                        !parameters.containsKey("roomId");
        return dontHaveSomeParameter ||
                !(parameters.get("userId") instanceof Long) ||
                !(parameters.get("roomId") instanceof Long);
    }
}
