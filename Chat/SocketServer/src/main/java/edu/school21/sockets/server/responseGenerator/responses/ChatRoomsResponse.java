package edu.school21.sockets.server.responseGenerator.responses;

import edu.school21.sockets.models.ChatRoom;

import java.util.List;
import java.util.StringJoiner;

public class ChatRoomsResponse {

    public static String generate(List<ChatRoom> entity) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (int i = 0; i < entity.size(); i++) {
            stringJoiner.add(String.format("%d %s", i+1, entity.get(i)));
        }
        return stringJoiner.toString();
    }

    public static String generate(ChatRoom entity) {
        return String.format("Имя комнаты: %s\nСоздатель комнаты: %s\n Дата создания: %s",
                entity.getName(), entity.getCreator().getName(), entity.getCreatedAt());
    }
}
