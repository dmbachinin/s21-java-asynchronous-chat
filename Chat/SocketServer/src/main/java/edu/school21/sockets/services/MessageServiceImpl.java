package edu.school21.sockets.services;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("messageService")
public class MessageServiceImpl implements MessageService{

    private final MessageRepository<Message> messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository<Message> messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> sendMessage(Long roomId, Long senderId, String content) {
        Message newMessage = new Message();
        newMessage.setRoomId(roomId);
        newMessage.setUserId(senderId);
        newMessage.setContent(content);
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
