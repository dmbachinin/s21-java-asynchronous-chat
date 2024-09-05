package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
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
public class GetRoomByIdCommand implements CommandHandler {

    private final ChatRoomService chatRoomService;
    private final ResponseGenerator responseGenerator;


    @Autowired
    public GetRoomByIdCommand(ChatRoomService chatRoomService, ResponseGenerator responseGenerator) {
        this.chatRoomService = chatRoomService;
        this.responseGenerator = responseGenerator;
    }

    @Override
    public ServerResponse execute(UserCommand command) {
        Map<String, Object> parameters = command.getParameters();
        if (checkParameters(parameters)) {
            return responseGenerator.generateResponseError(command.getCommand(),"Ошибка запроса");
        }
        Long id = ((Integer)parameters.get("roomId")).longValue();
        Optional<ChatRoom> chatRoomOptional = chatRoomService.findChatRoomById(id);
        CommandStatus commandStatus = chatRoomOptional.isPresent() ? CommandStatus.OK : CommandStatus.ERROR;
        return responseGenerator.generateResponse(command.getCommand(), commandStatus, chatRoomOptional.orElseGet(ChatRoom::new));
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        boolean dontHaveSomeParameter = !parameters.containsKey("roomId");
        return dontHaveSomeParameter || !(parameters.get("roomId") instanceof Integer);
    }

}
