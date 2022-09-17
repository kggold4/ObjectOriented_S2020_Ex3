package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * This Algo_DWGraph class implements the dw_graph_algorithms interface,
 * for executing the graph algorithms on DWGraph_DS graphs,
 * @Author Kfir Goldfarb and Nadav Keysar
 */
public class DWGraph_Algo implements dw_graph_algorithms {

    // the graph the algorithms work on
    private directed_weighted_graph graph;

    // childes is hashmap that store for every node in the graph his childes in inner hashmap
    private HashMap<Integer,HashMap<Integer,edge_data>> childes;

    // parents is hashmap that store for every node in the graph his parents in inner hashmap
    private HashMap<Integer,HashMap<Integer,edge_data>> parents;

    // the current mode count for checking any change
    private int current_mc;

    // default constructor
    public DWGraph_Algo() { this.init(new DWGraph_DS()); }

    // constructor by given a graph
    public DWGraph_Algo(directed_weighted_graph graph) { this.init(graph); }

    // constructor by given a json string
    public DWGraph_Algo(String json_string) {

        // creates new Gson object and a new graph
        Gson gsonObject = new Gson();
        directed_weighted_graph new_graph = new DWGraph_DS();
        Type type = new TypeToken<JsonObject>() {}.getType();

        // gets the graph as gson objects ans nodes and edges as json array from the json file
        JsonObject graph = gsonObject.fromJson(json_string, type);
        JsonArray nodes = graph.get("Nodes").getAsJsonArray();
        JsonArray edges = graph.get("Edges").getAsJsonArray();

        // getting nodes from json file
        for (JsonElement node : nodes) {
            String[] str_nodes = node.getAsJsonObject().get("pos").getAsString().split(",");
            node_data n = new NodeData(node.getAsJsonObject().get("id").getAsInt());
            n.setLocation(new GEOLocation(Double.parseDouble(str_nodes[0]),
                    Double.parseDouble(str_nodes[1]),
                    Double.parseDouble(str_nodes[2])));
            new_graph.addNode(n);
        }

        // getting edges from json
        for(JsonElement edge : edges) {
            new_graph.connect(edge.getAsJsonObject().get("src").getAsInt(),
                    edge.getAsJsonObject().get("dest").getAsInt(),
                    edge.getAsJsonObject().get("w").getAsDouble());
        }

        // initialize graph
        init(new_graph);
    }

    // initialize a new graph to the graph algorithms
    @Override
    public void init(directed_weighted_graph g) {

        this.graph = g;
        this.childes = new HashMap<>();
        this.parents = new HashMap<>();

        for(node_data node : this.graph.getV()) {

            // for every node create connection hashmap
            HashMap<Integer,edge_data> child = new HashMap<>();
            HashMap<Integer,edge_data> parent = new HashMap<>();

            this.childes.put(node.getKey(), child);
            this.parents.put(node.getKey(), parent);
        }

        // going throw all the nodes in the graph
        for(node_data node : this.graph.getV()) {

            // going throw all nodes dest
            for(edge_data e: this.graph.getE(node.getKey())) {

                this.childes.get(node.getKey()).put(e.getDest(), e);
                this.parents.get(e.getDest()).put(e.getSrc(), e);

            }
        }

        this.current_mc = this.graph.getMC();

    }

    // return the graph
    @Override
    public directed_weighted_graph getGraph() { return this.graph; }

    // return a deep copy of the graph using deep copy constructors in DWGraph_DS and NodeData classes
    @Override
    public directed_weighted_graph copy() { return new DWGraph_DS(this.graph); }

    // is connected (strongly) method
    @Override
    public boolean isConnected() {

        // if the graph as been change after initialize it to the graph algorithms
        if(this.current_mc != this.graph.getMC()) this.init(this.graph);

        // if there is less than 2 nodes the graph is connected
        if (this.graph.nodeSize() <= 1) return true;

        // if graph have more than 1 nodes but not have edges unconnected
        if (this.graph.edgeSize() == 0) return false;

        // if |V| - 1 >= |E| cannot be connected
        if(this.graph.nodeSize() - 1 > this.graph.edgeSize()) return false;

        // getting the first node_data in the graph
        node_data start = this.graph.getV().iterator().next();

        // reset all node in the graph to 0
        for(node_data node : this.graph.getV()) node.setTag(0);

        // tagging from start node all childes to tag 1
        taggingChildes(start);

        // counting the tags of all the nodes in the graph
        int countChildesTags = 0;
        for(node_data node : this.graph.getV()) countChildesTags += node.getTag();

        // checking if the tags counting is equals to node size
        if(countChildesTags != this.graph.nodeSize()) return false;

        // reset all node in the graph to 0
        for(node_data node : this.graph.getV()) node.setTag(0);

        // tagging from start node all parents to tag 1
        taggingParents(start);

        // counting the tags of all the nodes in the graph
        int countParentsTags = 0;
        for(node_data node : this.graph.getV()) countParentsTags += node.getTag();

        // checking if the tags counting is equals to node size
        if(countParentsTags != this.graph.nodeSize()) return false;

        return true;

    }

