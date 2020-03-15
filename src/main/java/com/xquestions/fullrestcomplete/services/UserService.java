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
@Service(value = "userService")
public class UserService implements UserDetailsService {

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
        String sql = "insert into users(username, password, enabled, roles) values" +
                "(:username, :password, :enabled, :roles);";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("enabled", user.getEnabled())
                .addValue("roles", user.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","))
                );

        namedParameterJdbcTemplate.update(sql, namedParameters);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = findByUsername(username);
        if(!optionalUser.isPresent()){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        });
        return authorities;
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();

            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setRoles(Arrays.stream(rs.getString("roles").split(","))
                    .map(Role::valueOf).collect(Collectors.toList()));

            return user;
        }
    }
}
