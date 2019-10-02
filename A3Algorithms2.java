/* A3Algorithms.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   (Add your name/studentID/date here)
   Kiana Pazdernik / V00896924/ July 24, 2019
*/ 

import java.awt.Color;
import java.util.Stack;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.ArrayDeque;

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
	
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		Stack<PixelVertex> S = new Stack<PixelVertex>();
		S.push(v);

		while(!S.empty()){
			PixelVertex n = S.peek();
			S.pop();


			n.color = fillColour;
			writer.setPixel(n.getX(), n.getY(), fillColour);
			visited.put(n, true);
			

			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){

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
		/*Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		Queue<PixelVertex> q = new LinkedList<PixelVertex>();
		q.add(v);

		while(q.isEmpty() == false){
			PixelVertex n = q.remove();
			

		
			n.color = fillColour;
			writer.setPixel(n.getX(), n.getY(), fillColour);
			visited.put(n, true);
			

			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){

				if(visited.containsKey(neighbour[i]) == false){
					q.add(neighbour[i]);
				}
			}

			
		}*/
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		Queue<PixelVertex> q = new LinkedList<PixelVertex>();
		q.add(v);

		while(q.isEmpty() == false){
			PixelVertex n = q.remove();
			if(visited.containsKey(n) == false){

				n.color = fillColour;
				writer.setPixel(n.getX(), n.getY(), fillColour);
				visited.put(n, true);
			}
			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){

				if(visited.containsKey(neighbour[i]) == false){
					q.add(neighbour[i]);
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
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		Stack<PixelVertex> S = new Stack<PixelVertex>();
		S.push(v);

		while(!S.empty()){
			PixelVertex n = S.peek();
			S.pop();

			if(n.getDegree() < 4){
				n.color = outlineColour;
				writer.setPixel(n.getX(), n.getY(), outlineColour);
			}
			
			visited.put(n, true);
			

			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){

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
		/*Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		Queue<PixelVertex> q = new LinkedList<PixelVertex>();
		q.add(v);

		while(q.isEmpty() == false){
			PixelVertex n = q.remove();
			

			if(n.getDegree() < 4){
				n.color = outlineColour;
				writer.setPixel(n.getX(), n.getY(), outlineColour);
			}
			
			visited.put(n, true);
			

			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){

				if(visited.containsKey(neighbour[i]) == false){
					q.add(neighbour[i]);
				}
			}

			
		}*/
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		Queue<PixelVertex> q = new LinkedList<PixelVertex>();
		q.add(v);

		while(q.isEmpty() == false){
			PixelVertex n = q.remove();
			if(visited.containsKey(n) == false){

				
				visited.put(n, true);
			}
			if(n.getDegree() < 4){
				n.color = outlineColour;
				writer.setPixel(n.getX(), n.getY(), outlineColour);
			}
			PixelVertex neighbour[] = n.getNeighbours();
			for(int i = 0; i < neighbour.length; i++){

				if(visited.containsKey(neighbour[i]) == false){
					q.add(neighbour[i]);
				}
			}
		}
	}

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph 
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		Map<PixelVertex, Boolean> visited = new HashMap<PixelVertex, Boolean>();
		int count = 0;
		
		for(int i = 0; i < G.getHeight(); i++){

			for(int j = 0; j < G.getWidth(); j++){

				PixelVertex n = G.getPixelVertex(i, j);
				PixelVertex neighbour[] = n.getNeighbours();
				for(int k = 0; k < neighbour.length; k++){
					if (visited.containsKey(neighbour[k]) == false) { 
            			DFSUtil(neighbour[k], visited); 
            			count += 1; 
       				} 
				}
			}

		}
		return count;
	}
	public static void DFSUtil(PixelVertex v, Map<PixelVertex, Boolean> visited){
		visited.put(v, true);
		PixelVertex neighbour[] = v.getNeighbours();
		for(int k = 0; k < neighbour.length; k++){
			if (visited.containsKey(neighbour[k]) == false) { 
				DFSUtil(neighbour[k], visited); 
			} 
		}
	}
	/*
	public static void DFSUtil(PixelVertex v, Map<PixelVertex, Boolean> visited){

		int n = G.length;

		// Iterative
		Stack<Integer> S = new Stack<Integer>();
		S.push(v);

		while(!S.empty()){
			int v = S.peek();

			if(visited[v] == false){
				visited[v] = true;
				System.out.println(v);
			}

			boolean allvisited = true;
			int unvisited = -1;

			for(int i = 0; i < n; i++){
				if(G[v][i] == 1 && visited[i] == false){
					allvisited = false;
					unvisited = i;

					break;
				}
			}

			if(allvisited == true){
				S.pop();
			}else{
				S.push(unvisited);
			}

		}
	}*/
	
}