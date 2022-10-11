package com.valex.controller;

import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.getCardDtoWithId;
import static com.valex.domain.mother.CardMother.getCardResponse;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.mapper.CardMapper;
import com.valex.domain.mother.CardMother;
import com.valex.domain.response.CardResponse;
import com.valex.service.CardService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerUnitTest {

  private final static String BASE_URL = "/cards";

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CardService cardService;

  @MockBean
  private CardMapper cardMapper;

  @InjectMocks
  private CardController cardController;

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

}
