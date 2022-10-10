package com.valex.domain.mother;

import com.valex.domain.dto.UserDto;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import com.valex.utils.Encoder;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;


@Getter
public final class UserMother {

  private static final Long ID = 1L;
  private static  final String NAME = "fulano";
  private static final String EMAIL = "fulano@email.com";
  private static final String PASSWORD = "123456";
  private static final String CPF = "67042218086";
  private static final Set<Card> CARDS = new HashSet<Card>();

  public static  Optional<User> getOptionalUser () {
    User user = getUser();
    return Optional.of(user);
  }

  public static Optional<User> getEmptyOptionalUser () {
    User user = new User();
    return Optional.empty();
  }

  public static User getUser () {
    User user = new User();

    user.setId(ID);
    user.setName(NAME);
    user.setEmail(EMAIL);
    user.setPassword(getEncodedPassword());
    user.setCpf(CPF);
    user.setCards(CARDS);

    return user;
  }

  public static UserDto getUserDtoWithoutId () {
    UserDto userDto = getUserDto();
    userDto.setId(null);
    return userDto;
  }

  public static UserDto getUserDto () {
    UserDto userDto = new UserDto();

    userDto.setId(ID);
    userDto.setName(NAME);
    userDto.setEmail(EMAIL);
    userDto.setPassword(getEncodedPassword());
    userDto.setCpf(CPF);

    return userDto;
  }

  public static UserRequest getUserRequest () {
    UserRequest userRequest = new UserRequest();

    userRequest.setName(NAME);
    userRequest.setEmail(EMAIL);
    userRequest.setPassword(PASSWORD);
    userRequest.setCpf(CPF);

    return userRequest;
  }

  public static List<User> getUserList() {
    User user = getUser();
    return List.of(user);
  }

  public static UserResponse getUserResponse() {
    UserResponse userResponse = new UserResponse();
    userResponse.setId(ID);
    userResponse.setName(NAME);
    userResponse.setEmail(EMAIL);
    userResponse.setCpf(CPF);

    return userResponse;
  }


  private static String getEncodedPassword () {
    return Encoder.encode(PASSWORD);
  }
}

