package ex0;

import java.util.*;

/**
 * this Graph_Algo class is implements the graph_algorithms interface and all his methods
 * @author kfir.goldfarb, GitHub link: https://github.com/kggold4
 */

public class Graph_Algo implements graph_algorithms {

    /** this graph is the graph that the graph algorithms work on */
    private graph graph;

    /** this array list is used in isConnected method, and will store nodes that connected */
    private LinkedList<node_data> visitedNodesIsConnected;

    /**
     * Graph_Algo constructors
     * define the this graph as the graph we want to execute the algorithms
     */

    public Graph_Algo() {
        this.graph = new Graph_DS();
        this.visitedNodesIsConnected = new LinkedList<node_data>();
    }

    public Graph_Algo(graph graph) {
        this.graph = graph;
        this.visitedNodesIsConnected = new LinkedList<node_data>();
    }

    /**
     * change the graph method
     * @param graph - the graph we want to change to, to execute the graph algorithms
     */

    @Override
    public void init(graph graph) {
        this.graph = graph;
        this.visitedNodesIsConnected = new LinkedList<node_data>();
    }

    /**
     * crate a new graph - will be a deep copy of this graph,
     * adding and connecting all the nodes from this graph in temp graph
     * @return temp as a deep copy of the graph
     */

    @Override
    public graph copy() {
        graph temp = new Graph_DS(this.graph);
        return temp;




/*
        // create new graph
        graph temp = new Graph_DS();

        // create new node_data for every node_data of this graph
        // add this new node_data to temp graph,
        // create an edge for every node and his neighbors
        for(node_data node : this.graph.getV()) {
            node_data copy = new NodeData(node.getKey(), node.getInfo());
            temp.addNode(copy);
            for(node_data ni : node.getNi()) temp.connect(copy.getKey(), ni.getKey());
        }

        // return temp the new deep copy graph
        return temp;*/
    }

    /**
     * this method return true iff there is a valid path from every node to each other,
     * first we check if the graph is empty or have 1 node at most - if true we return true,
     * else we get the collection of graph nodes (getV method) and casting it to Array list,
     * and than we get the first node and adding him to visitedNodesIsConnected linked list,
     * and than with a queue q we going throw all the nodes in the graph - like BFS algorithm,
     * every node we visit we change his tag from 0 to 1 and add it to visitedNodesIsConnected linked list,
     * if the visitedNodesIsConnected array list length is equal to graph nodes size (number of nodes) return true,
     * else return false.
     * @return true - iff there is a valid path from every node to each other, false - if not.
     */

    @Override
    public boolean isConnected() {

        // if the graph is empty or have 1 nodes at most return true
        if(this.graph.getV().isEmpty() || this.graph.nodeSize() <= 1) return true;

        // get the graph nodes collection, casting to array list to get the first node_data from the nodes hash map
        Collection<node_data> nodesCollection = this.graph.getV();
        node_data start = nodesCollection.iterator().next();

        // reset all tags of nodes in the graph - to 0
        this.resetTags();
        this.visitedNodesIsConnected.clear();

        // going throw all the connected nodes in the graph with BFS algorithm using queue
        Queue<node_data> q = new LinkedList<>();
        q.add(start);

        // add start to visitedNodesIsConnected
        visitedNodesIsConnected.add(start);
        start.setTag(1);
        while(!q.isEmpty()) {

            // polling a node from the queue and going throw his neighbors
            node_data node = q.poll();
            for(node_data ni : node.getNi()) {

                // if this node has not visited yet
                if(ni.getTag() == 0) {

                    // every neighbor and node we visit we adding to visitedNodesIsConnected,
                    // changing his tag to 1 and add to the queue
                    ni.setTag(1);
                    q.add(ni);
                    visitedNodesIsConnected.add(ni);
                }
            }
        }

        // if visitedNodesIsConnected size is equal to graph nodes size we went throw all the nodes in the graph,
        // and that mean the graph is connected and return true, else the graph is not connected and return false.
        if(visitedNodesIsConnected.size() == this.graph.nodeSize()) return true;
        return false;
    }

    /**
     * this method is using shortestPath method to get the path from src to dest,
     * and return the size - 1, number of edges in the shortest path from src to dest.
     * @param src - start node
     * @param dest - end (target) node
     * @return -1 if non such path, size - 1 of the path size.
     */

