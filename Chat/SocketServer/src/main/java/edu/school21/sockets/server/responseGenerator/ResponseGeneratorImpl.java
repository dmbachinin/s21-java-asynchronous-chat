package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.responseGenerator.responses.ErrorResponse;
import edu.school21.sockets.server.responseGenerator.responses.UserInfoResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component("responseGenerator")
public class ResponseGeneratorImpl implements ResponseGenerator {
    private final String separator = "-----------------------------";

    private final MessageComposer messageComposer;

    public ResponseGeneratorImpl(MessageComposer messageComposer) {
        this.messageComposer = messageComposer;
    }

    @Override
    public String generateResponse(CommandStatus status, User user) {
        String message;
        Map<String, Object> data = new HashMap<>();
        if (Objects.requireNonNull(status) == CommandStatus.OK) {
            message = new UserInfoResponse().generate(user);
            data.put("id", user.getId());
        } else {
            message = new ErrorResponse().generate("Пользователь не найден");
        }
        return messageComposer.generate(status, message, data);
    }

    @Override
    public String generateResponse(CommandStatus status, ChatRoom chatRoom) {
        return null;
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
    public String generateResponseError(String message) {
        return messageComposer.generate(CommandStatus.ERROR, message, new HashMap<>());
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
