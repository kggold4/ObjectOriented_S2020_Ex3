package ex1.src;

import java.io.Serializable;
import java.util.*;

// WGraph_DS class is the implementation of weighted_graph interface
// And represents an undirected (positive) weighted graph
public class WGraph_DS implements weighted_graph, Serializable {

    // nodes HashMap store all the nodes in the graph
    private HashMap<Integer,node_info> nodes;

    // neighbors HashMap is for store for any node in the graph a HashMap for node neighbors
    private HashMap<Integer,HashMap<Integer,node_info>> neighbors;

    // weights HashMap is for store for any node in the graph a HashMap for node neighbors weights
    private HashMap<Integer,HashMap<Integer,Double>> weights;

    // mc is the mode count that use for counting every change in the inner state of the graph
    // edgesCounter is for counting the number of edges in the graph
    private int mc, edgesCounter;

    // private method the add 1 to mode count at every change
    private void appendMC() { this.mc++; }

    // WGraph_DS constructor
    public WGraph_DS() {
        this.nodes = new HashMap<>();
        this.neighbors = new HashMap<>();
        this.weights = new HashMap<>();
        this.mc = this.edgesCounter = 0;
    }

    // deep copy constructor
    public WGraph_DS(weighted_graph other) {
        this.copy((WGraph_DS)other);
    }

    private void copy(WGraph_DS other) {
        this.nodes = other.nodes;
        this.neighbors = other.neighbors;
        this.weights = other.weights;
        this.edgesCounter = other.edgesCounter;
        this.mc = other.mc;
    }

    // getting from nodes HashMap the node_info by key
    // if the node_info cannot be found in the graph, the nodes HashMap will return null value
    @Override
    public node_info getNode(int key) { return this.nodes.get(key); }

    // checking if there a connection between two nodes in the graph method
    @Override
    public boolean hasEdge(int node1, int node2) {

        // getting nodes n1 and n2 from the graph by node1 and node2 keys
        node_info n1 = this.getNode(node1);
        node_info n2 = this.getNode(node2);

        // if the graph is empty or have only one node, or not have any edges
        // or if one of the nodes cannot be found in the graph of if they are equals
        if(this.nodeSize() <= 1 || n1 == null || // this.edgeSize() == 0 ||
                n2 == null || n1.equals(n2) || node1 == node2) return false;

        // getting n1 and n2 neighbors HashMaps
        HashMap<Integer,node_info> n1_neighbors = this.neighbors.get(n1.getKey());
        HashMap<Integer,node_info> n2_neighbors = this.neighbors.get(n2.getKey());

        // if n1 or n2 does not have any neighbors
        if(n1_neighbors == null || n2_neighbors == null) return false;
        else {
            node_info k2 = n1_neighbors.get(n2.getKey());
            node_info k1 = n2_neighbors.get(n1.getKey());
            if(k1 == null || k2 == null) return false;
            if(k1.getKey() == n1.getKey() && k2.getKey() == n2.getKey()) return true;

        }
        return false;
    }

    // get the double value of the edge weight between two nodes in the graph
    // return -1 if the nodes unconnected
    @Override
    public double getEdge(int node1, int node2) {

        // getting nodes n1 and n2 from the graph by node1 and node2 keys
        node_info n1 = this.getNode(node1);
        node_info n2 = this.getNode(node2);

        // if the graph is empty or have only one node, or not have any edges
        // or if one of the nodes cannot be found in the graph of if they are equals
        if(this.nodeSize() <= 1 || this.edgeSize() == 0 || n1 == null ||
                n2 == null || n1.equals(n2) || node1 == node2) return -1;

        // if n1 and n2 are neighbors return weight, else return -1
        if(this.hasEdge(n1.getKey(),n2.getKey())) return this.weights.get(n1.getKey()).get(n2.getKey());
        else return -1;
    }

    // adding new node_info to the graph by node_id key
    @Override
    public void addNode(int key) {

        // checking if there is a node_info in the graph with node_id by key
        if(this.nodes.get(key) != null) return;

        // else create node_info
        node_info n = new NodeInfo(key);
        this.nodes.put(n.getKey(), n);
        appendMC();
    }

