package com.xquestions.fullrestcomplete.services;

import com.xquestions.fullrestcomplete.models.User;
import com.xquestions.fullrestcomplete.security.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<List<User>> list() {
        String query = "select * from users";
        return Optional.of(jdbcTemplate.query(query, new Object[] {}, new UserRowMapper()));
    }

    public Optional<User> findByUsername(String username) {
        String query = "select * from users where username = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(jdbcTemplate.query(query, new Object[]{username}, new UserRowMapper()))
        );
    }

    public Optional<User> findById(int userId) {
        String query = "select * from users where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(jdbcTemplate.query(query, new Object[]{userId}, new UserRowMapper()))
        );
    }

    public void save(User user) {
        String sql = "insert into users(username, password, email, firstname, lastname, activated, roles) values" +
                "(:username, :password, :email, :firstname, :lastname, :activated, :roles);";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail())
                .addValue("firstname", user.getFirstname())
                .addValue("lastname", user.getLastname())
                .addValue("activated", user.isActivated())
                .addValue("roles", user.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","))
                );

        namedParameterJdbcTemplate.update(sql, namedParameters);

    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();

            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setActivated(rs.getBoolean("activated"));
            user.setRoles(Arrays.stream(rs.getString("roles").split(","))
                    .map(Role::valueOf).collect(Collectors.toList()));

            return user;
        }
    }
}
