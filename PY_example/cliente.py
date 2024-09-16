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
