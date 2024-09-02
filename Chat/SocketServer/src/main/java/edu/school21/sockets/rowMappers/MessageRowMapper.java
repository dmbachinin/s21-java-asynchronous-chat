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
        String messagePrefixName = Message.getTABLE_NAME();
        message.setId(resultSet.getLong(messagePrefixName + "_id"));
        message.setContent(resultSet.getString(messagePrefixName + "_content"));
        message.setCreatedAt(resultSet.getTimestamp(messagePrefixName + "_created_at"));

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

        message.setRoom(chatRoom);
        message.setUser(user);

        return message;

    }
}