    // connecting two nodes in the graph with the weight of the edge w
    // if the nodes already connected but the weight is different from w, only change w
    // if the nodes already connected and the weigh is equal to w, do nothing
    @Override
    public void connect(int node1, int node2, double w) {

        boolean haveBeenConnected = false;

        // if the graph is empty or have only one node - can't connect between nodes
        if(this.nodeSize() <= 1) return;

        // getting nodes n1 and n2 from the graph by node1 and node2 keys
        node_info n1 = this.getNode(node1);
        node_info n2 = this.getNode(node2);

        // first checking if both n1 and n2 is in the graph
        // if one of them is null - cannot be found in the graph
        // and if n1 and n2 are different nodes
        if(n1 == null || n2 == null || n1.equals(n2) || node1 == node2) return;

        // if n1 does not have any neighbors
        if(this.neighbors.get(n1.getKey()) == null && this.weights.get(n1.getKey()) == null) {

            // create neighbors HashMap for n1 neighbors
            HashMap<Integer,node_info> n1_neighbors_new = new HashMap<>();

            // insert n2 to neighbors HashMap of n1
            n1_neighbors_new.put(n2.getKey(),n2);

            // insert HashMap to neighbors HashMap
            this.neighbors.put(n1.getKey(),n1_neighbors_new);

            // create neighbors HashMap for n1 neighbors weights
            HashMap<Integer,Double> n1_weights_new = new HashMap<>();

            // insert n2 weight to neighbors weights HashMap of n1
            n1_weights_new.put(n2.getKey(),w);

            // insert HashMap to weights HashMap
            this.weights.put(n1.getKey(),n1_weights_new);

            haveBeenConnected = true;

        } else {

            HashMap<Integer,node_info> n1_neighbors = this.neighbors.get(n1.getKey());
            HashMap<Integer,Double> n1_weights = this.weights.get(n1.getKey());

            // if n1 is not connected to n2
            if(!this.hasEdge(n1.getKey(), n2.getKey())) {

            n1_neighbors.put(n2.getKey(),n2);
            n1_weights.put(n2.getKey(),w);

            haveBeenConnected = true;

            // if n1 is already connected to n2 and weight is equal to w
            } else if(this.hasEdge(n1.getKey(), n2.getKey()) && n1_weights.get(n2.getKey()) == w) {
                haveBeenConnected = false;
                return;
            }

            // if n1 is already connected to n2 and weight is not equal to w
            else {
                n1_weights.put(n2.getKey(),w);
                appendMC();
                haveBeenConnected = false;
            }
        }

        // if n2 does not have any neighbors
        if(this.neighbors.get(n2.getKey()) == null && this.weights.get(n2.getKey()) == null) {

            // create neighbors HashMap for n2 neighbors
            HashMap<Integer,node_info> n2_neighbors_new = new HashMap<>();

            // insert n1 to neighbors HashMap of n2
            n2_neighbors_new.put(n1.getKey(),n1);

            // insert HashMap to neighbors HashMap
            this.neighbors.put(n2.getKey(),n2_neighbors_new);

            // create neighbors HashMap for n2 neighbors weights
            HashMap<Integer,Double> n2_weights_new = new HashMap<>();

            // insert n1 weight to neighbors weights HashMap of n2
            n2_weights_new.put(n1.getKey(),w);

            // insert HashMap to weights HashMap
            this.weights.put(n2.getKey(),n2_weights_new);

            haveBeenConnected = true;

        } else {

            HashMap<Integer,node_info> n2_neighbors = this.neighbors.get(n2.getKey());
            HashMap<Integer,Double> n2_weights = this.weights.get(n2.getKey());

            // if n1 is not connected to n2
            if(!this.hasEdge(n1.getKey(), n2.getKey())) {

                n2_neighbors.put(n1.getKey(),n1);
                n2_weights.put(n1.getKey(),w);

                haveBeenConnected = true;

            // if n1 is already connected to n2 and weight is equal to w
            } else if(this.hasEdge(n1.getKey(), n2.getKey()) && n2_weights.get(n1.getKey()) == w) {
                haveBeenConnected = false;
                return;
            }

            // if n1 is already connected to n2 and weight is not equal to w
            else {
                n2_weights.put(n1.getKey(),w);
                appendMC();
                haveBeenConnected = false;
            }
        }

        if(haveBeenConnected) {
            appendMC();
            this.edgesCounter++;
        }
    }

    // getting a collection with all the nodes in the graph
    @Override
    public Collection<node_info> getV() { return this.nodes.values(); }

    // getting a collection with all neighbors nodes of node_info by node_id
    @Override
    public Collection<node_info> getV(int node_id) {

        // create an empty collection if node do not have any neighbors will return the empty collection
        Collection<node_info> empty = new ArrayList<>();

        // if node cannot be found in the graph
        node_info n = this.getNode(node_id);
        if(n.equals(null)) return empty;

        // if neighbors HashMap of n is null - not have any neighbors
        HashMap<Integer,node_info> n_neighbors = this.neighbors.get(n.getKey());
        if(n_neighbors == null) return empty;

        // if neighbors HashMap of n is exist - have neighbors
        else return new ArrayList<>(n_neighbors.values());
    }

