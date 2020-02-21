package datastructs1;

public class arrayList {
	int[] arr;
	int n;

	public arrayList() {
		this.arr = new int[2];
		this.n = 0;
	}

	public int get(int i) {
		if (i > n - 1) {
			throw new IndexOutOfBoundsException();
		} else {
			return this.arr[i];
		}
	}

	public void add(int index, int e) {
		if(n == this.arr.length) {
			System.out.println("Resizing");
			resizeArray();
		}
		checkIndex(index, n + 1);
		for (int i = this.n - 1; i > index; i--) {
			this.arr[i + 1] = this.arr[i];
		}
		this.arr[index] = e;
		this.n++;
	}
	
	public int delete(int index) {
		checkIndex(index, n+1);
		int deletedVAlue = this.arr[index];
		for(int i = index; i < this.arr.length - 1; i++) {
			arr[i] = arr[i+1];
		}
		this.n--;
		
		
		return deletedVAlue;
	}

	public void checkIndex(int index, int size) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public void resizeArray() {
		int[] newArr = new int[n*2 + 1];
		for(int i =0; i<n; i++) {
			newArr[i] = this.arr[i];
		}
		this.arr = newArr;
	}

	public int set(int index, int e) {
		int changedValue = this.arr[index];
		this.arr[index] = e;
		return changedValue;
	}
	
	public int size() {
		return this.n;
	}
	public String toString() {
		String x = "";
		for(int i = 0; i < n; i++) {
			x = x +  arr[i]+ " ";
		}
		return x;
		
	}

}
