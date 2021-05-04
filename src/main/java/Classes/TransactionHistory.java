package Classes;

import java.util.List;

class Tuple<E, F> {
    public E transaction;
    public F type;
}

public class TransactionHistory {

    List<Tuple<Transaction, String>> transactions;

}
