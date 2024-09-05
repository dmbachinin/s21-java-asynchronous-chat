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

        List<ChatRoom> chatRoomList = chatRoomService.getAllChatRooms();
        CommandStatus status = chatRoomList.isEmpty() ? CommandStatus.ERROR : CommandStatus.OK;
        return responseGenerator.generateResponseForChatRooms(command.getCommand(), status, chatRoomList);
    }

}
