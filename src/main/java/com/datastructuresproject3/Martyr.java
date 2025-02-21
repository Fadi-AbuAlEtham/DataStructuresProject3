package com.datastructuresproject3;

public class Martyr implements Comparable<Martyr> {

	// Attributes of Martyr
	private String name;
	private String date;
	private byte age;
	private String location;
	private String district;
	private char gender;

	// Default constructor
	public Martyr() {

	}

	// Constructor with parameters
	public Martyr(String name, byte age, char gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	// Constructor with parameters
	public Martyr(String name, String date, byte age, String location, String district, char gender) {
		this.name = name;
		this.date = date;
		this.age = age;
		this.location = location;
		this.district = district;
		this.gender = gender;
	}

	// Getter method for retrieving the name
	public String getName() {
		return name;
	}

	// Setter method for setting the name
	public void setName(String name) {
		this.name = name;
	}

	// Getter method for retrieving the date
	public String getDate() {
		return date;
	}

	// Setter method for setting the date
	public void setDate(String date) {
		this.date = date;
	}

	// Getter method for retrieving the age
	public byte getAge() {
		return age;
	}

	// Setter method for setting the age
	public void setAge(byte age) {
		this.age = age;
	}

	// Getter method for retrieving the location
	public String getLocation() {
		return location;
	}

	// Setter method for setting the location
	public void setLocation(String location) {
		this.location = location;
	}

	// Getter method for retrieving the district
	public String getDistrict() {
		return district;
	}

	// Setter method for setting the district
	public void setDistrict(String district) {
		this.district = district;
	}

	// Getter method for retrieving the gender
	public char getGender() {
		return gender;
	}

	// Setter method for setting the gender
	public void setGender(char gender) {
		this.gender = gender;
	}

	// Override toString method to provide a meaningful string representation of the
	// Martyr object
	@Override
	public String toString() {
		return "Martyr name: " + name + ", date: " + date + ", age: " + age + ", location: " + location + ", district: "
				+ district + ", gender: " + gender;
	}

	@Override
	public int compareTo(Martyr other) {
		int districtComparison = this.district.compareToIgnoreCase(other.district);
		if (districtComparison != 0) {
			return districtComparison;
		} else {
			return this.name.compareToIgnoreCase(other.name);
		}
	}
}
