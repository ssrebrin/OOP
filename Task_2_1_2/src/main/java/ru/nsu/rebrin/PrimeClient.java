package ru.nsu.rebrin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Client.
 */
public class PrimeClient {
    private static final String HOST = "localhost";
    public static int PORT = 8080;

    public static void main(String[] args) {
            try (Socket socket = new Socket(HOST, PORT)) {
                System.out.println("Trying connect to " + HOST + ":" + PORT);
                System.out.println("Connection successfully");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // Читаем данные от сервера
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        System.out.println("Server returned null");
                        return;
                    }
                    System.out.println("Server returned: " + input);
                    // Парсим JSON
                    ParsedData data = parseInput(input);
                    List<Integer> numbers = data.array;
                    int start = data.start;

                    // Обрабатываем числа, начиная с индекса start
                    for (int i = start; i < numbers.size(); i++) {
                        //System.out.println(numbers.get(i));
                        boolean isPrime = isPrime(numbers.get(i));
                        send(out, String.valueOf(isPrime));
                    }
                }

            } catch (IOException e) {
                System.out.println("Client error: " + e.getMessage());
                e.printStackTrace();
        }
    }

    /**
     * Check if is prime.
     *
     * @param n in
     * @return true or false
     */
    static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Send.
     *
     * @param out out
     * @param message message
     */
    static void send(BufferedWriter out, String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    /**
     * Parsing to JSON.
     *
     * @param input string
     * @return JSON
     */
    static ParsedData parseInput(String input) {
        // Ожидаемый формат: {"array":[1000003,1000003,...,6],"start":0}
        List<Integer> numbers = new ArrayList<>();
        int start = 0;

        // Удаляем скобки и разделяем по ключам
        String cleaned = input.replace("{", "").replace("}", "");
        String[] parts = cleaned.split(",\"start\":");
        String arrayPart = parts[0].replace("\"array\":", "").replace("[", "").replace("]", "");
        start = Integer.parseInt(parts[1]);

        // Парсим массив чисел
        if (!arrayPart.isEmpty()) {
            String[] numberStrings = arrayPart.split(",");
            for (String num : numberStrings) {
                numbers.add(Integer.parseInt(num.trim()));
            }
        }

        return new ParsedData(numbers, start);
    }

    /**
     * Data.
     */
    static class ParsedData {
        List<Integer> array;
        int start;

        ParsedData(List<Integer> array, int start) {
            this.array = array;
            this.start = start;
        }
    }
}