package jee.ensas.cardservice.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class AccountDto implements Serializable {

    private String id;
    private String userId;

    private String accountNumber;
    private double balance;
    private String Type;
    private String currency;
    private boolean isDeleted = false;
    private boolean isEnabled = false;
    private boolean isAccepted = false;
    private Date creationDate;
    private Set<String> cardIds = new HashSet<>();


}

