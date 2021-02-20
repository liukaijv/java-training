import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int port = 9999;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        ExecutorService executorService = Executors.newFixedThreadPool(40);

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("server start at port: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("accept client: " + socket.getLocalAddress() + ":" + socket.getPort());
                executorService.submit(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
