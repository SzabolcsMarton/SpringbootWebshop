package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.RegisterUserDto;
import com.szabolcs.SpringbootWebshop.Dto.UserDto;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.UserNotFoundException;
import com.szabolcs.SpringbootWebshop.Model.Role;
import com.szabolcs.SpringbootWebshop.Model.RoleEnum;
import com.szabolcs.SpringbootWebshop.Model.User;
import com.szabolcs.SpringbootWebshop.Repository.RoleRepository;
import com.szabolcs.SpringbootWebshop.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        return user.get();
    }

    @Override
    public User registerUser(RegisterUserDto userDto) {
//        if(userRepository.existsByEmail(userDto.email())){
//            throw new UserAlreadyExistsException("User already exists with email: " + userDto.email());
//        }
        validatePasswordsMatch(userDto);
        User newUser = new User();
        mapUserDtoToUser(newUser, mapRegisterDtoToUserDto(userDto));
        newUser.setPassword(userDto.passwordOne());
        setBasicUserRole(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public void updateUser(UserDto userDto, long id) {
        Optional<User> updatedUser = userRepository.findById(id);
        if (updatedUser.isEmpty()) {
            throw new UserNotFoundException("No user to update with id :" + id);
        }
        userRepository.save(mapUserDtoToUser(updatedUser.get(), userDto));
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> updatedUser = userRepository.findById(id);
        if (updatedUser.isEmpty()) {
            throw new UserNotFoundException("No user to update with id :" + id);
        }
        userRepository.deleteById(id);
    }

//    public Role createRole(RoleEnum roleEnum) {
//        Role role = new Role();
//        role.setRoleName(roleEnum.toString());
//        return roleRepository.save(role);
//    }

    private UserDto mapRegisterDtoToUserDto(RegisterUserDto regDto) {
        return new UserDto(regDto.firstName(), regDto.lastName(), regDto.email(), regDto.address());
    }

    private void validatePasswordsMatch(RegisterUserDto regDto) {
        if (!regDto.passwordOne().equals(regDto.passwordTwo())) {
            throw new IllegalArgumentException("Passwords don't match");
        }
    }

    private void setBasicUserRole(User user) {
        user.addRole(roleRepository.findByRole(RoleEnum.USER.toString()));
    }

}
