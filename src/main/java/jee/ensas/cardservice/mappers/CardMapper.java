package jee.ensas.cardservice.mappers;

import jee.ensas.cardservice.daos.Card;
import jee.ensas.cardservice.dtos.CardDto;
import jee.ensas.cardservice.exceptions.CardException;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardMapper {

    public CardDto map(Card card) {

        try {
            CardDto cardDto = new CardDto();

            cardDto.setId(card.getId());
            cardDto.setCardNumber(card.getCardNumber());
            cardDto.setCardHolderName(card.getCardHolderName());
            cardDto.setAccepted(card.isAccepted());
            cardDto.setDeleted(card.isDeleted());
            cardDto.setEnabled(card.isEnabled());
            cardDto.setCsv(card.getCsv());
            cardDto.setAgencyId(card.getAgencyId());
            cardDto.setAccountId(card.getAccountId());
            cardDto.setType(card.getType());
            cardDto.setDateExpiration(card.getDateExpiration());

            return cardDto;
        } catch (Exception e) {
            throw new CardException(HttpStatus.SC_EXPECTATION_FAILED, e.getMessage());
        }
    }

    public Card map(CardDto cardDto) {
        try {
            Card card = new Card();

            card.setId(cardDto.getId());
            card.setCardNumber(cardDto.getCardNumber());
            card.setCardHolderName(cardDto.getCardHolderName());
            card.setAccepted(cardDto.isAccepted());
            card.setDeleted(cardDto.isDeleted());
            card.setEnabled(cardDto.isEnabled());
            card.setCsv(cardDto.getCsv());
            card.setAgencyId(cardDto.getAgencyId());
            card.setAccountId(cardDto.getAccountId());
            card.setType(cardDto.getType());
            card.setDateExpiration(cardDto.getDateExpiration());

            return card;
        } catch (Exception e) {
            throw new CardException(HttpStatus.SC_EXPECTATION_FAILED, e.getMessage());
        }
    }

    public List<CardDto> map(List<Card> cards) {
        try {
            return cards
                    .stream()
                    .map(this::map)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CardException(HttpStatus.SC_EXPECTATION_FAILED, e.getMessage());
        }
    }

    public List<Card> toMap(List<CardDto> cardDtos) {
        try {
            return cardDtos
                    .stream()
                    .map(this::map)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CardException(HttpStatus.SC_EXPECTATION_FAILED, e.getMessage());
        }
    }


}
