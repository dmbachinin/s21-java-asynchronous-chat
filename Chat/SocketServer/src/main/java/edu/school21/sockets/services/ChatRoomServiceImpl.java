package edu.school21.sockets.services;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatRoomsRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("chatRoomService")
public class ChatRoomServiceImpl implements ChatRoomService{
    private final ChatRoomsRepository<ChatRoom> chatRoomChatRoomsRepository;
    private final UsersRepository<User> usersRepository;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomsRepository<ChatRoom> chatRoomChatRoomsRepository,
                               UsersRepository<User> usersRepository) {
        this.chatRoomChatRoomsRepository = chatRoomChatRoomsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<ChatRoom> createChatRoom(Long creatorId, String name, String description) {
        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setName(name);
        newChatRoom.setDescription(description);
        User user = usersRepository.findById(creatorId).orElseGet(User::new);
        if (user.getId() == null) {
            return Optional.empty();
        }
        newChatRoom.setCreator(user);
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
        } catch (DataIntegrityViolationException e) {
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
        boolean addStatus = true;
        try {
            chatRoomChatRoomsRepository.addUserToRoom(roomId, userId);
        } catch (DataIntegrityViolationException e) {
            addStatus = false;
        }
        return addStatus;
    }

    @Override
    public boolean removeUserFromRoom(Long roomId, Long userId) {
        boolean removeResult = true;
        try {
            chatRoomChatRoomsRepository.removeUserFromRoom(roomId, userId);
        } catch (DataIntegrityViolationException e) {
            removeResult = false;
        }
        return removeResult;
    }

    @Override
    public List<ChatRoom> findUserChatRooms(Long userId) {
        return chatRoomChatRoomsRepository.findUserChatRooms(userId);
    }

    @Override
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomChatRoomsRepository.findAll();
    }

    @Override
    public List<User> getAllConnectedUser(Long roomId) {
        return chatRoomChatRoomsRepository.getAllConnectedUser(roomId);
    }

    @Override
    public Optional<ChatRoom> getLastVisitRoom(Long userId) {
        return chatRoomChatRoomsRepository.getLastVisitRoom(userId);
    }
}
