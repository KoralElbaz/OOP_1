package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_DS_Test {
    private static Random _rnd = null;
    //========================================================================================================================//
    //======================================================Build graph=======================================================//
    //========================================================================================================================//

    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size-Number of nodes
     * @param e_size-Number of edges
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(1);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
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
     * Simple method for test if the graph contains the ex1.src.node_info
     * if
     */
    @Test
    public void testKey1(){
        weighted_graph g0 = new WGraph_DS();
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(1);
        assertEquals(3,g0.nodeSize());
    }

    @Test
    public void testHesEmpty1(){
        weighted_graph g0 = new WGraph_DS();
        node_info helper=g0.getNode(9);
        assertNull(helper);
    }

    @Test
    public void testHesEmptyNode1(){
        weighted_graph g0 = graph_creator(10,0);
        assertNotNull(g0.getNode(1));
    }
    @Test
    public void testHesEmptyNode2(){
        weighted_graph g1 = graph_creator(10,0);
        assertNull(g1.getNode(11));
    }

    @Test
    public void testHesEdge1(){
        weighted_graph g2 = graph_creator(10,0);
        g2.connect(1,8,3);
        assertTrue(g2.hasEdge(1,8));
    }
    @Test
    public void testNeighbors(){
        weighted_graph g = graph_creator(10,0);
        g.connect(1,3,12);
        g.connect(1,4,6);
        g.connect(1,2,6);
        g.connect(1,9,6);
        g.connect(1,8,3);
        int[] checkKey = {2, 3, 4, 8, 9};
        int i=0;
        for (node_info keys:g.getV(1)) {
            assertEquals(keys.getKey(),checkKey[i]);
            i++;
        }
    }

    @Test
    public void testNodesOfGraph(){
        weighted_graph g = graph_creator(10,0);
        int[] checkKey = {0, 1, 2, 3, 4,5,6,7,8,9};
        int i=0;
        for (node_info keys:g.getV()) {
            assertEquals(keys.getKey(),checkKey[i]);
            i++;
        }
    }

    @Test
    public void testHesEdge2(){
        weighted_graph g0 = graph_creator(10,0);
        g0.connect(1,3,12);
        assertFalse(g0.hasEdge(1,11));
    }
    @Test
    public void testRemoveNode1(){
        weighted_graph g4 = graph_creator(10,0);
        g4.removeNode(10);
        assertNull(g4.getNode(10));

    }
    @Test
    public void testRemoveNode2(){
        weighted_graph g4 = graph_creator(10,0);
        g4.removeNode(9);
        int s = g4.nodeSize();
        assertEquals(9,s);
    }
    @Test
    public void testRemoveNode3(){
        weighted_graph g4 = graph_creator(10,0);
        g4.removeNode(12);
        int s = g4.nodeSize();
        assertEquals(10,s);
    }

    @Test
    public void testRemoveEdge1(){
        weighted_graph g5 = graph_creator(10,0);
        g5.connect(1,3,12);
        g5.connect(1,4,6);
        g5.connect(3,7,2.8);
        g5.connect(1,8,3);
        g5.removeEdge(1,8);
        boolean ans=g5.hasEdge(1,8);
        assertEquals(false,ans);
    }
    @Test
    public void testRemoveEdge2(){
        weighted_graph g5 = graph_creator(10,0);
        g5.connect(1,3,12);
        g5.connect(1,4,6);
        g5.connect(3,7,2.8);
        g5.connect(1,8,3);
        g5.removeEdge(1,2);
        int numOfEdge=g5.edgeSize();
        assertEquals(numOfEdge,4);
    }
    @Test
    public  void testRemoveEdgeNodes(){
        weighted_graph g6 = graph_creator(10,30);
        int[] nodes = nodes(g6);
        int a0 = nodes[0];
        int a1 = nodes[1];
        int a2 = nodes[2];
        g6.removeEdge(a0,a1);
        g6.removeEdge(a2,a0);
        g6.removeEdge(a2,a1);
        g6.removeNode(a2);
        g6.removeNode(a2);
        assertEquals(9,g6.nodeSize());
        assertNotEquals(21,g6.edgeSize());
    }
    @Test
    public void testEdgeSizeAfterRemoveEdge(){
        weighted_graph g7 = new WGraph_DS();
        for (Integer i=0 ; i<=10 ; i++){
            g7.addNode(i);
        }
        g7.connect(1,0,2);
        g7.connect(1,1,0.2);
        g7.connect(1,2,5);
        g7.connect(1,3,7);
        g7.connect(1,4,12);
        g7.connect(1,5,80);
        g7.removeEdge(1,2);
        assertEquals(4,g7.edgeSize());
    }

    @Test
    public void testMC(){
        weighted_graph g7 = new WGraph_DS();
        for (Integer i=0 ; i<=10 ; i++){
            g7.addNode(i);
        }
        g7.connect(1,0,2);
        g7.connect(1,1,0.2);
        g7.connect(1,2,5);
        g7.connect(1,5,80);
        g7.removeEdge(1,2);
        assertEquals(15,g7.getMC());
    }

    @Test
    public void testTimeRun(){
        long start= new Date().getTime();
        weighted_graph g8 = graph_creator((int) Math.pow(10,6),(int) Math.pow(10,6));
//        for (Integer i=0; i<=5; i++){
//            for(Integer j=i+1; j<=Math.pow(10,7)-(i+1); j++) {
//                g8.connect(i, j, 0.5);
//            }
//        }
        long end= new Date().getTime();
        double dt= (end-start)/1000.0;
        System.out.println(dt);

    }
}
