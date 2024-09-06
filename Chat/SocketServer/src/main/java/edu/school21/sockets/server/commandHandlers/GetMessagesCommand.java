package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GetMessagesCommand implements CommandHandler {

    private final MessageService messageService;
    private final ResponseGenerator responseGenerator;


    @Autowired
    public GetMessagesCommand(MessageService messageService, ResponseGenerator responseGenerator) {
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
        Integer size = (Integer) parameters.get("size");
        Integer page = (Integer) parameters.get("page");
        List<Message> messageList = messageService.getMessagesByRoom(roomId, page, size);
        CommandStatus status = messageList.isEmpty() ? CommandStatus.ERROR :CommandStatus.OK;
        return responseGenerator.generateResponseForMessages(command.getCommand(), status, messageList);
    }

    public boolean checkParameters(Map<String, Object> parameters) {
        boolean dontHaveSomeParameter =
                !parameters.containsKey("roomId") ||
                !parameters.containsKey("size") ||
                !parameters.containsKey("page");
        return dontHaveSomeParameter ||
                !(parameters.get("roomId") instanceof Integer) ||
                !(parameters.get("size") instanceof Integer) ||
                !(parameters.get("page") instanceof Integer);
    }
}
