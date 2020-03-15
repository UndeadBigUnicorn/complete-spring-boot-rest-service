package com.xquestions.fullrestcomplete.controllers;

import com.xquestions.fullrestcomplete.exceptions.UserNotFoundException;
import com.xquestions.fullrestcomplete.models.User;
import com.xquestions.fullrestcomplete.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UsersController {

    @Resource(name = "userService")
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> retrieveAllUsers() {
        return userService.list()
                .orElseThrow(new UserNotFoundException("There are no users"));
    }

    @GetMapping("/{id}")
    public User retrieveUser(@PathVariable int id) {
        return userService.findById(id)
                .orElseThrow(new UserNotFoundException("User was not found"));
    }

}
