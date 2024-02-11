package jee.ensas.cardservice.services;

import jee.ensas.cardservice.daos.Card;
import jee.ensas.cardservice.dtos.AccountDto;
import jee.ensas.cardservice.dtos.CardDto;
import jee.ensas.cardservice.exceptions.CardException;
import jee.ensas.cardservice.mappers.CardMapper;
import jee.ensas.cardservice.repositories.CardRepository;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardMapper cardMapper;

    Logger logger = Logger.getLogger("myLogger");

    @Value("${urls.saveAccountCard}")
    private String saveAccountCardUrl;

    @Value("${urls.userAccounts}")
    private String userAccountsUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public List<CardDto> getAll(HttpServletRequest request) {
        try {
            return cardMapper.map(cardRepository.findAll());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public List<CardDto> getCardByAccount(String cardId, HttpServletRequest request) {
        try {
            return cardMapper.map(cardRepository.findByAccountId(cardId));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public CardDto getCard(String id, HttpServletRequest request) {
        try {
            return cardMapper.map(cardRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public CardDto getByCardNumber(String cardNumber, HttpServletRequest request) {
        try {
            return cardMapper.map(cardRepository.findByCardNumber(cardNumber));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public CardDto createCard(CardDto cardDto, HttpServletRequest request) {
        try {
            Card card = cardMapper.map(cardDto);
            card = cardRepository.save(card);
            cardDto.setId(card.getId());

            logger.log(Level.INFO, card.toString());

            addCardToAccount(cardDto.getId(), cardDto.getAccountId());

            return cardMapper.map(card);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, ex.getMessage());
        }
    }

    private void addCardToAccount(String id, String accountId) {
        try {
            Map<String, String> requestBody = new HashMap<>();

            requestBody.put("cardId", id);
            requestBody.put("accountId", accountId);

            webClientBuilder.build().put()
                    .uri(saveAccountCardUrl)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(requestBody), Map.class)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public CardDto updateCard(CardDto cardDto, HttpServletRequest request) {
        try {
            Card card = cardMapper.map(cardDto);
            return cardMapper.map(cardRepository.save(card));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public Set<CardDto> getClientCards(String userId, HttpServletRequest request) {
        try {
            AccountDto[] userAccounts = webClientBuilder.build().get()
                    .uri(userAccountsUrl + userId)
                    .retrieve()
                    .bodyToMono(AccountDto[].class).block();

            Set<CardDto> userCards = new HashSet<>();

            for (AccountDto account : userAccounts) {
                System.out.println(account);
                userCards.addAll(getCardByAccount(account.getId(), request));
            }
            return userCards;

        } catch (Exception e) {
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public CardDto enableCard(CardDto cardDto, HttpServletRequest request) {
        try {
            Card card = cardRepository.findById(cardDto.getId()).orElse(null);
            assert card != null;
            card.setEnabled(true);
            return cardMapper.map(cardRepository.save(card));
        } catch (Exception e) {
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public CardDto disableCard(CardDto cardDto, HttpServletRequest request) {
        try {
            Card card = cardRepository.findById(cardDto.getId()).orElse(null);
            assert card != null;
            card.setEnabled(false);
            return cardMapper.map(cardRepository.save(card));
        } catch (Exception e) {
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public List<CardDto> getByAgency(String agencyId, HttpServletRequest request) {
        try {
            return cardMapper.map(cardRepository.findByAgencyId(agencyId));
        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    public List<CardDto> getCardNotAcceptable() {
        try {
            return cardMapper.map(cardRepository.findByisAcceptedFalseOrderByCreatedAt());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public CardDto acceptCard(String cardId) {
        try {
            Card card = cardRepository.findById(cardId).orElse(null);
            card.setAccepted(true);
            return cardMapper.map(cardRepository.save(card));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CardException(HttpStatus.SC_FORBIDDEN, e.getMessage());

        }
    }
}
