package com.duke;

public class QouteBusinessRules implements IQouteBusinessRules {

    private long MAX_QUOTE_AGE_MILLIS = 15 * 60 * 1000;

    private static IQouteBusinessRules INSTANCE = new QouteBusinessRules();

    public static IQouteBusinessRules getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public long getMaxQuoteAgeMillis() {

        return MAX_QUOTE_AGE_MILLIS;
    }

    @Override
    public boolean hasQuoteExpired(long quoteTimeStamp){

        long quoteAge = getQuoteAge(quoteTimeStamp);

        if(quoteAge > getMaxQuoteAgeMillis())
            return false; // Quote has expired
        else
            return true; // Quote still valid
    }

    @Override
    public long getQuoteAge(long quoteTimeStamp) {

        return System.currentTimeMillis() - quoteTimeStamp;
    }
}