package edu.school21.sockets.server.CommandProcessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.sockets.server.commandHandlers.CommandHandler;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.commandHandlers.UserCommand;
import edu.school21.sockets.server.responseGenerator.MessageComposer;
import edu.school21.sockets.server.responseGenerator.ResponseGenerator;
import edu.school21.sockets.services.ChatRoomService;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("commandHandler")
public class CommandProcessor {

    private UsersService userService;
    private ChatRoomService chatRoomService;
    private MessageService messageService;
    private ResponseGenerator responseGenerator;

    private ObjectMapper objectMapper = new ObjectMapper();
    private final CommandFactory commandFactory;

    @Autowired
    public CommandProcessor(UsersService userService,
                            ChatRoomService chatRoomService,
                            MessageService messageService,
                            ResponseGenerator responseGenerator) {
        this.userService = userService;
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
        this.responseGenerator = responseGenerator;
        commandFactory = new CommandFactory(userService, chatRoomService, messageService, responseGenerator);
    }

    public String handeCommand(String jsonCommand) {
        try {
          UserCommand userCommand =   objectMapper.readValue(jsonCommand, UserCommand.class);
          String command = userCommand.getCommand();
          CommandHandler commandHandler = commandFactory.getCommandHandler(command);
          return commandHandler.execute(userCommand);

        } catch (JsonProcessingException  e) {
            e.printStackTrace();
        }
        return responseGenerator.generateResponseError("Неизвестная команда");
    }

}
