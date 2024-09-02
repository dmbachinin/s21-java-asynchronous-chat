package edu.school21.sockets.repositories;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;

import java.util.List;
import java.util.Optional;

public interface ChatRoomsRepository<T> extends CrudRepository<T> {
    List<User> getAllConnectedUser(Long roomId);

    List<ChatRoom> findUserChatRooms(Long userId);

    void addUserToRoom(Long roomId, Long userId);

    Optional<ChatRoom> getLastVisitRoom(Long userId);

    boolean removeUserFromRoom(Long roomId, Long userId);
}
