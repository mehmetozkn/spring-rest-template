package com.resttemplate.REST.Template.dto.converter;

import com.resttemplate.REST.Template.dto.UserDto;
import com.resttemplate.REST.Template.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public UserDto convertToUserDto(User from) {
        return new UserDto(
                from.getId(),
                from.getName(),
                from.getSurname());

    }
}
