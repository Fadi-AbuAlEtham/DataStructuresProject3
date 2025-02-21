package com.datastructuresproject3;

public class MartyrDate implements Comparable<MartyrDate> {
	// Attributes of MartyrDate
	private String date;
	private MartyrAvlTree martyrTree;

	public MartyrDate() {
		martyrTree = new MartyrAvlTree();
	}

	public MartyrDate(String date) {
		this.date = date;
		martyrTree = new MartyrAvlTree();
	}

	public MartyrDate(String date, MartyrAvlTree martyrTree) {
		super();
		this.date = date;
		this.martyrTree = martyrTree;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public MartyrAvlTree getMartyrTree() {
		return martyrTree;
	}

	public void setMartyrTree(MartyrAvlTree martyrTree) {
		this.martyrTree = martyrTree;
	}

	@Override
	public String toString() {
		return "MartyrDate [date=" + date + ", martyrList=" + martyrTree + "]";
	}

	@Override
	public int compareTo(MartyrDate o) {
		// Split the date strings into components (month, day, year)
		String[] dateComponents = this.date.split("/");
		String[] otherComponents = o.getDate().split("/");

		// Parse the components into integers for comparison
		int thisMonth = Integer.parseInt(dateComponents[0]);
		int thisDay = Integer.parseInt(dateComponents[1]);
		int thisYear = Integer.parseInt(dateComponents[2]);

		int otherMonth = Integer.parseInt(otherComponents[0]);
		int otherDay = Integer.parseInt(otherComponents[1]);
		int otherYear = Integer.parseInt(otherComponents[2]);

		// Compare years first
		if (thisYear < otherYear) {
			return -1;
		} else if (thisYear > otherYear) {
			return 1;
		} else {
			// If years are the same, compare months
			if (thisMonth < otherMonth) {
				return -1;
			} else if (thisMonth > otherMonth) {
				return 1;
			} else {
				// If months are the same, compare days
				if (thisDay < otherDay) {
					return -1;
				} else if (thisDay > otherDay) {
					return 1;
				} else {
					// Dates are equal
					return 0;
				}
			}
		}
	}

}
