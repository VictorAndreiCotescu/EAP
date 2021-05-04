package Classes;

import java.util.concurrent.atomic.AtomicInteger;

public class Transaction {

    public Transaction(int userId, double sum, boolean completed) {
        this.id = counter.getAndIncrement()+900000;
        this.userId = userId;
        this.sum = sum;
        this.completed = completed;
    }

    private static final AtomicInteger counter = new AtomicInteger();
    private int id = 0;
    private int userId;
    private double sum;
    private boolean completed;

    public Transaction() {


    }
}
