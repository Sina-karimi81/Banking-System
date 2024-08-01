package com.github.bankingsystem.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountCreationInputDTO {

    private String ownerName;
    private Float amount;

}
