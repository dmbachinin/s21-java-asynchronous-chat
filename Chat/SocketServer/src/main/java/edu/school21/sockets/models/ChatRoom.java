package edu.school21.sockets.models;

import java.sql.Timestamp;

public class ChatRoom {
    private Long id;
    private Long creatorId;
    private String name;
    private String description;
    private Timestamp createdAt;

    public ChatRoom() {}

    public ChatRoom(Long id, Long creator_id, String name, String description, Timestamp created_at) {
        this.id = id;
        this.creatorId = creator_id;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", creator_id=" + creatorId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created_at=" + createdAt +
                '}';
    }
}
