import java.io.*;
import java.net.Socket;

public class Client {

    private static final int port = 9999;
    private static final String host = "127.0.0.1";

    public static void main(String[] args) {

        Socket socket = null;
        BufferedReader reader = null;
        PrintStream writer = null;
        BufferedReader input = null;

        try {
            socket = new Socket(host, port);
            System.out.println("connect " + host + ":" + port);

            input = new BufferedReader(new InputStreamReader(System.in));

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintStream(socket.getOutputStream());

            System.out.println("waiting input...");

            while (true) {

                String inputMessage = input.readLine();
                if (inputMessage != null || !inputMessage.equals("")) {
                    System.out.println("input message: " + inputMessage);
                    writer.println(inputMessage);
                    writer.flush();
                }
                if (inputMessage.equals("exit")) {
                    break;
                }

                String message = reader.readLine();
                System.out.println("receive message: " + message);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
