public class Transaction {

    public Transaction(int id, int userId, double sum, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.sum = sum;
        this.completed = completed;
    }

    private int id;
    private int userId;
    private double sum;
    private boolean completed;

}
