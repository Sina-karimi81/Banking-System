package com.github.bankingsystem.business.util;

import com.github.bankingsystem.userInterface.AccountFunctions;

import java.util.Scanner;

/**
 * To get the user input in {@link AccountFunctions#run()}, I use this class. You may wonder why I didn't get the user input
 * directly in that method, well it was because I encountered an issue where if I wanted to get an integer input the {@link Scanner#nextInt()}
 * wouldn't count the \n that was inputted in the terminal and I could fix this in two ways:
 * <ol>
 *  <li>use {@link Scanner#nextLine()} after each input</li>
 *  <li>use {@link Scanner#nextLine()} to get the input as string and then to convert it to the type I want</li>
 * </ol>
 * and I went with the second option.
 */
public class UserInput {

    public static int getUserInputAsInt(Scanner scanner) {
        String input = scanner.nextLine();
        return Integer.parseInt(input);
    }

    public static float getUserInputAsFloat(Scanner scanner) {
        String input = scanner.nextLine();
        return Float.parseFloat(input);
    }

    public static String getUserInputAsString(Scanner scanner) {
        return scanner.nextLine();
    }

}
