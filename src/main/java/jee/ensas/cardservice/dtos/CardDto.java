package jee.ensas.cardservice.dtos;

import java.util.Date;

import org.springframework.data.annotation.LastModifiedDate;

import jee.ensas.cardservice.daos.EType;
import lombok.Data;

@Data
public class CardDto {
    private String id;

    private String cardNumber;
    private int csv;
    private String dateExpiration;
    private String cardHolderName;
    private EType type;
    private boolean isDeleted = false;
    private boolean isAccepted = false;
    private boolean isEnabled = false;

    private Date createdAt;
    private String accountId;
    private String agencyId;
}
