import api.*;

public class Main {

    public static void load(String file_name) {
        DWGraph_Algo graphAlgo = new DWGraph_Algo();
        long start_time = System.currentTimeMillis();
        graphAlgo.load(file_name);
        long end_time = System.currentTimeMillis();
        long time = end_time - start_time;
        System.out.println("load: " + time + " ms");
        shortest(graphAlgo);
    }

    public static void shortest(DWGraph_Algo graphAlgo) {
        long start_time = System.currentTimeMillis();
        graphAlgo.shortestPath(0,9);
        long end_time = System.currentTimeMillis();
        long time = end_time - start_time;
        System.out.println("shortest path: " + time + " ms");
    }

    public static void main(String[] args) {

        load("G_10_80_0.json");
        load("G_100_800_0.json");
        load("G_1000_8000_0.json");
        load("G_10000_80000_0.json");
        load("G_20000_160000_0.json");
        load("G_30000_240000_0.json");


    }
}