package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 * 
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {

	/*
	 * 
	 * YOUR CODE HERE
	 * 
	 */
	BinaryTreeNode<Key, Value> parent;
	BinaryTreeNode<Key, Value> left;
	BinaryTreeNode<Key, Value> right;
	boolean isDummy;

	public BinaryTreeNode(Key k, Value v) {
		super(k, v);
		this.left = null;
		this.right = null;
		this.isDummy = true;
		this.parent = null;

		/*
		 * 
		 * This constructor is needed for the autograder. You can fill the rest to your
		 * liking. YOUR CODE AFTER THIS:
		 * 
		 */
	}
	/*SETTERS*/
	public void setLeft(BinaryTreeNode<Key, Value> node) {
		this.left = node;
	}

	public void setRight(BinaryTreeNode<Key, Value> node) {
		this.right = node;
	}

	public void setIsDummy(boolean isDummy) {
		this.isDummy = isDummy;
	}
	
	public void setParent(BinaryTreeNode<Key, Value> node) {
		this.parent = node;
	}
	
	
	/*GETTERS*/
	public BinaryTreeNode<Key, Value> getLeft() {
		return this.left;
	}

	public BinaryTreeNode<Key, Value> getRight() {
		return this.right;
	}
	
	public BinaryTreeNode<Key, Value> getParent() {
		return this.parent;
	}

}
