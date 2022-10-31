package com.valex.service;

import static java.lang.String.valueOf;

import com.valex.domain.dto.CardDto;
import java.util.List;

public interface CardService {
  List<CardDto> findAll();
  CardDto findByIdOrFail(Long id);
  CardDto create (CardDto cardDto);
  CardDto activate (Long cardId, String passcode);
  List<CardDto> findCardsByUserId (Long id);
  CardDto updateBalance (Long cardId, Long newBalance);
}
