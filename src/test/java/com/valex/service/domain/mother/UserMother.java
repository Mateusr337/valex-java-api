package com.valex.service.domain.mother;

import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.utils.Encoder;
import java.util.HashSet;

public class UserMother {

  public static User getUser () {
    User user = new User();

    user.setId(5L);
    user.setName("Fulano");
    user.setEmail("fulano@email.com");
    user.setPassword(getEncodedPassword());
    user.setCpf("61635567041");
    user.setCards(new HashSet<Card>());

    return user;
  }

  private static String getEncodedPassword () {
    return Encoder.encode("123456");
  }
}

