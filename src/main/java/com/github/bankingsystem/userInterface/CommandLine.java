package com.github.bankingsystem.userInterface;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class CommandLine implements CommandLineRunner {

    private final AccountFunctions accountFunctions;
    private final ExecutorService executorService;

    public CommandLine(AccountFunctions accountFunctions, ExecutorService executorService) {
        this.accountFunctions = accountFunctions;
        this.executorService = executorService;
    }

    @Override
    public void run(String... args) throws Exception {
        executorService.submit(accountFunctions);
    }
}
