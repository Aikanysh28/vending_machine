package model;

import java.util.Scanner;

public class CardAcceptor implements PaymentReceiver {
    private int balance = 100;
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void deposit() {
        System.out.print("Введите номер карты: ");
        String card = sc.nextLine();
        System.out.print("Введите одноразовый пароль: ");
        String pin = sc.nextLine();

        if (!card.isEmpty() && !pin.isBlank()) {
            System.out.println("Пароль верный! Доступный баланс на карте: " + balance);
        } else {
            System.out.println("Неверный номер карты или пароль!");
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
