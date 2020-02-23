package datastructs1;

public class SingleyLinkedList {

	Node head;
	Node tail;
	int size;

	public void addFirst(int data) {
		this.head = new Node(data, head);
		if (this.tail == null) {
			this.tail = this.head;
		}
		this.size += 1;
	}

	public void addLast(int data) {
		Node newNode = new Node(data, null);
		if (this.tail == null) {
			this.head = newNode;
		} else {
			this.tail.setNext(newNode);
		}
		this.tail = newNode;
		size += 1;
	}

	public int removeFirst() {
		int removedValue = this.head.getData();
		if (isEmpty()) {
			return 0;
		}
		this.head = this.head.getNext();
		this.size -= 1;
		return removedValue;
	}

	public int removeLast() {
		if (isEmpty()) {
			return 0;
		}
		int deletedValue = tail.getData();
		if (this.head == this.tail) {
			deletedValue = this.head.getData();
			this.head = null;
			this.tail = null;
			return deletedValue;
		} // Handling the case where the head is the same as the tail
		else {
			this.tail = getNode(this.size() - 1);
			deletedValue = this.tail.getData();
			this.tail.setNext(null);
		}
		size -= 1;
		return deletedValue;
	}
	
	public void add(int index, int data) {
		Node prevNode = getNode(index - 1);
		Node newNode = new Node(data, prevNode.getNext());
		prevNode.setNext(newNode);
		size += 1;
	}
	
	public int remove(int index) {
		Node prevNode = getNode(index - 1);
		int deletedValue = prevNode.getNext().getData();
		prevNode.setNext(prevNode.getNext().getNext());
	//	prevNode.getNext().setNext(null);
		size -= 1;
		return deletedValue;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int getFirst() {
		if (isEmpty())
			return 0;
		else
			return head.getData();
	}

	public int getLast() {
		if (isEmpty())
			return 0;
		else
			return tail.getData();
	}

	public Node getNode(int index) {
		Node tmp = this.head;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		return tmp;
	}

	public int get(int index) {
		return getNode(index).getData();
	}

	public void set(int index, int element) {
		getNode(index).setData(element);
	}
	
	public void printList() {
		Node tmp = this.head;
		for(int i = 0;i < this.size(); i++) {
			System.out.printf("%d ", tmp.getData());
			tmp = tmp.getNext();
		}
	}

	private class Node {
		int data;
		Node next;

		// Constructor
		Node(int data, Node next) {
			this.data = data;
			this.next = next;
		}

		public int getData() {
			return this.data;
		}

		public int setData(int newData) {
			int oldData = this.data;
			this.data = newData;
			return oldData;
		}

		public Node getNext() {
			return this.next;
		}

		public void setNext(Node newNext) {
			this.next = newNext;
		}
	}

}
