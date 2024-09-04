package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.responseGenerator.responses.ChatRoomsResponse;
import edu.school21.sockets.server.responseGenerator.responses.ErrorResponse;
import edu.school21.sockets.server.responseGenerator.responses.MessageResponse;
import edu.school21.sockets.server.responseGenerator.responses.UserInfoResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component("responseGenerator")
public class ResponseGeneratorImpl implements ResponseGenerator {
    private final String separator = "-----------------------------";

    @Override
    public ServerResponse generateResponse(CommandStatus status, User user) {
        String message;
        Map<String, Object> data = new HashMap<>();
        if (Objects.requireNonNull(status) == CommandStatus.OK) {
            message = UserInfoResponse.generateWelcomeMessage(user);
            data.put("userId", user.getId());
        } else {
            message = ErrorResponse.generate("Пользователь не найден");
        }
        return  new ServerResponse(status, message, data);
    }

    @Override
    public ServerResponse generateResponse(CommandStatus status, ChatRoom chatRoom) {
        String message;
        Map<String, Object> data = new HashMap<>();
        if (Objects.requireNonNull(status) == CommandStatus.OK) {
            message = ChatRoomsResponse.generate(chatRoom);
            data.put("chatId", chatRoom.getId());
        } else {
            message = ErrorResponse.generate("Пользователь не найден");
        }
        return new ServerResponse(status, message, data);
    }

    @Override
    public String generateResponseForChatRooms(CommandStatus status, List<ChatRoom> chatRoomList) {
        return null;
    }

    @Override
    public String generateResponse(CommandStatus status, Message message) {
        return null;
    }

    @Override
    public String generateResponseForMessages(CommandStatus status, List<ChatRoom> messageList) {
        return null;
    }

    @Override
    public ServerResponse generateResponseError(String message) {
        return new ServerResponse(CommandStatus.ERROR,
                ErrorResponse.generate(message), new HashMap<>());
    }

    @Override
    public ServerResponse generateResponseMessage(String message) {
        return new ServerResponse(CommandStatus.OK,
                MessageResponse.generate(message), new HashMap<>());
    }


//
//    @Override
//    public String generateStartPage() {
//        return "Привет от сервера!\n" +
//                "1. Войти\n" +
//                "2. Зарегистрироваться\n" +
//                "3. Выйти\n";
//    }
//
//    @Override
//    public String generateUserInfo(User user) {
//        return "Привет " + user.getName() + "\n";
//    }
//
//    @Override
//    public String generateChatRooms(List<ChatRoom> chatRoomList) {
//        StringJoiner stringJoiner =  new StringJoiner("\n","Список комнат\n", separator);
//        for (ChatRoom chatRoom : chatRoomList) {
//            stringJoiner.add(generateChatRoomInfoShort(chatRoom));
//        }
//        return  stringJoiner.toString();
//    }
//
//    @Override
//    public String generateChatRoomInfoShort(ChatRoom chatRoom) {
//        return chatRoom.getName() + "\n";
//    }
//
//    @Override
//    public String generateChatRoomInfoLong(ChatRoom chatRoom) {
//        return "Название: " + chatRoom.getName() + "\n" +
//                "Описание: " + chatRoom.getDescription() + "\n" +
//                "Дата создания: " + chatRoom.getCreatedAt().toString() + "\n" +
//                separator;
//    }
//
//    @Override
//    public String generateMessages(List<Message> messageList) {
//        StringJoiner stringJoiner =  new StringJoiner("\n","", "");
//        for (Message message : messageList) {
//            stringJoiner.add(generateMessage(message));
//        }
//        return  stringJoiner.toString();
//    }
//
//    @Override
//    public String generateMessage(Message message) {
//        return message.getContent();
//    }
}
