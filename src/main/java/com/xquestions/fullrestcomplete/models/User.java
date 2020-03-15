package com.xquestions.fullrestcomplete.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xquestions.fullrestcomplete.security.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class User implements Serializable {

    @PositiveOrZero
    private long id;

    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @NotNull
    @Size(min = 4, max = 50)
    private String firstname;

    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;

    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @NotNull
    private boolean activated;

    @NotNull
    private List<Role> roles;

    public User() {}

    public User(@NotNull @Size(min = 4, max = 50) String username, @NotNull @Size(min = 4, max = 100) String password, @NotNull @Size(min = 4, max = 50) String firstname, @NotNull @Size(min = 4, max = 50) String lastname, @NotNull @Size(min = 4, max = 50) String email, @NotNull boolean activated) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.activated = activated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public boolean isActivated() {
        return activated;
    }

    @JsonProperty
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", activated=" + activated +
                ", roles=" + roles +
                '}';
    }
}
