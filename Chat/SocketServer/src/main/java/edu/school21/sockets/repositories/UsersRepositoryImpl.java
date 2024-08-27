package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Component("usersRepository")
public class UsersRepositoryImpl implements UsersRepository<User>{
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password_hash")
            );
        }
    }
    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<User> users = namedParameterJdbcTemplate.query(
                sql,
                params,
                new UserRowMapper()
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO users(email, password_hash) VALUES (:email, :password_hash)";
        Map<String, Object> params = new HashMap<>();
        params.put("email", entity.getEmail());
        params.put("password_hash", entity.getPasswordHash());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                sql, new MapSqlParameterSource(params), keyHolder, new String[]{"id"});

        Number genId = keyHolder.getKey();
        if (genId != null) {
            entity.setId((Long) genId);
        }
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET email = :email, password_hash = :password_hash WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        params.put("email", entity.getEmail());
        params.put("password_hash", entity.getPasswordHash());
        namedParameterJdbcTemplate.update(
                sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        namedParameterJdbcTemplate.update(
                sql, params);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        List<User> users = namedParameterJdbcTemplate.query(
                sql,
                params,
                new UserRowMapper()
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
}
