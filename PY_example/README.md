
# Ejemplo de Aplicación Cliente-Servidor en Python

Este es un ejemplo básico de una aplicación cliente-servidor en Python que demuestra los atributos de calidad como **escalabilidad**, **mantenibilidad** y **rendimiento**.

## Estructura de la Aplicación

- El **servidor** atiende múltiples clientes utilizando hilos (threads).
- El **cliente** se conecta al servidor y envía mensajes.

### Atributos de Calidad Evidenciados

1. **Escalabilidad**: El servidor puede manejar múltiples clientes simultáneamente usando hilos.
2. **Mantenibilidad**: El código está estructurado en funciones para mejorar su mantenibilidad.
3. **Rendimiento**: Al utilizar hilos, el servidor no se bloquea mientras espera respuestas de los clientes.

## Código del Servidor

```python
import socket
import threading

def handle_client(client_socket, client_address):
    print(f"[NUEVA CONEXIÓN] Cliente {client_address} conectado.")
    connected = True
    while connected:
        try:
            msg = client_socket.recv(1024).decode('utf-8')
            if msg:
                print(f"[{client_address}] {msg}")
                client_socket.send("Mensaje recibido".encode('utf-8'))
            else:
                connected = False
        except:
            connected = False
    client_socket.close()
    print(f"[DESCONEXIÓN] Cliente {client_address} desconectado.")

def start_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('127.0.0.1', 8080))
    server_socket.listen(5)
    print("[SERVIDOR INICIADO] Esperando conexiones...")

    while True:
        client_socket, client_address = server_socket.accept()
        client_thread = threading.Thread(target=handle_client, args=(client_socket, client_address))
        client_thread.start()

if __name__ == "__main__":
    start_server()
```

## Código del Cliente

```python
import socket

def start_client():
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect(('127.0.0.1', 8080))

    print("Conectado al servidor. Escribe un mensaje para enviar:")
    while True:
        msg = input("> ")
        if msg.lower() == 'salir':
            break
        client_socket.send(msg.encode('utf-8'))
        response = client_socket.recv(1024).decode('utf-8')
        print(f"[SERVIDOR] {response}")
    
    client_socket.close()

if __name__ == "__main__":
    start_client()
```

### Instrucciones para ejecutar

1. Ejecuta primero el código del servidor en un terminal o consola.
2. Ejecuta el cliente en otro terminal/consola para conectarte al servidor.
3. Envía mensajes desde el cliente, y el servidor los recibirá y responderá con una confirmación.
4. Para salir del cliente, escribe `salir`.

## Requisitos

- Python 3.x
- Módulos `socket` y `threading` incluidos en la biblioteca estándar de Python.

