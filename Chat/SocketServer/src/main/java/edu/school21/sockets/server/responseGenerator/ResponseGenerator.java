package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.communication.ServerResponse;

import java.util.List;

public interface ResponseGenerator {
    ServerResponse generateResponse(CommandStatus status, User user);
    ServerResponse generateResponse(CommandStatus status, ChatRoom chatRoom);
    ServerResponse generateResponseForChatRooms(CommandStatus status, List<ChatRoom> chatRoomList);
    String generateResponse(CommandStatus status, Message message);
    String generateResponseForMessages(CommandStatus status, List<ChatRoom> messageList);
    ServerResponse generateResponseError(String message);
    ServerResponse generateResponseMessage(String message);
}
