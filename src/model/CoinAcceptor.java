package model;

import java.awt.event.ActionListener;
import java.util.Scanner;

public class CoinAcceptor implements PaymentReceiver {
    private int balance = 0;
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void deposit() {
        System.out.print("Введите номинал монеты: ");
        try {
            int coin = Integer.parseInt(sc.nextLine());
            if (coin > 0) {
                balance += coin;
                System.out.println("Баланс пополнен. Текущий баланс: " + balance);
            } else {
                System.out.println("Монета должна быть положительной!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод. Введите число.");
        }
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void deduct(int amount) {
        balance -= amount;
    }


}
