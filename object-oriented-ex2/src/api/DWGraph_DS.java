package api;

import java.io.Serializable;
import java.util.*;

/**
 * DWGraph_DS class represents a directional weighted graph implements directed_weighted_graph interface,
 * @Author Kfir Goldfarb and Nadav Keysar
 */
public class DWGraph_DS implements directed_weighted_graph, Serializable {

    // nodes hashmap store all the node_data in the graph
    private HashMap<Integer, node_data> nodes;

    // edges hashmap store all the edge_data in the graph
    private HashMap<Integer, HashMap<Integer,edge_data>> edges;

    // mode count - counting any change in inner state of the graph
    private int mc;

    // edges counter
    private int ec;

    // default DWGraph_DS constructor
    public DWGraph_DS() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.mc = 0;
    }

    // deep copy constructor
    public DWGraph_DS(directed_weighted_graph new_graph) {

        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();

        // going throw all the nodes in the graph
        for(node_data node : new_graph.getV()) {
            node_data new_node = new NodeData(node);
            this.nodes.put(new_node.getKey(), new_node);

            HashMap<Integer,edge_data> connection = new HashMap<>();

            // for every node in the graph going throw edges with neightbors
            for(edge_data e: new_graph.getE(new_node.getKey())) connection.put(e.getDest(), e);
            this.edges.put(new_node.getKey(),connection);
        }

    }

    // getting the node_data from the nodes hashmap by given the node_data key,
    // if the node_data cannot be found in the graph the hashmap return null
    @Override
    public node_data getNode(int key) { return this.nodes.get(key); }

    // return the edge_data from edges hashmap between src and dest
    // if src do not connected to dest return null
    @Override
    public edge_data getEdge(int src, int dest) {
        if(this.hasEdge(src, dest) && this.edges.get(src) != null) return this.edges.get(src).get(dest);
        else return null;
    }

    // adding node_data to the graph, to nodes and edges hashmaps
    @Override
    public void addNode(node_data n) {
        int key = n.getKey();
        if(this.nodes.get(key) == null) {
            this.nodes.put(key, n);
            //this.edges.put(key, null);
            appendMC();
        }
    }

    /**
     * @param src node_data key
     * @param dest node_data key
     * @return true if src node are connected to dest node, else return false
     */
    private boolean hasEdge(int src, int dest) {

        // if the graph have less than two nodes or not have edges - src and dest cannot be connected
        if(this.nodeSize() <= 1 || this.edgeSize() == 0) return false;

        // getting src and dest node_data from the nodes hashmap
        node_data node1 = this.getNode(src);
        node_data node2 = this.getNode(dest);

        // if node1 or node2 are null cannot be fount in the graph
        // if node1 equals to node2 not connected
        if(node1 == null || node2 == null || src == dest || node1 == node2) return false;

        // getting the neighbors hashmap of src
        HashMap<Integer,edge_data> srcNeighbors = this.edges.get(src);

        // if src do not have neighbors
        if(srcNeighbors == null) return false;

            // if src are neighbor of dest
        else if(srcNeighbors.containsKey(dest)) return true;

            // if src have neighbors but not neighbor of dest
        else return false;
    }

    // this method connecting (creating a edge) between src and dest nodes
    @Override
    public void connect(int src, int dest, double w) {

        // if the graph have less the two nodes, src and dest nodes cannot be found in the graph
        if(this.nodeSize() <= 1) return;

        // getting src and dest node_data from the graph
        node_data node1 = this.getNode(src);
        node_data node2 = this.getNode(dest);

        // if node1 or node2 are null cannot be fount in the graph
        // if node1 equals to node2 cannot connecting
        if(node1 == null || node2 == null || src == dest || node1 == node2) return;

        // getting the neighbors hashmap of src
        HashMap<Integer,edge_data> srcNeighbors = this.edges.get(src);

        // src and dest are not connected

        // src do not have neighbors
        if(srcNeighbors == null) {

            // crate and edge between src and dest nodes
            edge_data edge = new EdgeData(node1, node2, w);

            // create a connection between src and dest nodes with edge
            HashMap<Integer, edge_data> connection = new HashMap<>();
            connection.put(dest, edge);

            // put the connection in edges hashmap in key of src
            this.edges.put(src,connection);
            appendEC();
            appendMC();

            // src have neighbors but not connected to dest
        } else if(!this.hasEdge(src, dest)) {

            // create new edge between src and dest nodes
            edge_data edge = new EdgeData(node1, node2, w);

            // put the connection in edges hashmap in key of src
            this.edges.get(src).put(dest,edge);
            appendEC();
            appendMC();

            // src and dest are connected
        } else {

            // if weight != w
            if(this.edges.get(src).get(dest).getWeight() != w) {

                // removing the current edge between src to dest
                edge_data edge = this.edges.get(src).get(dest);
                this.edges.get(src).remove(dest,edge);

                // create a new edge between src to dest and put in the edges hashmap
                edge_data new_edge = new EdgeData(node1, node2, w);
                this.edges.get(src).put(dest,new_edge);

                appendEC();
                appendMC();

                // if weight == w do nothing
            } else return;
        }
    }

    // getting all node_data in a collection from the graph - nodes hashmap
    @Override
    public Collection<node_data> getV() { return this.nodes.values(); }

    // getting all edge_data in a collection of the given id node_data from edges hashmap
    @Override
    public Collection<edge_data> getE(int node_id) {
        if(this.getNode(node_id) == null) return null;
        else if(this.edges.get(node_id) == null) return new ArrayList<>();
        else return this.edges.get(node_id).values();
    }

    public Collection<edge_data> getE() {
        List<edge_data> edgesList = new ArrayList<>();
        for (HashMap<Integer,edge_data> neighbors:this.edges.values()) for (edge_data edge : neighbors.values()) edgesList.add(edge);
        return edgesList;
    }

    // removing node_data from the graph
    @Override
    public node_data removeNode(int key) {

        // getting the node_data from nodes hashmap by given key
        node_data node = this.nodes.get(key);

        // if node is null, cannot be found in the graph
        if(node == null) return null;

        // getting node neighbors hashmap
        HashMap<Integer, edge_data> nodeNeighbors = this.edges.get(key);

        // if node not have neighbors
        if(nodeNeighbors == null) {

            // remove node from nodes hashmap
            this.nodes.remove(key,node);
            appendMC();
            return node;

            // if node have neighbors
        } else {

            // removing all connection between key node_data and his neighbors
            Collection<edge_data> temp = new ArrayList<>(this.getE(key));

            for(edge_data edge : temp) {

                this.removeEdge(edge.getDest(),edge.getSrc());
                this.removeEdge(edge.getSrc(),edge.getDest());

            }

            // remove node from nodes hashmap
            this.nodes.remove(key,node);
            appendMC();
            return node;
        }
    }

    // remove the connection between src and dest nodes
    @Override
    public edge_data removeEdge(int src, int dest) {

        // if the graph have less than two nodes or not have edges - src and dest cannot be connected
        if(this.nodeSize() <= 1 || edgeSize() == 0) return null;

        // getting src and dest node_data from the nodes hashmap
        node_data node1 = this.getNode(src);
        node_data node2 = this.getNode(dest);

        // if node1 or node2 are null cannot be fount in the graph
        // if node1 equals to node2 not connected
        if(node1 == null || node2 == null || src == dest || node1 == node2) return null;

        // if src and dest nodes are connected
        if(this.hasEdge(src,dest)) {

            // removing the current edge between src to dest
            edge_data edge = this.edges.get(src).get(dest);
            this.edges.get(src).remove(dest,edge);
            subtractEC();
            appendMC();
            return edge;

            // if src and dest nodes are not connected
        } else return null;
    }

    // return the number of nodes in the graph by nodes hashmap
    @Override
    public int nodeSize() { return this.nodes.size(); }

    // return the number of edges in the graph by edges hashmap
    @Override
    public int edgeSize() { return this.ec; }

    // return the mode count of the graph - number of changes in the inner state of the graph
    @Override
    public int getMC() { return this.mc; }

    /**
     * add 1 to the mode count
     */
    private void appendMC() { this.mc++; }

    /**
     * add 1 to the edge count
     */
    private void appendEC() { this.ec++; }

    /**
     * subtract 1 from the edge count
     */
    private void subtractEC() { this.ec--; }

    // to string method
    @Override
    public String toString() { return "" + this.getV(); }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DWGraph_DS)) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        return Objects.equals(nodes, that.nodes) &&
                Objects.equals(edges, that.edges);
    }

    // hash code method
    @Override
    public int hashCode() { return Objects.hash(nodes, edges); }
}