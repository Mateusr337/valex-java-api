package com.valex.service.domain.mother;

import com.valex.domain.dto.UserDto;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.utils.Encoder;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
    return Optional.of(user);
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


  private static String getEncodedPassword () {
    return Encoder.encode(PASSWORD);
  }
}

