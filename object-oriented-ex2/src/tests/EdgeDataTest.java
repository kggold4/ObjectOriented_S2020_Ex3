package tests;

import org.junit.jupiter.api.Test;
import api.*;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {

    @Test
    void getSrc() {

        // create src and dest nodes
        node_data src = new NodeData();
        node_data dest = new NodeData();

        // create an edge between them
        edge_data g = new EdgeData(src, dest, 1.25);
        assertEquals(src.getKey(),g.getSrc());
    }

    @Test
    void getDest() {

        // create src and dest nodes
        node_data src = new NodeData();
        node_data dest = new NodeData();

        // create an edge between them
        edge_data g = new EdgeData(src, dest, 1.25);
        assertEquals(dest.getKey(),g.getDest());
    }

    @Test
    void getWeight() {

        // create src and dest nodes
        node_data src = new NodeData();
        node_data dest = new NodeData();

        // create an edge between them
        edge_data g = new EdgeData(src, dest, 1.25);
        assertEquals(1.25,g.getWeight());

    }

    @Test
    void getInfo_setInfo() {

        // create src and dest nodes
        node_data src = new NodeData();
        node_data dest = new NodeData();

        // create an edge between them
        edge_data g = new EdgeData(src, dest, 1.25);
        String s = "edge_101";
        g.setInfo(s);
        assertEquals(s,g.getInfo());

    }

    @Test
    void getTag_setTag() {

        // create src and dest nodes
        node_data src = new NodeData();
        node_data dest = new NodeData();

        // create an edge between them
        edge_data g = new EdgeData(src, dest, 1.25);
        int t = 10;
        g.setTag(t);
        assertEquals(t,g.getTag());

    }
}