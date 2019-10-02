/* HullBuilder.java
   CSC 225 - Summer 2019

   Starter code for Convex Hull Builder. Do not change the signatures
   of any of the methods below (you may add other methods as needed).

   B. Bird - 03/18/2019
   (Add your name/studentID/date here)
   Kiana Pazdernik - V00896924 - 05/09/2019
*/

import java.util.LinkedList;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Arrays;



public class HullBuilder{

    /* Add constructors as needed */
    /* Using a Linked List Data Structure to build the Hull */
    // Global linked list that will contain all the points form the given file
    private LinkedList<Point2d> hullAllPoints = new LinkedList<Point2d>();
    private LinkedList<Point2d> sortedHull = new LinkedList<Point2d>();
    private ArrayList<Point2d> Upper = new ArrayList<Point2d>();
    private ArrayList<Point2d> Lower = new ArrayList<Point2d>();
 
    /*
     * Merge merges the two given Deque's of left and right in the ascending order
     * Returns a linked list (LL) of the merge sorted points
     */
    private LinkedList<Point2d> merge(Deque<Point2d> left, Deque<Point2d> right){
        LinkedList<Point2d> merged = new LinkedList<Point2d>();
        // While the left and right Deque are not empty
        while(!left.isEmpty() && !right.isEmpty()){
        
            // Gets the first element of the left and right side of the Deque
            Point2d pl = left.peek();
            Point2d pr = right.peek();
            // Getting the x-coordinate of the right and left side
            double plx = pl.x;
            double prx = pr.x;

            // Compares the x-coordinates of the left and right side
            if(Double.compare(plx, prx) < 0){
                // If the left side is smaller than the rihgt, add it to the merge LL
                merged.add(left.pop());
            // Otherwise if they are equal compare the y coordinate
            }else if(Double.compare(plx, prx) == 0){
                    // Gets the point from the left and right side, then the y-coordinate of the points
                    Point2d p1 = left.peek();
                    Point2d p2 = right.peek();
                    double ply = p1.y;
                    double pry = p2.y;

                    // Compares the right y-coordinate
                    if(Double.compare(ply, pry) <= 0){
                        // If the left y-coordinate is smaller, add to LL
                        merged.add(left.pop());
                    }else{
                        // Otherwise the right y-coordinate is smaller
                        merged.add(right.pop());
                    }
            // Otherwise the right x-coordinate is smaller, add it to the LL
            }else{
                merged.add(right.pop());
            }
        }
        // Add the rest of the left and right points
        merged.addAll(left);
        merged.addAll(right);
        // Return the LL 
        return merged;
    }
    /*
     * MergeSort takes a linkedlist of points and sorts them in ascending order
     * Returns a sorted linked list
     */
    public LinkedList<Point2d> mergeSort( LinkedList<Point2d> givenPoints){

        // Checks if the points size is greater than 1, because it would be sorted
        //if(givenPoints.size() != 1){
        if(givenPoints.size() > 1){
            // Initializes a left and right side LL
            LinkedList<Point2d> left = new LinkedList<Point2d>();
            LinkedList<Point2d> right = new LinkedList<Point2d>();

            // A boolean to keep track of wich side to put the elements
            boolean switchEle = true;
            // While the given LL is not empty
            while(!givenPoints.isEmpty()){
                // If the switch is true, add the point to the left side
                if(switchEle){
                    left.add(givenPoints.pop());
                }else{
                    // If its false add to the right side
                    right.add(givenPoints.pop());
                }
                // Continuously switches to true/false so left and right both get even elements
                switchEle = !switchEle;
            }
            // Recursively calls mergeSort to continue to split the list until it is one element
            mergeSort(left);
            mergeSort(right);
            // Then calls merge to merge both the left and right together
            givenPoints.addAll(merge(left, right));
        }
        // Returns the LL with the sorted points
        return givenPoints;
    }

    /* addPoint(P)
       Add the point P to the internal point set for this object.
       Note that there is no facility to delete points (other than
       destroying the HullBuilder and creating a new one). 
    */
    public void addPoint(Point2d P){
        // Adds a point to a global linked list of all points given
        hullAllPoints.add(P);

    }

