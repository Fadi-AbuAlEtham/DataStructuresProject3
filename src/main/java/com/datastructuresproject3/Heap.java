package com.datastructuresproject3;

public class Heap {

	private MartyrNode[] maxHeap;
	private int heapSize;
	private int size;

	public Heap(int capacity) {
		this.size = capacity;
		this.heapSize = 0;
		// +1 to avoid using index 0
		maxHeap = new MartyrNode[capacity + 1];
	}

	public MartyrNode[] getHeap() {
		return maxHeap;
	}

	public void setHeap(MartyrNode[] heap) {
		this.maxHeap = heap;
	}

	public int getHeapSize() {
		return heapSize;
	}

	public void setHeapSize(int heapSize) {
		this.heapSize = heapSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	private int left(int parent) {
		return 2 * parent;
	}

	private int right(int parent) {
		return 2 * parent + 1;
	}

	private int parent(int child) {
		return child / 2;
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}

	public void insert(MartyrNode data) {
		if (heapSize == size) {
			System.out.println("Heap is full!");
			return;
		}

		maxHeap[++heapSize] = data;

		// Heapify to maintain heap property (max heap)
		int current = heapSize;
		while (current > 1 && maxHeap[current].getElement().getAge() > maxHeap[parent(current)].getElement().getAge()) {
			swap(current, parent(current));
			current = parent(current);
		}
	}

	// Swaps two elements in the heap
	private void swap(int i, int j) {
		MartyrNode temp = maxHeap[i];
		maxHeap[i] = maxHeap[j];
		maxHeap[j] = temp;
	}

	// Extracts the maximum element from the heap (root)
	public MartyrNode extractMax() {
		if (isEmpty()) {
			System.out.println("Heap is full!");
			return null;
		}

		// root element
		MartyrNode max = maxHeap[1];

		// Move the last element to the root
		maxHeap[1] = maxHeap[heapSize];
		// Clear the last element
		maxHeap[heapSize--] = null;

		// Heapify to maintain heap property (max heap)
		heapify(1);

		return max;
	}

	// Heapify function to maintain heap property (max heap)
	private void heapify(int index) {
		int largest = index;
		int leftChild = left(index);
		int rightChild = right(index);

		// Check if left child is larger
		if (leftChild <= heapSize
				&& maxHeap[leftChild].getElement().getAge() > maxHeap[largest].getElement().getAge()) {
			largest = leftChild;
		}

		// Check if right child is larger
		if (rightChild <= heapSize
				&& maxHeap[rightChild].getElement().getAge() > maxHeap[largest].getElement().getAge()) {
			largest = rightChild;
		}

		// If root is not the largest, swap and recursively heapify
		if (largest != index) {
			swap(index, largest);
			heapify(largest);
		}
	}

	// Build a max heap from the current array
	public void buildHeap() {
		for (int i = heapSize / 2; i >= 1; i--) {
			heapify(i);
		}
	}

	public void heapSort() {
		if (isEmpty()) {
			System.out.println("Heap is empty!");
			return;
		}

		// Original size
		int n = heapSize;

		// Build a max heap
		buildHeap();

		// One by one extract elements from heap and put them at the end of heap
		for (int i = n; i >= 2; i--) {
			// Move current root (oldest martyr) to end
			swap(1, i);
			heapSize--;
			heapify(1);
		}

		// Reset heapSize back to its original value
		heapSize = n;
	}
}
