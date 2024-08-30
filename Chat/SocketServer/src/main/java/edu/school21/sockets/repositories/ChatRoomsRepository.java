package edu.school21.sockets.repositories;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;

import java.util.List;

public interface ChatRoomsRepository<T> extends CrudRepository<T> {
    List<User> getAllConnectedUser(Long roomId);

    List<ChatRoom> findUserChatRooms(Long userId);

    boolean addUserToRoom(Long roomId, Long userId);

    boolean updateLastRoom(Long userId, Long roomId);

    boolean removeUserFromRoom(Long roomId, Long userId);
}
