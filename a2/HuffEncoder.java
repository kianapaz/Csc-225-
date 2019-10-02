/* HuffEncoder.java

   Starter code for compressed file encoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   (Add your name/studentID/date here)
    Kiana Pazdernik/ V00896924/ July 6, 2019
*/

import java.io.*;
import java.util.LinkedList;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.*;

class TreeNode implements Comparable<TreeNode>{
    public TreeNode left;
    public TreeNode right;
    public byte ch;
    public int frequency;

    public TreeNode(byte ch, int freq) {
        left = null;
        right = null;
        ch = ch;
        frequency = freq;
    }
    public TreeNode() {
        left = null;
        right = null;
        ch = 0;
        frequency = 0;
        
    }
    // Method that checks if a certain treenode is a leaf aka a simple character. 
    public boolean isLeaf() {
        return left == null && right == null;
    }
    // CompareTo comapres the frequency value when adding to Priority Queue
    // CompareTo ensures the lowest Frequencies are added to the Prioirty Queue when merging leafs in buildTree()
    public int compareTo(TreeNode node1) {
        if(this.totalFrequency(this) < node1.totalFrequency(node1)){
            return -1;
        }else if(this.totalFrequency(this) > node1.totalFrequency(node1)){
            return 1;
        }else if(this.totalFrequency(this) == node1.totalFrequency(node1)){
            return 0;
        }
        return 0;
        
    }
    // Total frequency recurses through the left and right side of the tree to get the total frequency
    public int totalFrequency(TreeNode node1){
        int tot = 0;

        // If the node is null, the frequency is 0
        if(node1 == null){
            return 0;
        }
        // The tot int recurses through the right, left and root to get total frequency 
        tot = totalFrequency(node1.right) + totalFrequency(node1.left) + node1.frequency;
        return tot;
    }
}
/* 
 * BinaryTree initializiation to hold the Huffman tree
 */
class BinaryTree{
    public TreeNode root; 

    // Constructors 
    public BinaryTree(byte key, int freq) 
    { 
        root = new TreeNode(key, freq); 
    } 

    public BinaryTree() 
    { 
        root = null; 
    } 
}

/* 
 * Outline HuffEncoder given by B. Bird
 */ 
public class HuffEncoder{

    private BufferedInputStream inputFile;
    private HuffFileWriter outputWriter;

