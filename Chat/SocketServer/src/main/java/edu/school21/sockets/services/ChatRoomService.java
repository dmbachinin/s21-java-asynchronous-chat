package edu.school21.sockets.services;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {



    Optional<ChatRoom> createChatRoom(Long creatorId, String name, String description);
    boolean deleteChatRoom(Long roomId);
    Optional<ChatRoom> findChatRoomById(Long roomId);
    boolean addUserToRoom(Long roomId, Long userId);
    boolean removeUserFromRoom(Long roomId, Long userId);
    List<ChatRoom> findUserChatRooms(Long userId);

    List<User> getAllConnectedUser(Long roomId);
}
