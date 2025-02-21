package com.datastructuresproject3;

public class HashEntry {
	private int index;
	private MartyrDate key;
	private char status;
	private MartyrAvlTree head;

	public HashEntry() {
	}

	public HashEntry(MartyrDate key, char status) {
		this.key = key;
		this.status = status;
		head = null;
	}

	public HashEntry(int index, String date, char status) {
		this.index = index;
		this.key = new MartyrDate(date);
		this.status = status;
		head = null;
	}

	public HashEntry(MartyrDate key, char status, MartyrAvlTree head) {
		this.key = key;
		this.status = status;
		this.head = head;
	}

	public HashEntry(MartyrDate key2) {
		this.key = key2;
		this.status = 'E';
		this.head = null;
	}

	public MartyrDate getKey() {
		return key;
	}

	public void setKey(MartyrDate key) {
		this.key = key;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public MartyrAvlTree getHead() {
		return head;
	}

	public void setHead(MartyrAvlTree head) {
		this.head = head;
	}

	public int getIndex() {
		return index;
	}

	public String getDate() {
		return key.getDate();
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "HashEntry [key=" + key + ", status=" + status + ", head=" + head + "]";
	}

}