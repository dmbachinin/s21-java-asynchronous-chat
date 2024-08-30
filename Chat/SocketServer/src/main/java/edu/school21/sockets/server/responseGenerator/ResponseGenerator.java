package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import java.util.List;

public interface ResponseGenerator {
    String generateStartPage();
    String generateUserInfo(User user);
    String generateChatRooms(List<ChatRoom> chatRoomList);
    String generateChatRoomInfoShort(ChatRoom chatRoom);
    String generateChatRoomInfoLong(ChatRoom chatRoom);
    String generateMessages(List<Message> messageList);
    String generateMessage(Message message);
}
