package com.valex.controller;

import com.valex.domain.dto.CardDto;
import com.valex.domain.model.Card;
import com.valex.service.CardService;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/cards")
public class CardController {

  @Autowired
  private CardService cardService;

  @GetMapping
  public List<Card> getAll () {
    return this.cardService.getAll();
  }

  @PostMapping
  @ResponseStatus (code = HttpStatus.CREATED)
  public void create (@RequestBody CardDto cardDto) {
    this.cardService.create(cardDto);
  }

}
