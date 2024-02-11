package jee.ensas.cardservice.services;

import jee.ensas.cardservice.dtos.CardDto;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface CardService extends Serializable {

    List<CardDto> getAll(final HttpServletRequest request);

    List<CardDto> getCardByAccount(String userId, final HttpServletRequest request);
    
    List<CardDto> getCardNotAcceptable();

    CardDto createCard(CardDto cardDto, final HttpServletRequest request);

    CardDto updateCard(CardDto cardDto, final HttpServletRequest request);

    CardDto getCard(String id, HttpServletRequest request);

    CardDto getByCardNumber(String cardNumber, HttpServletRequest request);

    Set<CardDto> getClientCards(String userId, HttpServletRequest request);

    CardDto enableCard(CardDto cardDto, HttpServletRequest request);

    CardDto disableCard(CardDto cardDto, HttpServletRequest request);

    List<CardDto> getByAgency(String agencyId, HttpServletRequest request);
    CardDto acceptCard(String cardId);
}
