package jee.ensas.cardservice.daos;

import lombok.Data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("cards")
public class Card {

    @Id
    private String id;

    private String cardNumber;
    private String cardHolderName;
    private int csv;
    private String dateExpiration;
    private EType type;
    private boolean isDeleted = false;
    private boolean isAccepted = false;
    private boolean isEnabled = false;

    private String accountId;

    private String agencyId;

    @LastModifiedDate
    private Date createdAt = new Date();

}
