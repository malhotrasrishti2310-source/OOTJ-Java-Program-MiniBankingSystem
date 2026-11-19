
import java.util.ArrayList;

public class BankOperations {
    private BankAccount account;
    private ArrayList<String> transactions;

    public BankOperations(BankAccount account) {
        this.account = account;
        transactions = new ArrayList<>();

        transactions.add("Account opened with Rs. "
                + account.getBalance());
    }

    public boolean deposit(double amount) {
        if (account.deposit(amount)) {
            transactions.add("Deposited: Rs. " + amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (account.withdraw(amount)) {
            transactions.add("Withdrawn: Rs. " + amount);
            return true;
        }
        return false;
    }

    public BankAccount getAccount() {
        return account;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }
}
