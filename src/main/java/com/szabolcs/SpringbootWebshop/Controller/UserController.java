package com.szabolcs.SpringbootWebshop.Controller;

import com.szabolcs.SpringbootWebshop.Dto.RegisterUserDto;
import com.szabolcs.SpringbootWebshop.Dto.UserDto;
import com.szabolcs.SpringbootWebshop.Model.User;
import com.szabolcs.SpringbootWebshop.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }
    //Ide majd kell tenni security - role - admint
    @PostMapping("/register")
    public User createUser(@RequestBody RegisterUserDto regDto) {
        return userService.registerUser(regDto);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable long id, @RequestBody UserDto user) {
        userService.updateUser(user,id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }



}
