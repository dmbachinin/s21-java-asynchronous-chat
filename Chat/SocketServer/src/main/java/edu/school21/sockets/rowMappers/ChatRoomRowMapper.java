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
        String chatRoomPrefixName = ChatRoom.getTABLE_NAME();
        chatRoom.setId(resultSet.getLong(chatRoomPrefixName + "_id"));
        chatRoom.setName(resultSet.getString(chatRoomPrefixName + "_name"));
        chatRoom.setDescription(resultSet.getString(chatRoomPrefixName + "_description"));
        chatRoom.setCreatedAt(resultSet.getTimestamp(chatRoomPrefixName + "_created_at"));

        User user = new User();
        String userPrefixName = User.getTABLE_NAME();
        user.setId(resultSet.getLong(userPrefixName + "_id"));
        user.setName(resultSet.getString(userPrefixName + "_name"));
        user.setEmail(resultSet.getString(userPrefixName + "_email"));
        user.setPasswordHash(resultSet.getString(userPrefixName + "_password_hash"));

        chatRoom.setCreator(user);

        return chatRoom;
    }
}