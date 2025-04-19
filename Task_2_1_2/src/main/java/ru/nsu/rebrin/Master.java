package ru.nsu.rebrin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class Master { private static final int PORT = 5000; private static final int WORKER_COUNT = 3; private static final int TIMEOUT = 5000;

    public static void main(String[] args) throws IOException {
        int[] numbers = {2, 3, 5, 7, 9, 11, 13, 17, 19, 21, 23};
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket[] workers = new Socket[WORKER_COUNT];
        ObjectOutputStream[] outs = new ObjectOutputStream[WORKER_COUNT];
        ObjectInputStream[] ins = new ObjectInputStream[WORKER_COUNT];

        System.out.println("Ожидание воркеров...");

        for (int i = 0; i < WORKER_COUNT; i++) {
            Socket socket = serverSocket.accept();
            socket.setSoTimeout(TIMEOUT);
            workers[i] = socket;
            outs[i] = new ObjectOutputStream(socket.getOutputStream());
            ins[i] = new ObjectInputStream(socket.getInputStream());
            System.out.println("Воркера подключен: " + socket.getInetAddress());
        }

        int chunkSize = numbers.length / WORKER_COUNT;
        boolean foundNonPrime = false;

        for (int i = 0; i < WORKER_COUNT; i++) {
            int start = i * chunkSize;
            int end = (i == WORKER_COUNT - 1) ? numbers.length : start + chunkSize;
            int[] chunk = Arrays.copyOfRange(numbers, start, end);
            outs[i].writeObject(chunk);
        }

        for (int i = 0; i < WORKER_COUNT; i++) {
            try {
                Boolean hasNonPrime = (Boolean) ins[i].readObject();
                if (hasNonPrime) {
                    foundNonPrime = true;
                }
            } catch (SocketTimeoutException | ClassNotFoundException e) {
                System.out.println("Воркер " + i + " не ответил. Перераспределение...");
                // можно реализовать перераспределение здесь
            }
        }

        System.out.println("Результат: " + (foundNonPrime ? "Найдено непростое число." : "Все числа простые."));
    }
}