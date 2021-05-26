package Classes;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import Tools.*;

public class User {
    public User(String name, String email, String dob, double balance, int rank) {
        this.id = counter.getAndIncrement()+1001;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.balance = balance;
        this.rank = rank;
    }

    public User(int id, String name, String email, String dob, double balance, int rank) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.balance = balance;
        this.rank = rank;
    }

    public User(){
        this.id = 0;
    }

    public static AtomicInteger getCounter() {
        return counter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getCards() {
        return card;
    }

    /*public void addCard(Card card){
        this.cards.add(card);
    }*/

    private static final AtomicInteger counter = new AtomicInteger();
    private final int id;
    private String name;
    private String email;
    private String dob;
    private double balance;
    private int rank;
    private String card;
    private TransactionHistory transactionHistory = new TransactionHistory();


}
