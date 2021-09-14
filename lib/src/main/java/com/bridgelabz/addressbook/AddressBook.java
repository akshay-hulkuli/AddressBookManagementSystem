package com.bridgelabz.addressbook;

import java.util.*; 

public class AddressBook {
	private ArrayList<PersonDetails> referenceBook = new ArrayList<PersonDetails>();
	
	public void addPerson() {
		Scanner sc = new Scanner(System.in);
		PersonDetails person1 = new PersonDetails();
		System.out.println("Enter Person details:");
		System.out.println("Enter firstName:");
		person1.setFirstName(sc.next());
		System.out.println("Enter SecondName:");
		person1.setLastName(sc.next());
		System.out.println("Enter Address:");
		person1.setAddress(sc.next());
		System.out.println("Enter City:");
		person1.setCity(sc.next());
		System.out.println("Enter State:");
		person1.setState(sc.next());
		System.out.println("Enter Pin code:");
		person1.setPinCode(sc.nextInt());
		System.out.println("Enter Phone nmber:");
		person1.setPhoneNumber(sc.next());
		System.out.println("Enter email:");
		person1.setEmail(sc.next());
		sc.close();
		referenceBook.add(person1);
		
	}
}