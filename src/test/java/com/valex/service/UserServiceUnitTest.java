package com.valex.service;

import static com.valex.domain.mother.UserMother.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.UserDto;
import com.valex.domain.exception.ConflictException;
import com.valex.domain.mapper.UserMapper;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceUnitTest {

  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;

  @BeforeEach
  void setUp () {
    openMocks(this);
  }

  @Test
  void whenFindByIdOrThrowWithExistingIdThenReturnAnUser () {
    Optional<User> optionalUser = getOptionalUser();
    User user = optionalUser.get();

    when(userRepository.findById(anyLong())).thenReturn(optionalUser);

    User response = userService.findByIdOrFail(user.getId());

    assertEquals(User.class, response.getClass());
    assertEquals(user.getId(), response.getId());
  }

  @Test
  void whenFindByIdOrThrowWithNoExistingIdThenReturnThrowNotFoundException () {
    when(userRepository.findById(anyLong())).thenReturn(getEmptyOptionalUser());

    try {
      userService.findByIdOrFail(anyLong());

    } catch (Exception e) {
      assertEquals(NotFoundException.class, e.getClass());
      assertEquals("User not found", e.getMessage());
    }
  }

  @Test
  void whenFindAllThenReturnUserList () {
    UserDto userDto = getUserDto();
    User user = getUser();

    when(userRepository.findAll()).thenReturn(List.of(user));
    when(userMapper.modelToDto(anyList())).thenReturn(List.of(userDto));

    List<UserDto> response = userService.findAll();

    verify(userMapper).modelToDto(List.of(user));
    verify(userRepository).findAll();

    assertEquals(1, response.size());
    assertEquals(userDto.getId(), response.get(0).getId());
    assertEquals(UserDto.class, response.get(0).getClass());
  }

  @Test
  void whenCreateThenReturnUserDto () {
    User user = getUser();
    UserDto userDtoWithId = getUserDto();
    UserDto userDtoWithoutId = getUserDtoWithoutId();

    when(userRepository.findByEmail(anyString())).thenReturn(null);
    when(userMapper.dtoToModel(any(UserDto.class))).thenReturn(user);
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(userMapper.modelToDto(any(User.class))).thenReturn(userDtoWithId);

    UserDto response = userService.create(userDtoWithoutId);

    verify(userMapper).dtoToModel(userDtoWithoutId);
    verify(userMapper).modelToDto(user);

    assertEquals(UserDto.class, response.getClass());
    assertEquals(userDtoWithId.getId(), response.getId());
  }

  @Test
  void whenTryCreateExistEmailUserThenReturnConflictException () {
    when(userRepository.findByEmail(anyString())).thenReturn(getUser());

    try {
      userService.create(getUserDtoWithoutId());

    } catch (Exception e) {
      assertEquals(ConflictException.class, e.getClass());
      assertEquals("This email already exist", e.getMessage());
    }
  }

  @Test
  void deleteWithSuccess () {
    when(userRepository.findById(anyLong())).thenReturn(getOptionalUser());
    doNothing().when(userRepository).deleteById(anyLong());

    this.userService.delete(anyLong());

    verify(userRepository, times(1)).deleteById(anyLong());
  }
}