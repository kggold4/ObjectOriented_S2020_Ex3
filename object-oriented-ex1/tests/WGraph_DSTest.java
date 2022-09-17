package ex1.tests;

import org.junit.jupiter.api.*;

import ex1.src.*;

import static org.junit.jupiter.api.Assertions.*;

// this test run in my computer less than 30 seconds
public class WGraph_DSTest {

    // limit is the number of nodes in the graph in every test
    private static int limit = 1000000;

    // private function for getting random number between low and high
    private static int getRandom(int min, int max) {
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    // testing getNode method
    @Test()
    //@Timeout(42)
    void getNode() {

        // create graph
        weighted_graph g0 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g0.addNode(i);
            i++;
        }

        // compare the getNode value key of all nodes in the graph
        i = 0;
        while(i < limit) {
            assertEquals(i, g0.getNode(i).getKey());
            i++;
        }

        // compare non exist node to null
        assertEquals(null, g0.getNode(limit));
    }

    // testing hasEdge method
    @Test
    void hasEdge() {

        // create graph and add node 0 and 1
        weighted_graph g1 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g1.addNode(i);
            i++;
        }

        // connecting between node 0 to all others nodes
        i = 1;
        while(i < limit) {
            g1.connect(0, i, 1);
            i++;
        }

        // checking connection between all nodes to node 0
        i = 1;
        while(i < limit) {
            assertEquals(true, g1.hasEdge(0, i));
            i++;
        }

        // changing all nodes weights connection
        i = 1;
        while(i < limit) {
            g1.connect(0, i, 2);
            i++;
        }

        // checking connection between all nodes to node 0
        i = 1;
        while(i < limit) {
            assertEquals(true, g1.hasEdge(0, i));
            i++;
        }

        // removing all edges from the graph
        i = 1;
        while(i < limit) {
            g1.removeEdge(0, i);
            i++;
        }

