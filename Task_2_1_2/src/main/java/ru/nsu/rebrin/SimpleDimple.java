package ru.nsu.rebrin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Non prim number.
 */
public class SimpleDimple {
    public static int PORT = 8080;
    public LinkedList<ServerSomthing> serverList = new LinkedList<>();
    public static AtomicBoolean flag = new AtomicBoolean(false);
    public LinkedList<Arr> mainArr;
    public ServerSocket server;

    /**
     * Generate stats.
     *
     * @param args - args
     */
    public static void main(String[] args) throws IOException {
        System.out.println("SimpleDimple starting on port " + PORT + "...");
        SimpleDimple sd = new SimpleDimple();

        int[] a = generateLargeArray(1000);
        LinkedList<Integer> array = new LinkedList<>();
        for (int i : a) {
            array.add(i);
        }

        int ii = 0;
        int l = array.size();
        sd.mainArr = new LinkedList<>();
        while (ii < l) {
            sd.mainArr.add(new Arr(new LinkedList<>(array.subList(ii, Math.min(ii + 30, l)))));
            ii += 30;
        }

        System.out.println("Start server");
        sd.server = new ServerSocket(PORT);
        PORT = sd.server.getLocalPort();
        try {
            while (true) {
                float n = 0;
                float nn = 0;
                for (Arr arr : sd.mainArr) {
                    if (arr.flag.get()) {
                        n++;
                    }
                    nn++;
                }
                System.out.println(n / nn / 100 + "% done");
                Socket socket = sd.server.accept();
                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
                try {
                    Arr aaa = sd.giveArr(sd);
                    if (aaa == null) {
                        System.out.println("Everything is done");
                        break;
                    }
                    sd.serverList.add(sd.new ServerSomthing(socket, aaa, sd)); // Use sd.new
                } catch (IOException e) {
                    socket.close();
                }
            }
        } catch (SocketException e) {
            Arr aaa = sd.giveArr(sd);
            if (aaa == null) {
                System.out.println("Everything is done");
            } else {
                System.out.println("ServerSocket closed, stopping accept: " + e.getMessage());
            }
        } finally {
            sd.server.close();
        }
    }

    public Arr giveArr(SimpleDimple sd) throws IOException {
        boolean flag = false;
        synchronized (Arr.class) {
            for (Arr aa : sd.mainArr) {
                if (!aa.done.get()) {
                    flag = true;
                }
                if (!aa.busy.get() && !aa.done.get()) {
                    System.out.println("The subarray is selected");
                    aa.busy.set(true);
                    return aa;
                }
            }
        }
        if (!flag) {
            sd.server.close();
        }
        return null;
    }

    /**
     * Generate large array.
     *
     * @param size - size
     * @return - array
     */
    public static int[] generateLargeArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = 1000003;
        }
        array[size - 1] = 6;
        return array;
    }

    public void printProcent(LinkedList<Arr> arr) {
        float n = 0;
        float nn = 0;
        // Блокируется до возникновения нового соединения:
        for (Arr arrr : arr) {
            if (arrr.done.get()) {
                n++;
            }
            nn++;
        }
    }

    /**
     * Thread class.
     */
    class ServerSomthing extends Thread {
        public Socket socket;
        private BufferedReader in;
        private BufferedWriter out;
        private Arr arr;
        private SimpleDimple simpleDimple; // Reference to outer class

        public ServerSomthing(Socket ssocket, Arr aarr, SimpleDimple sd) throws IOException {
            socket = ssocket;
            arr = aarr;
            simpleDimple = sd;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // Устанавливаем таймаут на сокет (10 секунд)
            socket.setSoTimeout(5000);
            start();
        }

        public void setFlag() {
            simpleDimple.flag.set(true);
        }

        @Override
        public void run() {
            System.out.println("ServerSomthing started");
            String word;
            while (true) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("{\"array\":[");
                    for (int i = 0; i < arr.array.size(); i++) {
                        sb.append(arr.array.get(i));
                        if (i < arr.array.size() - 1) sb.append(",");
                    }
                    sb.append("],\"start\":").append(arr.last).append("}");
                    send(sb.toString());
                    while (true) {
                        word = in.readLine();

                        if (word == null) {
                            break;
                        }
                        if (word.equals("true")) {
                            arr.flag.set(true);
                            setFlag();
                            arr.last++;
                        }
                        if (word.equals("false")) {
                            arr.last++;
                        }
                        if (word.equals("stop")) {
                            break;
                        }
                        if (arr.last == arr.array.size()) {
                            arr.done.set(true);
                            break;
                        }
                    }
                    System.out.println(socket.getRemoteSocketAddress() + " done");
                    arr.busy.set(false);
                } catch (IOException e) {
                    if (arr.last >= arr.array.size()) {
                        arr.done.set(true);
                    }
                    arr.busy.set(false);
                    break;
                }
                try {
                    arr = simpleDimple.giveArr(simpleDimple);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (arr == null) {
                    break;
                }
            }
        }

        private void send(String msg) {
            try {
                out.write(msg + "\n");
                out.flush();
                System.out.println("Sending " + msg);
            } catch (IOException ignored) {
            }
        }
    }
}