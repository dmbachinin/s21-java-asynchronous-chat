package edu.school21.sockets.server.responseGenerator.responses;

import edu.school21.sockets.models.ChatRoom;

import java.util.List;

public class ChatRoomsResponse implements Response<List<ChatRoom>> {

    @Override
    public String generate(List<ChatRoom> entity) {
        return null;
    }
}
