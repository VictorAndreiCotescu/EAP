import java.util.concurrent.atomic.AtomicInteger;

public class Card {

    public Card(int userId, String cardNumber, int cvv) {
        this.id = counter.getAndIncrement()+10000;
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public Card(){

        this.id = counter.getAndIncrement()+10000;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    private static final AtomicInteger counter = new AtomicInteger();
    private final int id;
    private int userId;
    private String cardNumber;
    private int cvv;

}
