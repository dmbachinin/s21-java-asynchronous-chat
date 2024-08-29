package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.rowMappers.MessageRowMapper;
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

@Component("usersRepository")
public class MessageRepositoryImpl implements MessageRepository<Message>{
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        String sql = "SELECT * FROM messages WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<Message> messages = namedParameterJdbcTemplate.query(
                sql,
                params,
                new MessageRowMapper()
        );
        return messages.isEmpty() ? Optional.empty() : Optional.of(messages.get(0));
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query("SELECT * FROM messages", new MessageRowMapper());
    }

    @Override
    public void save(Message entity) {
        String sql = "INSERT INTO messages(room_id, user_id, content) VALUES (:room_id, :user_id, :content)";
        Map<String, Object> params = new HashMap<>();
        params.put("room_id", entity.getRoomId());
        params.put("user_id", entity.getUserId());
        params.put("content", entity.getContent());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                sql, new MapSqlParameterSource(params), keyHolder, new String[]{"id", "created_at"});

        Map<String, Object> generatedKeys = keyHolder.getKeys();
        if (generatedKeys != null) {
            Long generatedId = (Long) generatedKeys.get("id");
            Timestamp createdAt = (Timestamp) generatedKeys.get("created_at");

            entity.setId(generatedId);
            entity.setCreatedAt(createdAt);
        }
    }

    @Override
    public void update(Message entity) {
        String sql = "UPDATE messages SET room_id = :room_id, " +
                "user_id = :user_id, " +
                "content = :content, " +
                "created_at = :created_at WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        params.put("room_id", entity.getContent());
        params.put("content", entity.getContent());
        params.put("user_id", entity.getUserId());
        params.put("created_at", entity.getCreatedAt());
        namedParameterJdbcTemplate.update(
                sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM messages WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        namedParameterJdbcTemplate.update(
                sql, params);
    }

    @Override
    public List<Message> getMessagesForCurrentRoom(Long roomId, Integer count) {
        String sql = "SELECT * FROM messages WHERE room_id = :room_id LIMIT :count";
        Map<String, Object> params = new HashMap<>();
        params.put("room_id", roomId);
        params.put("count", count);

        return namedParameterJdbcTemplate.query(sql, params, new MessageRowMapper());
    }
}
