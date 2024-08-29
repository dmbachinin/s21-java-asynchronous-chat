package edu.school21.sockets.repositories;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.rowMappers.ChatRoomRowMapper;
import edu.school21.sockets.rowMappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        String sql = "SELECT us.* FROM users us " +
                "JOIN user_chat_rooms AS ucr ON ucr.user_id = us.id AND ucr.room_id = :roomId ";
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);

        return namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
    }

    @Override
    public void addUserIntoChatRoom(Long roomId, Long userId) {
        String sql = "INSERT INTO user_chat_rooms(user_id, room_id) VALUES (:roomId, :userId)";
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);
        params.put("userId", userId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<ChatRoom> findById(Long id) {
        String sql = "SELECT * FROM chat_rooms WHERE id = :id";
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
        String sql = "SELECT * FROM chat_rooms";
        return jdbcTemplate.query(sql, new ChatRoomRowMapper());
    }

    @Override
    public void save(ChatRoom entity) {
        String sql = "INSERT INTO chat_rooms(creator_id, name, description) " +
                "VALUES (:creator_id, :name, :description)";

        Map<String, Object> params = new HashMap<>();
        params.put("creator_id", entity.getCreatorId());
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
        params.put("creator_id", entity.getCreatorId());
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
        namedParameterJdbcTemplate.update(sql, params);
    }
}
