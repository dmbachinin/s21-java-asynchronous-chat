package edu.school21.sockets.rowMappers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowMapper implements RowMapper<Message> {



    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        Message message = new Message();
        message.setId(resultSet.getLong("m.id"));
        message.setContent(resultSet.getString("m.content"));
        message.setCreatedAt(resultSet.getTimestamp("m.created_at"));

        User user = new User();
        user.setId(resultSet.getLong("u.id"));
        user.setName(resultSet.getString("u.name"));
        user.setEmail(resultSet.getString("u.email"));
        user.setPasswordHash(resultSet.getString("u.password_hash"));
        message.setUser(user);

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(resultSet.getLong("c.id"));
        chatRoom.setName(resultSet.getString("c.name"));
        chatRoom.setDescription(resultSet.getString("c.description"));
        chatRoom.setCreatedAt(resultSet.getTimestamp("c.created_at"));
        message.setRoom(chatRoom);

        return message;

    }
}