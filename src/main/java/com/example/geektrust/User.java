package com.example.geektrust;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class User {
    @Getter
    private String name;
    private House house;
    @Getter
    private List<UserExpense> userExpenseList;
    @Setter
    @Getter
    private boolean isDue;
    
    User(String name, House house){
        this.name = name;
        this.house = house;
        this.userExpenseList = new ArrayList<>();
        isDue = false;
    }
    
    /**
     *
     *
     * 1000 A B C
     * B - A:333 , C:0
     * C - A:333, B:0
     *
     * 200 A, B
     * B - A:333, C:0, A:100
     *
     */
    
    public void addToUserExpenseList(User lender, long amount){
        boolean isUserFound = false;
        if(this.getName().equalsIgnoreCase(lender.getName())){
            return;
        }
        for(UserExpense userExpense: this.userExpenseList){
            if(userExpense.getUser().getName().equalsIgnoreCase(lender.getName())){
                isUserFound = true;
                userExpense.setAmount(userExpense.getAmount() + amount);
                break;
            }
        }
        if(!isUserFound) {
            userExpenseList.add(new UserExpense(lender, amount));
        }
    }
    
    public void printAllExpenses() {
        List<UserExpense> userExpenses = this.getUserExpenseList();
        for(UserExpense userExpense: userExpenses){
            System.out.println(userExpense.getUser().getName() + " " + userExpense.getAmount());
        }
    }
    
    public void updateDueStatus() {
        List<UserExpense> userExpenses = this.getUserExpenseList();
        boolean areExpensesZero = true;
        if(userExpenses == null){
            return;
        }
        for(UserExpense userExpense: userExpenses){
            if(userExpense.getAmount() > 0){
                areExpensesZero = false;
                break;
            }
        }
        if(areExpensesZero){
            this.setDue(false);
        }
    }
    
}
