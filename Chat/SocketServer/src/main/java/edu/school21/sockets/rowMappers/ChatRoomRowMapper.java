package edu.school21.sockets.rowMappers;

import edu.school21.sockets.models.ChatRoom;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatRoomRowMapper implements RowMapper<ChatRoom> {

    @Override
    public ChatRoom mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ChatRoom(
                resultSet.getLong("id"),
                resultSet.getLong("creator_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created_at")
        );
    }
}