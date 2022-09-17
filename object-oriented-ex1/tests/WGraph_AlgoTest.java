package ex1.tests;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import ex1.src.*;

import static org.junit.jupiter.api.Assertions.*;
public class WGraph_AlgoTest {

    // limit is the number of nodes in the graph in every test
    private static int limit = 1000000;

    // testing initialization method
    @Test
    void init() {

        // create two graphs
        weighted_graph g0 = new WGraph_DS();
        weighted_graph g1 = new WGraph_DS();

        // create graph algorithm
        weighted_graph_algorithms ga0 = new WGraph_Algo();

        // initialization g0
        ga0.init(g0);

        // checking if the get graph is equal to g0
        assertEquals(g0,ga0.getGraph());

        // initialization g1
        ga0.init(g1);

        // checking if the get graph is equal to g1
        assertEquals(g1,ga0.getGraph());
    }

    // testing getGraph method
    @Test
    void getGraph() {

        // create graph
        weighted_graph g2 = new WGraph_DS();
        g2.addNode(0);
        weighted_graph_algorithms ga2 = new WGraph_Algo(g2);
        g2.addNode(1);
        assertEquals(g2,ga2.getGraph());
    }

    // testing copy method
    @Test
    void copy() {

        // create graph with limit nodes
        weighted_graph g3 = new WGraph_DS();
        int i = 0;
        while(i < limit) {
            g3.addNode(i);
            i++;
        }
        weighted_graph_algorithms ga3 = new WGraph_Algo(g3);

        // get copy of the graph
        weighted_graph g4 = ga3.copy();

        // without change should be equals
        assertEquals(g3,g4);

        // after change the original graph should unequal
        g3.addNode(limit);
        assertNotEquals(g3,g4);
    }

    // testing isConnected method
    @Test
    void isConnected() {

        // create graph with limit nodes
        weighted_graph g5 = new WGraph_DS();
        int i = 0;
        while(i < limit) {
            g5.addNode(i);
            i++;
        }

        // connecting all the nodes in the graph to node 0
        i = 1;
        while(i < limit) {
            g5.connect(0, i, 3.45);
            i++;
        }
        weighted_graph_algorithms ga5 = new WGraph_Algo(g5);

        // should be connected
        assertTrue(ga5.isConnected());

        // adding an insulator node
        g5.addNode(limit);

        // should be unconnected
        assertFalse(ga5.isConnected());

        // remove last node
        g5.removeNode(limit);

        // disconnecting all nodes in the graph
        i = 1;
        while(i < limit) {
            g5.removeEdge(0, i);
            i++;
        }

        // connection every node to near node like 1 <-> 2 <-> 3 <-> ... <-> node(limit)
        i = 0;
        while(i < limit - 1) {
            g5.connect(i, i + 1, 7.45);
            i++;
        }

        // should be connected
        assertTrue(ga5.isConnected());

        // disconnected last nodes to his neighbors
        for(node_info n : g5.getV(limit - 1)) g5.removeEdge(limit - 1, n.getKey());

        // should be unconnected
        assertFalse(ga5.isConnected());

    }

    // testing shortestPathDist method
    @Test
    void shortestPathDist() {

        // create graph with 10 nodes
        weighted_graph g6 = new WGraph_DS();
        int i = 0;
        while(i < 10) {
            g6.addNode(i);
            i++;
        }

        // create connections like this:
        // path A: 0 -> 1 -> 2 -> 3 -> 4 -> 5
        // path B: 0 -> 6 -> 7 -> 8 -> 9 -> 5
        // the edges weights of path A: [1.5, 3.5, 4.5, 6.5, 1.25],  weights sum: 17.25
        // the edges weights of path B: [1.25, 3.5, 4.5, 6.5, 1.25], weights sum: 17

        // A path
        g6.connect(0,1,1.5);
        g6.connect(1,2,3.5);
        g6.connect(2,3,4.5);
        g6.connect(3,4,6.5);
        g6.connect(4,5,1.25);

        // B path
        g6.connect(0,6,1.25);
        g6.connect(6,7,3.5);
        g6.connect(7,8,4.5);
        g6.connect(8,9,6.5);
        g6.connect(9,5,1.25);

        weighted_graph_algorithms ga6 = new WGraph_Algo(g6);

        // should be 17 - path B
        assertEquals(17, ga6.shortestPathDist(0,5));

        // change weight of the edge between nodes 9 and 5 to 2
        g6.connect(9,5,2);

        // should be 17.25 - path A
        assertEquals(17.25, ga6.shortestPathDist(0,5));

        // destination between node to same node should be 0
        assertEquals(0, ga6.shortestPathDist(0,0));

        // adding another node to graph
        g6.addNode(10);

        // no valid path from node 0 to node 10 - should be -1
        assertEquals(-1, ga6.shortestPathDist(0,10));

        // node 11 is cannot be found on the graph - should be -1
        assertEquals(-1, ga6.shortestPathDist(0,11));
    }

