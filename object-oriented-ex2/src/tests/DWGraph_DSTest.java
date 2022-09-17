package tests;

import api.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DWGraph_DSTest {

    // number of nodes for the test
    private static int limit = 1000000;

    @Test
    void getNode() {

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // create a array of nodes
        node_data[] nodes = new node_data[limit];

        // create nodes from 0 to limit. add to the array and to the graph
        int i = 0;
        while(i < limit) {
            node_data n = new NodeData(i);
            nodes[i] = n;
            g.addNode(n);
            i++;
        }

        // compare all the array nodes to the graph nodes using getNode method with i index
        i = 0;
        while(i < limit) {
            assertEquals(nodes[i], g.getNode(i));
            i++;
        }
    }

    @Test
    void getEdge() {

        // create src and dest nodes
        node_data src = new NodeData();
        node_data dest = new NodeData();

        // create an edge between src and dest nodes
        edge_data edge = new EdgeData(src, dest, 10.5);

        // create new graph and add src and dest nodes
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(src);
        g.addNode(dest);

        // connect src to dest nodes
        g.connect(src.getKey(), dest.getKey(), 10.5);

        // compare edge and getEdge of src and dest nodes
        assertEquals(edge, g.getEdge(src.getKey(), dest.getKey()));

    }

    @Test
    void addNode() {

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // add limit nodes to the graph
        int i = 0;
        while(i < limit) {
            g.addNode(new NodeData(i));
            i++;
        }

        // compare all nodes by index i
        i = 0;
        while(i < limit) {
            assertEquals(0, g.getNode(0).getKey());
            i++;
        }
    }

    @Test
    void connect() {

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // add limit nodes to the graph
        int i = 0;
        while(i < limit) {
            g.addNode(new NodeData(i));
            i++;
        }

        // connecting every pair of nodes (0 -> 1, 2 -> 3 ...)
        i = 0;
        for(; i < limit - 1; i += 2) {
            int j = i + 1;
            g.connect(i, j,1.5);
        }

        // getting edge of every pair of nodes and compare with get src and i
        i = 0;
        while(i < limit - 1) {
            edge_data edge = g.getEdge(i, i + 1);
            assertEquals(i, edge.getSrc());
            i += 2;
        }
    }

    @Test
    void getV() {

        int limit = 60000;

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // add limit nodes to the graph
        int i = 0;
        while(i < limit) {
            g.addNode(new NodeData(i));
            i++;
        }

        ArrayList<node_data> nodes = new ArrayList<>();
        i = 0;
        while(i < limit) {
            nodes.add(new NodeData(i));
            i++;
        }

        i = 0;
        for(node_data n : g.getV()) {
            assertEquals(nodes.get(i).getKey(), n.getKey());
            i++;
        }

    }

    @Test
    void getE() {

        int limit = 60000;

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // add limit nodes to the graph
        int i = 0;
        while(i < limit) {
            g.addNode(new NodeData(i));
            i++;
        }

        i = 1;
        while(i < limit) {
            g.connect(0,i,1.5);
            i++;
        }

        i = 0;
        for(edge_data e : g.getE(0)) {
            i++;
            assertEquals(0, e.getSrc());
            assertEquals(i, e.getDest());
            assertEquals(1.5, e.getWeight());
        }

    }

    @Test
    void removeNode() {

        int limit = 50000;

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // add limit nodes to the graph
        int i = 0;
        while(i < limit) {
            g.addNode(new NodeData(i));
            i++;
        }

        i = 1;
        while(i < limit) {
            g.connect(0, i, 1.5);
            i++;
        }

        i = 1;
        while(i < limit) {
            assertNotEquals(null, g.getEdge(0, i));
            i++;
        }

        g.removeNode(0);

        i = 1;
        while(i < limit) {
            assertEquals(null, g.getEdge(0, i));
            i++;
        }
    }

    @Test
    void removeEdge() {

        int limit = 50000;

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // add limit nodes to the graph
        int i = 0;
        while(i < limit) {
            g.addNode(new NodeData(i));
            i++;
        }

        i = 1;
        while(i < limit) {
            g.connect(0, i, 1.5);
            i++;
        }

        i = 1;
        while(i < limit) {
            assertNotEquals(null, g.getEdge(0, i));
            i++;
        }

        i = 1;
        while(i < limit) {
            g.removeEdge(0, i);
            i++;
        }

        i = 1;
        while(i < limit) {
            assertEquals(null, g.getEdge(0, i));
            i++;
        }

    }

    @Test
    void nodeSize() {

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // add limit nodes to the graph
        int i = 0;
        while(i < limit) {
            g.addNode(new NodeData(i));
            i++;
        }

        assertEquals(limit, g.nodeSize());

        i = 0;
        while(i < limit) {
            g.removeNode(i);
            i++;
        }

        assertEquals(0, g.nodeSize());
    }

    @Test
    void edgeSize() {

        // create a graph
        directed_weighted_graph g = new DWGraph_DS();

        // add limit nodes to the graph
        int i = 0;
        while(i < limit) {
            g.addNode(new NodeData(i));
            i++;
        }

        i = 0;
        while(i < limit) {
            int j = i + 1;
            g.connect(i,j,10);
            i += 2;
        }

        assertEquals(limit / 2, g.edgeSize());

        i = 0;
        while(i < limit) {
            int j = i + 1;
            g.removeEdge(i,j);
            i += 2;
        }

        assertEquals(0, g.edgeSize());

    }
}