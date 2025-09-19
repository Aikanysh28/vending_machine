package model;

public interface PaymentReceiver {
    void deposit();
    int getBalance();
    void deduct(int amount);
}
