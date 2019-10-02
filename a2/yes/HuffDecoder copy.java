/* HuffDecoder.java

   Starter code for compressed file decoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   
   Kiana Pazdernik/ V00896924/ July 6, 2019
*/

import java.io.*;

/*
 * HuffDecoder outline is given by B.Bird
 */ 
public class HuffDecoder{

    private HuffFileReader inputReader;
    private BufferedOutputStream outputFile;

    /* Basic constructor to open input and output files. */
    public HuffDecoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputReader = new HuffFileReader(inputFilename);
        outputFile = new BufferedOutputStream(new FileOutputStream(outputFilename));
    }


    /*
     * Create a class Node 
     */
    class Node {
        // Initialize constructors for a right/left node with a byte[]
		private Node left;
		private Node right;
        private byte[] symbolB;
       
		public Node(byte[] value) {
			left = null;
			right = null;
			symbolB = value;
		}
        public Node() {
			left = null;
			right = null;
			symbolB = new byte[0];
		}
		
		// Method that checks if a certain treenode is a leaf aka a simple character. 
		public boolean isLeaf() {
			return left == null && right == null;
		}
	}
    /*
     * Class BinaryTree() to hold the tree
     */
    class BinaryTree{
        // Initialize a root
        private Node root; 
  
        // Constructors 
        public BinaryTree(byte[] key) 
        { 
            root = new Node(key); 
        } 
    
        public BinaryTree() 
        { 
            root = null; 
        } 
    }

 
    /* 
     * Decode takes a file of bits, and using a symbol table, decodes it
     */
    public void decode() throws IOException{

        // Initialize a BinaryTree with a root
        BinaryTree tr = new BinaryTree();
        tr.root = new Node();
        Node createNode = tr.root;

        // While true 
        // iterate through the inputreader creating a tree without inserting the symbols
        while(true){
            HuffFileSymbol x = inputReader.readSymbol();
            // If the symbol is null, break
            if(x == null){
                break;
            }

            // Otherwise for the length of two less htan SymbolBits, create a node without the symbol byte
            for(int i = 0; i < x.symbolBits.length - 1; i++){

                // If the int is a 0, it is on the left side of the tree
                if(x.symbolBits[i] == 0 && createNode.left == null){
                    // Add a new node on the left side
                    createNode.left = new Node();
                    createNode = createNode.left;
                // If the int is a 1, it is on the right side of the tree
                }else if(x.symbolBits[i] == 1 && createNode.right == null){
                    // Add a new node on the right side
                    createNode.right = new Node();
                    createNode = createNode.right;
                // If the bit is already created, no need to create it again on left or right side
                }else if(x.symbolBits[i] == 0 && createNode.left != null){
                    createNode = createNode.left;

                }else if(x.symbolBits[i] == 1 && createNode.right != null){
                    createNode = createNode.right;
                }
            
            }
            // Then for the length -1 of the Symbol Bits, add the symbol to the ending nodes
            // If the last bit is a 0, and the symbol to the left side
            if(x.symbolBits[x.symbolBits.length - 1] == 0){
                
                createNode.left = new Node(x.symbol);
                createNode = createNode.left;
            // If the last bit is a 0, and the symbol to the left side
            }else if(x.symbolBits[x.symbolBits.length - 1] == 1){

                createNode.right = new Node(x.symbol);
                createNode = createNode.right;
            }
            // Then add the nodes to the tree
            createNode = tr.root;
        }

        // Create a current node to Decode the Tree
        Node curr = tr.root;
        // Initialize an int data to 
        //int dataC;
       // While true, until the file reaches hte end at -1
        while(true){
            // If the stream bit is a 0
            int b = inputReader.readStreamBit(); 
            // Iterate on the left side of the tree
            if(b == 0){
                curr = curr.left;
            // If the stream bit is a 1, iterate the right side
            }else if(b == 1){
                curr = curr.right;
            // Otherwise it is the end of file
            }else if(b == -1){
                break;
            }
           
            // Iterate through the node current's symbol bits lenght
            for(int j = 0; j < curr.symbolB.length; j++){
        
                // Write each bit to the outputFile
                outputFile.write((int)curr.symbolB[j]);
                // If j is one less then the length, move curr to the tree root
                if(j == curr.symbolB.length-1){
                    curr = tr.root;
                }
                
            }

        }
        // Close the outputFile
        outputFile.close();

    }

    /*
     * Main() given by B.Bird
     */
    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.out.println("Usage: java HuffDecoder <input file> <output file>");
            
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try {
            HuffDecoder decoder = new HuffDecoder(inputFilename, outputFilename);
            decoder.decode();
        } catch (FileNotFoundException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
