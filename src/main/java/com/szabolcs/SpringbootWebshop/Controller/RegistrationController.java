package com.szabolcs.SpringbootWebshop.Controller;

import com.szabolcs.SpringbootWebshop.Dto.RegisterUserDto;
import com.szabolcs.SpringbootWebshop.Model.User;
import com.szabolcs.SpringbootWebshop.Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User registerUser(@RequestBody RegisterUserDto userDto) {
        return userService.registerUser(userDto);
    }
}
