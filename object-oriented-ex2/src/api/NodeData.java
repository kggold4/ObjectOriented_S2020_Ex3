package api;

import java.io.Serializable;
import java.util.Objects;

// this NodeData class represents a node (vertex) in a graph and implements the node_data interface
public class NodeData implements node_data, Serializable {

    // every node_data have a unique id, counting by static ids
    private static int ids = 0;

    // node_data id
    private int id;

    // node_data geographic location
    private geo_location location;

    // node_data weight
    private double weight;

    // node_data information, use like meta data
    private String info;

    // node_data tag, use for graph algorithms
    private int tag;

    public boolean visible;

    // NodeData default constructor
    public NodeData() {
        this.id = ids;
        ids++;
        this.location = new GEOLocation();
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }

    // NodeData constructor by given key
    public NodeData(int key) {
        this.id = key;
        this.location = new GEOLocation();
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }

    // NodeData constructor by given location
    public NodeData(double x, double y, double z) {
        this.id = ids;
        ids++;
        this.location = new GEOLocation(x, y, z);
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }

    // NodeData constructor by given location and given key
    public NodeData(int key, double x, double y, double z) {
        this.id = key;
        this.location = new GEOLocation(x, y, z);
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }

    // NodeData constructor by given location and given key
    public NodeData(int key, geo_location location) {
        this.id = key;
        this.location = location;
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }

    // deep copy constructor
    public NodeData(node_data n) {
        this.id = n.getKey();
        this.location = new GEOLocation(n.getLocation());
        this.weight = n.getWeight();
        this.info = n.getInfo();
        this.tag = n.getTag();
    }

    // return the node_data id
    @Override
    public int getKey() { return this.id; }

    // return node_data location
    @Override
    public geo_location getLocation() { return this.location; }

    // set new location to the node_data
    @Override
    public void setLocation(geo_location p) { this.location = p; }

    // return the weight of the node_data
    @Override
    public double getWeight() { return this.weight; }

    // set new weight to the node_data
    @Override
    public void setWeight(double w) { this.weight = w; }

    // return the information of the node_data, like meta data
    @Override
    public String getInfo() { return this.info; }

    // set new information to the graph
    @Override
    public void setInfo(String s) { this.info = s; }

    // return the tag of the node_data
    @Override
    public int getTag() { return this.tag; }

    // set new tag to the node_data
    @Override
    public void setTag(int t) { this.tag = t; }

    // to string method
    @Override
    public String toString() { return "" + this.id; }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeData)) return false;
        NodeData nodeData = (NodeData) o;
        return id == nodeData.id &&
                Double.compare(nodeData.weight, weight) == 0 &&
                Objects.equals(location, nodeData.location) &&
                Objects.equals(info, nodeData.info);
    }

    // hash code method
    @Override
    public int hashCode() { return Objects.hash(id, location, weight, info); }

    /**
     * getVisible and setVisible are used in gameClient.Ex2.java part 2 of the assignment
     * @return
     */
    public boolean getVisible() { return this.visible; }

    public void setVisible(boolean visible) { this.visible = visible; }
}