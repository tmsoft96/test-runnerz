package com.tmsoft.runnerz.run.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRestClient userRestClient;

    public UserController(UserRestClient userRestClient) {
        this.userRestClient = userRestClient;
    }

    @GetMapping("")
    Map<String, Object> findAll() {
        List<User> users =  userRestClient.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("ok", true);
        map.put("data", users);
        return map;
    }

    @GetMapping("/{id}")
    User findById(Integer id) {
        return userRestClient.findById(id);
    }
}
