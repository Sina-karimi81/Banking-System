package com.github.bankingsystem.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Account {
    private Long accountId;
    private String ownerName;
    private Float amount;
}
