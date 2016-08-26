package com.duke;

import com.duke.insurance.Purchase;
import com.duke.insurance.ProductionPurchaseCompletionSystem;
import com.duke.search.Policy;
import com.duke.search.Quote;
import com.duke.search.ProductionQuotingSystem;

import java.math.BigDecimal;
import java.util.*;

public class DukeOnlineInsuranceBroker implements InsuranceBroker {

    private Map<UUID, Quote> quotes;

    public DukeOnlineInsuranceBroker() {
        quotes = new HashMap<UUID, Quote>();
    }


    @Override
    public List<Policy> searchForCarInsurance(String make, String model, int year) {

        List<Policy> searchResults;
        searchResults = ProductionQuotingSystem.getInstance().searchFor(make, model, year);
        for (Policy policy : searchResults) {
            quotes.put(policy.id, new Quote(policy, System.currentTimeMillis()));
        }
        return searchResults;
    }

    @Override
    public void confirmPurchase(UUID id, String userAuthToken) {

        if (!quotes.containsKey(id)) {
            throw new NoSuchElementException("Offer ID is invalid");
        }

        Quote quote = quotes.get(id);

        if (!QouteBusinessRules.hasQuoteExpired(quote.timestamp)) {
            throw new IllegalStateException("Quote expired, please search again.");
        }

        long quoteAge = QouteBusinessRules.getQuoteAge(quote.timestamp);

        BigDecimal adminCharge = ChargeCalculationRules.getStandardAdminCharge(quoteAge, quote.policy.premium);
        BigDecimal totalPrice  = quote.policy.premium.add(adminCharge);

        Purchase completePurchase;
        long timeNow = System.currentTimeMillis();

        completePurchase = new Purchase(totalPrice, quote, timeNow, userAuthToken);
        ProductionPurchaseCompletionSystem.getInstance().process(completePurchase);
    }
}