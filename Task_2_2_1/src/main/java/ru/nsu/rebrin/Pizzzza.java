package ru.nsu.rebrin;

import java.util.Scanner;

/**
 * Interactive class to work with Pizzeria.
 */
public class Pizzzza {

    private final Pizzeria pizzeria;
    private final Scanner scanner;

    /**
     * Constructor.
     *
     * @param pizzeria - pizzeria
     * @param scanner  - scanner
     */
    public Pizzzza(Pizzeria pizzeria, Scanner scanner) {
        this.pizzeria = pizzeria;
        this.scanner = scanner;
    }

    /**
     * Main work.
     */
    public void letsWork() {
        System.out.println("Write");

        try {
            while (pizzeria.isOpen()) {
                System.out.print("Enter command: ");
                String userInput = scanner.nextLine();
                switch (userInput) {
                    case "order":
                        System.out.println("Order");
                        pizzeria.order();
                        break;
                    case "stop":
                        System.out.println("Stopping pizzeria...");
                        pizzeria.stop();
                        break;
                    default:
                        System.out.println("Use stop or order");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
                System.out.println("Scanner closed.");
            }
        }
    }
}
/*
    public static void main(String[] args) {
        try {
            // Чтение конфигурации из JSON-файла
            Pizzeria pizzeria = Pizzeria.fromJson("config.json");

            // Использование try-with-resources для автоматического закрытия Scanner
            try (Scanner scanner = new Scanner(System.in)) {
                Pizzzza manager = new Pizzzza(pizzeria, scanner);
                manager.letsWork();
            }
        } catch (IOException e) {
            System.err.println("Failed to read configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }*/