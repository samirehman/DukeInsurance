package com.duke;

import com.duke.insurance.ProductionPurchaseCompletionSystem;
import com.duke.insurance.Purchase;
import com.duke.search.Policy;
import com.duke.search.ProductionQuotingSystem;
import com.duke.search.Quote;

import java.math.BigDecimal;
import java.util.*;

public class DukeOnlineInsuranceBroker implements InsuranceBroker {

    private Map<UUID, Quote> quotes;

    PurchaseService purchaseService;
    QuotingSystem quotingSystem;
    Charges chargeCalculationRules;
    QouteRules qouteBusinessRules;

    public DukeOnlineInsuranceBroker(PurchaseService pService, QuotingSystem qSystem, Charges calculationRules,
            QouteRules qBusinessRules)
    {
        quotes = new HashMap<UUID, Quote>();
        purchaseService = pService;
        quotingSystem = qSystem;
        chargeCalculationRules = calculationRules;
        qouteBusinessRules = qBusinessRules;
    }
    public DukeOnlineInsuranceBroker()
    {
        quotes = new HashMap<UUID, Quote>();
        purchaseService = ProductionPurchaseCompletionSystem.getInstance();
        quotingSystem   = ProductionQuotingSystem.getInstance();
        chargeCalculationRules = ProductionChargeCalculationRules.getInstance();
        qouteBusinessRules = ProductionQouteBusinessRules.getINSTANCE();
    }
    @Override
    public List<Policy> searchForCarInsurance(String make, String model, int year) {

        List<Policy> searchResults;
        searchResults = quotingSystem.searchFor(make, model, year);
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

        // Check the quote validity
        if (!qouteBusinessRules.hasQuoteExpired(quote.timestamp)) {
            throw new IllegalStateException("Quote expired, please search again.");
        }
        // Get quote age
        long quoteAge = qouteBusinessRules.getQuoteAge(quote.timestamp);

        // Get the admin charge applicable
        BigDecimal adminCharge = chargeCalculationRules.getStandardAdminCharge(quoteAge, quote.policy.premium);

        // Add admin charge to premium amount and calculate total price
        BigDecimal totalPrice  = quote.policy.premium.add(adminCharge);

        Purchase completePurchase;
        long timeNow = System.currentTimeMillis();

        completePurchase = new Purchase(totalPrice, quote, timeNow, userAuthToken);
        purchaseService.process(completePurchase);
    }
}