    @Override
    public int shortestPathDist(int src, int dest) {

        // if src = dest - the path distance is 0
        if(src == dest && this.graph.getNode(src).equals(this.graph.getNode(dest))) return 0;

        // getting path from shortestPath method
        List<node_data> path = shortestPath(src, dest);

        // if non such path
        if(path == null) return -1;

            // size of the shortest path - 1 is the number of edges in this path
        else return path.size() - 1;
    }

    /**
     * this method getting a source and destination (nodes) in the graph,
     * and return list of path of nodes from the source to destination,
     * the algorithm is:
     * we create hash map called previous that store key - the node key, value - the previous node_data in the path,
     * we getting start and end nodes from the graph by src and dest keys,
     * if start or end nodes is null, one of them at least not in the graph so return null,
     * else we create a queue called q that will help us to go throw all nodes in the inner graph - BFS algorithm,
     * to distinguish if node have been visited or not we are using the tag of the node,
     * if the tag of the node is 0 - the node is not yet been visited, and if the tag is 1 - the node have been visited,
     * so ones we visit a node with the while loop we change his tag to 1 and add him to q,
     * in every visit of node we store in previous the node key and the previous node_data of the node,
     * if we got from start to end, we returning from buildPath the path from hash map previous.
     * @param src - start node.
     * @param dest - end (target) node.
     * @return path - the path from start node to end point, null - if non such path.
     */

    @Override
    public List<node_data> shortestPath(int src, int dest) {

        // if the graph is empty or not have edges
        if(this.graph.nodeSize() == 0 || this.graph.edgeSize() == 0) return null;

        // if src = dest
        if(src == dest && this.graph.getNode(src).equals(this.graph.getNode(dest))) {
            List<node_data> singleList = new ArrayList<>();
            singleList.add(this.graph.getNode(src));
            return singleList;
        }

        // create previous hash map, key - node_data key, value - previous node_data
        HashMap<Integer, node_data> previous = new HashMap<>();

        // getting start and end nodes from the graph
        node_data start = graph.getNode(src);
        node_data end = graph.getNode(dest);

        // if one of them is not in the graph
        if(start == null || end == null) return null;

        // going throw all the connected nodes in the graph with BFS algorithm using queue
        Queue<node_data> q = new LinkedList<>();

        // add first node_data
        q.add(start);
        start.setTag(1);

        while(!q.isEmpty()) {

            // polling node_data from the queue
            node_data node = q.poll();

            // got from start to end
            if(node.equals(end)) {

                // reset nodes tags to 0
                this.resetTags();

                // get path from buildPath private method
                return buildPath(src, dest, previous);
            }

            // with the queue going throw all neighbors of node
            for(node_data ni : node.getNi()) {

                // if the neighbor have not been visited
                if(ni.getTag() == 0) {

                    // set his tag to 1 - visited
                    ni.setTag(1);

                    // create him a previous node_data
                    previous.put(ni.getKey(), node);

                    // add him to queue
                    q.add(ni);
                }
            }
        }

        // can't reach to end
        return null;
    }

    /**
     * buildPath method will get from shortestPath method the source and destination in the graph and previous hash map,
     * we create new array list that will be the path from the source and destination,
     * with the previous hash map we go back from destination node to backward until we reach the source node,
     * and while this process we build a path list, reverse it and return the path,
     * @param src - start node key.
     * @param dest - end node key.
     * @param previous - hash map of with keys - node_id of node_data, and the value is previous node_data of this node.
     * @return path - the path from start node to end point.
     */

    private List<node_data> buildPath(int src, int dest, HashMap<Integer,node_data> previous) {

        // create a path using array list
        List<node_data> path = new ArrayList<>();

        // add last (target) node_data because going in reverse to the src node_data
        path.add(this.graph.getNode(dest));

        // with the previous hash map, from dest node we go back to previous nodes until we got to the src node
        while(dest != src) {

            // getting previous
            node_data prev = previous.get(dest);

            // adding to path
            path.add(prev);

            // make dest node equal to his previous node_data
            dest = prev.getKey();
        }

        // reversing the path to src --> node(2) --> ... --> node(p-1) --> dest, p as length of the path
        Collections.reverse(path);

        // returning path
        return path;
    }

    /**
     * this private method is reset the graph nodes (in graph nodes collection) tag to 0
     * after using algorithm that use the tags of the nodes in the graph
     */

    private void resetTags() { for(node_data i : this.graph.getV()) i.setTag(0); }
}