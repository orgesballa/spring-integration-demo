# echo-client.py

import socket

HOST_A = "127.0.0.1"  # The server's hostname or IP address
PORT_A = 10004  # The port used by the server

HOST_B = "127.0.0.1"  # The server's hostname or IP address
PORT_B = 10005  # The port used by the server

HOST_C = "127.0.0.1"  # The server's hostname or IP address
PORT_C = 10006  # The port used by the server


def client_a():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST_A, PORT_A))
        i = 0
        while i < 100:
            msg = "Hello, world to A - " + str(i) + " \000"
            s.sendall(bytes(msg, 'utf-8'))
            data = s.recv(1024)
            print(f"Received {data!r}")
            i += 1


def client_b():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST_B, PORT_B))
        i = 0
        while i < 100:
            msg = "Hello, world to B - " + str(i) + " \000"
            s.sendall(bytes(msg, 'utf-8'))
            data = s.recv(1024)
            print(f"Received {data!r}")
            i += 1


def client_c():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST_C, PORT_C))
        i = 0
        while i < 100:
            msg = "Hello, world to C - " + str(i) + " \000"
            s.sendall(bytes(msg, 'utf-8'))
            data = s.recv(1024)
            print(f"Received {data!r}")
            i += 1


if __name__ == '__main__':
    client_a()
    client_b()
    client_c()