    /* Create Hull takes a linked list of points 
     * and returns a linked list of the points as a Convex Hull 
     */
    public LinkedList<Point2d> createHull(LinkedList<Point2d> list){

        // Initializes an LL to hold the convex hull
        LinkedList<Point2d> hull = new LinkedList<Point2d>();
        // Sorts the given list by smallest to largest x-coordinate
        list = mergeSort(list);
        // Sets an int the size of the given LL
        int n = list.size();
        // If the list has 1 or less elements, returns the convex hull
        if(n <= 1){
            return list;
        }

        // Adding elements to the Upper Hull, loop from 0 to n-1
        for(int i = 0; i < n; i++){
            // If the size is greater than 2, meaning there are 2 elements in Upper
            // Can compare it's chirality
            while(Upper.size() >= 2){
                
                // Initializes a point p1,p2 to compare
                Point2d p1= Upper.get(Upper.size()-1);
                Point2d p2= Upper.get(Upper.size()-2);
                // Compares the chirality of the two last points in Upper
                // With the next point in the given LL list
                if(Point2d.chirality(p2, p1, list.get(i)) <= 0){
                    // If it is 0 or -1
                    // The middle point is removed beause it causes the left turn or straight line
                    Upper.remove(p1);
                }else{
                    break;
                }
                
            }
            // Otherwise we add the element to Upper
            Upper.add(list.get(i));
            // If the Upper's size after adding an element is 3
            if(Upper.size() >= 3){
                // Compare the last added element with the two before
                int t = Upper.indexOf(list.get(i));
                // If it's chirality is 0
                if(Point2d.chirality(Upper.get(t-2), Upper.get(t-1), Upper.get(t)) == 0){
                    // Remove the middle element because it is in a straight line, 
                    // And the element is not needed
                    Upper.remove(t-1);
                }
            }
        }
        // Adding elements to the Lower Hull
        for(int i = n-1; i >= 0; i--){
            // Checks if the amount of elements in the Lower Hull is above 2
            // That way the chirality check can be performed
            while(Lower.size() >= 2){
                // If so, initialize the last two points in the Lower Hull
                Point2d p1= Lower.get(Lower.size()-1);
                Point2d p2= Lower.get(Lower.size()-2);
                // Comparing the chirality with the last two points in Lower and the next poin tin the LL list
                if(Point2d.chirality(p2, p1, list.get(i)) <= 0){
                    // If it -1, 0
                    // Remove the middle point beucase it causes the left turn or straight line
                    Lower.remove(p1);
                }else{
                    break;
                }
            }
            // Otherwise adds the element to Lower Hull
            Lower.add(list.get(i));
            // If Lower hull's new size is 3 or greater
            if(Lower.size() >= 3){
                // Compare the last added element with the two before
                int t = Lower.indexOf(list.get(i));
                // If it's chirality is 0
                if(Point2d.chirality(Lower.get(t-2), Lower.get(t-1), Lower.get(t)) == 0){
                    // Remove the middle element because it is in a straight line, 
                    // And the element is not needed
                    Lower.remove(t-1);
                }
            }
            
        }
        // Remove the first and last two elements in lower Hull because they are the same in the Upper Hull
        Lower.remove(0);
        Lower.remove(Lower.size() -1);
        // Add all the elmeents to Upper hull
        Upper.addAll(Lower);
        // Transfer all the Upper Hull's points to the LL Hull
        for(Point2d p: Upper){
            hull.add(p);
        }

        // Return the Convex Hull 
        return hull;
        
    }

    /* getHull()
       Return a java.util.LinkedList object containing the points
       in the convex hull, in order (such that iterating over the list
       will produce the same ordering of vertices as walking around the 
       polygon).
    */
    public LinkedList<Point2d> getHull(){
        // SortedHull calls createHull() to return a Convex Hull
        sortedHull = createHull(hullAllPoints);
        // Returns a convex Hull
        return sortedHull;
        
    }

    /* isInsideHull(P)
       Given an point P, return true if P lies inside the convex hull
       of the current point set (note that P may not be part of the
       current point set). Return false otherwise.
     */
    public boolean isInsideHull(Point2d P){
        // Initializes a new list to hold the given points
        LinkedList<Point2d> newHull = new LinkedList<Point2d>();
        // Creates a Convex Hull of the given points
        newHull = createHull(hullAllPoints);
        // Initializes a boolean to keep track of if the point is inside/outside
        boolean value = false;
        
        // Initializes an int to store the length of the sortedHull array
        int n = newHull.size();
        
        // In a for loop, iterate through each point in the Convex Hull
        for(int i = 0; i < n-1; i++){
            // Check if the point is always to the right or on the Convex Hull
            int l = Point2d.chirality(newHull.get(i), newHull.get(i+1), P);
            if( l < 0){
                // If it is to the left, it is outside return false
                return false;
            }else{
                // Otherwise it is inside
                value = true;
            }
        }
        // Return the boolean value of value
        return value;
        
    }
}