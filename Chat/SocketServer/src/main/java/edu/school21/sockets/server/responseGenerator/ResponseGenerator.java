package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.communication.ServerResponse;

import java.util.List;

public interface ResponseGenerator {
    ServerResponse generateResponse(String command, CommandStatus status, User user);
    ServerResponse generateResponse(String command, CommandStatus status, ChatRoom chatRoom);
    ServerResponse generateResponseForChatRooms(String command, CommandStatus status, List<ChatRoom> chatRoomList);

    ServerResponse generateResponse(String command, CommandStatus status, Message message);
    ServerResponse generateResponseForMessages(String command, CommandStatus status, List<Message> messageList);
    ServerResponse generateResponseError(String command, String message);
    ServerResponse generateResponseMessage(String command, String message);
}
