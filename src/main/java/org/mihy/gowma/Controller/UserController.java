package org.mihy.gowma.Controller;

import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.User;
import org.mihy.gowma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(EndPoints.User.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping(EndPoints.User.USER_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable(value = EndPoints.PathVariable.USER_ID ) Integer userId) {
            return userService.get(userId);
    }

    @PutMapping(EndPoints.User.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user) {

        return userService.update(user);
    }

    @DeleteMapping(EndPoints.User.USER_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathParam(value = EndPoints.PathVariable.USER_ID) int userId) {

        userService.delete(userId);

    }

    @GetMapping(EndPoints.User.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return null;
    }


}
