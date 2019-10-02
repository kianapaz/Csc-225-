/* A3Algorithms.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   (Add your name/studentID/date here)
   Kiana Pazdernik / V00896924/ August 4, 2019
*/ 

import java.awt.Color;
import java.util.Stack;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.ArrayList;



public class A3Algorithms{

	/* FloodFillDFS(v, writer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/

	public static void FloodFillDFS(PixelVertex v, PixelWriter writer, Color fillColour){
	
		// Initialize a Map wiht a vertex adn boolean used to track the graph
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		// Initialize a Stack and push the given vertex to stack
		Stack<PixelVertex> S = new Stack<PixelVertex>();
		S.push(v);

		// While the stack is not empty
		while(!S.empty()){
			// Get the most recent vertex and remove it
			PixelVertex n = S.peek();
			S.pop();

			// Mark the map vertex true since it's been visited
			visited.put(n, true);
			// Initialize the vertex with the given colour, write pixel
			n.color = fillColour;
			writer.setPixel(n.getX(), n.getY(), fillColour);
			
			
			// For every neighbour of the the vertex
			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){
				// Check it is in the map and unvisited, then push to stack
				if(visited.containsKey(neighbour[i]) == false){
					S.push(neighbour[i]);
				}
			}

			
		}
		
	}
	
	/* FloodFillBFS(v, writer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/

	public static void FloodFillBFS(PixelVertex v, PixelWriter writer, Color fillColour){
		
		// Initialize a Map wiht a vertex adn boolean used to track the graph
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		// Initialize a Queue and append the given vertex
		Queue<PixelVertex> q = new LinkedList<PixelVertex>();
		q.add(v);

		// While the Queue is not empty
		while(q.isEmpty() == false){
			// Remove the latest vertex
			PixelVertex n = q.remove();
			// As long as the vertex in the map is unvisited
			if(visited.containsKey(n) == false){

				// Set the map vertex to visited
				visited.put(n, true);
				// Given the colour, intialize the vertex's colour and write pixel
				n.color = fillColour;
				writer.setPixel(n.getX(), n.getY(), fillColour);
				
				// For every neighbour the vertex has
				PixelVertex neighbour[] = n.getNeighbours();
				for(int i = 0; i < neighbour.length; i++){
					// If the neighbour has not been visited, add to queue
					if(visited.containsKey(neighbour[i]) == false){
						q.add(neighbour[i]);
					}
				}
			}
			
		}


	}
	



	
	/* OutlineRegionDFS(v, writer, outlineColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void OutlineRegionDFS(PixelVertex v, PixelWriter writer, Color outlineColour){
		
		// Initialize a Map with a vertex and bool
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		// Initialize a stack and push the given vertex
		Stack<PixelVertex> S = new Stack<PixelVertex>();
		S.push(v);

		// While the stack is not empty
		while(!S.empty()){
			// Get the latest vertex from stack
			PixelVertex n = S.peek();
			S.pop();

			// Mark the vertex as visited
			visited.put(n, true);

			// If the degree is less than 4, give the vertex a colour and write pixel
			if(n.getDegree() < 4){
				n.color = outlineColour;
				writer.setPixel(n.getX(), n.getY(), outlineColour);
			}
			
			// For every neighbour of the vertex
			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){
				// If it has not been visited, append to stack
				if(visited.containsKey(neighbour[i]) == false){
					S.push(neighbour[i]);
				}
			}

			
		}
	}
	
	/* OutlineRegionBFS(v, writer, outlineColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void OutlineRegionBFS(PixelVertex v, PixelWriter writer, Color outlineColour){

		// Initialize a map with a vertex and a bool
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		// Initialize a Queue and append given vertex
		Queue<PixelVertex> q = new LinkedList<PixelVertex>();
		q.add(v);

		// While the queue is not empty
		while(q.isEmpty() == false){
			// Remove the latest vertex
			PixelVertex n = q.remove();

			// If all the vertices are unvisited
			if(visited.containsKey(n) == false){

				// Mark the vertex as visited
				visited.put(n, true);

				// If the degree is less than 4, intiialize a colour to the vertex and write pixel
				if(n.getDegree() < 4){
					n.color = outlineColour;
					writer.setPixel(n.getX(), n.getY(), outlineColour);
				}
				
				// For every neighbour of the vertex
				PixelVertex neighbour[] = n.getNeighbours();
				for(int i = 0; i < neighbour.length; i++){
					// If it unvisited add it to queue
					if(visited.containsKey(neighbour[i]) == false){
						q.add(neighbour[i]);
					}
				}
			}
			
		}
	}

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph 
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		// Initialize a count
		int count = 0;
		// Initialize a stack to perform DFS on the graph to count components
		Stack<PixelVertex> S = new Stack<PixelVertex>();
		
		
		// Get an adjacency list from PixelGraph
		while(!G.adj_list.isEmpty()){
		
			// Checking to see if the stack is empty, if so add count and start dfs again
			if(S.empty()){
				// Increase the count sine a new round of DFS is taking place
				// Each DFS round gets a single component
				count++;
				// Get another vertex from the Adjencency list 
				// Using the keySet() which gives a set of keys, ove the set to aan array
				// Then get the first element (since it is not ordered it doesn't matter which vertex)
				PixelVertex v = (PixelVertex) G.adj_list.keySet().toArray()[0];
				S.push(v);
			}

			// Pop the vertex from stack
			PixelVertex n = S.pop();
			
			// Then remove the vertex from the adjacency list
			// Instead of marking the vertex as visited, remove since the verteices are grabbed at random
			G.adj_list.remove(n);

			// get the neighbours from the vertex
			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){
				// Check if the adjacency list contains this neighbour, and append to stack
				//if(G.adj_list.containsKey(neighbour[i])){
				if(G.adj_list.containsKey(neighbour[i])){

					S.push(neighbour[i]);
				}
			}

			
		}
		// Return the nmber of components
		return count;
	}
	

	
	
}