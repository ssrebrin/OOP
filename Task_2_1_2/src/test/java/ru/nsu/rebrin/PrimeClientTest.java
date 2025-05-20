package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeClientTest {
    @Test
    void testIsPrime() {
        assertFalse(PrimeClient.isPrime(1), "1 is not prime");
        assertTrue(PrimeClient.isPrime(2), "2 is prime");
        assertTrue(PrimeClient.isPrime(3), "3 is prime");
        assertFalse(PrimeClient.isPrime(4), "4 is not prime");
        assertTrue(PrimeClient.isPrime(1000003), "1000003 is prime");
        assertFalse(PrimeClient.isPrime(6), "6 is not prime");
    }

    @Test
    void testParseInput() {
        String input = "{\"array\":[1000003,6],\"start\":0}";
        PrimeClient.ParsedData data = PrimeClient.parseInput(input);

        assertEquals(2, data.array.size(), "Array should have 2 elements");
        assertEquals(1000003, data.array.get(0), "First element should be 1000003");
        assertEquals(6, data.array.get(1), "Second element should be 6");
        assertEquals(0, data.start, "Start should be 0");
    }

    @Test
    void testSend() throws IOException {
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        PrimeClient.send(writer, "true");

        assertEquals("true\n", stringWriter.toString(), "Send should write the message with a newline");
    }
    @Test
    void testServerClientInteraction() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Запускаем сервер
        executor.submit(() -> {
            try {
                SimpleDimple.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Даём серверу время на запуск
        Thread.sleep(1000);

        // Запускаем клиент
        executor.submit(() -> {
            try (Socket socket = new Socket("localhost", 8080)) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String input = in.readLine();
                PrimeClient.ParsedData data = PrimeClient.parseInput(input);
                for (int i = data.start; i < data.array.size(); i++) {
                    boolean isPrime = PrimeClient.isPrime(data.array.get(i));
                    PrimeClient.send(out, String.valueOf(isPrime));
                }
                PrimeClient.send(out, "stop");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Даём время на выполнение
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Проверяем, что сервер завершил работу
        assertTrue(SimpleDimple.flag.get(), "Server should have found a non-prime number (6)");
    }

}