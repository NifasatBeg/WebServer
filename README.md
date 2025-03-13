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
| **Std Dev (s)**           | 0.882           | 0.271          | 0.286       |

### **Analysis & Conclusion**
- The **Multi-Threaded model** shows the best stability with consistent response times and the lowest variance.
- The **Thread Pool model** performs similarly but with slightly higher variability.
- The **Single-Threaded model** struggles with scalability, exhibiting significant delays under heavy load.

### **Verdict**  
🚀 **Multi-Threaded is the most stable choice** for handling concurrent requests efficiently.  
📉 **Single-threaded should be avoided for high-load scenarios.**

## Future Enhancements
- Implementing additional concurrency models like async I/O for improved efficiency.
- Expanding the test with higher concurrency levels for deeper performance insights.

## Authors
- [Nifasat]

