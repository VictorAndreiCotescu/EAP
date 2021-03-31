import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    public User(String name, String email, Date dob, double balance, int rank) {

        this.id = counter.getAndIncrement()+1001;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
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

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card){
        this.cards.add(card);
    }

    private static final AtomicInteger counter = new AtomicInteger();
    private final int id;
    private String name;
    private String email;
    private Date dob;
    private double balance;
    private int rank;
    private List<Card> cards = new ArrayList<>();
    private TransactionHistory transactionHistory = new TransactionHistory();


}
