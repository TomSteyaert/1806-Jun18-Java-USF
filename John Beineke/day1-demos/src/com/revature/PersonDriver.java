package com.revature;

import com.revature.models.Person;

public class PersonDriver {

	public static void main(String[] args) {
		
		// create a person object
		// Default Constructor
		Person myPerson = new Person();
		System.out.println(myPerson.firstName);
		System.out.println(myPerson.calculateBMI());
	}
}
