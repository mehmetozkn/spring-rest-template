package com.resttemplate.REST.Template.service;


import com.resttemplate.REST.Template.dto.UserDto;
import com.resttemplate.REST.Template.dto.converter.UserDtoConverter;
import com.resttemplate.REST.Template.exception.UserNotFoundException;
import com.resttemplate.REST.Template.model.User;
import com.resttemplate.REST.Template.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter converter;


    public UserService(UserRepository userRepository, UserDtoConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    public UserDto createUser(User user) {
        return converter.convertToUserDto(userRepository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(converter::convertToUserDto)
                .collect(Collectors.toList());
    }


    protected User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User could not find by id: " + id));

    }

    public UserDto getUserById(Long id) {
        return converter.convertToUserDto(findUserById(id));
    }


    public User updateUser(Long id, UserDto user) {
        User updateUser = isUserExist(id);
        updateUser.setName(user.getName());
        updateUser.setSurname(user.getSurname());
        return userRepository.save(updateUser);

    }

    public User deleteUser(Long id) {
        User user = isUserExist(id);
        userRepository.delete(user);
        return user;

    }


    private User isUserExist(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("User could not find by id :" + id);
        }
        return user;
    }


}
