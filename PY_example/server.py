import socket
import threading

# Atributos de calidad:
# - Escalabilidad: El servidor puede manejar múltiples clientes usando hilos.
# - Mantenibilidad: El código está organizado y modular.
# - Rendimiento: Al usar hilos, el servidor puede atender varios clientes simultáneamente.

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
