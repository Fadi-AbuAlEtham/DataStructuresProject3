package com.datastructuresproject3;

public class LinkedListStack {
	private int size; // number of elements in the stack
	private Node Front; // pointer to the top node

	public LinkedListStack() {
		// empty stack
		Front = null;
		size = 0;
	}

	public void push(Object element) {
		Node newNode;
		newNode = new Node(element);

		newNode.setNext(Front);
		Front = newNode;

		size++;// update size
	}

	public void pushSorted(Object element) {
		Node newNode = new Node(element);

		if (Front == null || ((String) element).compareToIgnoreCase(((String) Front.getElement())) < 0) {
			newNode.setNext(Front);
			Front = newNode;
		} else {
			Node current = Front;
			while (current.getNext() != null
					&& ((String) element).compareToIgnoreCase(((String) current.getNext().getElement())) > 0) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
		}

		size++; // update size
	}

	public Object pop() {

		if (!isEmpty()) {
			Node top = Front; // save reference
			Front = Front.getNext();// Remove first node
			size--;
			return top.getElement();// Return the element from the saved ref
		} else
			return null;

	}

	public Object peek() {
		// Return the top element without changing the stack
		if (!isEmpty())
			return Front.getElement();
		else
			return null;
	}

	public int Size() {
		return size;
	}

	public boolean isEmpty() {
		return (Front == null); // return true if the stack is empty
	}

	public static boolean isBalanced(String expression) {
		LinkedListStack stack = new LinkedListStack();

		for (char ch : expression.toCharArray()) {
			// Push any open brackets onto the stack
			if (ch == '(' || ch == '{' || ch == '[') {
				stack.push(ch);
			} else {
				// Check for closing brackets
				if (stack.isEmpty()) {
					// If there are no open brackets to match, it's unbalanced
					return false;
				} else {
					char top = (char) stack.pop(); // Cast needed since pop returns Object
					if (!correctFormat(top, ch)) {
						// If the brackets do not match, it's unbalanced
						return false;
					}
				}
			}
		}

		// The stack should be empty if all opening brackets had matching closing
		// brackets
		return stack.isEmpty();
	}

	private static boolean correctFormat(char beg, char end) {
		if ((beg == '(' && end == ')')) {
			return true;
		} else if ((beg == '{' && end == '}')) {
			return true;
		} else if ((beg == '[' && end == ']')) {
			return true;
		} else
			return false;
	}

	public int max() {
		LinkedListStack tempStack = new LinkedListStack(); // Create a temporary
															// stack

		int maxValue = (int) pop(); // Assume the top element is the maximum
		tempStack.push(maxValue); // Push the assumed maximum value to the
									// temporary stack

		while (!isEmpty()) {
			int nextValue = (int) pop();
			maxValue = Math.max(maxValue, nextValue); // Update max value if
														// next value is greater
			tempStack.push(nextValue); // Push the popped element to the
										// temporary stack
		}

		// Restore the original stack
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}

