package com.xquestions.fullrestcomplete.controllers;

import com.xquestions.fullrestcomplete.exceptions.BadRequestException;
import com.xquestions.fullrestcomplete.exceptions.UserNotFoundException;
import com.xquestions.fullrestcomplete.models.LoginRequest;
import com.xquestions.fullrestcomplete.models.User;
import com.xquestions.fullrestcomplete.security.AuthenticationManager;
import com.xquestions.fullrestcomplete.security.TokenProvider;
import com.xquestions.fullrestcomplete.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource(name = "userService")
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    private static Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest login, HttpServletRequest request) {
        User user = userService.findByUsername(login.getUsername())
                .orElseThrow(new UserNotFoundException("User with this username was not found"));

        if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );

            List<User> users = (List<User>) request.getSession().getAttribute("LOGGED_USERS");
            //check if users are present in session or not
            if (users == null) {
                users = new ArrayList<>();
                // if users object is not present in session, set users in the request session
                request.getSession().setAttribute("LOGGED_USERS", users);
            }
            users.add(user);
            request.getSession().setAttribute("LOGGED_USERS", users);

            log.info("Logged in users: " + users.toString());

            return ResponseEntity.ok(tokenProvider.generateToken(authentication));
        } else {
            throw new UserNotFoundException("Invalid credentials provided");
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody User user) {

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("User with this username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
//                .fromCurrentRequest()
                .path("/first-login")
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // Just example of using session, we don't need this Logout at all
    @PostMapping("/logout")
    public void logout(@RequestParam int userid, HttpServletRequest request) {
        List<User> users = (List<User>) request.getSession().getAttribute("LOGGED_USERS");
        //check if users are present in session or not
        if (users != null) {
            users = users.stream()
                    .filter(user -> user.getId() != userid)
                    .collect(Collectors.toList());
            request.getSession().setAttribute("LOGGED_USERS", users);
        }
    }

}