        // checking connection between all nodes to node 0 - not connected
        i = 1;
        while(i < limit) {
            assertEquals(false, g1.hasEdge(0, i));
            i++;
        }
    }

    // testing getEdge method
    @Test
    void getEdge() {

        // create graph
        weighted_graph g2 = new WGraph_DS();

        // generate an array with random numbers with length of limit
        // numbers are double value between -10 to 10 (real numbers)
        double[] random = new double[limit];
        for(int a = 0; a < random.length; a++) {
            random[a] = Math.random() * 10;
        }

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g2.addNode(i);
            i++;
        }

        // connecting between first node to all other nodes with random values of weights of random array
        i = 1;
        while(i < limit) {
            g2.connect(0, i, random[i]);
            i++;
        }

        // getting in reverse all the weights of the edges that connection between first node to all other nodes
        i = limit - 1;
        while(i > 0) {
            assertEquals(random[i], g2.getEdge(0, i));
            i--;
        }

        // connecting again between all nodes to node 0 with random array but in reverse wey
        // changing only the weight
        i = limit - 1;
        while(i > 0) {
            g2.connect(0, i, random[i]);
            i--;
        }

        // checking if the weights have change properly
        i = 1;
        while(i < limit) {
            assertEquals(random[i], g2.getEdge(0, i));
            i++;
        }

        // removing all edges from the graph
        i = 0;
        while(i < limit) {
            g2.removeEdge(0,i);
            i++;
        }

        // checking if all edges do not exist - give -1
        i = 1;
        while(i < limit) {
            assertEquals(-1, g2.getEdge(0, i));
            i++;
        }

    }

    // testing addNode method
    @Test
    void addNode() {

        // create graph
        weighted_graph g3 = new WGraph_DS();

        // adding 100000 nodes to the graph
        int i = 0;
        while(i < limit) {
            g3.addNode(i);
            i++;
        }

        // checking if all 100000 nodes added to the graph
        // checking number of mode - 100000
        assertEquals(limit, g3.nodeSize());
        assertEquals(limit, g3.getMC());

        // adding the same nodes to the graph - doing nothing
        i = 0;
        while(i < limit) {
            g3.addNode(i);
            i++;
        }

        // doing same test
        assertEquals(limit, g3.nodeSize());
        assertEquals(limit, g3.getMC());
    }

    // testing connect method
    @Test
    void connect() {

        // create graph
        weighted_graph g4 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g4.addNode(i);
            i++;
        }

        // connect between all nodes
        i = 0;
        while(i < limit) {
            int temp = limit - i - 1;
            g4.connect(i, temp, 10);
            i++;
        }

        // checking connection
        i = 0;
        while(i < limit) {
            int temp = limit - i - 1;
            if(i == temp) { i++; } // if the node size is odd
            assertEquals(true, g4.hasEdge(i, temp));
            i++;
        }
    }

    // testing getV method (of all the nodes in the graph)
    @Test
    void getV() {

        // create graph
        weighted_graph g5 = new WGraph_DS();

        // limit - number of nodes
        int limit = 50000, i = 0;

        // adding nodes to the graph
        while(i < limit) {
            g5.addNode(i);
            i++;
        }

        // by getting the collection of the nodes graph
        // using for each loop and checking if equal to the same indexes as i integer
        i = 0;
        for(node_info n : g5.getV()) {
            assertEquals(i, n.getKey());
            i++;
        }
    }

    // testing getV method (of all neighbors of node in the graph)
    @Test
    void nodeGetV() {

        // create graph
        weighted_graph g6 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g6.addNode(i);
            i++;
        }

        // connecting all nodes to node 0
        i = 1;
        while(i < limit) {
            g6.connect(0, i, 1);
            i++;
        }

        // checking connection with all node 0 neighbors
        i = 1;
        for(node_info n : g6.getV(0)) {
            assertEquals(true, g6.hasEdge(0, i));
            i++;
        }
    }

    // testing removeNode method
    @Test
    void removeNode() {

        // create graph
        weighted_graph g7 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g7.addNode(i);
            i++;
        }

        // remove all nodes from the graph
        i = 0;
        while(i < limit) {
            g7.removeNode(i);
            i++;
        }

        // checking by node size
        assertEquals(0, g7.nodeSize());

        // checking by edges counter
        assertEquals(0, g7.edgeSize());

        // checking with getNode method only give null
        i = 0;
        while(i < limit) {
            assertEquals(null, g7.getNode(i));
            i++;
        }
    }

    @Test
    void removeEdge() {

        // create graph
        weighted_graph g8 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g8.addNode(i);
            i++;
        }

        // connecting every pair of nodes in the graph
        i = 0;
        int j = 1;
        while(j < limit) {
            g8.connect(i, j, Math.random() * 10);
            i += 2;
            j += 2;
        }

        // checking connection
        i = 0;
        j = 1;
        while(j < limit) {
            assertEquals(true, g8.hasEdge(i, j));
            i += 2;
            j += 2;
        }

        // remove connection
        i = 0;
        j = 1;
        while(j < limit) {
            g8.removeEdge(i, j);
            i += 2;
            j += 2;
        }

        // checking connection
        i = 0;
        j = 1;
        while(j < limit) {
            assertEquals(false, g8.hasEdge(i, j));
            i += 2;
            j += 2;
        }
    }

    // testing nodeSize method
    @Test
    void nodeSize() {

        // create graph
        weighted_graph g9 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g9.addNode(i);
            i++;
        }

        // checking node size
        assertEquals(limit, g9.nodeSize());

        // removing half of the nodes in the graph
        int half = limit / 2;
        i = limit - 1;
        while(i >= half) {
            g9.removeNode(i);
            i--;
        }

        // checking node size
        assertEquals(half, g9.nodeSize());

    }

    // testing edgeSize method
    // this test should run less than 18 seconds
    // the complexity of this test is high in purpose
    // the number of interactions in approximately is like:
    // first we going throw all nodes in the graph (limit time) three times,
    // and in every interaction we connect the node to random other node,
    // so the interactions is (limit * 3) + 1,
    // after that we are counting all nodes neighbors in the graph
    // so more limit * 3 interactions
    // and than we remove all the nodes in the graph and all the neighbors
    // so more (limit * 3) + 1 interactions
    // total interactions are ((limit * 3) + 1) * 2 + (limit * 3)
    // let limit be n, we have:
    // (3n + 1) * 2 + 3n = 6n + 2 + 3n = 9n + 2
    // if limit is 1000000 we got 9000002 interactions
    @Test()
    void edgeSize() {

        // create graph
        weighted_graph g10 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g10.addNode(i);
            i++;
        }

        // connecting random nodes in the graph several times
        i = 0;
        for(node_info n : g10.getV()) {
            g10.connect(i, getRandom(i, limit - 1), 1);
            i++;
        }
        i = 0;
        for(node_info n : g10.getV()) {
            g10.connect(i, getRandom(i, limit - 1), 1);
            i++;
        }
        i = 0;
        for(node_info n : g10.getV()) {
            g10.connect(i, getRandom(i, limit - 1), 1);
            i++;
        }

        // for every node in the graph - getting neighbors collection
        // calculate : edgeSize = numbers of all neighbors / 2
        long neighbors = 0;
        for(node_info n : g10.getV()) {
            for(node_info p : g10.getV(n.getKey())) {
                neighbors++;
            }
        }
        assertEquals(neighbors / 2, g10.edgeSize());

        // removing all nodes in the graph
        i = 0;
        while(i < limit) {
            g10.removeNode(i);
            i++;
        }

        assertEquals(0, g10.edgeSize());

    }

    // testing getMC method
    @Test
    void getMC() {

        // changes integer counting the numbers of changes in the inner state of the graph
        int changes = 0;

        // create graph
        weighted_graph g11 = new WGraph_DS();

        // adding nodes to the graph
        int i = 0;
        while(i < limit) {
            g11.addNode(i);
            changes++;
            i++;
        }

        // connect node 0 to all others nodes in the graph
        // adding nodes to the graph
        i = 1;
        while(i < limit) {
            g11.connect(0, i, 1);
            changes++;
            i++;
        }

        // changing weights
        i = 1;
        while(i < limit) {
            g11.connect(0, i, 2);
            changes += 2;
            i++;
        }

        // remove node 0
        g11.removeNode(0);
        i = 0;
        while(i < limit) {
            changes++;
            i++;
        }

        assertEquals(changes, g11.getMC());
    }
}