public class FillUp extends Transaction{

    public FillUp(int userId, double sum, boolean completed, int cardId) {
        super(userId, sum, completed);
        this.cardId = cardId;
    }

    private int cardId;
}
