package edu.school21.sockets.models;

public class User {
    private static final String TABLE_NAME = "user";
    private static final String[] COLUMN_NAME = {"id", "name", "email", "password_hash"};
    private Long id;
    private String email;
    private String name;
    private String passwordHash;

    public User() {
        this.id = null;
        this.email = null;
        this.name = null;
        this.passwordHash = null;
    }


    public User(Long id, String name, String email, String passwordHash) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public static String[] getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
