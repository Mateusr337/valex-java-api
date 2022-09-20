package com.valex.controller;

import com.valex.domain.dto.ActivateCardDto;
import com.valex.domain.dto.CardDto;
import com.valex.domain.model.Card;
import com.valex.service.CardService;
import java.util.List;
import javax.persistence.PostUpdate;
import javax.validation.Valid;
import org.apache.coyote.Request;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/cards")
public class CardController {

  private final CardService cardService;

  public CardController(CardService cardService) {
    this.cardService = cardService;
  }

  @GetMapping
  public List<Card> getAll () {
    return this.cardService.getAll();
  }

  @PostMapping
  @ResponseStatus (code = HttpStatus.CREATED)
  public void create (@RequestBody @Valid CardDto cardDto) {
    this.cardService.create(cardDto);
  }

  @PutMapping ("/activate/{id}")
  @ResponseStatus (code = HttpStatus.NO_CONTENT)
  public void activeCard (
      @RequestBody @Valid ActivateCardDto activateCardDto,
      @PathVariable Long id
  ) {
    //Pegar id passar para a service ver se o cartão existe
    //Salvar o CVV encoded e password
    //mudar status para active e setar a senha
  }

}
