package tests;

import api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    private static int limit = 1000000;

    @Test
    void init() {

        // create new graph
        directed_weighted_graph g0 = new DWGraph_DS();

        // adding limit nodes
        int i = 0;
        while(i < limit) {
            g0.addNode(new NodeData(i));
            i++;
        }

        // connecting all nodes to node 0
        i = 1;
        while(i < limit) {
            g0.connect(0, i, 10);
            i++;
        }

        // create new graph algorithm
        dw_graph_algorithms ga = new DWGraph_Algo();

        // initialize g0 to ga
        ga.init(g0);

        // checking if the init graph is equal to g0
        assertEquals(g0, ga.getGraph());

        // create another graph
        directed_weighted_graph g1 = new DWGraph_DS();

        // adding limit * 2 nodes
        i = 0;
        while(i < limit * 2) {
            g1.addNode(new NodeData(i));
            i++;
        }

        // initialize g1 to ga
        ga.init(g1);

        // checking if the init graph is equal to g1
        assertEquals(g1, ga.getGraph());

    }

    @Test
    void getGraph() {

        // create new graph
        directed_weighted_graph g0 = new DWGraph_DS();

        // adding limit nodes
        int i = 0;
        while(i < limit) {
            g0.addNode(new NodeData(i));
            i++;
        }

        // connecting all nodes to node 0
        i = 1;
        while(i < limit) {
            g0.connect(0, i, 10);
            i++;
        }

        // create new graph algorithm
        dw_graph_algorithms ga = new DWGraph_Algo(g0);

        // checking if the init graph is equal to g0
        assertEquals(g0, ga.getGraph());

    }

    @Test
    void copy() {

        // create new graph
        directed_weighted_graph g0 = new DWGraph_DS();

        // adding limit nodes
        int i = 0;
        while(i < limit) {
            g0.addNode(new NodeData(i));
            i++;
        }

        // create new graph algorithm
        dw_graph_algorithms ga = new DWGraph_Algo(g0);

        // getting a copy of g0
        directed_weighted_graph g1 = ga.copy();

        // change the original graph
        g0.addNode(new NodeData(limit));

        // checking if the change didn't change the new graph after a deep copy
        assertNotEquals(g0, g1);

    }

    @Test
    void isConnected() {

        // create new graph
        directed_weighted_graph g0 = new DWGraph_DS();

        // adding limit nodes
        int i = 0;
        while(i < limit) {
            g0.addNode(new NodeData(i));
            i++;
        }

        // create graph algorithms for g0 graph
        dw_graph_algorithms ga = new DWGraph_Algo(g0);


        // connection every node in the graph to his neighbor
        // [n0 -> n1], [n1 -> n2], [n2 -> n3],... [n(n-2) -> n(n-1)] // let n be limit
        i = 0;
        int j = 1;
        while(i < limit) {
            g0.connect(i,j,10);
            i++;
            j++;
        }

        int k = limit - 1;
        g0.connect(limit - 1, 0,23.36);

        assertTrue(ga.isConnected());

        // connection every node in the graph to his neighbor (reverse)
        // [n(n-1) -> n(n-2)], [n(n-2) -> n(n-3)],... [n1 -> n0] // let n be limit
        i = limit;
        j = limit - 1;
        while(i > 0) {
            g0.connect(i,j,5);
            i--;
            j--;
        }

        // closing to circle
        g0.connect(0, limit - 1,23.36);

        assertTrue(ga.isConnected());

        // by removing one of the edges the graph is still connected
        g0.removeEdge(limit / 2, (limit / 2) + 1);

        assertTrue(ga.isConnected());

        // by removing another one of the edges the graph is still connected
        g0.removeEdge((limit / 2) + 1, limit / 2);

        assertTrue(ga.isConnected());

        // by removing node 0 the graph is not connected
        g0.removeNode(0);

        assertFalse(ga.isConnected());

    }

    @Test
    void shortestPathDist() {

        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        node_data n4 = new NodeData(4);
        node_data n5 = new NodeData(5);

        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo(g);

        g.addNode(n0);g.addNode(n1);g.addNode(n2);g.addNode(n3);g.addNode(n4);g.addNode(n5);

        g.connect(0,1,3);
        g.connect(0,2,1);
        g.connect(1,3,4);
        g.connect(1,4,2);
        g.connect(2,4,3);
        g.connect(3,5,4);
        g.connect(4,5,4);
        g.connect(5,0,5);

        assertEquals(ga.shortestPathDist(0,5), 8.0);

    }

    @Test
    void shortestPath() {

        // create new graph
        directed_weighted_graph g0 = new DWGraph_DS();

        // adding limit nodes
        int i = 0;
        while(i < limit) {
            g0.addNode(new NodeData(i));
            i++;
        }

        // create graph algorithms for g0 graph
        dw_graph_algorithms ga = new DWGraph_Algo(g0);

        // connection every node in the graph to his neighbor
        // [n0 -> n1], [n1 -> n2], [n2 -> n3],... [n(n-2) -> n(n-1)] // let n be limit
        i = 0;
        int j = 1;
        while(i < limit) {
            g0.connect(i,j,10);
            i++;
            j++;
        }

        ArrayList<Integer> path = new ArrayList<>();
        i = 0;
        while(i < limit) {
            path.add(i);
            i++;
        }

        List<node_data> shortestPath = ga.shortestPath(0,limit-1);

        // checking if shortest path is equals to path array list
        i = 0;
        while(i < limit) {
            assertEquals(path.get(i), shortestPath.get(i).getKey());
            i++;
        }

    }

    @Test
    void save_load() {

        // creates some nodes
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        node_data n4 = new NodeData(4);
        node_data n5 = new NodeData(5);

        // create graph and initialize to graph algorithms
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo(g);

        // adding the nodes to the graph
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);

        // connecting some nodes
        g.connect(0, 1, 3);
        g.connect(0, 2, 1);
        g.connect(1, 3, 4);
        g.connect(1, 4, 2);
        g.connect(2, 4, 3);
        g.connect(3, 5, 4);
        g.connect(4, 5, 2);
        g.connect(5, 0, 5);

        // save the graph to a file
        ga.save("obj.json");

        // create a new graph algorithms and initialize a new graph from the file
        dw_graph_algorithms gb = new DWGraph_Algo();
        gb.load("obj.json");

        // checking if both graphs are equals
        assertEquals(ga.getGraph(), gb.getGraph());
    }
}