package com.bridgelabz.addressbook;

import java.util.*; 

public class AddressBook {
	private ArrayList<PersonDetails> referenceBook = new ArrayList<PersonDetails>();
	
	public void addPerson() {
		
		PersonDetails person1 = new PersonDetails();
		person1.setFirstName("Akshaya");
		person1.setLastName("HP");
		person1.setAddress("Hulkuli, Thirthahalli");
		person1.setCity("Shimoga");
		person1.setState("Karnataka");
		person1.setPinCode(577415);
		person1.setPhoneNumber("9482857473");
		person1.setEmail("akshay@gmail.com");
		
		referenceBook.add(person1);
	
	}
}