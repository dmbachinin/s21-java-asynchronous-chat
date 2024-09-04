package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.services.ChatRoomService;
import edu.school21.sockets.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class SendMessageCommand implements CommandHandler {

    private final MessageService messageService;
    private final ResponseGenerator responseGenerator;


    @Autowired
    public SendMessageCommand(MessageService messageService, ResponseGenerator responseGenerator) {
        this.messageService = messageService;
        this.responseGenerator = responseGenerator;
    }

    @Override
    public ServerResponse execute(UserCommand command) {
        Map<String, Object> parameters = command.getParameters();
        if (checkParameters(parameters)) {
            return responseGenerator.generateResponseError("Ошибка запроса");
        }
        Long roomId = (Long) parameters.get("roomId");
        Long senderId = (Long) parameters.get("senderId");
        String content = (String) parameters.get("content");
        Optional<Message> optional = messageService.sendMessage(roomId, senderId, content);
        CommandStatus commandStatus = optional.isPresent() ? CommandStatus.OK : CommandStatus.ERROR;
        return responseGenerator.generateResponse(commandStatus, optional.orElseGet(Message::new));
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        boolean dontHaveSomeParameter =
                !parameters.containsKey("roomId") ||
                !parameters.containsKey("senderId") ||
                !parameters.containsKey("content");
        return dontHaveSomeParameter ||
                !(parameters.get("roomId") instanceof Long) ||
                !(parameters.get("senderId") instanceof Long);
    }
}
