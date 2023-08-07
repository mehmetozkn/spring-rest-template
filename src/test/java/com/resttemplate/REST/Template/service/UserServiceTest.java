package com.resttemplate.REST.Template.service;
import com.resttemplate.REST.Template.dto.UserDto;
import com.resttemplate.REST.Template.dto.converter.UserDtoConverter;
import com.resttemplate.REST.Template.exception.UserNotFoundException;
import com.resttemplate.REST.Template.model.User;
import com.resttemplate.REST.Template.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userDtoConverter = mock(UserDtoConverter.class);
        userService = new UserService(userRepository, userDtoConverter);
    }

    @Test
    public void testCreateUser_shouldReturnUser(){
        User user = new User(1L,"Mehmet", "Özkan");
        UserDto userDto = new UserDto(1L,"Mehmet", "Özkan");

        when(userDtoConverter.convertToUserDto(user)).thenReturn(userDto);
        when(userRepository.save(any())).thenReturn(user);

        UserDto result = userService.createUser(user);
        assertEquals(result, userDto);
    }

    @Test
    public void testFindByUserId_whenUserIdExists_shouldReturnUser() {
        User user = new User(1, "Mehmet", "Özkan");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User result = userService.findByUserId(1L);
        assertEquals(result, user);
    }

    @Test
    public void testFindByUserId_whenUserIdDoesNotExists_shouldThrowUserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findByUserId(1L));
    }

    @Test
    public void testGetUserById_whenUserIdExists_shouldReturnUser(){
        User user = new User();
        UserDto userDto = new UserDto(1L,"Mehmet", "Özkan");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userDtoConverter.convertToUserDto(any())).thenReturn(userDto);

        UserDto result = userService.getUserById(1L);
        assertEquals(result, userDto);
    }

    @Test
    public void testGetUserById_whenUserIdDoesNotExists_shouldThrowUserNotFoundException() {
       when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
        verifyNoInteractions(userDtoConverter);
    }

    @Test
    public void testGetAllUsers_shouldReturnResponseEntityUser(){
        List<User> users = new ArrayList<>();
        User user1 = new User(1L,"Mehmet", "Özkan");
        User user2 = new User(2L,"Ahmet", "Ak");

        users.add(user1);
        users.add(user2);

        UserDto userDto1 = new UserDto(1L,"Mehmet", "Özkan");
        UserDto userDto2 = new UserDto(2L,"Ahmet", "Ak");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
      when(userDtoConverter.convertToUserDto(user1)).thenReturn(userDto1);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user2));
       when(userDtoConverter.convertToUserDto(user2)).thenReturn(userDto2);

        List<UserDto> result = userService.getAllUsers();
        result.add(userDto1);
        result.add(userDto2);

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(users.get(0).getName(), result.get(0).getName());

    }

    @Test
    public void testUpdateUser_whenUserIdExists_sholdReturnUser(){
        User user = new User(2L ,"Ahmet","Ak");
        UserDto userDto = new UserDto(1L ,"Mehmet","Ozkan");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L,userDto);

        assertEquals(result.getName(), user.getName());
        assertEquals(result.getSurname(), user.getSurname());

    }

    @Test
    public void testDeleteUser_shouldReturnUser(){
        User user = new User(1L,"Mehmet", "Özkan");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.deleteUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(user.getName(), result.getName());

        verify(userRepository, times(1)).delete(user);

    }

    @Test
    public void testDeleteUserNotFound_shouldThrowUserNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, ()->
                ReflectionTestUtils.invokeMethod(userService, "deleteUser", 1L));

    }

    @Test
    public void testUserIdExists_shouldReturnUser(){
        User user = new User(1L, "Mehmet", "Özkan");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User result = ReflectionTestUtils.invokeMethod(userService, "isUserExists", 1L);

        assertNotNull(result);
        assertEquals(user, result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getSurname(), result.getSurname());
    }

    @Test
    public void testUserIdDoesNotExists_shouldThrowUserNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                ()->ReflectionTestUtils.invokeMethod(userService, "isUserExists", 1L));
    }

}