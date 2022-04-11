package com.example.geektrust;

import lombok.Getter;
import lombok.Setter;

public class UserExpense {
    @Getter
    private User user;
    @Getter
    @Setter
    private long amount;
    
    UserExpense(User user, long amount){
        this.user = user;
        this.amount = amount;
    }
}
