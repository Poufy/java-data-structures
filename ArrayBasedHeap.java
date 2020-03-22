package code;

import java.util.ArrayList;
import java.util.Comparator;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 * 
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	// Use this arraylist to store the nodes of the heap.
	// This is required for the autograder.
	// It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but
	// then you do not have to deal with dynamic resizing
	protected ArrayList<Entry<Key, Value>> nodes;
	Comparator<Key> comparator;

	/*
	 * 
	 * YOUR CODE BELOW THIS
	 * 
	 */

	public ArrayBasedHeap() {
		this.nodes = new ArrayList<Entry<Key, Value>>();
	}

	@Override
	public int size() {
		return this.nodes.size();
	}

	@Override
	public boolean isEmpty() {
		return this.nodes.size() == 0;
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		this.comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return this.comparator;
	}

	@Override
	public void insert(Key k, Value v) {
		this.nodes.add(new Entry<Key, Value>(k, v)); // Adding the entry to the last index.
		upHeap(nodes.size() - 1);
	}

	@Override
	public Entry<Key, Value> pop() {
		if (isEmpty()) {
			return null;
		}
		Entry<Key, Value> returnEntry = this.nodes.get(0);
		swap(0, size() - 1);
		this.nodes.remove(size() - 1);
		downHeap(0, size() - 1);
		return returnEntry;
	}

	@Override
	public Entry<Key, Value> top() {
		if (isEmpty()) {
			return null;
		}
		return this.nodes.get(0);
	}

	@Override
	public Value remove(Key k) {
		int removeIndex = -1;
		for (int i = 0; i < nodes.size(); i++) {
			if (comparator.compare(k, nodes.get(i).getKey()) == 0) {
				removeIndex = i;
			}
		}

		if (removeIndex == -1) {
			return null;
		}
		Value removeValue = this.nodes.get(removeIndex).getValue();
		if (removeIndex == size() - 1) { // If last element is removed.
			this.nodes.remove(size() - 1);
			return removeValue;
		}
		swap(removeIndex, size() - 1);
		this.nodes.remove(size() - 1);
		if(comparator.compare(this.nodes.get(removeIndex).getKey(), this.nodes.get(parentIndexOf(removeIndex)).getKey()) < 0) {
			upHeap(removeIndex);
		}else {
			downHeap(removeIndex, size() - 1);
		}
		return removeValue;
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		Key oldKey = null;
		for (int i = 0; i < this.size(); i++) {
			if (comparator.compare(entry.getKey(), this.nodes.get(i).getKey()) == 0
					&& entry.getValue() == this.nodes.get(i).getValue()) {
				oldKey = this.nodes.get(i).getKey();
				this.nodes.set(i, new Entry<Key, Value>(k, this.nodes.get(i).getValue()));
				//When we change the key we need to fix the heap order so we check the key of the parent and children and downheap or uphead
				if(comparator.compare(this.nodes.get(i).getKey(), this.nodes.get(parentIndexOf(i)).getKey()) < 0) {
					upHeap(i);
				}else {
					downHeap(i, size() - 1);
				}
			}
		}
		
		
		return oldKey;
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		Key oldKey = null;
		for (int i = 0; i < this.size(); i++) {
			if (v == this.nodes.get(i).getValue()) {
				oldKey = this.nodes.get(i).getKey();
				this.nodes.set(i, new Entry<Key, Value>(k, v));
				if(comparator.compare(this.nodes.get(i).getKey(), this.nodes.get(parentIndexOf(i)).getKey()) < 0) {
					upHeap(i);
				}else {
					downHeap(i, size() - 1);
				}
			}
		}
		return oldKey;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		Value oldValue = null;
		for (int i = 0; i < this.size(); i++) {
			if (comparator.compare(entry.getKey(), this.nodes.get(i).getKey()) == 0) {
				oldValue = this.nodes.get(i).getValue();
				this.nodes.set(i, new Entry<Key, Value>(entry.getKey(), v));
			}
		}
		return oldValue;
	}

	public void downHeap(int i, int last) {
		int ind = i;
		int lc = leftChildOf(i);
		int rc = rightChildOf(i);
		if (lc <= last && comparator.compare(nodes.get(ind).getKey(), nodes.get(lc).getKey()) > 0) {
			ind = lc;
		}
		if (rc <= last && comparator.compare(nodes.get(ind).getKey(), nodes.get(rc).getKey()) > 0) {
			ind = rc;
		}
		if (ind != i) {
			swap(i, ind);
			downHeap(ind, last);
		}
	}
//	public void downHeap(int i) {
//		while (hasLeftChild(i)) {
//			int smallChild = leftChildOf(i);
//			if (hasRightChild(i)) {
//				int rightChild = rightChildOf(i);
//				if (comparator.compare(this.nodes.get(rightChild).getKey(), this.nodes.get(smallChild).getKey()) < 0) {
//					smallChild = rightChild;
//				}
//			}
//
//			if (comparator.compare(this.nodes.get(smallChild).getKey(), this.nodes.get(i).getKey()) >= 0) {
//				break;
//			}
//			swap(i, smallChild);
//			i = smallChild;
//		}
//	}

	public void upHeap(int i) {
		while (i > 0) {
			int parentIndex = parentIndexOf(i);
			if (comparator.compare(this.nodes.get(i).getKey(), this.nodes.get(parentIndex).getKey()) >= 0) {
				break;
			}
			swap(i, parentIndex);
			i = parentIndex;
		}
	}

	public void swap(int i, int j) {
		Entry<Key, Value> tmp = this.nodes.get(i);
		this.nodes.set(i, this.nodes.get(j));
		this.nodes.set(j, tmp);
	}

	public boolean checkIndex(int i) {
		if (i < 0 || i > size() - 1)
			return false;
		return true;
	}

	public int parentIndexOf(int i) {
		return (int) ((i - 1) / 2);
	}

	public int leftChildOf(int i) {
		return 2 * i + 1;
	}

	public int rightChildOf(int i) {
		return 2 * i + 2;
	}

	public boolean hasRightChild(int i) {
		return rightChildOf(i) < size();
	}

	public boolean hasLeftChild(int i) {
		return leftChildOf(i) < size();
	}

}