    /**
     * by getting node_data n, recursively tag all neighbors the can reach as childes as 1
     * @param n
     */
    private void taggingChildes(node_data n) {

        Queue<node_data> queue = new LinkedList<>();

        queue.add(n);
        while(!queue.isEmpty()) {
            node_data temp = queue.poll();
            temp.setTag(1);
            for(edge_data e : this.childes.get(temp.getKey()).values()) {
                if(this.graph.getNode(e.getDest()).getTag() == 0) queue.add(this.graph.getNode(e.getDest()));
            }
        }
    }

    /**
     * by getting node_data n, recursively tag all neighbors the can reach as parents as 2
     * @param n
     */
    private void taggingParents(node_data n) {

        Queue<node_data> queue = new LinkedList<>();

        queue.add(n);
        while(!queue.isEmpty()) {
            node_data temp = queue.poll();
            temp.setTag(1);
            for(edge_data e : this.parents.get(temp.getKey()).values()) {
                if(this.graph.getNode(e.getSrc()).getTag() == 0) queue.add(this.graph.getNode(e.getSrc()));
            }
        }
    }

    // shortestPathDist method
    @Override
    public double shortestPathDist(int src, int dest) {

        // if the graph is empty
        if(this.graph.nodeSize() == 0) return -1;

        // getting start and end nodes from the graph
        node_data start = graph.getNode(src);
        node_data end = graph.getNode(dest);

        // if one of them is not in the graph
        if(start == null || end == null) return -1;

        // if src is equal to dest
        if(start.getKey() == end.getKey()) return 0;

        // if the graph not have edges
        if(this.graph.edgeSize() == 0) return -1;

        // get the path from src to dest from shortestPath method
        List<node_data> path = shortestPath(src, dest);

        // if path is null - cannot reach from src to dest nodes
        if(path == null) return -1;

        // if the path is valid, counting the distance of the shortest path
        double distance = 0;
        for(int i = 0; i < path.size() - 1; i++) {
            distance += this.graph.getEdge(path.get(i).getKey(), path.get(i + 1).getKey()).getWeight();
        }

        // return the distance
        return distance;
    }

    // shortestPath method
    @Override
    public List<node_data> shortestPath(int src, int dest) {

        // if the graph is empty
        if(this.graph.nodeSize() == 0) return null;

        // getting start and end nodes from the graph
        node_data start = graph.getNode(src);
        node_data end = graph.getNode(dest);

        // if one of them is not in the graph
        if(start == null || end == null) return null;

        // if src is equal to dest
        // return list with only one node src == dest
        if(start.getKey() == end.getKey()) {
            List<node_data> singleList = new ArrayList<>();
            singleList.add(start);
            return singleList;
        }

        // if the graph not have edges
        if(this.graph.edgeSize() == 0) return null;

        // using priority queue for dijkstra's algorithm
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // hashmap for store previous node of a node
        HashMap<Integer, node_data> previous = new HashMap<>();
        HashMap<Integer, Double> weights = new HashMap<>();

        // reset all nodes tag in the graph to "infinity"
        for(node_data node : this.graph.getV()) weights.put(node.getKey(), Double.MAX_VALUE);

        // assign start (src) tag to 0 and add it to the priority queue
        weights.put(start.getKey(), 0.0);
        pq.add(start.getKey());

        // dijkstra's algorithm
        while(!pq.isEmpty()) {

            // poll the shortest priority node, set as visited
            node_data pop = this.graph.getNode(pq.poll());

            // neighbor of pop node
            for(edge_data edge: this.graph.getE(pop.getKey())) {

                // calculate the shortest weight between node to start node
                double newDist = weights.get(pop.getKey()) + this.graph.getEdge(edge.getSrc(), edge.getDest()).getWeight();
                if(newDist < weights.get(edge.getDest())) {

                    // setting new shortest weight, add to queue and set previous
                    weights.put(edge.getDest(), newDist);
                    pq.add(edge.getDest());
                    previous.put(edge.getDest(), pop);
                }
            }
        }

        // create path array list
        List<node_data> path = new ArrayList<>();

        // going back from dest to src nodes throw previous hashmap and build the path
        node_data k = end;
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

        // return path
        return path;
    }

