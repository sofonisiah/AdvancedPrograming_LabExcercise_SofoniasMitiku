import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {

    private static final int PORT = 5000;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static final String HISTORY_FILE = "chat_history.txt";

    public static void main(String[] args) {

        System.out.println("Server started...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                ClientHandler client = new ClientHandler(socket);
                clients.add(client);

                new Thread(client).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {

        saveMessage(message);

        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public static String loadHistory() {

        StringBuilder history = new StringBuilder();

        try {

            File file = new File(HISTORY_FILE);

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {
                history.append(line).append("\n");
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return history.toString();
    }

    private static void saveMessage(String message) {

        try {

            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(HISTORY_FILE, true)
            );

            writer.write(message);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {

        private Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ClientHandler(Socket socket) {

            this.socket = socket;

            try {

                writer = new PrintWriter(socket.getOutputStream(), true);

                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            try {

                writer.println(loadHistory());

                String message;

                while ((message = reader.readLine()) != null) {
                    broadcast(message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            writer.println(message);
        }
    }
}
