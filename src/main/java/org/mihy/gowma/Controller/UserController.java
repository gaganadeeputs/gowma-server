package org.mihy.gowma.Controller;

import org.mihy.gowma.model.User;
import org.mihy.gowma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("api/v1/user")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("api/v1/user/{userId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Integer userId) {
            return userService.get(userId);
    }

    @PutMapping("/user")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user) {

        return userService.update(user);
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathParam(value = "userId") int userId) {

        userService.delete(userId);

    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return null;
    }


}
