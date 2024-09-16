import java.io.*;
import java.net.*;

public class Cliente {
    private static final String HOST = "127.0.0.1";
    private static final int PUERTO = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Conectado al servidor. Escribe un mensaje para enviar (o 'salir' para desconectar):");
            String mensaje;
            while (!(mensaje = teclado.readLine()).equalsIgnoreCase("salir")) {
                out.println(mensaje);
                String respuesta = in.readLine();
                System.out.println("[SERVIDOR] " + respuesta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
