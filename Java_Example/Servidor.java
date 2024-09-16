import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private static final int PUERTO = 8080;
    private static final ExecutorService pool = Executors.newFixedThreadPool(10); // Para manejar múltiples clientes

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("[SERVIDOR INICIADO] Esperando conexiones...");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("[NUEVA CONEXIÓN] Cliente conectado.");
                pool.execute(new ClienteHandler(clienteSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClienteHandler implements Runnable {
    private Socket clienteSocket;

    public ClienteHandler(Socket socket) {
        this.clienteSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true)) {

            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                System.out.println("[CLIENTE] " + mensaje);
                out.println("Mensaje recibido");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clienteSocket.close();
                System.out.println("[DESCONEXIÓN] Cliente desconectado.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
