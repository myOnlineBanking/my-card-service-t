package jee.ensas.cardservice.controllers;

import jee.ensas.cardservice.dtos.CardDto;
import jee.ensas.cardservice.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Card")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping("/getAll")
    public List<CardDto> getCards(final HttpServletRequest request) {
        return cardService.getAll(request);
    }


    @GetMapping("/getByAgency")
    public List<CardDto> getCards (@RequestParam String agencyId , final HttpServletRequest request){
        return cardService.getByAgency(agencyId , request);
    }



    @GetMapping("/getNotAcceptable")
    public List<CardDto> getCardsNotAcceptable() {
        return cardService.getCardNotAcceptable();
    }

    @GetMapping("/get")
    public CardDto getCard(@RequestParam String id, final HttpServletRequest request) {
        return cardService.getCard(id, request);
    }

    @GetMapping("/getByCardNumber")
    public CardDto getByCardNumber(@RequestParam String cardNumber, final HttpServletRequest request) {
        return cardService.getByCardNumber(cardNumber, request);
    }

    @GetMapping("/getAccountCards")
    public List<CardDto> getAccountCards(@RequestParam String accountId, final HttpServletRequest request) {
        return cardService.getCardByAccount(accountId, request);
    }

    @GetMapping("/getClientCards")
    public Set<CardDto> getClientCards(@RequestParam String userId, final HttpServletRequest request) {
        return cardService.getClientCards(userId, request);
    }

    @PostMapping("/create")
    public CardDto createCards(@RequestBody CardDto cardDto, final HttpServletRequest request) {
        return cardService.createCard(cardDto, request);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/enable")
    public CardDto enableCard(@RequestBody CardDto cardDto, final HttpServletRequest request) {
        return cardService.enableCard(cardDto, request);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/disable")
    public CardDto disableCard(@RequestBody CardDto cardDto, final HttpServletRequest request) {
        return cardService.disableCard(cardDto, request);
    }


    @PutMapping("/accept/{cardId}")
    public CardDto acceptCard(@PathVariable(required = true, name = "cardId") String cardId) {
        return cardService.acceptCard(cardId);
    }

    @PutMapping("/update")
    public CardDto updateCard(@RequestBody CardDto cardDto, final HttpServletRequest request) {
        return cardService.updateCard(cardDto, request);
    }

}
