import java.util.*;
abstract class BankAccount {
    protected String accountNumber;
    protected String accountHolderName;
    protected int balance;

    BankAccount(String accountNumber, String accountHolderName, int balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited " + amount + " to " + accountNumber + ". New balance: " + balance);
    }

    abstract void withdraw(int amount) throws Exception;

    void displayDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;
    private int minimumBalance = 1000;

    SavingsAccount(String accountNumber, String accountHolderName, int balance, double interestRate) {
        super(accountNumber, accountHolderName, balance);
        this.interestRate = interestRate;
    }

    @Override
    void withdraw(int amount) throws Exception {
        if (balance - amount < minimumBalance) {
            throw new Exception("Insufficient Funds in Savings Account");
        } else {
            balance -= amount;
            System.out.println("Withdrawn " + amount + " from " + accountNumber + ". New balance: " + balance);
        }
    }
}

class CurrentAccount extends BankAccount {
    private int overdraftLimit;

    CurrentAccount(String accountNumber, String accountHolderName, int balance, int overdraftLimit) {
        super(accountNumber, accountHolderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    void withdraw(int amount) throws Exception {
        if (balance - amount < -overdraftLimit) {
            throw new Exception("Overdraft limit exceeded for Current Account");
        } else {
            balance -= amount;
            System.out.println("Withdrawn " + amount + " from " + accountNumber + ". New balance: " + balance);
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BankAccount[] accounts = new BankAccount[2];

        System.out.println("Enter Savings Account details (accountNumber, accountHolderName, balance, interestRate):");
        String saccountNumber = sc.next();
        String saccountHolderName = sc.next();
        int sbalance = sc.nextInt();
        double interestRate = sc.nextDouble();
        accounts[0] = new SavingsAccount(saccountNumber, saccountHolderName, sbalance, interestRate);

        System.out.println("Enter Current Account details (accountNumber, accountHolderName, balance, overdraftLimit):");
        String caccountNumber = sc.next();
        String caccountHolderName = sc.next();
        int cbalance = sc.nextInt();
        int overdraftLimit = sc.nextInt();
        accounts[1] = new CurrentAccount(caccountNumber, caccountHolderName, cbalance, overdraftLimit);

        try {
            System.out.println("Enter amount to withdraw from Savings Account:");
            int amount1 = sc.nextInt();
            accounts[0].withdraw(amount1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Enter amount to withdraw from Current Account:");
            int amount2 = sc.nextInt();
            accounts[1].withdraw(amount2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Enter amount to deposit to Savings Account:");
        int deposit1 = sc.nextInt();
        accounts[0].deposit(deposit1);

        System.out.println("Enter amount to deposit to Current Account:");
        int deposit2 = sc.nextInt();
        accounts[1].deposit(deposit2);

        System.out.println("Account details:");
        for (BankAccount account : accounts) {
            account.displayDetails();
        }

        sc.close();
    }
}
