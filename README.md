# Object-Oriented Programming Course Assignment #4
### Authors: Kfir Goldfarb and Nadav Keysar
<i>
this project is to implement directed weighted data structure and graph algorithms using Python Programming language, in this project we have three parts:
</i>

## Part 1 - DiGraph class:
Build directed weighted graph data structure (DiGraph class) implements the GraphInterface abstract class (as an interface).
in this Class we implement those methods:

1. ``v_size()`` - return the number of nodes in the graph (|V|)
2. ``e_size()`` - return the number of edges in the graph (|E|)
3. ``get_all_v()`` - return a dictionary of graph nodes {(node_id : NodeData)}
4. ``all_in_edges_of_node(node_id)`` - return a dictionary of all the nodes connected to node {(parent_id, weight)}
5. ``all_in_edges_of_node(node_id)`` - return a dictionary of all the nodes connected from the node {(child_id, weight)}
6. ``get_mc()`` - return graph mode count
7. ``add_edge(node1_id, node2_id, weight)`` - connect between two nodes with weight
8. ``add_node(node_id, position)`` - adding node to the graph
9. ``remove_node(node_id)`` - remove node from the graph and all the connections with other nodes
10. ``remove_edge(node1_id, node2_id)`` - removing connection between node1_id and node2_id

## Part 2 - GraphAlgo class:
Build graph algorithms class for the directed and wighted graphs.
in this Class we implement those methods:

1. ``get_graph()`` - return the init graph
2. ``save_to_json(file_name)`` - save the graph to a json file with the given file name
3. ``load_from_json(file_name)`` - load the graph from a json file by the give file name
4. ``shortest_path(node1_id, node2_id)`` - compute the shortest path between to nodes using Dijkstra's algorithm, and return (weight, path)
5. ``connected_component(node_id)`` - compute strongly connected components for a single node and return the list of the components
6. ``connected_components()`` - for each node in the graph use connected_component method and compute all graph strongly connected components and return a nested list of all the connected component in the graph 
7. ``plot_graph()`` - plot the graph wo a window using matplotlib

## Part 3 - Comparisons:
In this part we compare our algorithms between our Ex2 assignment algorithms that we build in java - see in <a href="https://github.com/kggold4/ObjectOriented_S2020_Ex2.git">Object-Oriented Ex2</a>,
and with NetworkX library algorithms - see in <a href="https://networkx.org/">networkx.org</a>

### Algorithms Performance Comparisons:

#### Python:

##### G_10_80_0:

<table>
    <tr>
        <td>
            load to file
        </td>
        <td>
            1 ms
        </td>
    </tr>
</table>

#### Java:

#### NetworkX: