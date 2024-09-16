
# Ejemplo de Aplicación Cliente-Servidor en Java

Este es un ejemplo básico de una aplicación cliente-servidor en **Java** que demuestra los atributos de calidad como **escalabilidad**, **mantenibilidad** y **rendimiento**.

## Estructura de la Aplicación

- El **servidor** atiende múltiples clientes utilizando hilos (threads) y un `ExecutorService`.
- El **cliente** se conecta al servidor y envía mensajes.

### Atributos de Calidad Evidenciados

1. **Escalabilidad**: El servidor maneja múltiples clientes simultáneamente usando un pool de hilos.
2. **Mantenibilidad**: El código está estructurado en clases para mejorar su mantenibilidad.
3. **Rendimiento**: Al utilizar hilos, el servidor no se bloquea mientras espera respuestas de los clientes.

## Código del Servidor

```java
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
```

## Código del Cliente

```java
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
```

### Instrucciones para ejecutar

1. Ejecuta primero el código del servidor en un terminal o consola.
2. Ejecuta el cliente en otro terminal/consola para conectarte al servidor.
3. Envía mensajes desde el cliente, y el servidor los recibirá y responderá con una confirmación.
4. Para salir del cliente, escribe `salir`.

## Requisitos

- Java JDK 8 o superior.
- Editor de texto o entorno de desarrollo integrado (IDE) como IntelliJ IDEA o Eclipse.

