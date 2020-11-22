# OOP-Ex1
This project is a continue of ex0 project and represents an unidirectional weighted graph.
In this project we have 2 main class.
The class are:
1. ex1.src.WGraph_DS - This class represnets an unidirectional weighted graph.
             - In this class we have a private class WNodeData - This class represents a node in an undirected                  weighted graph. 
2. ex1.src.WGraph_Algo - This class represnets an Undirected (positive) Weighted Graph Theory algorithms.
 
# 1.ex1.src.WGraph_DS
This class include the forward function:constructor ,getNode ,hasEdge ,getEdge ,addNode ,connect ,getV ,
getV(int node_id) ,removeNode ,removeEdge ,nodeSize ,edgeSize ,getMC ,equals ,toString.

-Notice:
In order to realize this class I used 2 HashMap.
The firat HashMap contains all the nodes in the graph and the secoend HashMap contains all the key nodes in the graph and who is the key that is connected to this node and the distance between them.
 

# 1.A.WNodeData
This class include the forward function:constructor ,getKey ,getInfo ,setInfo ,getTag ,setTag ,equals ,toString.

-Notice: 
Each node in the graph contains an ID number, maximum distance and string.

# 2.ex1.src.WGraph_Algo
This class include the forward function: init, getGraph ,copy ,
isConnected ,shortestPathDist ,shortestPath(using with dijkstra algorithem) ,save&load file.

-Notice:
dijkstra- is an algorithm for finding the shortest paths between nodes in a graph.

