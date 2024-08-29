package edu.school21.sockets.repositories;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;

import java.util.List;

public interface ChatRoomsRepository<T> extends CrudRepository<T> {
    List<User> getAllConnectedUser(Long roomId);

    void addUserIntoChatRoom(Long roomId, Long userId);
}