    public HuffEncoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputFile = new BufferedInputStream(new FileInputStream(inputFilename));
        outputWriter = new HuffFileWriter(outputFilename);
    }

    /* 
     * reverse() takes a stack and reverses the order into an int array
     */
    public int[] reverse( Stack<Integer> s){
        
        // Create an array and pop all the elements from stack into the array
        int[] arrayInt = new int[s.size()];
        int i = 0;
        while(!s.empty() ){
            arrayInt[i] = s.pop();
            i++;
        }
        
        // Reverse the order in the int array by creating a temp array
        int n = arrayInt.length;
        int[] b = new int[n]; 
        int j = n; 
        for (int k = 0; k < n; k++) { 
            b[j - 1] = arrayInt[k]; 
            j = j - 1; 
        } 
        // Return the int array in reversed order from the given stack
        return b;

    }
   
    // Initalize a private symbolTable to hold the finalized TreeMap containing a byte and Integer[]
    private TreeMap<Byte, Integer[]> symbolTable = new TreeMap<Byte, Integer[]>();
    /*
     * Encoding the values using a stack, accepts a node, a stack and a HuffFileWriter
     */
    public void traverse(TreeNode n, Stack<Integer> s, HuffFileWriter x){

        // Checking if the node is a leaf
        if(n.right == null && n.left == null){
           
            // call the reverse method to reverese the stack 
            int[] symbolBit = reverse(s);

            // Create a temp Integer[] to transfer all the values from int[] to Integer[]
            Integer[] temp = new Integer[symbolBit.length];
            int i = 0;
            for(int bit : symbolBit){
                temp[i++] = Integer.valueOf(bit);
            }

            // Place the symbol byte[] and the symbolBits Integer[] to TreeMap
            symbolTable.put(n.ch, temp);
            // Place the symbol and symbolBits to the HuffFileSymbol
            HuffFileSymbol encode = new HuffFileSymbol(n.ch, symbolBit);
            // Write the HuffFileSymbol with the symbol and symbolBits
            x.writeSymbol(encode);
            
        }else{
            // Otherwise encode the bits 
            // push a 0 for going left
            s.push(0);
            // Create a new stack that contains all the values from original stack to traverse
            Stack<Integer> s2 = new Stack<Integer>();
            s2.addAll(s);
            traverse(n.left, s2, x);
            // push a 1 for going right
            s.pop();
            s.push(1);
            // Create a new stack that contains all the values from original stack to traverse
            Stack<Integer> s3 = new Stack<Integer>();
            s3.addAll(s);
            traverse(n.right, s3, x);
            s.pop();
        }

    }

    /* 
     * solveIterative iterates through the linked list of input bytes, and adds the vyte to a Treemap countint it's frequency
     */ 
    public static TreeMap<Byte, Integer> recurseFreq(LinkedList<Byte> listB)
	{
        // Initialize a Treemap
		TreeMap<Byte, Integer> tmap = new TreeMap<>();
 
		// Initialize a ListIterate to iterate through the input bytes
        ListIterator<Byte> looping = listB.listIterator();
		while(looping.hasNext()){
            // Get the byte value
            byte val = listB.pop();
            // If it is not in the Treemap
			if(!tmap.containsKey(val))
			{
				// Add it to tree map with a frequency 1
				tmap.put(val, 1);
			}
			else {
				// If it is in the TreeMap
                // Add one to the frequency
				tmap.put(val, tmap.get(val)+1);
			}
		}	
        // Return TreeMap with symbol and frequency
		return tmap;
	}


	// Compute the frequency of each byte, then create the Huffman tree according to frequencies
    public PriorityQueue<TreeNode> buildTree(LinkedList<Byte> input_bytes){
        
        // Initialize a Priority Queue to hold hte Huffman tree
        PriorityQueue<TreeNode> pq = new PriorityQueue<TreeNode>();

        // Implements a TreeMap that contains the byte and frequency 
        TreeMap<Byte, Integer> tmap = recurseFreq(input_bytes);
       
        // Iterate through the treeMap to initialize a leaf node and insert in Priority Queue
        for (Map.Entry<Byte, Integer> entry : tmap.entrySet()) { 
            // Initialize a tree node for every leaf node
            TreeNode node = new TreeNode();
            // Given the symbol, initialize the byte node to be equal to that symbol
            node.ch = entry.getKey();
            // Given frequency, initialize node
            node.frequency = entry.getValue();
           
            // Add to the priority queue
            pq.add(node);
           
        }

        // While the Priority Queue is greater than 1 element
        while (pq.size() > 1) {
            // Removing the nodes with the 2 lowest frequencies
            TreeNode node1 = pq.remove();
            TreeNode node2 = pq.remove();

            // Merge the nodes together using a parent node
            TreeNode parent = new TreeNode();
            parent.left = node1;
            parent.right = node2;
           
            // Then add the parent node to the Priority Queue
            pq.add(parent);
        }
        // Return the priority Queue with one element containing the Huffman tree
        return pq;
      
	}

    /*
     * Given a file, encode the file using Huffman Encoding
     */ 
    public void encode() throws IOException{

        
        // Linked List Byte given by B. Bird
        LinkedList<Byte> input_bytes = new LinkedList<Byte>();
        for(int nextByte = inputFile.read(); nextByte != -1; nextByte = inputFile.read()){
            input_bytes.add((byte)nextByte);
        }

        // Creates a copy of the input data 
        LinkedList<Byte> encoding = new LinkedList<Byte>();
        encoding.addAll(input_bytes);

        // Initializes a Huffman tree calling build tree
        PriorityQueue<TreeNode> pq = buildTree(input_bytes);


        // Create a Binary Tree with the tree from the Priority Queue
        BinaryTree tr = new BinaryTree();
        tr.root = pq.remove();
        Stack<Integer> a =new Stack<Integer>();

        // Traverse the tree encoding the Bit sequence for each symbol
        traverse(tr.root, a, outputWriter);
        // Finalize the symbols
        outputWriter.finalizeSymbols();
        
        // Create listIterator looping, to loop through the input bytes
        ListIterator<Byte> looping = encoding.listIterator();
       
        while(looping.hasNext()){
            // Pop the byte from listIterator
            byte inputB = encoding.pop();
            // Check for the byte in the symbol table
            if(symbolTable.containsKey(inputB)){
                
                // Then get the bit sequence from the symbolTable
                Integer[] arrCopy = symbolTable.get(inputB);
                
                // Iterate and call writeStreamBit with each int from the bit sequence
                for(Integer item: arrCopy){
                    outputWriter.writeStreamBit(item);
                }
            }

        }
        // Close the HuffFileReader
        outputWriter.close();
        
    }

    /*
     * Main() given by B. Bird
     */
    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.err.println("Usage: java HuffEncoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try{
            HuffEncoder encoder = new HuffEncoder(inputFilename, outputFilename);
            encoder.encode();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: "+e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: "+e.getMessage());
        }

    }
}

        //Suggested algorithm:

        //Compute the frequency of each input symbol. Since symbols are one character long,
        //you can simply iterate through input_bytes to see each symbol.
        
        //Build a prefix code for the encoding scheme (if using Huffman Coding, build a 
        //Huffman tree).
        
        //Write the symbol table to the output file

        //Call outputWriter.finalizeSymbols() to end the symbol table

        //Iterate through each input byte and determine its encode bitstring representation,
        //then write that to the output file with outputWriter.writeStreamBit()

        //Call outputWriter.close() to end the output file
