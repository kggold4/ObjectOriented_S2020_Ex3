package ex1.src;

import java.util.*;
import java.io.*;

// WGraph_Algo class is the implementation of weighted_graph_algorithms interface
// And represents an undirected (positive) weighted Graph Theory algorithms
public class WGraph_Algo implements weighted_graph_algorithms {

    // the init graph
    private transient weighted_graph graph;

    // constructor by given a graph
    public WGraph_Algo(weighted_graph graph) { this.graph = graph; }

    // default constructor
    public WGraph_Algo() { this.graph = new WGraph_DS(); }

    // initialization method
    @Override
    public void init(weighted_graph g) { this.graph = g; }

    // getting this graph
    @Override
    public weighted_graph getGraph() { return this.graph; }

    // return a graph that is a deep copy of the init graph
    // create new graph called temp using WGraph_DS deep copy constructor
    @Override
    public weighted_graph copy() { return new WGraph_DS(this.graph); }

    // checking if the graph is connected
    // mean that there a valid path from any node in the graph to any others nodes
    @Override
    public boolean isConnected() {

        // if the graph is empty or have 1 nodes at most return true
        if(this.graph.getV().isEmpty() || this.graph.nodeSize() <= 1) return true;

        // if edges count is less then nodes count - 1, cannot be connected
        if(this.graph.edgeSize() < this.graph.nodeSize() - 1) return false;

        // visitedNodes will contain the node that are visited in BFS algorithm
        List<node_info> visitedNodes = new ArrayList<>();

        // get the graph nodes collection, and getting the first node_info from the nodes hash map
        Collection<node_info> nodesCollection = this.graph.getV();
        node_info start = nodesCollection.iterator().next();

        // reset all tags of nodes in the graph - to 0
        for(node_info n : nodesCollection) n.setTag(0);

        // going throw all the connected nodes in the graph with BFS algorithm using queue
        Queue<node_info> q = new LinkedList<>();
        q.add(start);

        // add start to visitedNodes
        visitedNodes.add(start);
        start.setTag(1);

        // BFS algorithm
        while(!q.isEmpty()) {

            // polling a node from the queue and going throw his neighbors
            node_info node = q.poll();
            for(node_info n : this.graph.getV(node.getKey())) {

                // if this node has not visited yet
                if(n.getTag() == 0) {

                    // every neighbor and node we visit we adding to visitedNodes,
                    // changing his tag to 1 and add to the queue
                    n.setTag(1);
                    q.add(n);
                    visitedNodes.add(n);
                }
            }
        }

        // if visitedNodes size is equal to graph nodes size we went throw all the nodes in the graph,
        // and that mean the graph is connected and return true, else the graph is not connected and return false.
        if(visitedNodes.size() == this.graph.nodeSize()) return true;
        else return false;
    }

    // return the shortest path distance (weight) between src and dest nodes
    // by using shortestPathDist method
    @Override
    public double shortestPathDist(int src, int dest) {

        // getting the path from src to dest nodes by shortestPath method
        List<node_info> path = shortestPath(src, dest);

        // if the path is null, there no a valid path between src to dest and return -1
        if(path == null) return -1;

        // if src and dest are the same nodes
        else if(path.size() == 1) return 0;

        // else return the tag of the dest node after the shortestPath use dijkstra's algorithm
        else return graph.getNode(dest).getTag();

    }

    // calculate the shortest path from src to dest nodes in the graph by edges weights using dijkstra's algorithm
    @Override
    public List<node_info> shortestPath(int src, int dest) {

        // if the graph is empty
        if(this.graph.nodeSize() == 0) return null;

        // getting start and end nodes from the graph
        node_info start = graph.getNode(src);
        node_info end = graph.getNode(dest);

        // if one of them is not in the graph
        if(start == null || end == null) return null;

        // if src is equal to dest
        // return list with only one node src == dest
        if(start.getKey() == end.getKey()) {
            List<node_info> singleList = new ArrayList<>();
            singleList.add(start);
            return singleList;
        }

        // if the graph not have edges
        if(this.graph.edgeSize() == 0) return null;

        // using priority queue for dijkstra's algorithm
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // hashmap for adding visited nodes
        // HashMap<Integer,Boolean> visitedNodes = new HashMap<>();

        // hashmap for store previous node of a node
        HashMap<Integer, node_info> previous = new HashMap<>();

        // reset all nodes tag in the graph to "infinity"
        for(node_info node : this.graph.getV()) {
            node.setTag(Double.MAX_VALUE);
            //visitedNodes.put(node.getKey(), false);
        }

        // assign start (src) tag to 0 and add it to the priority queue
        start.setTag(0);
        pq.add(start.getKey());

        // dijkstra's algorithm
        while(!pq.isEmpty()) {

            // poll the shortest priority node, set as visited
            node_info pop = this.graph.getNode(pq.poll());
            //visitedNodes.put(pop.getKey(), true);

            // neighbor of pop node
            for(node_info node: this.graph.getV(pop.getKey())) {

                // calculate the shortest weight between node to start node
                double newDist = pop.getTag() + this.graph.getEdge(node.getKey(), pop.getKey());
                if(newDist < node.getTag()) {

                    // setting new shortest weight, add to queue and set previous
                    node.setTag(newDist);
                    pq.add(node.getKey());
                    previous.put(node.getKey(), pop);

                }
            }
        }

        // create path array list
        List<node_info> path = new ArrayList<>();

        // going back from dest to src nodes throw previous hashmap and build the path
        node_info k = end;
        path.add(k);
        while(k != start) {

            // if the nodes are in the graph but no valid path between them
            if(previous.get(k.getKey()) == null) return null;

            // go to previous node
            k = previous.get(k.getKey());
            path.add(k);
        }

        // reverse the path to src -> n1 -> n2 -> ... -> dest
        Collections.reverse(path);

        // return the path
        return path;
    }

    // I was helped with this java serialization tutorial:
    // https://www.tutorialspoint.com/java/java_serialization.htm
    // save method is saving the init graph to a file as object
    // the file name is the file string argument
    @Override
    public boolean save(String file)  {

        // trying to save the object to a file
        try {

            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // write to file
            out.writeObject((WGraph_DS)this.graph);

            // close file
            out.close();
            fileOut.close();
            return true;

        // fail write to file
        } catch (IOException i) { return false; }
    }

    // load graph object from file
    @Override
    public boolean load(String file) {

        // trying to read the file
        try {

            // get the file
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // init the graph object
            this.graph = (weighted_graph)in.readObject();

            // close the file
            in.close();
            fileIn.close();
            return true;
        }

        // fail read the file
        catch (IOException i) { return false; }

        // fail read the object
        catch (ClassNotFoundException c) { return false; }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WGraph_Algo)) return false;
        WGraph_Algo that = (WGraph_Algo) o;
        return Objects.equals(graph, that.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph);
    }
}