		return maxValue;
	}

	public int min() {
		LinkedListStack tempStack = new LinkedListStack(); // Create a temporary
															// stack

		int minValue = (int) pop(); // Assume the top element is the minimum
		tempStack.push(minValue); // Push the assumed minimum value to the
									// temporary stack

		while (!isEmpty()) {
			int nextValue = (int) pop();
			minValue = Math.min(minValue, nextValue); // Update max value if
														// next value is lower
			tempStack.push(nextValue); // Push the popped element to the
										// temporary stack
		}

		// Restore the original stack
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}

		return minValue;
	}

	public void printListReverse() {
		LinkedListStack tempStack = new LinkedListStack();

		while (!isEmpty()) {
			tempStack.push(pop());
		}

		while (!tempStack.isEmpty()) {
			Object obj = tempStack.pop();
			System.out.println(obj);
			push(obj);
		}
	}

	public void printList() {
		LinkedListStack tempStack = new LinkedListStack();

		// Push elements from the original stack to the temporary stack
		// while printing them
		while (!isEmpty()) {
			Object obj = pop();
			System.out.println(obj);
			tempStack.push(obj);
		}

		// Push back the elements from the temporary stack to the original stack
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}
	}

	public static String infixToPostfix(String infix) {
		LinkedListStack operatorStack = new LinkedListStack();
		String postfix = "";
		infix = infix.trim();
		char nextCharacter;

		for (int i = 0; i < infix.length(); i++) {
			nextCharacter = infix.charAt(i);

			switch (nextCharacter) {
			case '^':
				if (nextCharacter == '^') { // Check for double exponentiation
					postfix += operatorStack.pop(); // Pop the first ^
				}
				while (!operatorStack.isEmpty()
						&& precedence(nextCharacter) < precedence((char) operatorStack.peek())) {
					postfix += operatorStack.pop();
				}
				operatorStack.push(nextCharacter);
				break;

			case '+':
			case '-':
			case '*':
			case '/':
				while (!operatorStack.isEmpty()
						&& precedence(nextCharacter) <= precedence((char) operatorStack.peek())) {
					postfix += operatorStack.pop();
				}
				operatorStack.push(nextCharacter);
				break;

			case '(':
				operatorStack.push(nextCharacter);
				break;

			case ')':
				char topOperator = (char) operatorStack.pop();
				while (topOperator != '(') {
					postfix += topOperator;
					topOperator = (char) operatorStack.pop();
				}
				break;

			default:
				postfix += nextCharacter;
				break;
			}
		}

		while (!operatorStack.isEmpty()) {
			postfix += operatorStack.pop();
		}

		return postfix;
	}

	public static String postfixToInfix(String postfix) {
		LinkedListStack operatorStack = new LinkedListStack();
		LinkedListStack valueStack = new LinkedListStack();

		postfix = postfix.trim();
		char nextCharacter;

		for (int i = 0; i < postfix.length(); i++) {
			nextCharacter = postfix.charAt(i);

			switch (nextCharacter) {
			case '^':
			case '+':
			case '-':
			case '*':
			case '/':
				String operandTwo = (String) valueStack.pop();
				String operandOne = (String) valueStack.pop();
				String operator = String.valueOf(nextCharacter); // Convert char
																	// to String
				String infixExpression = "(" + operandOne + operator + operandTwo + ")";
				valueStack.push(infixExpression);
				break;

			case '(':
				operatorStack.push(String.valueOf(nextCharacter)); // Convert
																	// char to
																	// String
				break;

			case ')':
				while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
					String topOperator = (String) operatorStack.pop();
					operandTwo = (String) valueStack.pop();
					operandOne = (String) valueStack.pop();
					infixExpression = "(" + operandOne + topOperator + operandTwo + ")";
					valueStack.push(infixExpression);
				}
				operatorStack.pop(); // Remove the opening parenthesis
				break;

			default:
				valueStack.push(String.valueOf(nextCharacter)); // Push operand
																// as a String
				break;
			}
		}

		// After processing, the final infix expression is at the top of the
		// value stack
		return (String) valueStack.pop();
	}

	private static int precedence(char operator) {
		switch (operator) {
		case '^':
			return 3;
		case '*':
		case '/':
			return 2;
		case '+':
		case '-':
			return 1;
		default:
			return 0; // Assuming other characters have lowest precedence
		}
	}

	public boolean isPalindromeFadi() {
		boolean isPal = true;
		LinkedListStack tempStack = new LinkedListStack();
		LinkedListStack tempStack2 = new LinkedListStack();

		while (!isEmpty()) {
			Object obj = pop();
			tempStack.push(obj);
			tempStack2.push(obj);
		}

		while (!tempStack2.isEmpty()) {
			push(tempStack2.pop());
		}

		while (!isEmpty()) {
			Object obj = pop();
			tempStack2.push(obj);
			if (!obj.equals(tempStack.pop())) {
				isPal = false;
				break;
			}
			tempStack2.push(obj);
		}

		while (!tempStack2.isEmpty()) {
			push(tempStack2.pop());
		}

		return isPal;
	}

	public boolean isPalindromeFiras() {
		if (isEmpty()) {
			return true;
		}

		LinkedListStack temp = new LinkedListStack();
		LinkedListStack restore = new LinkedListStack();

		int halfSize = size / 2;
		for (int i = 0; i < halfSize; i++) {
			Object object = pop();
			temp.push(object);
			restore.push(object);
		}

		if (size % 2 == 1) {
			restore.push(pop());
		}

		boolean isPalindrome = true;
		while (!temp.isEmpty() && !isEmpty()) {
			Object firstHalf = temp.pop();
			Object secondHalf = pop();
			restore.push(secondHalf);
			if (!firstHalf.equals(secondHalf)) {
				isPalindrome = false;
			}
		}

		while (!restore.isEmpty()) {
			push(restore.pop());
		}

		return isPalindrome;
	}

	public void sortOddsFirst() {
		LinkedListStack evenStack = new LinkedListStack();
		LinkedListStack oddStack = new LinkedListStack();
		Integer temp;

		while (!isEmpty()) {
			temp = (Integer) pop();
			if (temp % 2 == 0) {
				evenStack.push(temp); // Push evens to temporary stack
			} else {
				oddStack.push(temp); // Push odds to oddStack
			}
		}

		// Push evens back to the original stack in their original order
		while (!evenStack.isEmpty()) {
			push(evenStack.pop());
		}

		// Push odd numbers on top of the evens
		while (!oddStack.isEmpty()) {
			push(oddStack.pop());
		}
	}

	public void sortEvenFirst() {
		LinkedListStack evenStack = new LinkedListStack();
		LinkedListStack oddStack = new LinkedListStack();
		Integer temp;

		while (!isEmpty()) {
			temp = (Integer) pop();
			if (temp % 2 == 0) {
				evenStack.push(temp); // Push evens to temporary stack
			} else {
				oddStack.push(temp); // Push odds to oddStack
			}
		}

		// Push odd numbers on top of the evens
		while (!oddStack.isEmpty()) {
			push(oddStack.pop());
		}

		// Push evens back to the original stack in their original order
		while (!evenStack.isEmpty()) {
			push(evenStack.pop());
		}
	}

	public void pushAndSortAscending(Object element) {
		LinkedListStack tempStack = new LinkedListStack();

		// Push elements from the original stack to the temporary stack until
		// the new element finds its correct position
		while (!isEmpty() && (int) element > (int) peek()) {
			tempStack.push(pop());
		}

		// Push the new element onto the original stack
		push(element);

		// Push back the elements from the temporary stack to the original stack
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}
	}

	public void sortAscending() {
		LinkedListStack tempStack = new LinkedListStack(); // Temporary stack

		while (!isEmpty()) {
			int temp = (int) pop(); // Assuming elements are integers for
									// sorting

			// Find the appropriate position for the element in the temporary
			// stack
			while (!tempStack.isEmpty() && (int) tempStack.peek() > temp) {
				push(tempStack.pop()); // Push larger elements from tempStack
										// back to original
			}

			tempStack.push(temp); // Push the current element to the temporary
									// stack
		}

		// Move elements back to the original stack in ascending order
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}
	}

	public void sortDescending() {
		LinkedListStack tempStack = new LinkedListStack(); // Temporary stack

		while (!isEmpty()) {
			int temp = (int) pop(); // Assuming elements are integers for
									// sorting

			// Find the appropriate position for the element in the temporary
			// stack
			while (!tempStack.isEmpty() && (int) tempStack.peek() < temp) {
				push(tempStack.pop()); // Push smaller elements from tempStack
										// back to original
			}

			tempStack.push(temp); // Push the current element to the temporary
									// stack
		}

		// Move elements back to the original stack in descending order
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}
	}

	public LinkedListStack reverse() {
		LinkedListStack tempStack = new LinkedListStack(); // Create an empty stack

		// Move all elements from the original stack to the temporary stack
		while (!isEmpty()) {
			tempStack.push(pop());
		}

		// Move elements back from the temporary stack to the original stack, reversing
		// the order
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}
		return tempStack;
	}

	public boolean find(Object obj) {
		LinkedListStack stack = new LinkedListStack();
		boolean found = false;

		while (!isEmpty()) {
			Object obje = pop();
			stack.push(obje);

			if (obje.equals(obj)) {
				found = true;
				break;
			}
		}

		while (!stack.isEmpty()) {
			push(stack.pop());
		}

		return found;
	}
}