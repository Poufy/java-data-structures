package code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import given.iMap;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

	/*
	 * 
	 * YOUR CODE BELOW THIS
	 * 
	 */
	BinaryTreeNode<Key, Value> root;
	Comparator<Key> comparator;
	ArrayList<BinaryTreeNode<Key, Value>> lstOfNodes;
	ArrayList<Key> lstOfKeys;
	int size;

	public BinarySearchTree() {
		root = new BinaryTreeNode<Key, Value>(null, null);
		lstOfNodes = new ArrayList<BinaryTreeNode<Key, Value>>();
		lstOfKeys = new ArrayList<Key>();
		this.size = 0;
	}

	@Override
	public Value get(Key k) {
		if (isEmpty()) {
			return null;
		}
		if (isExternal(getNode(k))) {
			return null;
		} else {
			return getNode(k).getValue();
		}
	}

	@Override
	public Value put(Key k, Value v) {
		BinaryTreeNode<Key, Value> newNode = getNode(k);
		if (isEmpty()) {
			this.root.setIsDummy(false);
			this.root.setKey(k);
			this.root.setValue(v);
			expandDummies(this.root);
			this.size++;
			return null;
		} else if (isExternal(newNode)) {
			newNode.setIsDummy(false);
			expandDummies(newNode);
			newNode.setKey(k);
			newNode.setValue(v);
			this.size++;
			return null;
		} else {
			Value oldValue = newNode.getValue(); // This means a key with this value already exists.
			newNode.setValue(v);
			return oldValue;
		}
	}

	@Override
	public Value remove(Key k) {
		BinaryTreeNode<Key, Value> node = getNode(k);

		if (isEmpty()) {
			return null;
		}
		if (isExternal(node)) {
			return null;
		}
		// Case where BOTH children are dummies just remove the node
		if (isExternal(getLeftChild(node)) && isExternal(getRightChild(node))) {
			Value v = node.getValue();
			dummify(node);
			this.size--;
			return v;
		}
		// Case where only left child is external we put its left child in its place.
		else if (isExternal(getLeftChild(node))) {
			Value v = node.getValue();
			if (isRoot(node)) {
				node.setKey(node.getRight().getKey());
				node.setValue(node.getRight().getValue());
				dummify(node.getRight());
			}
			if (isRightChild(node)) {
				node.getParent().setRight(node.getRight());
				node.getRight().parent = node.getParent();
			} else if (isLeftChild(node)) {
				node.getParent().setLeft(node.getRight());
				node.getRight().parent = node.getParent();
			}
			this.size--;
			return v;

		} // Case where only right child is external we put its left child in its place.
		else if (isExternal(getRightChild(node))) {
			Value v = node.getValue();
			if (isRoot(node)) {
				node.setKey(node.getLeft().getKey());
				node.setValue(node.getLeft().getValue());
				dummify(node.getLeft());
			}
			if (isRightChild(node)) {
				node.getParent().setRight(node.getLeft());
				node.getLeft().parent = node.getParent();
			} else if (isLeftChild(node)) {
				node.getParent().setLeft(node.getLeft());
				node.getLeft().parent = node.getParent();
			}
			this.size--;
			return v;
		} // Case where both children are not dummies.
		else {
			Value v = node.getValue();
			BinaryTreeNode<Key, Value> succesor = ceiling(k);
			node.setKey(succesor.getKey());
			node.setValue(succesor.getValue());
			if (succesor.getRight().isDummy) {
				dummify(succesor);
			} else if (!succesor.getRight().isDummy) {
				if (isRightChild(node)) {
					node.getParent().setRight(node.getRight());
					node.getRight().parent = node.getParent();
				} else if (isLeftChild(node)) {
					node.getParent().setLeft(node.getRight());
					node.getRight().parent = node.getParent();
				}
			}
			this.size--;
			return v;
		}
	}

	public void replace(BinaryTreeNode<Key, Value> from, BinaryTreeNode<Key, Value> to) {
		if (from == root) {
			to.setParent(null);
			root = to;
			return;
		}
		if (isLeftChild(from)) {
			from.getParent().setLeft(to);
		} else if (isRightChild(from)) {
			from.getParent().setRight(to);
		}
		to.setParent(from.getParent());
	}

	@Override
	public Iterable<Key> keySet() {
		inOrderKeyTraversal(getRoot(), lstOfKeys);
		return this.lstOfKeys;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	public void dummify(BinaryTreeNode<Key, Value> node) {
		node.setIsDummy(true);
		node.setRight(null);
		node.setLeft(null);
		node.setValue(null);
		node.setKey(null);
	}

	@Override
	public BinaryTreeNode<Key, Value> getRoot() {
		return this.root;
	}

	@Override
	public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
		return node.getParent();
	}

	@Override
	public boolean isInternal(BinaryTreeNode<Key, Value> node) {
		return !node.isDummy;
	}

	@Override
	public boolean isExternal(BinaryTreeNode<Key, Value> node) {
		return node.isDummy;
	}

	@Override
	public boolean isRoot(BinaryTreeNode<Key, Value> node) {
		return node.getParent() == null;
	}

	@Override
	public BinaryTreeNode<Key, Value> getNode(Key k) {
		return search(k, this.root);
	}

	public BinaryTreeNode<Key, Value> search(Key k, BinaryTreeNode<Key, Value> node) {
		if (isExternal(node)) { // If we reach a dummy external node.
			return node;
		}
		if (getComparator().compare(k, node.getKey()) == 0) {
			return node;
		} else if (getComparator().compare(k, node.getKey()) > 0) {
			return search(k, getRightChild(node));
		} else {
			return search(k, getLeftChild(node));
		}
	}

	@Override
	public Value getValue(Key k) {
		return getNode(k).getValue();
	}

	@Override
	public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
		return node.getLeft();
	}

	@Override
	public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
		return node.getRight();
	}

	@Override
	public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
		if (isLeftChild(node)) {
			return getRightChild(getParent(node));
		} else if (isRightChild(node)) {
			return getLeftChild(getParent(node));
		}
		return null;
	}

	@Override
	public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
		if (!isRoot(node) && node.getParent().getLeft() == node)
			return true;
		return false;
	}

	@Override
	public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
		if (!isRoot(node) && node.getParent().getRight() == node)
			return true;
		return false;
	}

	@Override
	public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {

		if (isEmpty()) {
			return this.lstOfNodes;
		}
		inOrderNodeTraversal(this.getRoot(), lstOfNodes);
		return this.lstOfNodes;
	}

	public void inOrderNodeTraversal(BinaryTreeNode<Key, Value> node, List<BinaryTreeNode<Key, Value>> lstOfNodes) {
		if (isInternal(node)) {
			inOrderNodeTraversal(getLeftChild(node), lstOfNodes);
			this.lstOfNodes.add(node);
			inOrderNodeTraversal(getRightChild(node), lstOfNodes);
		}
	}

	public void inOrderKeyTraversal(BinaryTreeNode<Key, Value> node, List<Key> lstOfKeys) {
		if (isInternal(node)) {
			inOrderKeyTraversal(getLeftChild(node), lstOfKeys);
			this.lstOfKeys.add(node.getKey());
			inOrderKeyTraversal(getRightChild(node), lstOfKeys);
		}
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		comparator = C;
	}

	@Override
	public BinaryTreeNode<Key, Value> ceiling(Key k) {
		BinaryTreeNode<Key, Value> node = getNode(k);
		if (isEmpty()) {
			return null;
		}
		if (isExternal(node)) { // If this is external then we did not even find the key
			while (node != this.getRoot()) {
				node = node.getParent();
				if (comparator.compare(node.getKey(), k) > 0) {
					return node;
				}
			}
			return null;
		} else if (comparator.compare(k, node.getKey()) == 0) {
			// CASE WHERE WE NEED THE CEILING FOR 5 AND 5 ALREADY EXISTS WE DON'T WANNA
			// RETURN 5 WE RETURN HIGHER VALUE
			/////////////////
			if (isExternal(getRightChild(node))) { // If right child is a dummy then no higher key
				return null;
			}
			node = getRightChild(node); // Move one to the right then keep going left.
			while (!isExternal(getLeftChild(node))) {
				node = getLeftChild(node);
			}
			return node;
			/////////////////
		}

		return node;
	}

	@Override
	public BinaryTreeNode<Key, Value> floor(Key k) {
		BinaryTreeNode<Key, Value> node = getNode(k);
		if (isEmpty()) {
			return null;
		}
		if (isExternal(node)) { // If this is external then we did not even find the key
			if (comparator.compare(k, node.getParent().getKey()) > 0) {
				return node.getParent();
			} else {
				return null;
			}
		}
		return node;
	}

	@Override
	public Comparator<Key> getComparator() {
		return this.comparator;
	}

	public void expandDummies(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> firstDummy = new BinaryTreeNode<Key, Value>(null, null);
		BinaryTreeNode<Key, Value> secondDummy = new BinaryTreeNode<Key, Value>(null, null);
		if (getLeftChild(node) == null) {
			node.setLeft(firstDummy);
			firstDummy.setParent(node);
		}
		if (getRightChild(node) == null) {
			node.setRight(secondDummy);
			secondDummy.setParent(node);
		}

	}
}