    // save the graph to a json file method
    @Override
    public boolean save(String file) {

        if(this.graph != null) {
            if(!file.contains(file + ".json")) {

                // creates Gson and Json Object for the json file
                Gson gsonObject = new Gson();
                JsonObject json = new JsonObject();

                // creates nodes and edges json arrays to save as json array
                JsonArray nodes = new JsonArray();
                JsonArray edges = new JsonArray();

                // save nodes to json array
                for(node_data node : this.graph.getV()) {
                    JsonObject current_node = new JsonObject();
                    if(node.getLocation() != null) {
                        String location = node.getLocation().x() + ", " + node.getLocation().y() + ", " + node.getLocation().z();
                        current_node.addProperty("pos", location);
                    } else current_node.addProperty("pos", "null");
                    current_node.addProperty("id", node.getKey());
                    nodes.add(current_node);

                    // save edges to json array
                    for(edge_data edge : this.graph.getE(node.getKey())) {
                        JsonObject current_edge = new JsonObject();
                        current_edge.addProperty("src", edge.getSrc());
                        current_edge.addProperty("w", edge.getWeight());
                        current_edge.addProperty("dest", edge.getDest());
                        edges.add(current_edge);
                    }
                }

                // adding nodes and edges json arrays to json object
                json.add("Nodes", nodes);
                json.add("Edges", edges);

                // trying to save the json objects to a file
                try {

                    // create the file
                    PrintWriter writer = new PrintWriter(new File(file));

                    // write to the file
                    writer.write(gsonObject.toJson(json));

                    // flushing the writer
                    writer.flush();

                    // close file after saving
                    writer.close();
                    return true;

                    // fail to save to a file
                } catch (FileNotFoundException e) { e.printStackTrace(); }
            }
        }

        return false;

    }

    // load the graph from a json file method
    @Override
    public boolean load(String file) {

        if(!file.contains(file + ".json")) {

            // trying to read the graph object from the json file
            try {
                Gson gsonObject = new Gson();

                // create new graph to initialize to this class
                this.graph = new DWGraph_DS();
                Type type = new TypeToken<JsonObject>() {}.getType();

                // crate a reader for the file
                System.out.println(file);
                JsonReader reader = new JsonReader(new FileReader(file));

                // gets the graph as gson objects ans nodes and edges as json array from the json file
                JsonObject graph = gsonObject.fromJson(reader, type);
                JsonArray nodes = graph.get("Nodes").getAsJsonArray();
                JsonArray edges = graph.get("Edges").getAsJsonArray();

                // getting nodes from json file
                for (JsonElement node : nodes) {
                    node_data n = new NodeData(node.getAsJsonObject().get("id").getAsInt());
                    if(node.getAsJsonObject().get("pos") != null) {
                        String[] str_nodes = node.getAsJsonObject().get("pos").getAsString().split(",");
                        n.setLocation(new GEOLocation(Double.parseDouble(str_nodes[0]),
                                Double.parseDouble(str_nodes[1]),
                                Double.parseDouble(str_nodes[2])));
                    }



                    this.graph.addNode(n);
                }

                // getting edges from json
                for(JsonElement edge : edges) {
                    this.graph.connect(edge.getAsJsonObject().get("src").getAsInt(),
                            edge.getAsJsonObject().get("dest").getAsInt(),
                            edge.getAsJsonObject().get("w").getAsDouble());
                }

                // initialize graph
                init(this.graph);
                return true;

                // fail to read object from json file
            } catch (FileNotFoundException e) { e.printStackTrace(); }
        }
        return false;
    }

    /**
     * get lists of lists of the node in the graph
     * @return
     */
    public List<List<node_data>> getLists (){
        DFS dfs = new DFS((DWGraph_DS)this.graph);
        resetTags();
        return dfs.getLists();
    }

    /**
     * reset all the node's tag to -1, and set visible as false
     */
    public void resetTags() {
        for(node_data node : graph.getV()) {
            ((NodeData) node).setVisible(false);
            node.setTag(-1);
        }
    }

    /**
     * helpful private class using DFS algorithm
     */
    private class DFS {
        DWGraph_DS current_graph;
        Stack<NodeData> stack;
        List<List<node_data>> lists;

        // constructor by given graph
        public DFS(DWGraph_DS graph) {
            this.current_graph = graph;
            this.stack = new Stack<>();
            this.lists = new ArrayList<>();
        }

        public List<List<node_data>> dfs() {
            for(node_data nds : this.current_graph.getV()) if(((NodeData) nds).getVisible() == false) dfs((NodeData)nds);
            return this.lists;
        }

        public void dfs(NodeData node) {
            node.setVisible(true);
            stack.push(node);
            boolean isListRoot = true;
            for(edge_data edge : graph.getE(node.getKey())) {
                NodeData neighbor = (NodeData) graph.getNode(edge.getDest());
                if(neighbor.getVisible() == false) dfs(neighbor);
                if(node.getTag() > neighbor.getTag()){
                    node.setTag(neighbor.getTag());
                    isListRoot = false;
                }
            }
            if (isListRoot) {
                List<node_data> component = new ArrayList<>();
                while(true) {
                    node_data temp_node = this.stack.pop();
                    component.add(temp_node);
                    temp_node.setTag(Integer.MAX_VALUE);
                    if(temp_node == node) break;
                }
                this.lists.add(component);
            }
        }

        public List<List<node_data>> getLists() { return this.lists; }
    }
}
