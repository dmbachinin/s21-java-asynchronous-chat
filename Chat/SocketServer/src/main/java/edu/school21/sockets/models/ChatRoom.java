package edu.school21.sockets.models;

import java.sql.Timestamp;

public class ChatRoom {
    private static final String TABLE_NAME = "chat_rooms";
    private static final String[] COLUMN_NAME = {"id", "name", "creator_id", "description", "created_at"};
    private Long id;
    private User creator;
    private String name;
    private String description;
    private Timestamp createdAt;

    public ChatRoom() {
        id = null;
        creator = new User();
        name = null;
        description = null;
        createdAt = null;
    }

    public ChatRoom(Long id, String name, String description, Timestamp created_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "ChatRoom{" +
                "id=" + id +
                ", creator=" + creator +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created_at=" + createdAt +
                '}';
    }
}
