package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<Message> sendMessage(Long roomId, Long senderId, String content);
    List<Message> getMessagesByRoom(Long roomId, int page, int size);
    Optional<Message> getMessageById(Long messageId);
}