    // returning the node that removed, if the node cannot be found in the graph return null
    @Override
    public node_info removeNode(int key) {

        // getting n node from the graph by key
        node_info n = this.getNode(key);

        // if n found in the graph
        if(n != null) {

            // getting n neighbors collection
            Collection<node_info> n_neighbors = this.getV(n.getKey());

            // getting n neighbors weights HashMap
            HashMap n_weights = this.weights.get(n.getKey());

            // if n have neighbors
            if(!n_neighbors.isEmpty() && n_weights != null) {

                // remove any connection between n and his neighbors
                for(node_info neighbor : n_neighbors) {
                    this.removeEdge(neighbor.getKey(), n.getKey());
                }

                // remove n neighbors HashMap from neighbors HashMap
                this.neighbors.remove(n.getKey(), neighbors.get(n.getKey()));

                // remove n neighbors weights HashMap from neighbors HashMap
                this.weights.remove(n. getKey(), n_weights);
            }

            // removing n from the graph
            this.nodes.remove(n.getKey(), n);
            this.appendMC();
            return n;

        }
        // if n cannot be found in the graph
        return null;
    }

    // remove the connection between two nodes in the graph, if they unconnected - do nothing
    @Override
    public void removeEdge(int node1, int node2) {

        // if the graph is empty or have only one node or not have edges - do nothing
        if(this.nodeSize() <= 1 || this.edgeSize() == 0) return;

        // getting nodes n1 and n2 from the graph by node1 and node2 keys
        node_info n1 = this.getNode(node1);
        node_info n2 = this.getNode(node2);

        // first checking if both n1 and n2 is in the graph
        // if one of them is null - cannot be found in the graph
        // and if n1 and n2 are different nodes
        // also checking if between n1 and n2 has edge
        if(n1 == null || n2 == null || n1.equals(n2) || node1 == node2
                || !this.hasEdge(n1.getKey(), n2.getKey())) return;

        // before removing the connection get the neighbors and weights HashMap of n1 and n2
        HashMap<Integer,node_info> n1_neighbors = this.neighbors.get(n1.getKey());
        HashMap<Integer,Double> n1_weights = this.weights.get(n1.getKey());

        // before removing the connection get the neighbors and weights HashMap of n2 and n1
        HashMap<Integer,node_info> n2_neighbors = this.neighbors.get(n2.getKey());
        HashMap<Integer,Double> n2_weights = this.weights.get(n2.getKey());

        // getting weight of the edge between n1 and n2
        double w = this.getEdge(n1.getKey(), n2.getKey());

        // remove connection between n1 to n2
        n1_neighbors.remove(n2.getKey(), n2);
        n1_weights.remove(n2.getKey(), w);

        // remove connection between n2 to n1
        n2_neighbors.remove(n1.getKey(), n1);
        n2_weights.remove(n1.getKey(), w);

        this.edgesCounter--;
        appendMC();
    }

    // getting the numbers of nodes in the graph
    @Override
    public int nodeSize() { return this.nodes.size(); }

    // getting the number of edges in the graph
    @Override
    public int edgeSize() { return this.edgesCounter; }

    // getting the mode count of the graph
    // (number of changes in the inner state of the graph)
    @Override
    public int getMC() { return this.mc; }

    // to string method
    @Override
    public String toString() {
        return "" + this.getV();
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WGraph_DS)) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return this.mc == wGraph_ds.mc &&
                this.edgesCounter == wGraph_ds.edgesCounter && Objects.equals(this.nodes, wGraph_ds.nodes) &&
                Objects.equals(this.neighbors, wGraph_ds.neighbors) && Objects.equals(this.weights, wGraph_ds.weights);
    }

    // hash code method
    @Override
    public int hashCode() {
        return Objects.hash(nodes, neighbors, weights, mc, edgesCounter);
    }

    // NodeInfo class that implements the node_info interface
    // NodeInfo class represents a node (vertex) in the graph
    private class NodeInfo implements node_info, Serializable {

        // node_id is a unique value for any node_info
        private int node_id;

        // every node_info have an integer called tag that use for doing some algorithms on the nodes
        private double tag;

        // every node_info have a string value used like meta data
        private String info;

        // NodeInfo constructor
        public NodeInfo(int node_id) {
            this.node_id = node_id;
            this.tag = 0;
            this.info = "";
        }

        // getting the node_id associated to this node_info
        @Override
        public int getKey() { return this.node_id; }

        // getting the info associated to this node_info
        @Override
        public String getInfo() { return this.info; }

        // setting a new info value associated to this node_info
        @Override
        public void setInfo(String s) { this.info = s; }

        // getting the tag value associated to this node_info
        @Override
        public double getTag() { return this.tag; }

        // setting the tag value associated to this node_info
        @Override
        public void setTag(double t) { this.tag = t; }

        @Override
        // to string method
        public String toString() { return "" + this.node_id; }

        // equals method
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NodeInfo)) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return node_id == nodeInfo.node_id && Double.compare(nodeInfo.tag, tag) == 0 &&
                    Objects.equals(info, nodeInfo.info);
        }

        // hash code method
        @Override
        public int hashCode() { return Objects.hash(node_id, tag, info); }
    }
}
