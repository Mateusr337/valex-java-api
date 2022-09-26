package com.valex.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.valex.domain.dto.UserDto;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

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

  private User user;

  private UserDto userDto;

  private Optional<User> optionalUser;

  private Optional<User> emptyOptionalUser;

  @BeforeEach
  void setUp () {
    MockitoAnnotations.openMocks(this);
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
      User response = userService.findByIdOrFail(ID);
    } catch (Exception e) {
      assertEquals(NotFoundException.class, e.getClass());
      assertEquals("{user.not.found}", e.getMessage());
    }
  }

  private void startUser () {
    this.user = new User(ID, NAME, EMAIL, PASSWORD, CPF, CARDS);
    this.optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD, CPF, CARDS));
    this.userDto = new UserDto(NAME, PASSWORD, PASSWORD, CPF);
    this.emptyOptionalUser = Optional.of(new User());
  }
}