package com.valex.service;

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
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceUnitTest {

  private  static  final Long ID = 1L;
  private static  final String NAME = "mateus";
  public static final String EMAIL = "mateus@email.com";
  public static final String PASSWORD = "123456";
  public static final String CPF = "67042218086";
  public static final Set<Card> CARDS = new HashSet<Card>();

  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;

  private User user;
  private UserDto userDtoWithoutId;
  private UserDto userDtoWithId;
  private Optional<User> optionalUser;
  private Optional<User> emptyOptionalUser;

  @BeforeEach
  void setUp () {
    openMocks(this);
    startUser();
  }

  @Test
  void whenFindByIdOrThrowWithExistingIdThenReturnAnUser () {
    when(userRepository.findById(anyLong())).thenReturn(optionalUser);

    User response = userService.findByIdOrFail(ID);

    assertEquals(User.class, response.getClass());
    assertEquals(ID, response.getId());
  }

  @Test
  void whenFindByIdOrThrowWithNoExistingIdThenReturnThrowNotFoundException () {
    when(userRepository.findById(anyLong())).thenReturn(emptyOptionalUser);

    try {
      userService.findByIdOrFail(ID);

    } catch (Exception e) {
      assertEquals(NotFoundException.class, e.getClass());
      assertEquals("User not found", e.getMessage());
    }
  }

  @Test
  void whenFindAllThenReturnUserList () {
    when(userRepository.findAll()).thenReturn(List.of(user));
    when(userMapper.modelToDto(anyList())).thenReturn(List.of(userDtoWithId));

    List<UserDto> response = userService.findAll();

    System.out.println(response);

    assertEquals(1, response.size());
    assertEquals(ID, response.get(0).getId());
  }

  @Test
  void whenCreateThenReturnUserDto () {
    when(userRepository.findByEmail(anyString())).thenReturn(null);
    when(userMapper.dtoToModel(any(UserDto.class))).thenReturn(user);
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(userMapper.modelToDto(any(User.class))).thenReturn(userDtoWithId);

    UserDto response = userService.create(userDtoWithoutId);

    verify(userMapper).dtoToModel(userDtoWithoutId);
    verify(userMapper).modelToDto(user);

    assertEquals(UserDto.class, response.getClass());
  }

  @Test
  void whenTryCreateExistEmailUserThenReturnConflictException () {
    when(userRepository.findByEmail(anyString())).thenReturn(user);

    try {
      userService.create(userDtoWithoutId);

    } catch (Exception e) {
      assertEquals(ConflictException.class, e.getClass());
      assertEquals("This email already exist", e.getMessage());
    }
  }

  @Test
  void deleteWithSuccess () {
    when(userRepository.findById(anyLong())).thenReturn(optionalUser);
    doNothing().when(userRepository).deleteById(anyLong());

    this.userService.delete(ID);

    verify(userRepository, times(1)).deleteById(anyLong());
  }

  private void startUser () {
    this.user = new User(ID, NAME, EMAIL, PASSWORD, CPF, CARDS);
    this.optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD, CPF, CARDS));
    this.userDtoWithoutId = new UserDto(null, NAME, EMAIL, PASSWORD, CPF);
    this.userDtoWithId = new UserDto(ID, NAME, EMAIL, PASSWORD, CPF);
    this.emptyOptionalUser = Optional.of(new User());
  }
}