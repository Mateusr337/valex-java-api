package com.valex.controller;

import static com.valex.domain.enumeration.CardStatus.*;
import static com.valex.domain.enumeration.CardType.*;
import static com.valex.domain.mother.CardMother.*;
import static com.valex.domain.mother.UserMother.getUser;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.domain.dto.CardDto;
import com.valex.domain.mapper.impl.CardMapper;
import com.valex.domain.model.User;
import com.valex.domain.request.ActivateCardRequest;
import com.valex.domain.request.CardRequest;
import com.valex.domain.response.CardResponse;
import com.valex.service.CardService;
import com.valex.service.impl.CardServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerUnitTest {

  private final static String BASE_URL = "/cards";

  @InjectMocks
  private CardController cardController;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CardServiceImpl cardService;

  @MockBean
  private CardMapper cardMapper;

  @Test
  @WithMockUser
  void givenFindAllCardsThenReturnCardsList () throws Exception {
    CardResponse cardResponse = getCardResponse(DEBIT, DISABLED);
    given(cardService.findAll()).willReturn(List.of(getCardDtoWithId(DEBIT)));
    given(cardMapper.dtoToResponse(anyList())).willReturn(List.of(cardResponse));

    mvc.perform(get(BASE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(cardResponse.getId()))
        .andExpect(jsonPath("$[0].userId").value(cardResponse.getUserId()))
        .andExpect(jsonPath("$[0].userName").value(cardResponse.getUserName()))
        .andExpect(jsonPath("$[0].number").value(cardResponse.getNumber()))
        .andExpect(jsonPath("$[0].status").value(cardResponse.getStatus().name()))
        .andExpect(jsonPath("$[0].cvv").doesNotHaveJsonPath())
        .andExpect(jsonPath("$[0].passcode").doesNotHaveJsonPath())
        .andExpect(jsonPath("$[0].type").value(cardResponse.getType().name()))
        .andExpect(jsonPath("$[0].limitCredit").value(cardResponse.getLimitCredit()))
        .andExpect(jsonPath("$[1]").doesNotHaveJsonPath());

    verify(cardService).findAll();
    verify(cardMapper).dtoToResponse(anyList());
  }

  @Test
  @WithMockUser
  void givenValidNewCardThenReturnCreatedUser () throws Exception {
    CardRequest cardRequest = getCardRequest(CREDIT);
    CardResponse cardResponse = getCardResponse(CREDIT, DISABLED);

    given(cardMapper.requestToDto(any(CardRequest.class))).willReturn(getCardDtoWithoutId(CREDIT));
    given(cardService.create(any(CardDto.class))).willReturn(getCardDtoWithId(CREDIT));
    given(cardMapper.dtoToResponse(any(CardDto.class))).willReturn(cardResponse);

    mvc.perform(post(BASE_URL)
        .content(new ObjectMapper().writeValueAsString(cardRequest))
        .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(cardResponse.getId()))
        .andExpect(jsonPath("$.userId").value(cardResponse.getUserId()))
        .andExpect(jsonPath("$.userName").value(cardResponse.getUserName()))
        .andExpect(jsonPath("$.number").value(cardResponse.getNumber()))
        .andExpect(jsonPath("$.status").value(cardResponse.getStatus().name()))
        .andExpect(jsonPath("$.cvv").doesNotHaveJsonPath())
        .andExpect(jsonPath("$.passcode").doesNotHaveJsonPath())
        .andExpect(jsonPath("$.type").value(cardResponse.getType().name()))
        .andExpect(jsonPath("$.limitCredit").value(cardResponse.getLimitCredit()));

    verify(cardMapper).requestToDto(any(CardRequest.class));
    verify(cardMapper).dtoToResponse(any(CardDto.class));
    verify(cardService).create(any(CardDto.class));
  }

  @Test
  @WithMockUser
  void givenValidCardActiveCardThenReturnActivatedCard () throws Exception {
    CardResponse cardResponse = getCardResponse(CREDIT, ACTIVE);
    CardDto activatedCardDto = getActivatedCardDto(CREDIT);
    Long cardId = cardResponse.getId();

    given(cardService.activate(anyLong(), anyString())).willReturn(activatedCardDto);
    given(cardMapper.dtoToResponse(any(CardDto.class))).willReturn(cardResponse);

    String URL = BASE_URL + "/activate/" + cardId;
    ActivateCardRequest bodyRequest = new ActivateCardRequest(getPasscode());

    mvc.perform(patch(URL)
            .content(new ObjectMapper().writeValueAsString(bodyRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(cardId))
        .andExpect(jsonPath("$.userId").value(cardResponse.getUserId()))
        .andExpect(jsonPath("$.userName").value(cardResponse.getUserName()))
        .andExpect(jsonPath("$.number").value(cardResponse.getNumber()))
        .andExpect(jsonPath("$.status").value(ACTIVE.name()))
        .andExpect(jsonPath("$.balance").value(0L))
        .andExpect(jsonPath("$.type").value(cardResponse.getType().name()))
        .andExpect(jsonPath("$.limitCredit").value(cardResponse.getLimitCredit()));

    verify(cardService).activate(cardId, getPasscode());
    verify(cardMapper).dtoToResponse(activatedCardDto);
  }

  @Test
  @WithMockUser
  void givenFindCardsByUserIdReturnCardsList () throws Exception {
    User user = getUser();
    CardResponse cardResponse = getCardResponse(DEBIT, DISABLED);
    List<CardDto> cardDtoList = List.of(getCardDtoWithId(DEBIT));

    given(cardService.findCardsByUserId(anyLong())).willReturn(cardDtoList);
    given(cardMapper.dtoToResponse(anyList())).willReturn(List.of(cardResponse));

    String URL = BASE_URL + "/users/" + user.getId();

    mvc.perform(get(URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(cardResponse.getId()))
        .andExpect(jsonPath("$[0].userId").value(cardResponse.getUserId()))
        .andExpect(jsonPath("$[0].userName").value(cardResponse.getUserName()))
        .andExpect(jsonPath("$[0].number").value(cardResponse.getNumber()))
        .andExpect(jsonPath("$[0].status").value(cardResponse.getStatus().name()))
        .andExpect(jsonPath("$[0].type").value(cardResponse.getType().name()))
        .andExpect(jsonPath("$[0].limitCredit").value(cardResponse.getLimitCredit()))
        .andExpect(jsonPath("$[0].balance").value(0L))
        .andExpect(jsonPath("$[1]").doesNotHaveJsonPath());

    verify(cardService).findCardsByUserId(user.getId());
    verify(cardMapper).dtoToResponse(cardDtoList);
  }

}
