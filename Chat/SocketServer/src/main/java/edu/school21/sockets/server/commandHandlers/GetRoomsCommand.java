package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GetRoomsCommand implements CommandHandler {

    private final ChatRoomService chatRoomService;
    private final ResponseGenerator responseGenerator;


    @Autowired
    public GetRoomsCommand(ChatRoomService chatRoomService, ResponseGenerator responseGenerator) {
        this.chatRoomService = chatRoomService;
        this.responseGenerator = responseGenerator;
    }

    @Override
    public ServerResponse execute(UserCommand command) {
        Map<String, Object> parameters = command.getParameters();
        if (checkParameters(parameters)) {
            return responseGenerator.generateResponseError(command.getCommand(),"Ошибка запроса");
        }
        Long id = (Long) parameters.get("userId");
        List<ChatRoom> chatRoomList = chatRoomService.findUserChatRooms(id);
        CommandStatus status = CommandStatus.OK;
        if (chatRoomList.isEmpty()) {
            status = CommandStatus.ERROR;
        }
        return responseGenerator.generateResponseForChatRooms(command.getCommand(), status, chatRoomList);
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        boolean dontHaveSomeParameter = !parameters.containsKey("userId");
        return dontHaveSomeParameter || !(parameters.get("userId") instanceof Long);
    }
}
