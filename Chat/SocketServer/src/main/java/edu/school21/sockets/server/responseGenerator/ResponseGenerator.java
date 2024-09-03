package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandStatus;

import java.util.List;

public interface ResponseGenerator {
    String generateResponse(CommandStatus status, User user);
    String generateResponse(CommandStatus status, ChatRoom chatRoom);
    String generateResponseForChatRooms(CommandStatus status, List<ChatRoom> chatRoomList);
    String generateResponse(CommandStatus status, Message message);
    String generateResponseForMessages(CommandStatus status, List<ChatRoom> messageList);
    String generateResponseError(String message);
}
