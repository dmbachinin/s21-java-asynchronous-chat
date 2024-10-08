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
            return responseGenerator.generateResponseError(command.getCommand(),"Ошибка запроса");
        }
        Long roomId = ((Integer) parameters.get("roomId")).longValue();
        Long userId = ((Integer) parameters.get("userId")).longValue();
        String content = (String) parameters.get("content");
        Optional<Message> optional = messageService.sendMessage(roomId, userId, content);
        CommandStatus commandStatus = optional.isPresent() ? CommandStatus.OK : CommandStatus.ERROR;
        return responseGenerator.generateResponse(command.getCommand(),commandStatus, optional.orElseGet(Message::new));
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        boolean dontHaveSomeParameter =
                !parameters.containsKey("roomId") ||
                !parameters.containsKey("userId") ||
                !parameters.containsKey("content");
        return dontHaveSomeParameter ||
                !(parameters.get("roomId") instanceof Integer) ||
                !(parameters.get("userId") instanceof Integer);
    }
}
