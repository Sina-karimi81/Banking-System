package com.github.bankingsystem.userInterface;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * This class is the starting point of the application since it is implementing the CommandLineRunner interface because
 * spring will automatically call the {@link CommandLineRunner#run(String...)} method. I am using the class to create a
 * thread for each instance of the application
 */
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
