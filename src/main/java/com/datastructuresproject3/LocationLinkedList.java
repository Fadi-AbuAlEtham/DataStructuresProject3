package com.datastructuresproject3;

public class LocationLinkedList {
	// Attributes of LocationLinkedList
	private LocationNode Front, Back;
	private int Size;

	// Default constructor
	public LocationLinkedList() {
		Front = Back = null;
		Size = 0;
	}

	// Getter method for retrieving the front
	public LocationNode getFront() {
		return Front;
	}

	// Setter method for setting the front
	public void setFront(LocationNode front) {
		Front = front;
	}

	// Getter method for retrieving the back
	public LocationNode getBack() {
		return Back;
	}

	// Setter method for setting the back
	public void setBack(LocationNode back) {
		Back = back;
	}

	// Getter method for retrieving the size
	public int getSize() {
		return Size;
	}

	// Setter method for setting the size
	public void setSize(int size) {
		Size = size;
	}

	/* Methods go here */
	public void addFirst(String element) {
		LocationNode newNode;
		newNode = new LocationNode(element);
		// Empty List
		if (Size == 0) {
			Front = Back = newNode;
		} else { // Not empty list
			newNode.setNext(Front);
			Front = newNode;
		}
		Size++;// update Size
	}

	// Getter method for retrieving the firstElement
	public Object getFirst() {
		// Empty List
		if (Size == 0)
			return null;
		else
			// First element
			return Front.getElement();
	}

	// Method that adds element at the end of the list
	public void addLast(String element) {
		LocationNode newNode;
		newNode = new LocationNode(element);
		if (Size == 0) { // Empty List
			Front = Back = newNode;
		} else {
			Back.setNext(newNode);
			Back = newNode; // Or Back=Back.next;
		}
		Size++; // update Size
	}

	// Getter method for retrieving the lastElement
	public Object getLast() {
		// Empty list
		if (Size == 0)
			return null;
		else
			// Last element
			return Back.getElement();
	}

	// Getter method for retrieving element at specific index
	public Object get(int index) {
		// empty list
		if (Size == 0)
			return null;
		// first element
		else if (index == 0)
			return getFirst();
		// last element
		else if (index == Size - 1)
			return getLast();
		// neither first nor last
		else if (index > 0 && index < Size - 1) {
			LocationNode current = Front;
			// iterates over elements
			for (int i = 0; i < index; i++)
				current = current.getNext();
			return current.getElement();
		} else
			return null; // out of boundary
	}

	// Return the size of the list
	public int size() {
		return Size;
	}

	// Method that adds element to a specific index in the list
	public void add(int index, String element) {
		// Empty list
		if (index == 0)
			addFirst(element);
		// If the list was full
		else if (index >= size())
			addLast(element);
		// If the list has elements
		else {
			LocationNode newNode = new LocationNode(element);
			// used to advance to proper position
			LocationNode current = Front;
			// Iterates over elements
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			// update size
			Size++;
		}
	}

	// Method that adds element to the end of the list
	public void add(String element) {
		add(size(), element);
	}

	// Method that removes the first element
	public boolean removeFirst() {
		// empty list
		if (Size == 0)
			return false;
		// one element inside list
		else if (Size == 1)
			Front = Back = null;
		// more than one element in the list
		else
			Front = Front.getNext();
		// update size
		Size--;
		return true;
	}

	// Method that removes the last element of the list
	public boolean removeLast() {
		// empty list
		if (Size == 0)
			return false;
		// one element inside the list
		else if (Size == 1)
			Front = Back = null;
		else {
			// more than one element in the list
			LocationNode current = Front;
			for (int i = 0; i < Size - 2; i++)
				current = current.getNext();
			current.setNext(null);
			Back = current;
		}
		// update size
		Size--;
		return true;
	}

	// Method that removes element at specific index
	public boolean remove(int index) {
		// empty linked list
		if (Size == 0)
			return false;
		// remove first element
		else if (index == 0)
			return removeFirst();
		// remove last element
		else if (index == getSize() - 1)
			return removeLast();
		// iterates over locations to find the target index
		else if (index > 0 && index < Size - 1) {
			LocationNode current = Front;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			current.setNext(current.getNext().getNext());
			Size--;
			return true;
		} else
			// out of boundary(invalid index)
			return false;
	}

	// Clear the list by removing all elements
	public void clear() {
		Front = null;
		Size = 0;
	}

	// Find the index of the first occurrence of the specified element
	public int find(String element) {
		LocationNode current = Front;
		int index = 0;
		// if the element is a location
			// Iterates over locations
			while (current != null) {
				// If the location's name is same as the location in the list, the index will be
				// returned
				if ((current.getElement()).equalsIgnoreCase(element)) {
					return index;
				}
				current = current.getNext();
				index++;
			}
			// Element not found
			return -1;
	}

	// Method to insert a location to the list but sorted alphabetically
	public void insertLocationSorted(LocationNode newNode) {
		// The location wanted to be added
		String loc = newNode.getElement();

		// Check the existence of the location in the list
		if (find(loc) != -1) {
			return;
		}

		// If the list is empty or the new location comes before the first one
		// alphabetically
		if (Front == null || (Front.getElement()).compareToIgnoreCase((newNode.getElement())) >= 0) {
			newNode.setNext(Front);
			Front = newNode;
		} else {
			LocationNode current = Front;
			// Find the appropriate position to insert the new location
			while (current.getNext() != null
					&& (current.getNext().getElement()).compareToIgnoreCase((newNode.getElement())) < 0) {
				current = current.getNext();
			}
			// Insert the new location node at the appropriate position
			newNode.setNext(current.getNext());
			current.setNext(newNode);
		}
		// Update the Back reference if newNode is the last node
		if (newNode.getNext() == null) {
			Back = newNode;
		}
		// Increment the size of the list
		Size++;
	}

	// Method to retrieve the node that contains the target element
	public LocationNode findNode(String data) {
		LocationNode current = Front;
		// Iterates over locations
		while (current != null) {
			// If the location's name is same as the location in the list, return the node
			if ((current.getElement()).equalsIgnoreCase((data))) {
				return current; // return the node with the matching name
			}
			current = current.getNext();
		}
		// Node with the target element was not found
		return null;
	}

	// Method that prints the elements inside the list
	public void print() {
		LocationNode current = Front;
		while (current != null) {
			System.out.println(current.getElement());
			current = current.getNext();
		}
	}

	// Method that prints the elements inside the list from certain node
	public void print(LocationNode current) {
		if (current != null) {
			System.out.println(current.getElement());
			print(current.getNext());
		}
	}
}