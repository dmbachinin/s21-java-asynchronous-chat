package edu.school21.sockets.rowMappers;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Message(
                resultSet.getLong("id"),
                resultSet.getLong("room_id"),
                resultSet.getLong("user_id"),
                resultSet.getString("content"),
                resultSet.getTimestamp("created_at")
        );
    }
}