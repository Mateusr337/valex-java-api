package com.valex.controller;

import com.valex.domain.dto.CardDto;
import com.valex.domain.mapper.CardMapper;
import com.valex.domain.request.ActivateCardRequest;
import com.valex.domain.request.CardRequest;
import com.valex.domain.model.Card;
import com.valex.domain.response.CardResponse;
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

  @Autowired
  private CardMapper cardMapper;

  @GetMapping
  public List<CardResponse> findAll() {
    return cardMapper.dtoToResponse(this.cardService.findAll());
  }

  @PostMapping
  @ResponseStatus (HttpStatus.CREATED)
  public CardResponse create (@RequestBody @Valid CardRequest cardRequest) {
    CardDto cardDto =  this.cardService.create(this.cardMapper.requestToDto(cardRequest));
    return cardMapper.dtoToResponse(cardDto);
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
  public List<CardResponse> findByClientId (@PathVariable ("id") Long id) {
    List<CardDto> cardDtoList = this.cardService.findCardsByUserId(id);
    return cardMapper.dtoToResponse(cardDtoList);
  }

}
