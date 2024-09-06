package edu.school21.sockets.rowMappers;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        String userPrefixName = User.getTABLE_NAME();
        user.setId(resultSet.getLong(userPrefixName + "_id"));
        user.setName(resultSet.getString(userPrefixName + "_name"));
        user.setEmail(resultSet.getString(userPrefixName + "_email"));
        user.setPasswordHash(resultSet.getString(userPrefixName + "_password_hash"));

        return user;
    }
}
