package com.example.geektrust;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//	    File file = new File(args[0]);
        File file = new File("sample_input/input2.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        House house = new House(1);
        String line = bufferedReader.readLine();
        while(line != null){
//            System.out.println("Input : " + line);
            String[] inputBlocks = line.split(" ");
            if("MOVE_IN".equalsIgnoreCase(inputBlocks[0])){
                moveIn(house, inputBlocks);
            }
            else if("SPEND".equalsIgnoreCase(inputBlocks[0])){
                spend(house, inputBlocks);
            }
            else if("DUES".equalsIgnoreCase(inputBlocks[0])){
                dues(house, inputBlocks);
            }
            else if("CLEAR_DUE".equalsIgnoreCase(inputBlocks[0])){
                clearDue(house, inputBlocks);
            }
            else if("MOVE_OUT".equalsIgnoreCase(inputBlocks[0])){
                moveOut(house, inputBlocks);
            }
            line = bufferedReader.readLine();
        }
	}
    
    private static void moveIn(House house, String[] inputBlocks) {
        String userName = inputBlocks[1];
        User users = new User(userName, house);
        house.addUsers(users);
    }
    
    private static void dues(House house, String[] inputBlocks) {
        String name = inputBlocks[1];
        User borrower = house.getUserByName(name);
        if(borrower == null){
            System.out.println("MEMBER_NOT_FOUND");
        }else {
            borrower.printAllExpenses();
        }
    }
    
    private static void moveOut(House house, String[] inputBlocks) {
        String userName = inputBlocks[1];
        User user = house.getUserByName(userName);
        user.updateDueStatus();
        house.removeUser(userName);
    }
    
    private static void clearDue(House house, String[] inputBlocks) {
        String borrowerName = inputBlocks[1];
        String lenderName = inputBlocks[2];
        long amount = Long.valueOf(inputBlocks[3]);
        User borrower = house.getUserByName(borrowerName);
        List<UserExpense> borrowerExpenseList = borrower.getUserExpenseList();
        long remainingDues = 0;
        for(int i=0; i<borrowerExpenseList.size(); i++){
            UserExpense lenderAndAmount = borrowerExpenseList.get(i);
            if(lenderName.equalsIgnoreCase(lenderAndAmount.getUser().getName())){
                if(lenderAndAmount.getAmount() >= amount){
                    remainingDues = lenderAndAmount.getAmount() - amount;
                    lenderAndAmount.setAmount(lenderAndAmount.getAmount() - amount);
                }
                else{
                    System.out.println("INCORRECT_PAYMENT");
                    return;
                }
            }
        }
        System.out.println(remainingDues);
    }
    
    private static void spend(House house, String[] inputBlocks) {
        boolean borrowerNotFound = false;
        long amount = Long.valueOf(inputBlocks[1]);
        String spentByUser = inputBlocks[2];
        User lender = house.getUserByName(spentByUser);
        if(lender == null){
            System.out.println("MEMBER_NOT_FOUND");
            return;
        }
        List<User> borrowers = new ArrayList<>();
        for(int i=3; i< inputBlocks.length; i++){
            String borrowerName = inputBlocks[i];
            User borrower = house.getUserByName(borrowerName);
            if(borrower == null){
                System.out.println("MEMBER_NOT_FOUND");
                return;
            }
            borrowers.add(borrower);
        }
        
        Split split = Split.EQUAL;
        Expense expense = new Expense(lender, borrowers, split, amount);
        house.addExpense(expense);
        System.out.println("SUCCESS");
    }
}
