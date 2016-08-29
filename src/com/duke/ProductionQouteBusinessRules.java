package com.duke;

public class ProductionQouteBusinessRules implements QouteRules {

    private long MAX_QUOTE_AGE_MILLIS = 15 * 60 * 1000;

    private static QouteRules INSTANCE = new ProductionQouteBusinessRules();

    public static QouteRules getINSTANCE() {
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