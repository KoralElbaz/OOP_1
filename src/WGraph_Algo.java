package ex1.src;

import java.io.*;
import java.util.*;

/**
 * This class realize an Undirected (positive) Weighted Graph Theory algorithms including:
 * copy , init(graph) ,isConnected() ,double shortestPathDist(int src, int dest) ,
 * List<node_data> shortestPath(int src, int dest) ,Save(file) ,Load(file).
 **/

public class WGraph_Algo implements weighted_graph_algorithms, Serializable{
    private weighted_graph myGraph;


    @Override
    public void init(weighted_graph g) {
        this.myGraph=g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.myGraph;
    }


    @Override
    public weighted_graph copy() {
        weighted_graph oldToNew=new WGraph_DS();
        copyEdges(oldToNew);
        copyNodes(oldToNew);
        return oldToNew;
    }
    /**
     * this method copy the edges from this graph to new graph (oldToNew)
     * @param oldToNew
     */
    private void copyEdges(weighted_graph oldToNew) {
        for (node_info node:this.myGraph.getV())
        {
            for (node_info ni:this.myGraph.getV()){
                double dist=this.myGraph.getEdge(node.getKey(),ni.getKey());
                oldToNew.connect(node.getKey(),ni.getKey(),dist);
            }
        }
    }
    /**
     * this method copy nodes from this graph to new graph (oldToNew)
     * @param oldToNew
     */
    private void copyNodes(weighted_graph oldToNew) {
        for (node_info node:this.myGraph.getV())
        {
            oldToNew.addNode(node.getKey());
        }
    }

    /**dijkstra- is an algorithm for finding the shortest paths between nodes in a graph.
     * @param src-The path start with this first node
     * @return
     */
    private HashMap<node_info,node_info> dijkstra(node_info src){
        this.upDateTag(); this.upDateInfo(); //up date the visited list and the min dist
        HashMap<node_info,node_info> parentChange=new HashMap<>();//save the pred that do the min dist
        Queue<node_info> q = new LinkedList<>();
        src.setTag(0);
        q.add(src); //Add to the Queue the first ex1.src.node_info
        parentChange.put(src,null); //Add to the parentChange the first ex1.src.node_info and because is the first he has no previous

        while(!q.isEmpty()) // running on this queue until is empty.
        {
            node_info ans=q.remove();
            if(!q.contains(ans)) //if this queue already contains this value i will not check again.
            {
                for (node_info neighbour : this.myGraph.getV(ans.getKey())) // running on this list of connected to this ans value.
                {
                    if(neighbour.getInfo().equals("White"))
                        //checking if i have already visited this node.
                        //Black==visited , White==no visited
                    {
                        double newDistance=this.myGraph.getEdge(ans.getKey(),neighbour.getKey())+ans.getTag();
                        //Calculates the new distance with the current (pred) distance
                        if(newDistance < neighbour.getTag()) //checking if the new dist smallest from neighbour distance
                        {
                            neighbour.setTag(newDistance);//up date the tag if we found smallest distance
                            parentChange.put(neighbour,ans);//Add to the parentChange the neighbour and his previous (ans)
                            q.add(neighbour);//Add to the Queue the neighbour ex1.src.node_info
                        }
                    }
                }
                ans.setInfo("Black"); //because i checked on the neighbors i chance the info to Black==visited
            }
        }
        return parentChange;
    }
    /**
     * this method update the info of this graph's nodes to be "White" ,as in the beginning.
     * @return this graph after update the info
     */
    private void upDateInfo() {
        for(node_info node: this.myGraph.getV()) {
            node.setInfo("White");
        }
    }
    /**
     * this method update the tag of this graph's nodes to be Double.MAX_VALUE ,as in the beginning.
     * @return this graph after update the tag
     */
    private void upDateTag() {
        for(node_info node: this.myGraph.getV()) {
            node.setTag(Double.MAX_VALUE);
        }
    }

    @Override
    public boolean isConnected() {
        if(this.myGraph.getV().size()==0||this.myGraph.getV().size()==1)return true;
        this.dijkstra(myGraph.getV().stream().findFirst().get());
        for (node_info node : this.myGraph.getV()) {
            if(node.getInfo().equals("White"))return false;
        }
        return true;
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        return shortestPath(src,dest)!=null? this.myGraph.getNode(dest).getTag():-1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        node_info v1=this.myGraph.getNode(src);
        node_info v2=this.myGraph.getNode(dest);
        if(v1==null || v2==null)return null;
        HashMap<node_info,node_info> pred=this.dijkstra(v1);
        List<node_info> shortPath = new ArrayList<>();

        node_info current=this.myGraph.getNode(dest);
        while(current!=this.myGraph.getNode(src))
        {
            shortPath.add(current);

            node_info ans=pred.get(current);
            current=ans;
        }
        shortPath.add(this.myGraph.getNode(src));
        return reverse(shortPath);
    }
    /**
     * This function reverses the values in the list
     * @param path - Short path
     * @return Short path after reverse
     */
    private List<node_info> reverse(List<node_info> path) {
        List<node_info> shortPath = new ArrayList<>();
        for (int i=path.size()-1 ; i>=0 ; i--)
        {
            shortPath.add(path.get(i));
        }
        return shortPath;
    }

    @Override
    public boolean save(String file) {
        try {
            FileOutputStream GraphToFile = new FileOutputStream(file);
            ObjectOutputStream oos=new ObjectOutputStream(GraphToFile);
            oos.writeObject(this.myGraph);
            oos.close();
            GraphToFile.close();
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        try {
            FileInputStream FileToGraph= new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(FileToGraph);
            this.myGraph=(weighted_graph)ois.readObject();
            ois.close();
            FileToGraph.close();
        }
        catch (Exception  e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
