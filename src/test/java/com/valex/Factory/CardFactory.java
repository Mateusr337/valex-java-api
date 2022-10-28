package com.valex.Factory;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.utils.GenerateCardData.expirationDate;

import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.model.User;
import com.valex.domain.response.CardResponse;
import com.valex.repository.CardRepository;
import com.valex.utils.Encoder;
import com.valex.utils.GenerateCardData;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardFactory {

  @Autowired
  private CardRepository cardRepository;

  public Card createCardInTheDatabase (CardType cardtype, User user) {

    Card card = getCardBaseData(cardtype, user);
    return cardRepository.save(card);

  }

  public Card createActivatedCardInTheDatabase (CardType cardtype, User user) {

    Card card = getCardBaseData(cardtype, user);
    card.setStatus(ACTIVE);

    if (cardtype == CREDIT) {
      card.setLimitCredit(500000L);
      card.setBalance(0L);

    } else if (cardtype == DEBIT) {
      card.setBalance(500000L);
      card.setLimitCredit(0L);
    }

    return cardRepository.save(card);

  }

  private Card getCardBaseData (CardType cardtype, User user) {

    Card card = new Card();
    card.setUser(user);
    card.setUserName(user.getName());
    card.setNumber(GenerateCardData.Number());
    card.setStatus(DISABLED);
    card.setCvv(GenerateCardData.CVV());
    card.setPasscode(Encoder.encode("1234"));
    card.setType(cardtype);
    card.setExpirationDate(expirationDate());
    card.setBalance(0L);
    card.setLimitCredit(0L);
    return card;
  }
}
