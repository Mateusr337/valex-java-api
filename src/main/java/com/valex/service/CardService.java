package com.valex.service;

import static com.valex.utils.Encoder.encode;
import static java.lang.String.valueOf;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.Card;
import com.valex.domain.validation.CardPasscodeValidation;
import com.valex.domain.validation.CardTypeAndLimitValidation;
import java.util.List;
import java.util.Optional;

public interface CardService {
  List<CardDto> findAll();
  CardDto findByIdOrFail(Long id);
  CardDto create (CardDto cardDto);
  CardDto activate (Long cardId, String passcode);
  List<CardDto> findCardsByUserId (Long id);
}
