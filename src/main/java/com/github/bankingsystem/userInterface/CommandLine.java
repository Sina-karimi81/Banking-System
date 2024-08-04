package com.github.bankingsystem.userInterface;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class CommandLine implements CommandLineRunner {

    private final AccountCommands accountCommands;
    private final ExecutorService executorService;

    public CommandLine(AccountCommands accountCommands, ExecutorService executorService) {
        this.accountCommands = accountCommands;
        this.executorService = executorService;
    }

    @Override
    public void run(String... args) throws Exception {
        executorService.submit(accountCommands::run);
    }
}
