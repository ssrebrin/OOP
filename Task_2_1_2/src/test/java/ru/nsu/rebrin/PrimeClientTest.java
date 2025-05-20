package ru.nsu.rebrin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

/**
 * PrimeClient test
 */
public class PrimeClientTest {
    private ExecutorService executor;
    private ByteArrayOutputStream outContent;
    private ServerSocket testServer;
    private int testPort;

    @BeforeEach
    void setUp() throws IOException {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        executor = Executors.newFixedThreadPool(5);

        // Запускаем тестовый сервер с динамическим портом
        testServer = new ServerSocket(0);
        testPort = testServer.getLocalPort();
        // Устанавливаем порт для клиента
        PrimeClient.PORT = testPort;
    }

    @AfterEach
    void tearDown() throws IOException, InterruptedException {
        executor.shutdownNow();
        if (testServer != null && !testServer.isClosed()) {
            testServer.close();
        }
        System.setOut(System.out);
    }

    @Test
    void testMainSuccessfulConnectionAndProcessing() throws InterruptedException {
        // Запускаем тестовый сервер
        executor.submit(() -> {
            try (Socket clientSocket = testServer.accept()) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Отправляем JSON с массивом чисел
                String json = "{\"array\":[1000003,6],\"start\":0}";
                out.write(json + "\n");
                out.flush();

                // Читаем ответы клиента
                String response1 = in.readLine(); // Для 1000003 (простое)
                String response2 = in.readLine(); // Для 6 (не простое)
                assertEquals("true", response1, "Client should return true for 1000003");
                assertEquals("false", response2, "Client should return false for 6");

                // Ожидаем "stop" от клиента
                String stop = in.readLine();
                assertEquals("stop", stop, "Client should send 'stop' after processing");

                // Отправляем второй массив
                String json2 = "{\"array\":[7],\"start\":0}";
                out.write(json2 + "\n");
                out.flush();

                String response3 = in.readLine(); // Для 7 (простое)
                assertEquals("true", response3, "Client should return true for 7");

                String stop2 = in.readLine();
                assertEquals("stop", stop2, "Client should send 'stop' after processing");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Запускаем клиента
        executor.submit(() -> {
            try {
                PrimeClient.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Ждём завершения
        Thread.sleep(2000); // Даём время на обработку

        String output = outContent.toString();
        assertTrue(output.contains("Trying connect to localhost:" + testPort), "Client should print connection attempt");
        assertTrue(output.contains("Connection successfully"), "Client should connect successfully");
        assertTrue(output.contains("Server returned: {\"array\":[1000003,6],\"start\":0}"), "Client should print first JSON");
        //assertTrue(output.contains("Server returned: {\"array\":[7],\"start\":0}"), "Client should print second JSON");
    }

    @Test
    void testMainServerReturnsNull() throws InterruptedException {
        // Запускаем тестовый сервер, который сразу закроет соединение
        executor.submit(() -> {
            try (Socket clientSocket = testServer.accept()) {
                // Сразу закрываем соединение, чтобы клиент получил null
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Запускаем клиента
        executor.submit(() -> {
            try {
                PrimeClient.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Ждём завершения
        Thread.sleep(1000);

        String output = outContent.toString();
        assertTrue(output.contains("Trying connect to localhost:" + testPort), "Client should print connection attempt");
        assertTrue(output.contains("Connection successfully"), "Client should connect successfully");
        assertTrue(output.contains("Server returned null"), "Client should handle null response and exit");
    }

    @Test
    void testMainServerNotAvailable() throws InterruptedException, IOException {
        // Закрываем тестовый сервер, чтобы клиент не смог подключиться
        testServer.close();

        // Запускаем клиента
        executor.submit(() -> {
            try {
                PrimeClient.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Ждём завершения
        Thread.sleep(1000);

        String output = outContent.toString();
        //assertTrue(output.contains("Trying connect to localhost:" + testPort), "Client should print connection attempt");
        assertTrue(output.contains("Client error: Connection refused"), "Client should handle connection refusal");
    }
}