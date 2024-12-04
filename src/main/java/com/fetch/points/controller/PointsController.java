package com.fetch.points.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.points.service.PointsService;
import com.fetch.points.transactions.Transaction;

/**
 * Contains API endpoints for adding/spending points and getting payer balances.
 */
@RestController
@RequestMapping("/api/points")
public class PointsController {

    private final PointsService pointsService;

    @Autowired
    public PointsController(PointsService pointsService) {this.pointsService = pointsService;}

    /**
     * Adds points by adding transaction and returns 200 status code.
     * @param transaction user transaction with payer
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addPoints(@RequestBody Transaction transaction) {
        pointsService.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Spends points and returns the payers whose points were spent and the amount of points they spent.
     * Returns a 400 status code if the user's point balance is less than the amount requested to spend.
     * @param request
     * @return
     */
    @PostMapping("/spend")
    public ResponseEntity<List<Map<String, Object>>> spendPoints(@RequestBody Map<String, Integer> request) {

        // spends points and returns payers with their amount of points spent, throws an exception
        // if user doensn't have enough points to spend 
        try {
            List<Map<String, Object>> result = pointsService.spendPoints(request.get("points"));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
    }

    /**
     * Returns all payers with their total points balance and a 200 status code
     * @return
     */
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Integer>> getBalances() {
        Map<String, Integer> balances = pointsService.getBalances();
        return ResponseEntity.status(HttpStatus.OK).body(balances); 
    }
}
