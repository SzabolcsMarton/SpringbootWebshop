package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.UserDto;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.UserAlreadyExistsException;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.UserNotFoundException;
import com.szabolcs.SpringbootWebshop.Model.User;
import com.szabolcs.SpringbootWebshop.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static User mapUserDtoToUser(User user, UserDto userDto) {
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setAddress(userDto.address());
        user.setEmail(userDto.email());
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users at all");
        }
        return users;
    }

    @Override
    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found with id: " + id);
        }
        return user.get();
    }

    @Override
    public User registerUser(UserDto userDto) {
        if(userRepository.existsByEmail(userDto.email())){
            throw new UserAlreadyExistsException("User already exists with email: " + userDto.email());
        }
        User newUser = new User();
        mapUserDtoToUser(newUser, userDto);
        return userRepository.save(newUser);
    }

    @Override
    public void updateUser(UserDto userDto, long id) {
        Optional<User> updatedUser = userRepository.findById(id);
        if(updatedUser.isEmpty()){
            throw new UserNotFoundException("No user to update with id :" + id);
        }
        userRepository.save(mapUserDtoToUser(updatedUser.get(), userDto));
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> updatedUser = userRepository.findById(id);
        if(updatedUser.isEmpty()){
            throw new UserNotFoundException("No user to update with id :" + id);
        }
        userRepository.deleteById(id);
    }



}
