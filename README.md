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
seq 500 | xargs -P 50 -I {} curl -o /dev/null -s -w "Request {}: %{time_total} s\n" http://localhost:8010
```

- `seq 500` → Generates 500 requests.
- `xargs -P 50` → Runs 50 requests in parallel.
- `curl -o /dev/null -s -w "Request {}: %{time_total} s\n"` → Measures response time for each request.

### **Benchmark Results**

| Metric                    | Single-Threaded | Multi-Threaded | Thread Pool |
|---------------------------|---------------:|--------------:|------------:|
| **Avg Response Time (s)**  | 4.745           | 0.991          | 1.001       |
| **Min Response Time (s)**  | 0.162           | 0.331          | 0.313       |
| **Max Response Time (s)**  | 5.447           | 1.562          | 1.616       |
| **Std Dev (s)**            | 0.882           | 0.271          | 0.286       |

### **Analysis & Conclusion**
- The **Multi-Threaded model** shows the best stability with consistent response times and the lowest variance.
- The **Thread Pool model** performs similarly but with slightly higher variability.
- The **Single-Threaded model** struggles with scalability, exhibiting significant delays under heavy load.

### **Verdict**
📉 **Single-threaded processing** has the highest response time and variability, making it unsuitable for performance-critical applications.

🚀 **Multi-threaded execution** significantly reduces response time (0.991s avg) and provides better consistency.

📈 **Thread pool execution** offers similar performance to multi-threading (1.001s avg) while maintaining stable resource utilization.

## Theory
The client-to-socket mapping is done at multiple levels, depending on whether you're referring to low-level OS networking or application-level server handling:

### **1. OS-Level (TCP/IP Stack)**
- The OS maintains a mapping between the client IP/port and the server socket.
- The server’s listening socket (via `bind()` and `listen()`) accepts new connections.
- When a client connects, the OS creates a new socket (file descriptor) for that client. This socket maps the client's IP & port to a server-side socket.

### **2. Application-Level (Server-Side Code)**
- In server applications (like Python, Java, or Node.js), client-socket mapping is often handled in a hashmap or dictionary to track client connections.
- In multi-threaded or event-driven servers, each client socket is mapped to a connection object or worker thread/process.

**Example in Python (socketserver):**

```python
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
```

### **3. In Load Balancers & Reverse Proxies**
- Load balancers (like Nginx, HAProxy) maintain a mapping of client connections to backend servers.
- This ensures consistent client-server mapping for session persistence.

### **Conclusion**
- At the **OS level**, mapping is done in the kernel’s TCP stack.
- At the **application level**, mapping is usually done using hashmaps, lists, or connection pools to track active clients.

## Future Enhancements
- Implementing additional concurrency models like async I/O for improved efficiency.
- Expanding the test with higher concurrency levels for deeper performance insights.

## Authors
- Nifasat
