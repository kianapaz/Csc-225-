/* PixelVertex.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
  
    Kiana Pazdernik / V00896924/ August 4, 2019
*/

import java.util.ArrayList;
import java.awt.Color;

public class PixelVertex{

	int x, y;
	ArrayList<PixelVertex> neighbours = new ArrayList<PixelVertex>();
	Color color;


	/* Constructor that accepts coordiantes, neighbours and a colour */
	public PixelVertex(int x, int y, ArrayList<PixelVertex> neighbours, Color color){
		this.x = x;
		this.y = y;
		this.neighbours = neighbours;
		this.color = color;
	
	}

	// Constructor wihtout neighbours
	public PixelVertex(int x, int y, Color colour){
		this.x = x;
		this.y = y;
		this.color = color;
		
	}
	// Constructor to initialize a null Pixelvertex
	public PixelVertex(){
		this.x = 0;
		this.y = 0;
		this.color = null;
		
	}


	/* getX()
	   Return the x-coordinate of the pixel corresponding to this vertex.
	*/
	public int getX(){
		return x;
	}
	
	/* getY()
	   Return the y-coordinate of the pixel corresponding to this vertex.
	*/
	public int getY(){
		return y;
	}
	
	/* getNeighbours()
	   Return an array containing references to all neighbours of this vertex.
	   The size of the array must be equal to the degree of this vertex (and
	   the array may therefore contain no duplicates).
	*/
	public PixelVertex[] getNeighbours(){
		// Initialize an array of Pixel Vertices
		PixelVertex[] objects =  new PixelVertex[neighbours.size()];
		// With the neighbours, convert to an array using toArray()
		objects =  neighbours.toArray(objects);
		return objects;
	}
	
	/* addNeighbour(newNeighbour)
	   Add the provided vertex as a neighbour of this vertex.
	*/
	public void addNeighbour(PixelVertex newNeighbour){
		// Add neighbours
		neighbours.add(newNeighbour);

	}
	
	/* removeNeighbour(neighbour)
	   If the provided vertex object is a neighbour of this vertex,
	   remove it from the list of neighbours.
	*/
	public void removeNeighbour(PixelVertex neighbour){
		// Remove neighbours
		neighbours.remove(neighbour);

	}
	
	/* getDegree()
	   Return the degree of this vertex. Since the graph is simple, 
	   the degree is equal to the number of distinct neighbours of this vertex.
	*/
	public int getDegree(){

		// Return the size of the neighbours
        return neighbours.size();

		
	}
	
	/* isNeighbour(otherVertex)
	   Return true if the provided PixelVertex object is a neighbour of this
	   vertex and false otherwise.
	*/
	public boolean isNeighbour(PixelVertex otherVertex){

		// For the length of the neighbours
		for(int i = 0; i < neighbours.size(); i++){
			// If the neighbour vertex is euqual to the given vertex
			if( neighbours.get(i) == otherVertex){
				// Return true
				return true;
			}

		}
		return false;
	}
	
}