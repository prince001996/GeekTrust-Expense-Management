package com.example.geektrust;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class House {
    private long houseNumber;
    private  int countOfTenants;
    @Getter
    private List<User> users;
    private long totalExpenditure;
    
    House(int houseNumber){
        this.houseNumber = houseNumber;
        countOfTenants = 0;
        users = new ArrayList<>();
        totalExpenditure = 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return houseNumber == house.houseNumber && countOfTenants == house.countOfTenants && totalExpenditure == house.totalExpenditure && Objects.equals(getUsers(), house.getUsers());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(houseNumber);
    }
    
    
    
    public void addUsers(User user){
        if(countOfTenants+1 > 3){
            System.out.println("HOUSEFUL");
            return;
        }
        users.add(user);
        countOfTenants++;
        System.out.println("SUCCESS");
        if(countOfTenants > 1){
            initializeUserExpenses();
        }
    }
    
    private void initializeUserExpenses() {
        for(int i=0; i<users.size(); i++){
            for(int j=0; j<users.size(); j++){
                if(i!=j){
                    users.get(i).addToUserExpenseList(users.get(j), 0);
                }
            }
        }
    }
    
    public void addExpense(Expense expense){
        calculateExpenses(expense);
        updateExpensesForAll(this);
    }
    
    private void updateExpensesForAll(House house) {
        List<User> users = house.getUsers();
        for(int i=0; i<users.size(); i++){
            User currentBorrower = users.get(i);
            List<UserExpense> borrowerExpenses = currentBorrower.getUserExpenseList();
            for(int j=0; j<borrowerExpenses.size(); j++){
                UserExpense currentlenderExpense = borrowerExpenses.get(j);
                if(currentlenderExpense.getAmount() > 0){
                    
                }
            }
        }
    }
    
    private void calculateExpenses(Expense expense){
        User lender = expense.getLender();
        List<User> borrowers = expense.getBorrowers();
        long amountPerPerson = Math.round(expense.getAmount()/(double)(borrowers.size()+1));
        for(int i=0; i<borrowers.size(); i++){
            borrowers.get(i).addToUserExpenseList(lender, amountPerPerson);
            borrowers.get(i).setDue(true);
        }
    }

    public void removeUser(String name){
        boolean userFound = false;
        User userToBeRemoved = null;
        if(countOfTenants > 0){
            for(User user: users){
                if(name.equalsIgnoreCase(user.getName())){
                    userFound = true;
                    userToBeRemoved = user;
                }
            }
        }

        if(!userFound){
            System.out.println("MEMBER_NOT_FOUND");
            return;
        }

        if(!userToBeRemoved.isDue()){
            users.remove(userToBeRemoved);
            countOfTenants--;
            System.out.println("SUCCESS");
        }
        else{
            System.out.println("FAILURE");
        }
        return;
    }

    public User getUserByName(String name) {
        for(User user: users){
            if(name.equalsIgnoreCase(user.getName())){
                return user;
            }
        }
        return null;
    }
}
