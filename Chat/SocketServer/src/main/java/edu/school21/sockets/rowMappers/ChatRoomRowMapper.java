package edu.school21.sockets.rowMappers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatRoomRowMapper implements RowMapper<ChatRoom> {

    @Override
    public ChatRoom mapRow(ResultSet resultSet, int i) throws SQLException {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(resultSet.getLong("c.id"));
        chatRoom.setName(resultSet.getString("c.name"));
        chatRoom.setDescription(resultSet.getString("c.description"));
        chatRoom.setCreatedAt(resultSet.getTimestamp("c.created_at"));

        User user = new User();
        user.setId(resultSet.getLong("u.id"));
        user.setName(resultSet.getString("u.name"));
        user.setEmail(resultSet.getString("u.email"));
        user.setPasswordHash(resultSet.getString("u.password_hash"));

        chatRoom.setCreator(user);

        return chatRoom;
    }
}