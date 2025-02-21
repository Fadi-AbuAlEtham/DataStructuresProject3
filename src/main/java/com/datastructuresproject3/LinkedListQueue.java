package com.datastructuresproject3;

public class LinkedListQueue {

    private Node front, rear;
    private int size;

    // Constructor for initializing the queue
    public LinkedListQueue() {
        front = rear = null;
        size = 0;
    }

    // Method to add elements to the queue
    public void enQueue(Object element) {
        Node newNode = new Node(element);
        if (rear == null) {
            // If the queue is empty, both front and rear will point to the new node
            front = rear = newNode;
        } else {
            // Add the new node at the end of the queue and update rear
            rear.setNext(newNode);
            rear = newNode;
        }
        size++;
    }

    // Method to remove an element from the queue
    public Object deQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }
        Object element = front.getElement();
        front = front.getNext();

        // If the queue becomes empty after dequeuing
        if (front == null) {
            rear = null;
        }
        size--;
        return element;
    }
    
    public Object front() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }
        return front.getElement();
    }


    // Method to check if the queue is empty
    public boolean isEmpty() {
        return front == null;
    }

    // Method to check if the queue is full (not applicable for linked list
    // implementation)
    public boolean isFull() {
        return false; // A linked list queue is typically never full (limited only by memory)
    }

    // Method to get the size of the queue
    public int size() {
        return size;
    }
}
