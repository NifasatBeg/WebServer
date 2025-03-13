# Web Server Performance Benchmark

## Overview
This project implements a basic web server in three different models:
1. **Single-Threaded** – Handles one request at a time.
2. **Multi-Threaded** – Spawns a new thread for each request.
3. **Thread Pool** – Uses a fixed number of worker threads.

The goal is to compare their performance under load and identify the most efficient model.

## Performance Benchmarking

To evaluate the efficiency of different server implementations, we used the following **load-testing command**:

```sh
seq 500 | xargs -P 50 -I {} curl -o /dev/null -s -w "Request {}: %{time_total} s\n" http://localhost:8080
```

- `seq 500` → Generates 500 requests.
- `xargs -P 50` → Runs 50 requests in parallel.
- `curl -o /dev/null -s -w "Request {}: %{time_total} s\n"` → Measures response time for each request.

### **Benchmark Results**

| Metric                    | Single-Threaded | Multi-Threaded | Thread Pool |
|---------------------------|---------------:|--------------:|------------:|
| **Avg Response Time (s)**  | 0.006055       | 0.005674      | 0.005568    |
| **Min Response Time (s)**  | 0.002298       | 0.002184      | 0.002423    |
| **Max Response Time (s)**  | 0.048209       | 0.019674      | 0.022847    |
| **Std Dev (s)**           | 0.004776       | 0.002332      | 0.002908    |

### **Analysis & Conclusion**
- The **Thread Pool model** has the **lowest average response time (0.005568s)**, making it the most efficient under load.
- The **Single-Threaded model** has a **very high max response time (0.048s)**, indicating poor scalability.
- The **Multi-Threaded model** offers a balance between efficiency and consistent response times.
- **Thread Pool and Multi-Threaded models outperform Single-Threaded significantly**, making them better suited for handling concurrent requests.

### **Verdict**  
🚀 **Thread Pool is the best choice** for handling concurrent requests efficiently.  
📉 **Single-threaded should be avoided for high-load scenarios.**


## Theory
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
