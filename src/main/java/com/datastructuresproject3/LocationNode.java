package com.datastructuresproject3;


public class LocationNode {
	// Attributes of Martyr
	private String element;
	private LocationNode next;
	private LocationLinkedList head;

	// Constructor with parameters
	public LocationNode(String element) {
		this(element, null, null);
	}

	// Constructor with parameters
	public LocationNode(String element, LocationNode next, LocationLinkedList head) {
		this.element = element;
		this.next = next;
		this.head = head;
	}

	// Getter method for retrieving the element
	public String getElement() {
		return element;
	}

	// Setter method for setting the element
	public void setElement(String element) {
		this.element = element;
	}

	// Getter method for retrieving the next
	public LocationNode getNext() {
		return next;
	}

	// Setter method for setting the next
	public void setNext(LocationNode next) {
		this.next = next;
	}

	// Getter method for retrieving the head
	public LocationLinkedList getHead() {
		return head;
	}

	// Setter method for setting the head
	public void setHead(LocationLinkedList head) {
		this.head = head;
	}

	// Override toString method to provide a meaningful string representation of the
	// LocationNode object
	@Override
	public String toString() {
		return "SingleNode [element=" + element + ", next=" + next + ", head=" + head + "]";
	}
}
