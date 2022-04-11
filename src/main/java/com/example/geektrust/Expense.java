package com.example.geektrust;

import lombok.Getter;

import java.util.List;

public class Expense {
    private static long UNIQUE_ID = 1;
    private long id;
    @Getter
    private Split split;
    @Getter
    private User lender;
    @Getter
    private List<User> borrowers;
    @Getter
    private long amount;
    
    Expense(User lender, List<User> borrowerList, Split split, long amount){
        this.lender = lender;
        this.borrowers = borrowerList;
        this.split = split;
        this.amount = amount;
    }
    
    private static long getUniqueId(){
        return UNIQUE_ID++;
    }
}
