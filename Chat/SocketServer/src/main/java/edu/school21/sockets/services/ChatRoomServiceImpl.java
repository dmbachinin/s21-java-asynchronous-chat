package edu.school21.sockets.services;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("chatRoomService")
public class ChatRoomServiceImpl implements ChatRoomService{
    private final ChatRoomsRepository<ChatRoom> chatRoomChatRoomsRepository;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomsRepository<ChatRoom> chatRoomChatRoomsRepository) {
        this.chatRoomChatRoomsRepository = chatRoomChatRoomsRepository;
    }

    @Override
    public Optional<ChatRoom> createChatRoom(Long creatorId, String name, String description) {
        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setName(name);
        newChatRoom.setDescription(description);
        newChatRoom.setCreatorId(creatorId);
        Optional<ChatRoom> result = Optional.empty();
        try {
            chatRoomChatRoomsRepository.save(newChatRoom);
            result = Optional.of(newChatRoom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean deleteChatRoom(Long roomId) {
        boolean deleteStatus = true;
        try {
            chatRoomChatRoomsRepository.delete(roomId);
        } catch (Exception e) {
            e.printStackTrace();
            deleteStatus = false;
        }
        return deleteStatus;
    }

    @Override
    public Optional<ChatRoom> findChatRoomById(Long roomId) {
        return chatRoomChatRoomsRepository.findById(roomId);
    }

    @Override
    public boolean addUserToRoom(Long roomId, Long userId) {
        boolean addStatus = false;
        try {
            addStatus = chatRoomChatRoomsRepository.addUserToRoom(roomId, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addStatus;
    }

    @Override
    public boolean removeUserFromRoom(Long roomId, Long userId) {
        return chatRoomChatRoomsRepository.removeUserFromRoom(roomId, userId);
    }

    @Override
    public List<ChatRoom> findUserChatRooms(Long userId) {
        return chatRoomChatRoomsRepository.findUserChatRooms(userId);
    }

    @Override
    public List<User> getAllConnectedUser(Long roomId) {
        return chatRoomChatRoomsRepository.getAllConnectedUser(roomId);
    }
}
