# Graphs--Algorithm-Design
This repository contains a collection of Java source files that address various algorithmic and simulation challenges. Each file demonstrates advanced problem-solving and system modeling skills in Java, offering efficient and effective solutions for different types of computational problems.


## 1. Counting
Determine the number of common paths in 2 directed graphs from node 1 to N.
* **readInput():** Reads the graphs from the file `numarare.in`.
* **initializeDp():** Initializes the vectors for dynamic programming.
* **countCommonParts():** Calculates the number of common paths using topological sorting and dynamic programming.
* **topologicalSort():** Performs topological sorting of the nodes in the graphs.
* **writeOutput():** Writes the result to the file `numarare.out`.

### Program Complexity
* **Time:** O(N + M), where N is the number of nodes and M is the number of edges. This is due to the topological traversal and processing each edge once.
* **Space:** O(N + M) due to storing the graphs and dynamic programming vectors.

## 2. Trains
Determine the longest possible path between two cities in a train network. The network is represented as a directed graph, where the nodes represent cities and the edges represent direct routes.
* **readInput():** Reads the graphs from the file `trenuri.in`.
* **dfs():** Used to find the longest path from a starting city to a destination; if the current city is the same as the destination, it means we have reached the destination and return 1 (path of length 1). If the result from the current city has already been calculated and stored in `longestPathMemo`, return the stored value. Initialize the variable `maxLength` to 0 to start the calculation and iterate through the neighbors of the current city. If a valid path is found, update `maxLength` to retain the longest path found. Store the maximum length in `longestPathMemo` and finally return `maxLength`.
* **writeOutput():** Writes the result to the file `trenuri.out`.

### Program Complexity
* **Time:** O(V + E) - V = number of cities; E = number of edges; each node and each edge is visited.
* **Space:** O(V) - storing the state of each node.

## 3. Roads
Find the lowest cost to reach a common location z from two locations x and y in a directed and weighted graph. The problem is solved using Dijkstra's algorithm to calculate the minimum distances.
* **readInput():** Reads the data from the file `drumuri.in`.
* **initArrays():** Initializes the adjacency lists of the graphs.
* **createEmptyArrayList(size):** Creates an array of empty lists.
* **computeDistances():** Calculates the minimum distances from x, y to z using Dijkstra.
* **dijkstra(start, graph, weights):** Implementation of Dijkstra's algorithm to calculate the minimum distances from a start node to all other nodes in the graph. Initializes the distance array with maximum values. Creates a priority queue to select the node with the minimum distance. Processes each node, updates distances, and adds adjacent nodes to the priority queue if a shorter path is found.
* **writeOutput():** Writes the final result to `drumuri.out`.

### Algorithm Complexity
* **Time:** O((N + M) * log N) - from Dijkstra.
* **Space:** O(N + M) - data structures for the graph.

## 4. Scandal
Determine if there is a valid allocation of true/false values for the variables of a set of logical conditions. The program uses Tarjan's algorithm to find strongly connected components (SCC) in a directed graph, deriving the solution to the 2-SAT problem from the structure of these components.

Types and conditions:
* Type 0: a XOR b
* Type 1: a ⇒ ¬b
* Type 2: ¬a ⇒ b
* Type 3: a XNOR b

* **extractComp(graph, endNode, stack, order):** Extracts a strongly connected component (SCC) from the stack and adds it to the list.
* **tarjan(graph, startNode, stack, order, time):** Implementation of Tarjan's algorithm for finding strongly connected components in a graph.
* **findOrder(graph):** Finds the order of the nodes using Tarjan.
* **solve(graph):** Solves the 2-SAT problem using the strongly connected components to determine the value of the variables.

### Program Complexity
* **Time:** O(V + E) - V = number of variables; E = number of edges.
* **Space:** O(V + E)
