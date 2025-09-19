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

            int amount = 0;
            while (true) {
                System.out.print("Введите сумму для пополнения: ");
                String input = sc.nextLine();
                try {
                    amount = Integer.parseInt(input);
                    if (amount > 0) break;
                    else System.out.println("Сумма должна быть больше 0.");
                } catch (NumberFormatException e) {
                    System.out.println("Введите корректное число.");
                }
            }

            balance += amount;
            System.out.println("Баланс успешно пополнен. Текущий баланс: " + balance);
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
