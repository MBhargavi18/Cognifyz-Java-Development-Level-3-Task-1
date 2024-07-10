import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static String serverAddress;
    private static int serverPort;

    public static void main(String[] args) throws IOException {
        serverAddress = "127.0.0.1";  // Change this to your server's address
        serverPort = 12345;  // Ensure this matches the server's port

        Socket socket = new Socket(serverAddress, serverPort);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        System.out.println("Connected to chat server");

        new Thread(new IncomingMessageHandler(in)).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            out.println(scanner.nextLine());
        }
        
        scanner.close();
        socket.close();
    }

    private static class IncomingMessageHandler implements Runnable {
        private BufferedReader in;

        public IncomingMessageHandler(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Error reading message: " + e.getMessage());
            }
        }
    }
}
