package edu.school21.sockets.repositories;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.rowMappers.ChatRoomRowMapper;
import edu.school21.sockets.rowMappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("chatRoomsRepository")
public class ChatRoomsRepositoryImpl implements ChatRoomsRepository<ChatRoom> {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ChatRoomsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllConnectedUser(Long roomId) {
        String sql = "SELECT "
                +  RequestBuilder.generateColumnNames("u", User.getCOLUMN_NAME(), User.getTABLE_NAME())
                + " FROM users u " +
                "JOIN user_chat_rooms ucr ON ucr.user_id = u.id AND ucr.room_id = :roomId " +
                "ORDER BY ucr.id";
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);

        return namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
    }

    @Override
    public List<ChatRoom> findUserChatRooms(Long userId) {
        String sql = "SELECT "
                + RequestBuilder.generateColumnNames("u1", User.getCOLUMN_NAME(), User.getTABLE_NAME())
                + ", "
                + RequestBuilder.generateColumnNames("c", ChatRoom.getCOLUMN_NAME(), ChatRoom.getTABLE_NAME())
                + " FROM users u " +
                "JOIN user_chat_rooms ucr ON ucr.user_id = u.id " +
                "JOIN  chat_rooms c ON c.id = ucr.room_id " +
                "JOIN  users u1 ON c.creator_id = u1.id " +
                "WHERE u.id = :userId ORDER BY c.id";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return  namedParameterJdbcTemplate.query(sql, params, new ChatRoomRowMapper());
    }

    @Override
    public void addUserToRoom(Long roomId, Long userId) {
        String sql = "SELECT COUNT(*) FROM user_chat_rooms WHERE user_id = :userId AND room_id = :roomId";
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);
        params.put("userId", userId);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        if (count != null && count > 0) {
            removeUserFromRoom(roomId, userId);
        }

        sql = "INSERT INTO user_chat_rooms(user_id, room_id) VALUES (:userId, :roomId)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void removeUserFromRoom(Long roomId, Long userId) {
        String sql = "DELETE FROM user_chat_rooms WHERE user_id = :userId AND room_id = :roomId ";
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);
        params.put("userId", userId);
        int affectedRows = namedParameterJdbcTemplate.update(sql, params);

        if (affectedRows == 0) {
            throw new DataIntegrityViolationException("");
        }
    }

    @Override
    public Optional<ChatRoom> getLastVisitRoom(Long userId) {
        String sql = "SELECT "
                + RequestBuilder.generateColumnNames("u", User.getCOLUMN_NAME(), User.getTABLE_NAME())
                + ", "
                + RequestBuilder.generateColumnNames("c", ChatRoom.getCOLUMN_NAME(), ChatRoom.getTABLE_NAME())
                + ", ucr.id  FROM user_chat_rooms ucr " +
                "JOIN chat_rooms c ON ucr.room_id = c.id " +
                "JOIN users u ON c.creator_id = u.id " +
                "WHERE ucr.user_id = :user_id " +
                "ORDER BY ucr.id DESC LIMIT 1";
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        List<ChatRoom> chatRooms = namedParameterJdbcTemplate.query(
                sql,
                params,
                new ChatRoomRowMapper()
        );
        return chatRooms.isEmpty() ? Optional.empty() : Optional.of(chatRooms.get(0));
    }

    @Override
    public Optional<ChatRoom> findById(Long id) {
        String sql = "SELECT "
                + RequestBuilder.generateColumnNames("u", User.getCOLUMN_NAME(), User.getTABLE_NAME())
                + ", "
                + RequestBuilder.generateColumnNames("c", ChatRoom.getCOLUMN_NAME(), ChatRoom.getTABLE_NAME())
                + " FROM chat_rooms c " +
                "JOIN users u ON c.creator_id = u.id " +
                "WHERE c.id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<ChatRoom> chatRooms = namedParameterJdbcTemplate.query(
                sql,
                params,
                new ChatRoomRowMapper()
        );
        return chatRooms.isEmpty() ? Optional.empty() : Optional.of(chatRooms.get(0));
    }

    @Override
    public List<ChatRoom> findAll() {
        String sql = "SELECT "
                + RequestBuilder.generateColumnNames("u", User.getCOLUMN_NAME(), User.getTABLE_NAME())
                + ", "
                + RequestBuilder.generateColumnNames("c", ChatRoom.getCOLUMN_NAME(), ChatRoom.getTABLE_NAME())
                + " FROM chat_rooms c " +
                "JOIN users u ON c.creator_id = u.id";
        return jdbcTemplate.query(sql, new ChatRoomRowMapper());
    }

    @Override
    public void save(ChatRoom entity) {
        String sql = "INSERT INTO chat_rooms(creator_id, name, description) " +
                "VALUES (:creator_id, :name, :description)";

        Map<String, Object> params = new HashMap<>();
        params.put("creator_id", entity.getCreator().getId());
        params.put("name", entity.getName());
        params.put("description", entity.getDescription());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource(params),
                keyHolder,
                new String[]{"id", "created_at"}
        );

        Map<String, Object> generatedKeys = keyHolder.getKeys();
        if (generatedKeys != null) {
            Long generatedId = (Long) generatedKeys.get("id");
            Timestamp createdAt = (Timestamp) generatedKeys.get("created_at");

            entity.setId(generatedId);
            entity.setCreatedAt(createdAt);
        }
    }

    @Override
    public void update(ChatRoom entity) {
        String sql = "UPDATE chat_rooms SET " +
                "creator_id = :creator_id, " +
                "name = :name, " +
                "description = :description, " +
                "created_at = :created_at " +
                "WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        params.put("creator_id", entity.getCreator().getId());
        params.put("name", entity.getName());
        params.put("description", entity.getDescription());
        params.put("created_at", entity.getCreatedAt());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM chat_rooms WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        int affectedRows = namedParameterJdbcTemplate.update(sql, params);
        if (affectedRows == 0) {
            throw new DataIntegrityViolationException("");
        }
    }
}
