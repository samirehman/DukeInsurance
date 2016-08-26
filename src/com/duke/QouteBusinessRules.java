package com.duke;

public class QouteBusinessRules {

    private static long MAX_QUOTE_AGE_MILLIS = 15 * 60 * 1000;

    public static long getMaxQuoteAgeMillis() {

        return MAX_QUOTE_AGE_MILLIS;
    }

    public static boolean hasQuoteExpired(long quoteTimeStamp){

        long quoteAge = getQuoteAge(quoteTimeStamp);

        if(quoteAge > getMaxQuoteAgeMillis())
            return false; // Quote has expired
        else
            return true; // Quote still valid
    }

    public static long getQuoteAge(long quoteTimeStamp) {

        return System.currentTimeMillis() - quoteTimeStamp;
    }
}