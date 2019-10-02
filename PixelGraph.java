/* PixelGraph.java
   CSC 225 - Summer 2019

   B. Bird - 04/28/2019
   
    Kiana Pazdernik / V00896924/ August 4, 2019
*/ 

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class PixelGraph{

	// Initialize an adjacency list to hold the vertices and arraylis tof neighbours 
	Map<PixelVertex, ArrayList<PixelVertex>> adj_list = new HashMap<PixelVertex, ArrayList<PixelVertex>>();
	ArrayList<PixelVertex> neighbours = new ArrayList<PixelVertex>();
	PixelVertex vertex[][];
	/* PixelGraph constructor
	   Given a 2d array of colour values (where element [x][y] is the colour 
	   of the pixel at position (x,y) in the image), initialize the data
	   structure to contain the pixel graph of the image. 
	*/
	public PixelGraph(Color[][] imagePixels){

		// Initialize a temp double array that will hold the PixelVertex from the Color 2d array
		PixelVertex temp[][] = new PixelVertex[imagePixels.length][imagePixels[0].length];

		// Initialize an Arraylist that will then hold the neighbours of the vertices
		ArrayList<PixelVertex> list = new ArrayList<PixelVertex>();

		// For the height of the color array
		for(int i = 0; i < imagePixels.length; i++){

			// For the width of the color array 
			for(int j = 0; j < imagePixels[0].length; j++){
				// Initialize a Pixelvertex with color
				PixelVertex val1 = new PixelVertex(i, j, imagePixels[i][j]);
				// Give the temp[][] the PixelVertex, and place into adjacency list
				temp[i][j] = val1;
				adj_list.put(val1, list);
			}
		}

		// Make the vertex 2d array equal to the temp array 
		vertex = temp;

		// For the hieght and width of the colour array
		for(int i = 0; i < imagePixels.length; i++){

			for(int j = 0; j < imagePixels[i].length; j++){
				
				// If the colour's column is not on the right edge
				if( j != imagePixels[i].length-1){
					// And if the colour of the pixel and the colour of the one to the right is the same
					if(imagePixels[i][j].getRGB() == imagePixels[i][j+1].getRGB()){
						// Add that pixel to it's neighbour
						temp[i][j].addNeighbour(temp[i][j+1]);
					}
					
				}
				// If the colour's column is not on the left edge
				if( j != 0){
					// And the colour of the pixel and the colour of the one to the left is the same
					if(imagePixels[i][j].getRGB() == imagePixels[i][j-1].getRGB()){
					
						// Add that pixel to it's neighbour
						temp[i][j].addNeighbour(temp[i][j-1]);
					}
					
				}
				// If the colour's row above isn't on the edge
				if( i != imagePixels.length-1){
					// And the colour of the pixel and the colour of the one above it is the same
					if(imagePixels[i][j].getRGB() == imagePixels[i+1][j].getRGB()){
						
						// Add that pixel to it's neighbour
						temp[i][j].addNeighbour(temp[i+1][j]);
					}
					
				}
				// If the colour's row below isn't on the edge
				if(  i != 0){
					// And the colour of the pixel and the colour of the one below it is the same
					if(imagePixels[i][j].getRGB() == imagePixels[i-1][j].getRGB()){
						// Add that pixel to it's neighbour
						temp[i][j].addNeighbour(temp[i-1][j]);
					}
					
				}
			
			}
		}
	}
	
	/* getPixelVertex(x,y)
	   Given an (x,y) coordinate pair, return the PixelVertex object 
	   corresponding to the pixel at the provided coordinates.
	   This method is not required to perform any error checking (and you may
	   assume that the provided (x,y) pair is always a valid point in the 
	   image).
	*/
	public PixelVertex getPixelVertex(int x, int y){

		// Initialize a Pixelvertex
		PixelVertex val1 = new PixelVertex();
		// Iterate through the adjacency list
		for (Map.Entry<PixelVertex, ArrayList<PixelVertex>> entry : adj_list.entrySet()){
			// Set the pixel vertex to the adjacency's key
			val1 = entry.getKey();
			// Compare the key to the x and y cooridnate
			if(val1.getX() == x && val1.getY() == y){
				return val1;
			}
			
		}
		return null;
	}
	
	/* getWidth()
	   Return the width of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getWidth(){
		// Each adj.list's neighbours can vary, so
		// The vertex 2d array's first row's length returns the width
		int widths = vertex[0].length;
		
		return widths;
	}
	
	/* getHeight()
	   Return the height of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getHeight(){
		// The adjacencie's size returns the total size of the adj.list
		// The vertex 2d array length returns the height
		int height = vertex.length;
		
		return height;
	}


	
}