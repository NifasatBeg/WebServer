The client-to-socket mapping is done at multiple levels, depending on whether you're referring to low-level OS networking or application-level server handling:

1. OS-Level (TCP/IP Stack)

When a client connects to a server:

The OS maintains a mapping between client IP/port and the server socket.

The server's listening socket (bind() and listen()) accepts new connections.

When a client connects, the OS creates a new socket (file descriptor) for that client.

This socket maps the client's IP & port to a server-side socket.


2. Application-Level (Server-Side Code)

In a server application (like in Python, Java, or Node.js), the client-socket mapping is often handled in a hashmap or dictionary to track client connections:

In multi-threaded or event-driven servers, each client socket is mapped to a connection object or a worker thread/process.

Example in Python (socketserver):

clients = {}  # Dictionary to map client addresses to sockets

def handle_client(client_socket, client_address):
    clients[client_address] = client_socket  # Store mapping
    while True:
        data = client_socket.recv(1024)
        if not data:
            break
        print(f"Received from {client_address}: {data}")

    del clients[client_address]  # Remove when client disconnects
    client_socket.close()


3. In Load Balancers & Reverse Proxies

If a load balancer (like Nginx, HAProxy) is used, it also maintains a mapping of client connections to backend servers.

This ensures consistent client-server mapping for session persistence.


Conclusion

At the OS level, mapping is done in the kernel’s TCP stack.

At the application level, mapping is usually done using hashmaps, lists, or connection pools to track active clients.
