package com.fetch.points.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fetch.points.transactions.Transaction;

/**
 * Contains logic for adding transactions and spending points, as well as getting total 
 * payer balances.
 */
@Service
public class PointsService {

    private final List<Transaction> transactions = new ArrayList<>();
    private final HashMap<String, Integer> payerBalances= new HashMap<>();
    private int totalPoints = 0;

    /**
     * Adds transaction to list of transactions and updates map of payer balances with
     * the new total amount of points from the given payer.
     * @param transaction transaction with payer
     */
    public void addTransaction(Transaction transaction) {

        transactions.add(transaction);
        String payer = transaction.getPayer();
        int points = transaction.getPoints();

        // updates payer balance and total point balance with the new points from transaction
        payerBalances.put(payer, payerBalances.getOrDefault(payer, 0) + points);
        totalPoints += points; 
    }

    /**
     * Spends points by subtracting points from payers in order of most recent transactions until
     * all points have been spent. Throws an exception if the user does not have enough points to
     * spend.
     * @param points the amount of points to be spent by user
     * @return a list of maps containing each payer that was used to spend points and their
     * respective amounts spent
     */
    public List<Map<String, Object>> spendPoints(int points) {

        // checking if the points being spent are greater than the total amount of user points 
        // and throwing an exception if so
        if (totalPoints < points) {
            throw new IllegalArgumentException("Not enough points in balance.");
        }

        transactions.sort(Comparator.comparing(Transaction::getTimestamp));

        // using a hash map to keep track of how much each payer is spending
        Map<String, Integer> payerPointsMap = new HashMap<>();
        int currPoints;
        String currPayer;

        // iterating through transactions from most recent to least and spending points from each
        // eligible transaction
        for (Transaction transaction: transactions) {
            
            currPoints = transaction.getPoints();
            currPayer = transaction.getPayer();

            // stop iterating when enough points have been used from other payers
            if (points <= 0) break;

            int pointsToSpend = Math.min(currPoints, points);

            // updating transaction and payer balance
            transaction.setPoints(currPoints - pointsToSpend);
            payerBalances.put(currPayer, payerBalances.get(currPayer) - pointsToSpend);

            // adding current payer to hash map and how many points they lost
            payerPointsMap.put(currPayer, payerPointsMap.getOrDefault(currPayer, 0) - pointsToSpend);

            points = Math.max(0, points - pointsToSpend);

        }

        // converting the hash map to a list of maps
        List<Map<String, Object>> combinedPayers = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : payerPointsMap.entrySet()) {
            combinedPayers.add(Map.of("payer", entry.getKey(), "points", entry.getValue()));
        }

        return combinedPayers;
    }

    /**
     * Returns all current payer point balances.
     * @return HashMap containing each payer name and their current point balance
     */
    public Map<String, Integer> getBalances() {return payerBalances;}
}
