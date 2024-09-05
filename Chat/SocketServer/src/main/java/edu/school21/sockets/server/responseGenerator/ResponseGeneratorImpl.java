package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.responseGenerator.responses.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("responseGenerator")
public class ResponseGeneratorImpl implements ResponseGenerator {
    private final String separator = "-----------------------------";

    @Override
    public ServerResponse generateResponse(String command, CommandStatus status, User user) {
        String message;
        Map<String, Object> data = new HashMap<>();
        if (Objects.requireNonNull(status) == CommandStatus.OK) {
            message = UserInfoResponse.generateWelcomeMessage(user);
            data.put("userId", user.getId());
        } else {
            message = ErrorResponse.generate("Пользователь не найден");
        }
        return new ServerResponse(command, status, message, data);
    }

    @Override
    public ServerResponse generateResponse(String command, CommandStatus status, ChatRoom chatRoom) {
        String message;
        Map<String, Object> data = new HashMap<>();
        if (Objects.requireNonNull(status) == CommandStatus.OK) {
            message = ChatRoomsResponse.generate(chatRoom);
            data.put("roomId", chatRoom.getId());
        } else {
            message = ErrorResponse.generate("Комната не найдена");
        }
        return new ServerResponse(command, status, message, data);
    }

    @Override
    public ServerResponse generateResponseForChatRooms(String command, CommandStatus status, List<ChatRoom> chatRoomList) {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> chatRooms = new ArrayList<>();
        if (status == CommandStatus.OK) {
            for (ChatRoom chatRoom : chatRoomList) {
                Map<String, Object> chatRoomInfo = new HashMap<>();
                chatRoomInfo.put("roomId", chatRoom.getId());
                chatRoomInfo.put("name", chatRoom.getName());
                chatRooms.add(chatRoomInfo);
            }
            data.put("rooms", chatRooms);
        }
        return new ServerResponse(command, status, null, data);
    }

    @Override
    public ServerResponse generateResponse(String command, CommandStatus status, Message message) {
        return new ServerResponse(command, status, MessageResponse.generate(message), new HashMap<>());
    }

    @Override
    public ServerResponse generateResponseForMessages(String command, CommandStatus status, List<Message> messageList) {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> messages = new ArrayList<>();
        if (status == CommandStatus.OK) {
            for (Message message : messageList) {
                Map<String, Object> chatRoomInfo = new HashMap<>();
                chatRoomInfo.put("roomId", message.getId());
                chatRoomInfo.put("content", message.getContent());
                chatRoomInfo.put("senderName", message.getUser().getName());
                chatRoomInfo.put("createAt", message.getCreatedAt());
                messages.add(chatRoomInfo);
            }
            data.put("messages", messages);
        }
        return new ServerResponse(command, status, null, data);
    }

    @Override
    public ServerResponse generateResponseError(String command, String message) {
        return new ServerResponse(command, CommandStatus.ERROR,
                ErrorResponse.generate(message), new HashMap<>());
    }

    @Override
    public ServerResponse generateResponseMessage(String command, String message) {
        return new ServerResponse(command, CommandStatus.OK,
                InfoResponse.generate(message), new HashMap<>());
    }
}
