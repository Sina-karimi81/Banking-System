package com.github.bankingsystem.business.util;

import java.math.BigInteger;
import java.util.UUID;

/**
 * This class is used to generate unique Ids for different parts of the system.
 */
public class IdGenerator {

    /**
     * I am using UUID to generate random unique numbers. to make sure that I get positive values, I get the MSB of the
     * generated UUID and use & with Long.MAX_VALUE, you can see why in <a href="https://stackoverflow.com/questions/15184820/how-to-generate-unique-positive-long-using-uuid/">this link</a>>
     * @return a positive,random, unique Long
     */
    public static Long generateAccountUniqueId() {
         long generatedVal = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        return generatedVal;
    }

}
