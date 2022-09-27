package com.valex.controller;

import com.valex.domain.request.ActivateCardRequest;
import com.valex.domain.request.CardRequest;
import com.valex.domain.model.Card;
import com.valex.service.CardService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping ("/cards")
public class CardController {

  @Autowired
  private CardService cardService;

  @GetMapping
  public List<Card> findAll() {
    return this.cardService.findAll();
  }

  @PostMapping
  @ResponseStatus (HttpStatus.CREATED)
  public void create (@RequestBody @Valid CardRequest cardDto) {
    this.cardService.create(cardDto);
  }

  @PatchMapping ("/activate/{id}")
  @ResponseStatus (HttpStatus.NO_CONTENT)
  public void activeCard (
      @RequestBody @Valid ActivateCardRequest activateCardDto,
      @PathVariable ("id") Long cardId
  ) {
    this.cardService.activate(cardId, activateCardDto.getPasscode());
  }

  @GetMapping ("/clients/{id}")
  public List<Card> findByClientId (@PathVariable ("id") Long id) {
    return this.cardService.findCardsByUserId(id);
  }

}
