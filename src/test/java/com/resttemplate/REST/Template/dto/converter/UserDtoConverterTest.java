package com.resttemplate.REST.Template.dto.converter;

import com.resttemplate.REST.Template.dto.UserDto;
import com.resttemplate.REST.Template.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDtoConverterTest {

    private UserDtoConverter userDtoConverter;

    @BeforeEach
    void setUp() {
        userDtoConverter = mock(UserDtoConverter.class);

    }

    @Test
    public void testConvertUserDto_shouldReturnUserDto() {
        User user = new User(1L, "Mehmet", "Özkan");
        UserDto userDto = new UserDto(1L, "Mehmet", "Özkan");
        when(userDtoConverter.convertToUserDto(any())).thenReturn(userDto);

        assertEquals(user.getId(), userDto.getId());
    }
}