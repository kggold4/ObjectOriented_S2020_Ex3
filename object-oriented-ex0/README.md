# Assignment ex0
## Author: Kfir Goldfarb,

this ex0 Assignment is to implements undirectional unweighted Graph Data Structure, we have three interfaces to implement:

	1. node_data
	2. graph
	3. grapg_algorithms


## the implementation:

	1. node_data:

		This interface represents the set of operations applicable on a node (vertex) in an (undirectional) unweighted graph,

		the methods in the interface are:
		getKey - Return the key (id) associated with this node.
		getNi - returns a collection with all the Neighbor nodes of this node_data.
		hasNi - return true iff this<==>key are adjacent, as an edge between them.
		addNi - This method adds the node_data (t) to this node_data.
		removeNode - Removes the edge this-node.
		getInfo - return the remark (meta data) associated with this node.
		setInfo - Allows changing the remark (meta data) associated with this node.
		getTag - Temporal data (aka color: e,g, white, gray, black), which can be used be algorithms.
		setTag - Allow setting the "tag" value for temporal marking an node - common.
		the implementation: of this interface is NodeData class.

	2. graph:

		This interface represents an undirectional unweighted graph.
		It should support a large number of nodes (over 10^6, with average degree of 10).

		the methods in the interface are:
		getNode - return the node_data by the node_id.
		hasEdge - return true iff (if and only if) there is an edge between node1 and node2.
		addNode - add a new node to the graph with the given node_data.
		connect - Connect an edge between node1 and node2.
		getV - This method return a pointer (shallow copy) for the collection representing all the nodes in the graph.
		getV(node) - This method returns a collection containing all the nodes connected to node_id.
		removeNode - Delete the node (with the given ID) from the graph.
		removeEdge - Delete the edge from the graph.
		nodeSize - return the number of vertices (nodes) in the graph.
		edgeSize - return the number of edges (undirectional graph).
		getMC - return the Mode Count - for testing changes in the graph.

	3. grapg_algorithms:

		This interface represents the "regular" Graph Theory algorithms.

		the methods in the interface are:
		init - Init the graph on which this set of algorithms operates on.
		copy - Compute a deep copy of this graph.
 		isConnected - Returns true if and only if (iff) there is a valid path from EVREY node to each.
		shortestPathDist - returns the length of the shortest path between src to dest.
		shortestPath - returns the the shortest path between src to dest.
		
		
		
	