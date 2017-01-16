/** 
 * Riley Hanson
 * CMSC 401: Assignment 4
 * Graphs & Paths
 */



import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class cmsc401 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int nVertex = in.nextInt();
		int mEdge = in.nextInt();
		ArrayList<Node> nodeList = new ArrayList<Node>();
		int[] hotelPrices = new int[nVertex + 1];
		int[][] paths = new int[nVertex + 1][nVertex + 1];

		// add hotel price to a separate array with the index number as the node
		for (int i = 3; i < hotelPrices.length; i++) {
			in.nextInt();
			hotelPrices[i] = in.nextInt();
		}
		
		// creating all nodes
		for (int i = 0; i < nVertex + 1; i++) {
			nodeList.add(new Node(i));
		}
		
		//storing inputs into 2d matrix of path lengths
		for (int i = 0; i < mEdge; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			int val = in.nextInt();
			paths[x][y] = val;
			paths[y][x] = val;
		}
		
		in.close();

		// adding hotel prices to initial node parameters
		for (int i = 1; i < nVertex + 1; i++) {
			nodeList.get(i).hotelCost = hotelPrices[i];
		}

		// putting path lengths to each node as an initial variable
		for (int o = 0; o < nodeList.size(); o++) {
			for (int i = 0; i < nodeList.size(); i++) {
				nodeList.get(o).edgeDistance.add(i, paths[o][i]);
			} }
		
		//adding elements to Node neighbors list<NODE>
		for (int o = 0; o < nodeList.size(); o++) {
			for (int i = 0; i < nodeList.size(); i++) {
				if (nodeList.get(o).edgeDistance.get(i) > 0){
					nodeList.get(o).neighbors.add(nodeList.get(i));
				} } }
		
		nodeList.get(0).isVisited = true;
		nodeList.get(1).distanceFromSource = 0;
		Node currentNode = nodeList.get(1);

		while(!nodeList.get(2).isVisited){

			scout(currentNode);
			currentNode.isVisited = true;
			currentNode = getShortestPath(currentNode);	
		}
		System.out.println(nodeList.get(2).distanceFromSource);	
	}
		
	public static class Node {
		int number;
		int distanceFromSource;
		int hotelCost;
		boolean isVisited;
		ArrayList<Integer> edgeDistance;
		ArrayList<Node> neighbors;
			public Node(int number) {
				this.number = number;
				isVisited = false;
				distanceFromSource = Integer.MAX_VALUE;
				hotelCost = 0;
				edgeDistance = new ArrayList<Integer>();
				neighbors = new ArrayList<Node>();
		} }

	//looks for and updates unvisited neighbor nodes with smallest distance
	public static void scout(Node n){
		for(int i = 0; i < n.neighbors.size();i++){
			if(!n.neighbors.get(i).isVisited){
			if (getEdgeDistance(n, n.neighbors.get(i)) + n.neighbors.get(i).hotelCost + n.distanceFromSource < n.neighbors.get(i).distanceFromSource){
				n.neighbors.get(i).distanceFromSource = getEdgeDistance(n, n.neighbors.get(i)) + n.neighbors.get(i).hotelCost +n.distanceFromSource;	
			} } } }

	
//CALL AFTER SCOUT - returns Node with shortest path of neighbors of n
	public static Node getShortestPath(Node n) {
		Node temp = null;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n.neighbors.size();i++){
			if(!n.neighbors.get(i).isVisited){
			  if (n.neighbors.get(i).distanceFromSource < min){
				min = n.neighbors.get(i).distanceFromSource;
				temp = n.neighbors.get(i);
			}
		}}
		return temp;
	}
	//returns edge distance between 2 nodes
	public static int getEdgeDistance(Node start, Node neighbor) {
		return start.edgeDistance.get(neighbor.number);
	}
}