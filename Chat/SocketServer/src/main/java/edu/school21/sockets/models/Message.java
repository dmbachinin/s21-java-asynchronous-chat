package edu.school21.sockets.models;

import java.sql.Timestamp;

public class Message {
    private Long id;
    private Long roomId;
    private Long userId;
    private String content;
    private Timestamp createdAt;

    public Message() {}

    public Message(Long id, Long room_id, Long user_id, String content, Timestamp created_at) {
        this.id = id;
        this.roomId = room_id;
        this.userId = user_id;
        this.content = content;
        this.createdAt = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
