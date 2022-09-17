package api;

import java.io.Serializable;
import java.util.Objects;

// this EdgeData class represents a edge between two nodes in a graph and implements edge_data interface
public class EdgeData implements edge_data, Serializable {

    // src and dest nodes
    private node_data src, dest;

    // weight of the edge
    private double weight;

    // information of the edge, use like meta data
    private String info;

    // edge tag, use for graph algorithms
    private int tag;

    // if edge is short
    private Boolean shortEdge;

    // EdgeData default constructor
    public EdgeData(node_data src, node_data dest, double weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.info = "";
        this.tag = 0;
        this.shortEdge = false;
    }

    // return the key (id) of the src node
    @Override
    public int getSrc() {
        return this.src.getKey();
    }

    // return the key (id) of the dest node
    @Override
    public int getDest() { return this.dest.getKey(); }

    // return the weight of the edge
    @Override
    public double getWeight() {
        return this.weight;
    }

    // return the information of the edge
    @Override
    public String getInfo() {
        return this.info;
    }

    // set new information to the edge
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    // return the tag of the edge
    @Override
    public int getTag() {
        return this.tag;
    }

    // set new tag to the edge
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public boolean getShort() { return this.shortEdge; }

    public void setShort(boolean shortEdge) { this.shortEdge = shortEdge; }

    @Override
    public String toString() {
        return "src: " + this.src + ", dest: " + this.dest + ", weight: " + this.weight;
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeData)) return false;
        EdgeData edgeData = (EdgeData) o;
        return Double.compare(edgeData.weight, weight) == 0 &&
                Objects.equals(src, edgeData.src) &&
                Objects.equals(dest, edgeData.dest) &&
                Objects.equals(info, edgeData.info);
    }

    // hash code method
    @Override
    public int hashCode() { return Objects.hash(src, dest, weight, info); }
}