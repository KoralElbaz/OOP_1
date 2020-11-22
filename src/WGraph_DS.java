package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class realize an unidirectional weighted graph.
 */
public class WGraph_DS  implements weighted_graph, Serializable {
    /**
     * HashMap <Integer,ex1.src.node_info> graph  - This HashMap contains all the nodes in the graph.
     * HashMap <Integer,HashMap<Integer,Double>> EdgeWithNi - This HashMap contains all the key nodes in the graph
     * and who is the key that is connected to this node and the distance between them.
     */
    private static final long serialVersionUID =1L;
    private HashMap <Integer,node_info> graph;
    private HashMap <Integer,HashMap<Integer,Double>> EdgeWithNi;

    private int edgeSize;
    private int nodeSize;
    private int mc;

    public WGraph_DS(){
        this.graph=new HashMap<>();
        this.EdgeWithNi=new HashMap<>();
        this.edgeSize=0;
        this.nodeSize=0;
        this.mc=0;
    }

    @Override
    public node_info getNode(int key) { ;
        return this.graph.containsKey(key)?this.graph.get(key):null;
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        return this.EdgeWithNi.get(node1).containsKey(node2)?true:false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        return hasEdge(node1,node2)?this.EdgeWithNi.get(node1).get(node2).doubleValue():-1;

    }

    @Override
    public void addNode(int key) {
        if(this.graph.containsKey(key)) return;
        node_info v1=new WNodeData(key);
        this.graph.put(key,v1);
        HashMap<Integer, Double> newMap=new HashMap<>();
        this.EdgeWithNi.put(key,newMap);
        this.nodeSize++;
        this.mc++;
    }

   
    @Override
    public void connect(int node1, int node2, double w) {
        if(node1==node2)return;
        else if(getNode(node1)==null || getNode(node2)==null) return;
        if(!this.hasEdge(node1, node2)) {
            this.EdgeWithNi.get(node1).put(node2, w);
            this.EdgeWithNi.get(node2).put(node1, w);
            this.mc++;
            this.edgeSize++;
        }
    }

    @Override
    public Collection<node_info> getV() {
        return this.graph.values();
    }

    @Override
    public Collection<node_info> getV(int node_id)
    {
        ArrayList<node_info> neighbors = new ArrayList<>();
        if (this.EdgeWithNi.containsKey(node_id)) {
                for ( Integer keyNi:this.EdgeWithNi.get(node_id).keySet()) {
                    node_info ans=getNode(keyNi);
                    neighbors.add(ans);
                }
        }
        return neighbors;
    }

    @Override
    public node_info removeNode(int key) {
        node_info nodeHelp=this.getNode(key);
        if(nodeHelp==null) return null;
            while(this.EdgeWithNi.get(key).size()!=0) {
                Integer ans=this.EdgeWithNi.get(key).keySet().stream().findFirst().get();
                this.removeEdge(key, ans);
            }
            this.EdgeWithNi.remove(key);
            this.graph.remove(key);
            this.nodeSize--;
            this.mc++;

        return nodeHelp;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if (this.hasEdge(node1, node2)) {
            this.EdgeWithNi.get(node1).remove(node2);
            this.EdgeWithNi.get(node2).remove(node1);
            this.edgeSize--;
            this.mc++;
        }
    }

    @Override
    public int nodeSize() {
        return nodeSize;
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public int getMC() {
        return mc;
    }
    /**
     *This function compares two ex1.src.WGraph_DS (this and other) in a graph.
     * @param o from this type.
     * @return true if equal , else false.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return this.toString().equals(wGraph_ds.toString());
    }


    public String toString()
    {
        String s = "[ ";
        for(Integer it:EdgeWithNi.keySet()){
            s+= "Key: "+ EdgeWithNi.get(it).toString() + " , ";
            for (Integer ni:EdgeWithNi.get(it).keySet()) {
                s+=" Ni: "+ni +" Tag: "+this.EdgeWithNi.get(it).get(ni).doubleValue();
            }
        }
        s+=" ]";
        return s;

    }

    //=========================================================================================================================================//
    /**
     *this class realize a node in an undirected weighted graph.
     */
    private class WNodeData implements node_info, Serializable {

        private int key;
        private double tag;
        private String info;


        public WNodeData(Integer keyNi) {
            this.key=keyNi;
            this.setTag(Double.MAX_VALUE);
            this.setInfo("White");
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info=s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag=t;
        }
        /**
         *This function compares two WNodeData (this and other) in a graph.
         * @param o from this type.
         * @return true if equal , else false.
         */

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WNodeData wNodeData = (WNodeData) o;
            return this.toString().equals(wNodeData.toString());
        }

        public String toString() {
            return "NodeData{"+"Id= " + this.key + "    Tag= "+this.getTag()+" }" ;
        }
    }
}
