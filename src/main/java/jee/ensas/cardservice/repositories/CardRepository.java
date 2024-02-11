package jee.ensas.cardservice.repositories;

import jee.ensas.cardservice.daos.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends MongoRepository<Card, String> {

    List<Card> findByAgencyId(@Param(value = "agencyId") String agencyId);
    Card findByCardNumber(@Param(value = "cardNumber") String cardNumber);
    List<Card> findByAccountId(@Param(value = "accountId") String accountId);
    List<Card> findByisAcceptedFalseOrderByCreatedAt();
}
