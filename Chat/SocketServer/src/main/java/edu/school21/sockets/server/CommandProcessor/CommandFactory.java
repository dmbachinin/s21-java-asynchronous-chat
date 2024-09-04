package edu.school21.sockets.server.CommandProcessor;

import edu.school21.sockets.server.commandHandlers.*;
import edu.school21.sockets.server.responseGenerator.MessageComposer;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.services.ChatRoomService;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandFactory {
    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    @Autowired
    public CommandFactory(UsersService userService,
                          ChatRoomService chatService,
                          MessageService messageService,
                          ResponseGenerator responseGenerator) {
        commandHandlers.put("LOG_IN", new LogInCommand(userService, responseGenerator));
        commandHandlers.put("SING_UP", new SingUpCommand(userService, responseGenerator));
        commandHandlers.put("CREATE_ROOM", new CreateRoomCommand(chatService, responseGenerator));
        commandHandlers.put("JOIN_THE_ROOM", new JoinTheRoomCommand(chatService, responseGenerator));
        commandHandlers.put("GET_ROOMS", new JoinTheRoomCommand(chatService, responseGenerator));
        commandHandlers.put("SEND_MESSAGE", new SendMessageCommand(messageService, responseGenerator));
        commandHandlers.put("GET_MESSAGE", new GetMessagesCommand(messageService, responseGenerator));
        commandHandlers.put("GET_LAST_VISIT_ROOM", new GetMessagesCommand(messageService, responseGenerator));
    }

    public CommandHandler getCommandHandler(String command) {
        return commandHandlers.get(command);
    }
}
