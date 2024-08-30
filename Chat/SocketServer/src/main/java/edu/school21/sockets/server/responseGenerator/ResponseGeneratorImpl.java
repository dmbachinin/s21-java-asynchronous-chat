package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;

@Component("responseGenerator")
public class ResponseGeneratorImpl implements ResponseGenerator {
    private final String separator = "-----------------------------";
    @Override
    public String generateStartPage() {
        return "Привет от сервера!\n" +
                "1. Войти\n" +
                "2. Зарегистрироваться\n" +
                "3. Выйти\n";
    }

    @Override
    public String generateUserInfo(User user) {
        return "Привет " + user.getName() + "\n";
    }

    @Override
    public String generateChatRooms(List<ChatRoom> chatRoomList) {
        StringJoiner stringJoiner =  new StringJoiner("\n","Список комнат\n", separator);
        for (ChatRoom chatRoom : chatRoomList) {
            stringJoiner.add(generateChatRoomInfoShort(chatRoom));
        }
        return  stringJoiner.toString();
    }

    @Override
    public String generateChatRoomInfoShort(ChatRoom chatRoom) {
        return chatRoom.getName() + "\n";
    }

    @Override
    public String generateChatRoomInfoLong(ChatRoom chatRoom) {
        return "Название: " + chatRoom.getName() + "\n" +
                "Описание: " + chatRoom.getDescription() + "\n" +
                "Дата создания: " + chatRoom.getCreatedAt().toString() + "\n" +
                separator;
    }

    @Override
    public String generateMessages(List<Message> messageList) {
        StringJoiner stringJoiner =  new StringJoiner("\n","", "");
        for (Message message : messageList) {
            stringJoiner.add(generateMessage(message));
        }
        return  stringJoiner.toString();
    }

    @Override
    public String generateMessage(Message message) {
        return message.getContent();
    }
}
