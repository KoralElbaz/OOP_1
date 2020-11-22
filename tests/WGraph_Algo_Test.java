package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WGraph_Algo_Test {
    private static Random _rnd = null;
    //========================================================================================================================//
    //======================================================Build graph=======================================================//
    //========================================================================================================================//

    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int w = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            g.connect(i,j,w);
        }
        return g;
    }

    /**
     * Simple method for returning an array with all the ex1.src.node_info of the graph,
     * Note: this should be using an  Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    //========================================================================================================================//
    //=======================================================Tests============================================================//
    //========================================================================================================================//
    /**
     * Simple empty test
     */
    @Test
    public void testIsConnected() {
        weighted_graph g0 = graph_creator(0, 0, 1);
        weighted_graph_algorithms ga0 = new WGraph_Algo();
        ga0.init(g0);
        boolean b = ga0.isConnected();
        assertEquals(true , b);
    }

    /**
     * Simple single node graph
     */
    @Test
    public void testIsConnected1() {
        weighted_graph g0 = graph_creator(1, 0, 1);
        weighted_graph_algorithms ga0 = new WGraph_Algo();
        ga0.init(g0);
        boolean b = ga0.isConnected();
        assertEquals(true , b);
    }

    /**
     * graph with two nodes and no edges - not connected
     */
    @Test
    public void testIsConnected2() {
        weighted_graph g0 = graph_creator(2, 0, 1);
        weighted_graph_algorithms ga0 = new WGraph_Algo();
        ga0.init(g0);
        boolean b = ga0.isConnected();
        assertEquals(false , b);
    }

    /**
     * graph with two nodes and a single edge - connected
     */
    @Test
    public void testIsConnected3() {
        weighted_graph g0 = graph_creator(2, 1, 1);
        weighted_graph_algorithms ga0 = new WGraph_Algo();
        ga0.init(g0);
        boolean b = ga0.isConnected();
        assertEquals(true , b);
    }

    /**
     * test connectivity & shortest path of a double-list like graph
     */
    @Test
    public void testShortestPathDist() {
        int size=6;
        weighted_graph g  = graph_creator(size, 1, 1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        int[] nodes = nodes(g);
        for(int i=2;i<size;i++) {
            int n0 = nodes[i-2];
            int n1 = nodes[i-1];
            int n2 = nodes[i];
            g.connect(n0, n1,2);
            g.connect(n0, n2,0.5);
        }
        double dist = ga.shortestPathDist(nodes[0], nodes[size-1]);
        assertEquals((int)dist,size/2);
    }

    @Test
    public void testShortestPathDist1() {
        int size=8;
        weighted_graph g  = graph_creator(size, 1, 1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        int[] nodes = nodes(g);
        for(int i=2;i<size;i++) {
            int n0 = nodes[i-2];
            int n1 = nodes[i-1];
            int n2 = nodes[i];
            g.connect(n0, n1,2);
            g.connect(n0, n2,0.5);
        }
        int ind = nodes[size/2];
        g.removeNode(ind);
        double dist = ga.shortestPathDist(nodes[0], nodes[size-1]);
        assertEquals((int)dist,size/2);
    }


    @Test
    public void testShortestPathDist2() {
        weighted_graph g0 = new WGraph_DS();
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.addNode(5);
        g0.addNode(6);
        g0.connect(1, 2, 7);
        g0.connect(1, 3, 9);
        g0.connect(1, 6, 14);
        g0.connect(2, 4, 15);
        g0.connect(2, 3, 10);
        g0.connect(3, 4, 11);
        g0.connect(3, 6, 2);
        g0.connect(6, 5, 9);
        g0.connect(5, 4, 6);
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g0);
        assertEquals(20.0,g1.shortestPathDist(1,5));
    }

    @Test
    public void testShortestPath1() {
        weighted_graph g0 = new WGraph_DS();
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.addNode(5);
        g0.addNode(6);
        g0.connect(1, 2, 7);
        g0.connect(1, 3, 9);
        g0.connect(1, 6, 14);
        g0.connect(2, 4, 15);
        g0.connect(2, 3, 10);
        g0.connect(3, 4, 11);
        g0.connect(3, 6, 2);
        g0.connect(6, 5, 9);
        g0.connect(5, 4, 6);
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g0);
        int i=0;
        int[] shortPath = {1, 3, 6, 5};
        List <node_info> Path = g1.shortestPath(1,5);
        for (node_info keys:Path) {
            assertEquals(shortPath[i], Path.get(i).getKey());
            i++;
        }
    }
    @Test
    public void testShortestPath2() {
        weighted_graph g =new WGraph_DS();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g.addNode(0);
        g.addNode(2);
        g.addNode(1);
        g.addNode(4);
        g.addNode(5);
        g.connect(1,2,9);
        g.connect(0,4,4);
        g.connect(0,1,2);
        g.connect(4,5,7);
        g.connect(5,1,3);
        g1.init(g);
        int i=0;
        int[] shortPath = {5, 1, 0};
        List <node_info> Path = g1.shortestPath(5,0);
        for (node_info keys:Path) {
            assertEquals(shortPath[i], Path.get(i).getKey());
            i++;
        }
    }

    /**
     * Simple test of shortest path
     */
    @Test
    public void test_path() {
        int size=100;
        weighted_graph g = graph_creator(size,0,1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        int[] nodes = nodes(g);
        for(int i=2;i<size;i++) {
            int n0 = nodes[i-2];
            int n1 = nodes[i-1];
            int n2 = nodes[i];
            g.connect(n0, n1,10.7);
            g.connect(n0, n2,2.4);
        }
        List<node_info> path = ga.shortestPath(nodes[0], nodes[size-1]);
        assertEquals(path.size() , size/2+1);
    }

    @Test
    public void saveLoad() {
        weighted_graph g1 = graph_creator(30,15,1);
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g1);
        assertTrue(g2.save("mySaveFile"));
        weighted_graph_algorithms g3 = new WGraph_Algo();
        g3.load("mySaveFile");
        weighted_graph g4=g3.getGraph();
        assertTrue(g1.equals(g4));
    }

}
