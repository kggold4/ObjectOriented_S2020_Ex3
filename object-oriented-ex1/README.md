# Assignment 1 
## by Kfir Goldfarb
assignment 1 is an improvement of assignments 0, this assignment represents an undirectional weighted graph.
this graph support large number of nodes (over 10^6, with average degree of 10).
in the assignment we have three interfaces to implement:

1. node_info
2. weighted_graph
3. weighted_graph_algorithms

## WGraph_DS

WGraph_DS is the class that implement the weighted_graph interface,
and in the class there is a NodeInfo class as a private class that implement the node_info interface.

#### NodeData

first about the NodeData class, this class represents a node (vertex) in the graph,
as a private class, all the methods of the node_data in the class are private and only the WGraph_DS class can access them,
in NodeInfo class there is a five methods that implemented from node_info interface:

1. getKey - this method return the key (id) associated with the node, each node have a unique key, in the class this field variable called 'node_id' and his type is int.
2. getInfo - this method return  the remark (meta data) associated with this node, each node have a variable of type String that called 'info', it's use for store meta data and other string.
3. setInfo - this method allows changing the remark (meta data) associated with this node, by given argument String called 's'.
4. getTag - this method return the tag of the node - temporal data (aka distance, color, or state) which can be used be algorithms, each node have a variable called 'tag' and his type is int.
5. setTag - this method allow setting the "tag" value for temporal marking a node, by given argument double called 't'.

also in NodeInfo class we have implementation for toString, equals and hashCode methods.

#### WGraph_DS

in the WGraph_DS class there is a twelve methods that implemented from weighted_graph interface:

1. getNode - return the node_data by the node_id, by given argument int called 'key', all the nodes of the graph are store in a hashmap called 'nodes', the key of the hashmap for every node_info value is the node_id.
2. hasEdge - this method return true if and only if is an edge between node1 and node2 (arguments), each graph have a hashmap of hashmaps, the main hashmap is for every node (key is the node_id), and the inner hashmap is for store the neighbors of each node in the graph, for example if we a graph with nodes 1, 2 and 3 and 1 is connected by edges between 2 and 3 nodes, so if we get from the main neighbor's hashmap by key of node 1, we get the inner hashmap of node 1 with the nodes 2 and 3 in it, (stores by keys).
3. getEdge - this method return the weight of the edge between node1 and node2 arguments, in case and there is no such edge return -1.
4. addNode - this method get an int as argument called 'key', create a NodeInfo (from the private class), and add it to the graph (to the node's hashmap), if there is already a node with such a key no action performed.
5. connect - this method gets three arguments - key of node1 and node2, and weight called 'w' and his type is double, the weight is represent the weight between the nodes, each graph has a hashmap like neighbor's hashmap that called 'weights', that instead of store for each node a hashmap of neighbors, it's store the weights of the neighbors, the method will check if there is a connection between node1 and node2 by the neighbor's hashmap, if there is, check by the weight's hashmap if the weights is equals to 'w', if it is do nothing, but if different update to the new weight, in case and node1 and node2 nodes are unconnected create a connection - make as neighbors and store weight.
6. getV - this method return a collection representing all the nodes in the graph.
7. getV (by given specific node) - return a collection representing all the nodes that neighbors of the specific node.
8. removeNode - this method remove a node from the graph (by given his key) and all the connections between his neighbors (using removeEdge method), returning the node that removed, in case the node cannot be found in the graph do nothing and return null.
9. removeEdge - this method remove the connection between the given arguments node1 and node2 keys, if one of them cannot be found in the graph or unconnected to each other, do nothing.
10. nodeSize - this method return the number of node there is in this graph, by using the size method of node's hashmap.
11. edgeSize - this method return the number of edges there is between nodes that are in the graph, each graph have a field variable called 'edgesCounter', his type is int, and his purpose is to counting the number of the edges in the graph, this edgesCounter is the value that returning.
12. getMC - this method return the mode count - each graph have a field variable called 'mc', his type is int, and is purpose it's to count the number any change in the inner state of the graph (addNode, connect, removeNode and removeEdge), this 'mc' is the value that returning.

also in WGraph_DS class we have implementation for toString, equals and hashCode methods.


## WGraph_Algo

WGraph_Algo is the class that implement the weighted_graph_algorithms interface,
in the WGraph_Algo class there is a eight methods that implemented from weighted_graph_algorithms interface:

1. init - this method initialization a graph to the WGraph_Algo class, each weighted_graph_algorithms object have field variable called 'graph', his type is 'weighted_graph'.
2. getGraph - this method return the underlying graph of which this class works.
3. copy - this method return a deep copy of this weighted graph.
4. isConnected - this method return true if and only if there is a valid path from every node to each other node in the graph.
5. shortestPathDist - this method return the length of the shortest path between src to dest nodes arguments (by keys), this method is working in by the shortestPath method that return the shortest path between src and dest nodes, if the path length (list) is null - return -1 because there no valid path from src and dest nodes, if the path length is 1 - return 0 because src and dest nodes are the same node, if the path length is bigger than 1 return the tag of dest node (after using dijkstra's algorithm in shortestPath, the tag of dest node is the wight length between him and the src node).
6. shortestPath - this method return the shortest path (list of nodes) between src to dest nodes by using dijkstra's algorithm.
7. save - this method save the object graph to a file in the machine (file name adn directory is the given String argument called 'file'), in case and fail save to the file return false, if succeed return true.
8. load -this method load the object graph from a file in the machine (file name and directory is the given String argument called 'file'), in case and fail load from the file return false, if succeed return true.

also in WGraph_Algo class we have implementation for toString, equals and hashCode methods.

* for the two classes I create junit test called WGraph_DSTest and WGraph_AlgoTest