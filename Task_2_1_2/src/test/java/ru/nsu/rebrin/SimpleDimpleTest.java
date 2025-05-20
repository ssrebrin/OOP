package ru.nsu.rebrin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleDimpleTest {
    private ByteArrayOutputStream outContent;
    private ExecutorService executor;
    private SimpleDimple sd;

    @BeforeEach
    void setUp() {
        sd = new SimpleDimple();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        executor = Executors.newFixedThreadPool(2);
        // Сбрасываем статические данные перед каждым тестом
        sd.hasPrime.set(false);
        sd.PORT = 0; // Сбрасываем порт, чтобы main мог выбрать новый
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        executor.shutdownNow();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS), "Executor should terminate within 10 seconds");
        System.setOut(System.out); // Восстанавливаем System.out
    }

    @Test
    void testGenerateLargeArray() {
        int size = 100;
        int[] array = sd.generateLargeArray(size);
        assertEquals(size, array.length, "Array size should match the input size");
        assertEquals(6, array[size - 1], "Last element should be 6");
        for (int i = 0; i < size - 1; i++) {
            assertEquals(1000003, array[i], "All elements except the last should be 1000003");
        }
    }

    @Test
    void testMainMethod() throws InterruptedException {
        CountDownLatch serverReady = new CountDownLatch(1);
        CountDownLatch clientDone = new CountDownLatch(1);

        // Запускаем сервер
        executor.submit(() -> {
            try {
                sd.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Ждём, пока сервер не выведет "Start server", что означает готовность к подключению
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 5000) { // Ждём до 5 секунд
            String output = outContent.toString();
            if (output.contains("Start server")) {
                serverReady.countDown();
                break;
            }
            try {
                Thread.sleep(100); // Проверяем каждые 100 мс
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        assertTrue(serverReady.await(0, TimeUnit.SECONDS), "Server should be ready to accept connections within 5 seconds");

        // Проверяем, что сервер запустился
        String output = outContent.toString();
        assertTrue(output.contains("SimpleDimple starting on port"), "Server should start");
        assertTrue(output.contains("Start server"), "Server should print start message");


        // Запускаем клиента после того, как сервер готов
        executor.submit(() -> {
            try (Socket socket = new Socket("localhost", sd.PORT)) {
                socket.setSoTimeout(5000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                while (true) {
                    String input = in.readLine();
                    if (input == null) break;

                    PrimeClient.ParsedData data = PrimeClient.parseInput(input);
                    for (int i = data.start; i < data.array.size(); i++) {
                        boolean isPrime = PrimeClient.isPrime(data.array.get(i));
                        out.write(String.valueOf(isPrime) + "\n");
                        out.flush();
                    }
                    //out.write("stop\n");
                    //out.flush();

                    //if (data.array.contains(6)) break;
                }
                clientDone.countDown();
            } catch (IOException e) {
                e.printStackTrace();
                clientDone.countDown();
            }
        });

        // Ждём завершения клиента
        assertTrue(clientDone.await(15, TimeUnit.SECONDS), "Client should complete within 15 seconds");

        // Проверяем результаты
        output = outContent.toString();
        assertTrue(output.contains("Everything is done"), "Server should finish when all tasks are done");
        assertTrue(sd.hasPrime.get(), "Server should have found a non-prime number (6)");
    }
}