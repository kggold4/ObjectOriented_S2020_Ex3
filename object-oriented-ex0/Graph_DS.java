package ex0;

import java.util.Collection;
import java.util.HashMap;

/**
 * this Graph_DS class is implements the graph interface and all his methods,
 * in every Graph_DS there is a hash map with all the node_data in the graph,
 * also there is a edgesCounter that counting the number of edges in the graph between nodes,
 * also there is a mc (mode count) that counting any changes in the graph.
 * @author kfir.goldfarb, GitHub link: https://github.com/kggold4
 */

public class Graph_DS implements graph {

    /** edges counter of this graph and mode count */
    private int edgesCounter, mc;

    /** nodes hash map */
    private HashMap<Integer,node_data> nodes;

    /**
     * Graph_DS constructor,
     * create and define nodes hash map, edges counter and mode count.
     */

    public Graph_DS() {
        this.nodes = new HashMap<>();
        this.edgesCounter = this.mc = 0;
    }


    public Graph_DS(graph other) {
        this.copy((Graph_DS)other);
    }

    private void copy(Graph_DS other) {
        this.nodes = other.nodes;
        this.edgesCounter = other.edgesCounter;
        this.mc = other.mc;
    }


    /**
     * adding 1 to Mode Count at any change in the inner state of the graph,
     * this is a private method that calls from other inner method in this class.
     */

    private void appendMC() { this.mc++; }

    /**
     * getting node_data by nodes hash map,
     * if the the node is null he cannot be found in the graph and return null,
     * else return node.
     * total complexity is O(1)
     * @param key - the node_id
     * @return null - if the node_data is not in the graph, node - if the node_data is in the graph
     */

    @Override
    public node_data getNode(int key) {

        // if the node_data is null it's mean that the node is not in the graph
        node_data node = this.nodes.get(key);
        if(node == null) return null;

            // else the node is in the graph
        else return node;
    }

    /**
     * first checking if graph is empty from nodes, if empty return false,
     * next getting nodes_data from nodes hash map in complexity of O(1),
     * next checking if both nodes aren't null, and if connected and return true in accordance,
     * else return false.
     * @param node1
     * @param node2
     * @return true - if node 1 and node 2 are neighbors, false - if not
     */

    @Override
    public boolean hasEdge(int node1, int node2) {

        // first check if the graph is empty
        if(this.nodes.isEmpty()) return false;

        // getting nodes n1 and n2 from the graph by node1 and node2 keys
        node_data n1 = this.getNode(node1);
        node_data n2 = this.getNode(node2);

        // checking if n1 and n2 are not null, if they are one of them at least is not in the graph
        // checking if n1 != n2, and if the nodes are neighbors by ni hash map in node data
        if(n1 != null && n2 != null && !n1.equals(n2) && node1 != node2 && n1.hasNi(node2) && n2.hasNi(node1)) return true;
        else return false;
    }

    /**
     * adding node_data node to the nodes hash map in complexity of O(1),
     * @param node - the node_data we adding to the tree
     */

    @Override
    public void addNode(node_data node) {
        this.nodes.put(node.getKey(), node);
        appendMC();
    }

    /**
     * first getting nodes by keys from the hash map in complexity of O(1),
     * next checking if both nodes aren't null,
     * if one of them are null - cannot found in the graph - do nothing,
     * else addNi method add n2 to neighbors collection of n1,
     * and addNi method add n1 to neighbors collection of n2,
     * [addNi method is in complexity of O(1) - (adding to ni hash map)],
     * total complexity is O(1) * 6 = O(1).
     * @param node1
     * @param node2
     */

    @Override
    public void connect(int node1, int node2) {

        // getting nodes n1 and n2 from the graph by node1 and node2 keys
        node_data n1 = this.getNode(node1);
        node_data n2 = this.getNode(node2);

        // checking if n1 and n2 are not null, if they are one of them at least is not in the graph
        // checking if n1 != n2, and if the nodes are not neighbors by ni hash map in node data
        if(n1 != null && n2 != null && !n1.equals(n2) && node1 != node2 && !n1.hasNi(node2) && !n2.hasNi(node1)) {
            n1.addNi(n2);
            n2.addNi(n1);
            edgesCounter++;
            appendMC();
        }
    }

    /**
     * get from the nodes hash map a collection (ArrayList) of the node and return,
     * total complexity is O(1).
     * @return a collection of nodes of the graph
     */

    @Override
    public Collection<node_data> getV() { return this.nodes.values(); }

    /**
     * get from the nodes hash map a node_data and return a collection of the neighbors,
     * total complexity is O(1).
     * @param node_id
     * @return a collection of nodes that are neighbors of node_id node_data
     */

    @Override
    public Collection<node_data> getV(int node_id) {
        return this.getNode(node_id).getNi();
    }

    /**
     * first get node_data from key node_id,
     * if node_data is null - node_data cannot be found in the graph and return null,
     * else node_data is in the graph,
     * and get neighbors collection and for each node remove edge with node_data,
     * then remove node_data from node hash map,
     * and return node_data of key node_id,
     * in worst case assume that this node_data is connected with all graph node,
     * and this why total complexity is O(n).
     * @param key
     * @return
     */

    @Override
    public node_data removeNode(int key) {

        // getting node from the graph by key
        node_data node = this.getNode(key);

        // if node is null - he cannot be found in the graph - return null
        if(node == null) return null;

            // else remove eny connection between node to his neighbors, and remove node from nodes hash map
        else {
            for(node_data i : node.getNi()) removeEdge(key, i.getKey());
            this.nodes.remove(key, node);
            appendMC();
            return node;
        }
    }

    /**
     * first if graph is empty do nothing,
     * next getting from the nodes hash map n1 and n2 nodes in complexity of O(1),
     * if n1 or n2 is null (cannot be found in the graph) do nothing,
     * else delete each other from each other neighbor hash map,
     * total complexity is O(1).
     * @param node1
     * @param node2
     */

    @Override
    public void removeEdge(int node1, int node2) {

        // if the graph is empty
        if(this.nodes.isEmpty()) return;

        // getting nodes n1 and n2 from the graph by node1 and node2 keys
        node_data n1 = this.getNode(node1);
        node_data n2 = this.getNode(node2);

        // checking if n1 and n2 are not null, if they are one of them at least is not in the graph
        // checking if n1 != n2, and if the nodes are neighbors by ni hash map in node data
        if(n1 != null && n2 != null && !n1.equals(n2) && node1 != node2 && n1.hasNi(node2) && n2.hasNi(node1)) {
            n1.removeNode(n2);
            n2.removeNode(n1);
            edgesCounter--;
            appendMC();
        }
    }

    /**
     * return the number of nodes in the graph - size of nodes in nodes hash map.
     * total complexity is O(1)
     * @return
     */

    @Override
    public int nodeSize() { return this.nodes.size(); }

    /**
     * return the number of edges in the graph - edgesCounter
     * total complexity is O(1)
     * @return
     */

    @Override
    public int edgeSize() { return this.edgesCounter; }

    /**
     * return the mode count - mc
     * total complexity is O(1)
     * @return
     */

    @Override
    public int getMC() { return this.mc; }

    /**
     * to string method for testing
     * @return
     */

    public String toString() {
        if(this.nodes.isEmpty()) return "[]";
        String str = "[";
        for(node_data i : this.getV()) str += i + ",";
        str = str.substring(0, str.length() - 1);
        return str + "]";
    }
}