package com.datastructuresproject3;

public class Node {
	// Attributes of Martyr
	private Object element;
	private Node next;

	// Constructor with parameters
	public Node(Object element) {
		this(element, null);
	}

	// Constructor with parameters
	public Node(Object element, Node next) {
		this.element = element;
		this.next = next;
	}

	// Getter method for retrieving the element
	public Object getElement() {
		return element;
	}

	// Setter method for setting the element
	public void setElement(Object element) {
		this.element = element;
	}

	// Getter method for retrieving the next
	public Node getNext() {
		return next;
	}

	// Setter method for setting the next
	public void setNext(Node next) {
		this.next = next;
	}

	// Override toString method to provide a meaningful string representation of the
	// MartyrNode object
	@Override
	public String toString() {
		return "SingleNode [element=" + element + ", next=" + next + "]";
	}
}
