package edu.school21.sockets.services;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatRoomsRepository;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("messageService")
public class MessageServiceImpl implements MessageService{

    private final MessageRepository<Message> messageRepository;
    private final ChatRoomsRepository<ChatRoom> chatRoomsRepository;
    private final UsersRepository<User> usersRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository<Message> messageRepository,
                              ChatRoomsRepository<ChatRoom> chatRoomsRepository,
                              UsersRepository<User> usersRepository) {
        this.messageRepository = messageRepository;
        this.chatRoomsRepository = chatRoomsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<Message> sendMessage(Long roomId, Long senderId, String content) {
        Message newMessage = new Message();
        newMessage.setContent(content);
        newMessage.setUser(usersRepository.findById(senderId).orElseGet(User::new));
        newMessage.setRoom(chatRoomsRepository.findById(roomId).orElseGet(ChatRoom::new));
        Optional<Message> result = Optional.empty();
        try {
            messageRepository.save(newMessage);
            result = Optional.of(newMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Message> getMessagesByRoom(Long roomId, int page, int size) {
        return messageRepository.getMessagesByRoom(roomId, page, size);
    }

    @Override
    public Optional<Message> getMessageById(Long messageId) {
        return messageRepository.findById(messageId);
    }
}