    // testing shortestPath method
    @Test
    void shortestPath() {

        // create graph with 10 nodes
        weighted_graph g7 = new WGraph_DS();
        int i = 0;
        while(i < 10) {
            g7.addNode(i);
            i++;
        }

        // create connections like this:
        // path A: 0 -> 1 -> 2 -> 3 -> 4 -> 5
        // path B: 0 -> 6 -> 7 -> 8 -> 9 -> 5
        // the edges weights of path A: [1.5, 3.5, 4.5, 6.5, 1.25],  weights sum: 17.25
        // the edges weights of path B: [1.25, 3.5, 4.5, 6.5, 1.25], weights sum: 17

        String A = "[0, 1, 2, 3, 4, 5]";
        String B = "[0, 6, 7, 8, 9, 5]";

        // A path
        g7.connect(0,1,1.5);
        g7.connect(1,2,3.5);
        g7.connect(2,3,4.5);
        g7.connect(3,4,6.5);
        g7.connect(4,5,1.25);

        // B path
        g7.connect(0,6,1.25);
        g7.connect(6,7,3.5);
        g7.connect(7,8,4.5);
        g7.connect(8,9,6.5);
        g7.connect(9,5,1.25);

        weighted_graph_algorithms ga7 = new WGraph_Algo(g7);

        // should be path B
        assertEquals(B, ga7.shortestPath(0,5).toString());

        // change weight of the edge between nodes 9 and 5 to 2
        g7.connect(9,5,2);

        // should be path A
        assertEquals(A, ga7.shortestPath(0,5).toString());

        // destination between node to same node should be [0]
        List<Integer> singleList = new ArrayList<>();
        singleList.add(0);
        assertEquals(singleList.toString(), ga7.shortestPath(0,0).toString());

        // adding another node to graph
        g7.addNode(10);

        // no valid path from node 0 to node 10 - should be null
        assertEquals(null, ga7.shortestPath(0,10));

        // node 11 is cannot be found on the graph - should be null
        assertEquals(null, ga7.shortestPath(0,11));

        // create new graph with limit nodes
        weighted_graph g8 = new WGraph_DS();
        i = 0;
        while(i < limit) {
            g8.addNode(i);
            i++;
        }

        // connect every pair nodes in the graph
        i = 0;
        while(i < limit - 1) {
            g8.connect(i, i + 1, 0.15);
            i++;
        }

        // crate a path list with integer: [0, 1, ..., node(limit - 1)]
        List<Integer> path = new ArrayList<>();
        i = 0;
        while(i < limit) {
            path.add(i);
            i++;
        }

        weighted_graph_algorithms ga8 = new WGraph_Algo(g8);

        // checking if path list same as shortest path from 0 to node(limit - 1)
        assertEquals(path.toString(), ga8.shortestPath(0,limit - 1).toString());

    }

    // testing save and methods
    @Test
    void save_load() {

        int newLimit = limit / 100;

        // create new graph with limit nodes
        weighted_graph g9 = new WGraph_DS();
        int i = 0;
        while(i < newLimit) {
            g9.addNode(i);
            i++;
        }

        // connect every pair nodes in the graph
        i = 0;
        while(i < newLimit - 1) {
            g9.connect(i, i + 1, 0.15);
            i++;
        }

        weighted_graph_algorithms ga9 = new WGraph_Algo(g9);

        // save graph to a file
        assertTrue(ga9.save("g9.obj"));

        // load object to new graph algorithm
        weighted_graph_algorithms ga10 = new WGraph_Algo();
        assertTrue(ga10.load("g9.obj"));

        // ga9 graph should be same as ga10 graph
        assertEquals(ga9.getGraph(), ga10.getGraph());
    }
}