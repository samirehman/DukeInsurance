package com.duke;

/**
 * Created by samirehman on 27/08/2016.
 */
public interface QouteRules {
    long getMaxQuoteAgeMillis(); // Return the Maximum Quote age

    boolean hasQuoteExpired(long quoteTimeStamp); // Return the Quote validity

    long getQuoteAge(long quoteTimeStamp); // Return the Quote age
}
