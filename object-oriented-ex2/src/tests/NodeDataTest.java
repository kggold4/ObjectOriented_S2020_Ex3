package tests;

import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {

    private int limit = 100000;

    @Test
    void getKey() {

        // the default constructor assign to every node a key with a counter stating at 0
        // so the first node have an id of 0, the second 1 and on and on...

        // create limit nodes with default constructor
        int i = 0;
        node_data[] nodes0 = new node_data[limit];
        while(i < limit) {
            nodes0[i] = new NodeData();
            i++;
        }

        // checking if the nodes id is expected as i counter
        i = 0;
        while(i < limit) {
            assertEquals(i, nodes0[i].getKey());
            i++;
        }

        // create limit nodes with constructor by given key
        i = 0;
        node_data[] nodes1 = new node_data[limit];
        while(i < limit) {
            nodes1[i] = new NodeData(i);
            i++;
        }

        // checking if the nodes id is expected as given key
        i = 0;
        while(i < limit) {
            assertEquals(i, nodes1[i].getKey());
            i++;
        }
    }

    @Test
    void getLocation() {
        // create node with location by constructor
        node_data n = new NodeData(2.5,3.6,9.2);
        geo_location g = new GEOLocation(2.5,3.6,9.2);
        assertEquals(g, n.getLocation());
    }

    @Test
    void setLocation() {
        // setting a new location to the node
        node_data n = new NodeData();
        geo_location g = new GEOLocation(2.5,3.6,9.2);
        n.setLocation(g);
        assertEquals(g, n.getLocation());
    }

    @Test
    void getWeight_setWeight() {
        // set weight to the node and get it
        node_data n = new NodeData();
        double l = 646.2646;
        n.setWeight(l);
        assertEquals(l,n.getWeight());

    }

    @Test
    void getInfo_setInfo() {
        // set info to the node and get it
        node_data n = new NodeData();
        String s = "node_101";
        n.setInfo(s);
        assertEquals(s,n.getInfo());
    }

    @Test
    void getTag_setTag() {
        // set tag to the node and get it
        node_data n = new NodeData();
        n.setTag(5);
        assertEquals(5, n.getTag());
    }
}