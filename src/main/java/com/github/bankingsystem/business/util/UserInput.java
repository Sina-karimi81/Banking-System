package com.github.bankingsystem.business.util;

import java.util.Scanner;

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
