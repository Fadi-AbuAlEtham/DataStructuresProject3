package com.datastructuresproject3;

public class MartyrNode {
	private Martyr element; // store data
	private MartyrNode left; // left child
	private MartyrNode right; // right child
	private int height; // Height

	public MartyrNode(Martyr element) {
		this(element, null, null);
	}

	public MartyrNode(Martyr element, MartyrNode left, MartyrNode right) {
		this.element = element;
		this.left = left;
		this.right = right;
		this.height = 0;
	}

	public Martyr getElement() {
		return element;
	}

	public void setElement(Martyr element) {
		this.element = element;
	}

	public MartyrNode getLeft() {
		return left;
	}

	public void setLeft(MartyrNode left) {
		this.left = left;
	}

	public MartyrNode getRight() {
		return right;
	}

	public void setRight(MartyrNode right) {
		this.right = right;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "MartyrNode [element=" + element + ", left=" + left + ", right=" + right + ", height=" + height + "]";
	}

}