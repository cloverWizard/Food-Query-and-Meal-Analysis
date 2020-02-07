import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * @author D-team 12, CS400, Fall 2018
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {
	
    private Node root; //Root of the tree
    private int branchingFactor; //Branching factor is the number of children nodes for internal nodes of the tree
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        root = new LeafNode();
    }
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
    	System.out.println("Root is now: " + root.toString());
    	System.out.println("Insert (" + key + ")"); //", " + value + ")");
    	
        root.insert(key, value);
    }
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        
        return root.rangeSearch(key, comparator);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
		Queue<List<Node>> queue = new LinkedList<List<Node>>();
		queue.add(Arrays.asList(root));
		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
			while (!queue.isEmpty()) {
				List<Node> nodes = queue.remove();
				sb.append('{');
				Iterator<Node> it = nodes.iterator();
				while (it.hasNext()) {
					Node node = it.next();
					sb.append(node.toString());
					if (it.hasNext())
						sb.append(", ");
					if (node instanceof BPTree.InternalNode)
						nextQueue.add(((InternalNode) node).children);
				}
				sb.append('}');
				if (!queue.isEmpty())
					sb.append(", ");
				else
					sb.append('\n');
			}
			queue = nextQueue;
		}
		return sb.toString();
	}

    
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        List<K> keys; //List of keys
        int amountKeys() { //Number of keys in a node
			return keys.size();
        }
        
        /**
         * Package constructor
         */
        Node() {
            keys = new ArrayList<>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } //End of abstract class Node
 
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {
    	
		List<Node> children; //List of children nodes

		/**
         * Package constructor
         */
		InternalNode() { 
			this.keys = new ArrayList<K>();
			this.children = new ArrayList<Node>();
		}

		/**
        * (non-Javadoc)
        * @see BPTree.Node#getFirstLeafKey()
        */
		K getFirstLeafKey() {
			return children.get(0).getFirstLeafKey();
		}
		
		/**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
		boolean isOverflow() {
			return children.size() > branchingFactor;
		}
		
		/**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
		void insert(K key, V value) {
			Node child = getChild(key);
			child.insert(key, value);
			if (child.isOverflow()) {
				Node sibling1 = child.split();
				insertChild(sibling1.getFirstLeafKey(), sibling1);
			}
			if (root.isOverflow()) {
				Node sibling2 = split();
				InternalNode newRoot = new InternalNode();
				newRoot.keys.add(sibling2.getFirstLeafKey());
				newRoot.children.add(this);
				newRoot.children.add(sibling2);
				root = newRoot;
			}
		}

		/**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
		Node split() {
			int min = amountKeys() / 2 + 1;
			int max = amountKeys();
			InternalNode sibling = new InternalNode();
			sibling.keys.addAll(keys.subList(min, max));
			sibling.children.addAll(children.subList(min, max + 1));
			keys.subList(min - 1, max).clear();
			children.subList(min, max + 1).clear();
			return sibling;
		}
		
		/**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
		List<V> rangeSearch(K key, String comparator) {
        	int loc = Collections.binarySearch(keys, key);
			int index = loc >= 0 ? loc + 1 : -loc - 1;
			return children.get(index).rangeSearch(key, comparator);
}

		void insertChild(K key, Node child) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			if (loc >= 0) {
				children.set(childIndex, child);
			} else {
				keys.add(childIndex, key);
				children.add(childIndex + 1, child);
			}
		}
		
		Node getChild(K key) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			return children.get(childIndex);
		}
		
	} //End of class InternalNode

    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        List<V> values; //List of values
        LeafNode next; //Reference to the next leaf node
        LeafNode previous; //Reference to the previous leaf node
        InternalNode parent; //Reference to the parent internal node
        
        /**
         * Package constructor
         */
        LeafNode() {
        	keys = new ArrayList<K>();
			values = new ArrayList<V>();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	return values.size() > branchingFactor - 1;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
        	int index1 = Collections.binarySearch(keys, key);
			int index2;
			if (index1 >= 0) {
			    index2 = index1;
			} else {
			    index2 = -index1 - 1;
			}
			
			//Adds values to BPTree
			if (index1 >= 0) {
				values.set(index2, value);
			} else {
				keys.add(index2, key);
				values.add(index2, value);
			}
			
			if (root.isOverflow()) {
				Node sibling = split();
				InternalNode newRoot = new InternalNode();
				newRoot.keys.add(sibling.getFirstLeafKey());
				newRoot.children.add(this);
				newRoot.children.add(sibling);
				root = newRoot;
			}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	System.out.println("The size of key is " + keys.size());
        	System.out.println("Leaf node split");
        	
        	LeafNode newNode = new LeafNode();
			int min = (amountKeys() + 1) / 2;
			int max = amountKeys();
			newNode.keys.addAll(keys.subList(min, max));
			newNode.values.addAll(values.subList(min, max));

			keys.subList(min, max).clear();
			values.subList(min, max).clear();

			newNode.next = next;
			next = newNode;
			return newNode;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch1(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	ArrayList<V> result = new ArrayList<>();
        	
        	int index1 = Collections.binarySearch(keys, key);
        	
        	if (index1 == -1) {
        		if (previous != null) {
        			return previous.rangeSearch(key, comparator);
        		}
        		return result;
        	}
        	if (index1 < -keys.size() + 1) {
        		if (next != null) {
        			return next.rangeSearch(key, comparator);
        		}
        		return result;
        	}	
        	else {	
        		if (comparator.contentEquals("==")) {
        			if (index1 >= 0) {
        				int index2 = index1;
        				while (keys.get(index2).equals(key)) {
        					result.add(values.get(index2));
        					if (index2 == 0) {
        						return previous.rangeSearch(key, comparator);
        					}
        					else 
        						index2--;
        				}
        				index2 = index1 + 1;
        				while (keys.get(index2).equals(key)) {
        					result.add(values.get(index2));
        					if (index2 == 0) {
        						return next.rangeSearch(key, comparator);
        					}
        					else 
        						index2++;
        				}
        			}
        			return result;
        		}
        		if (comparator.contentEquals("<=")) {
        			if (index1 >= 0) {
        				int index2 = index1;
        				while (keys.get(index2).equals(key)) {
        					result.add(values.get(index2));
        					if (index2 == 0) {
        						return previous.rangeSearch(key, comparator);
        					}
        					else {
        						index2--;
        					}
        				}
        				for (int i = index2; i < keys.size(); i++) {
        					result.add(values.get(i));
        				}
        				LeafNode cur = this;
        				while (cur.next != null) {
        					cur = cur.next;
        					result.addAll(cur.values);
        				}
        			}
        			if (index1 < 0) {
        				int index2 = -index1 - 1;
        				for (int i = index2; i < keys.size(); i++) {
        					result.add(values.get(i));
        				}
        				LeafNode cur = this;
        				while (cur.next != null) {
        					cur = cur.next;
        					result.addAll(cur.values);
        				}
        			}
        			return result;
        		}	
        		if (comparator.contentEquals("=")) {
        			if (index1 >= 0) {
        				int index = index1 + 1;
        				while (keys.get(index).equals(key)) {
        					result.add(values.get(index));
        					if (index == 0) {
        						return next.rangeSearch(key, comparator);
        					}
        					else 
        						index++;
        				}
        				for (int i = index; i >= 0 ; i --) {
        					result.add(values.get(i));
        				}
        				LeafNode cur = this;
        				while (cur.previous != null) {
        					cur = cur.previous;
        					result.addAll(cur.values);
        				}
        			}
        			if (index1 < 0) {
        				int index2 = - index1 - 1;
        				for (int i = index2; i >= 0 ; i--) {
        					result.add(values.get(i));
        				}
        				LeafNode cur = this;
        				while (cur.previous != null) {
        					cur = cur.previous;
        					result.addAll(cur.values);
        				}	
        			} 
        			return result;
        		}
        		return result;
        	}
        }
        
    } //End of class LeafNode
    
    
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1.0d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Double j = dd[rnd1.nextInt(10)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
//        System.out.println("The size of filted value is " + filteredValues.size());
        		
//        System.out.println("Filtered values: " + filteredValues.toString());
    }

} //End of class BPTree
