import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private PaymentReceiver paymentReceiver;

    private static boolean isExit = false;

    private AppRunner(PaymentReceiver paymentReceiver) {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        this.paymentReceiver = paymentReceiver;
    }


    public static void run() {
        int method = choosePaymentMethod();

        PaymentReceiver receiver;
        if (method == 1) {
            receiver = new CoinAcceptor();
        } else {
            receiver = new CardAcceptor();
        }

        AppRunner app = new AppRunner(receiver);

        while (!isExit) {
            app.startSimulation();
        }
    }

    public static int choosePaymentMethod() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (true) {
            System.out.println("Выберите тип оплаты:");
            System.out.println("1 - монетами,  2 - картой");

            String input = scanner.nextLine();

            if (input.equals("1") || input.equals("2")) {
                choice = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Некорректный ввод! Попробуйте снова.");
            }
        }
        return choice;
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумму: " + paymentReceiver.getBalance());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentReceiver.getBalance() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        System.out.println(" a - Пополнить баланс");
        showActions(products);
        System.out.println(" i - Сменить способ оплаты");
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1).toLowerCase();

        if ("a".equalsIgnoreCase(action)) {
            paymentReceiver.deposit();
            return;
        }
        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }
        if ("i".equalsIgnoreCase(action)) {
            changePaymentMethod();
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    paymentReceiver.deduct(products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    return;
                }
            }
            print("Недопустимая буква. Попрбуйте еще раз.");
        } catch (IllegalArgumentException e) {
            print("Ошибка ввода.");
        }

    }
    private void changePaymentMethod() {
        int method = choosePaymentMethod();
        if (method == 1) {
            this.paymentReceiver = new CoinAcceptor();
        } else {
            this.paymentReceiver = new CardAcceptor();
        }
        print("Способ оплаты успешно изменен!");
    }


    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
