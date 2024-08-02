package com.github.bankingsystem.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountCreationInputDTO {

    private String ownerName;
    private Float amount;

}
