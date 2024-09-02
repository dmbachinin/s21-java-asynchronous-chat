package edu.school21.sockets.models;

import java.sql.Timestamp;

public class Message {
    private static final String TABLE_NAME = "messages";
    private static final String[] COLUMN_NAME = {"id", "room_id", "user_id", "content", "created_at"};
    private Long id;
    private ChatRoom room;
    private User user;
    private String content;
    private Timestamp createdAt;

    public Message() {}

    public Message(Long id, String content, Timestamp created_at) {
        this.id = id;
        this.content = content;
        this.createdAt = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public static String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public static String[] getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", room=" + room +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
