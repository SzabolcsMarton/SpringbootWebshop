package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.UserDto;
import com.szabolcs.SpringbootWebshop.Model.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUser();
    User getUserById(long id);
    User createUser(UserDto user);
    void updateUser(UserDto user, long id);
    void deleteUser(long id);

}
