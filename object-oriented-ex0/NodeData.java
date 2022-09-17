package ex0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * this NodeData class is implements the node_data interface and all his methods
 * in every NodeData there is a node_id - the key of the node
 * and there is a meta data like tag and info
 * also in every node there is a hash map of the neighbors nodes
 * @author kfir.goldfarb, GitHub link: https://github.com/kggold4
 */

public class NodeData implements node_data {

    /** id's counter as a static variable */
    private static Integer ids = 0;

    /** node id ,tag and info */
    private Integer node_id;
    private int tag;
    private String info;

    /** node neighbors */
    private HashMap<Integer,node_data> ni;

    /**
     * NodeData constructors,
     * create and define the node_id of this node (assign from ids static variable),
     * info, tag and ni - neighbors hash map
     */

    public NodeData() {
        this.node_id = ids;
        ids++;
        this.info = "";
        this.tag = 0;
        this.ni = new HashMap<>();
    }


    public NodeData(int node_id, String info) {
        this.node_id = node_id;
        this.info = info;
        this.tag = 0;
        this.ni = new HashMap<>();
    }

    /**
     * return the node_id of this node_data.
     * @return
     */

    @Override
    public int getKey() { return this.node_id; }

    /**
     * @return a collection of node_data neighbors
     */

    @Override
    public Collection<node_data> getNi() { return new ArrayList<>(this.ni.values()); }

    /**
     * checking if the node by key node_id is connected to this node_data,
     * first if neighbors collection is empty return false
     * else get node node_data by key node_id,
     * if node is null, node cannot be found in the ni hash map and return false
     * else the node found on the neighbors hash map and return true
     * @param key
     * @return true is node_data by key is neighbor of the node_data
     */

    @Override
    public boolean hasNi(int key) {
        if(this.ni.size() == 0 || this.ni.get(key) == null) return false;
        else return true;
    }

    /**
     * add node as a neighbor to this node_data
     * if the node is already in the ni HashMap do nothing
     * @param node
     */

    @Override
    public void addNi(node_data node) { if(!this.ni.containsValue(node)) this.ni.put(node.getKey(), node); }

    /**
     * checking if node is contains in this node_data neighbors hash map
     * if the node is not in the neighbors hash map do nothing
     * else remove it from neighbors hash map
     * @param node
     */

    @Override
    public void removeNode(node_data node) { if(this.ni.containsValue(node)) this.ni.remove(node.getKey()); }

    /**
     * return the information of this node_data (string)
     * @return
     */

    @Override
    public String getInfo() { return this.info; }

    /**
     * set the information of this node_data (string)
     * @param s
     */

    @Override
    public void setInfo(String s) { this.info = s; }

    /**
     * return the tag value of this node_data (int)
     * @return
     */

    @Override
    public int getTag() { return this.tag; }

    /**
     * set the tag value of this node_data (int)
     * @param t - the new value of the tag
     */

    @Override
    public void setTag(int t) { this.tag = t; }

    /**
     * to string method for testing
     * @return
     */

    public String toString() { return "" + this.node_id; }

}