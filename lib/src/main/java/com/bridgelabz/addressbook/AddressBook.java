package com.bridgelabz.addressbook;

import java.util.*; 

public class AddressBook {
	private PersonDetails[] referenceBook = new PersonDetails[20];
	private int numOfContacts = 0;
	
	public void addPerson() {
		System.out.println("Enter Person details:");
		
		Scanner sc = new Scanner(System.in);
		PersonDetails person1 = new PersonDetails();
		
		System.out.println("Enter firstName:");
		String name = sc.next();
		for(int i=0; referenceBook[i]!= null;i++) {
			if(referenceBook[i].getFirstName().equals(name)) {
				System.out.println("the name is already taken");
				return;
			}
		}
		
		person1.setFirstName(name);
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
		
		referenceBook[numOfContacts] = person1;
		numOfContacts++;
		
	}
	
	public void editPerson(String name) {
		PersonDetails person = null ;
		int i = 0;
		//Check entered person name
		while(referenceBook[i]!= null) {
			if(referenceBook[i].getFirstName().equals(name)) {
				person = referenceBook[i];
				break;
			}
			i++;
		}
		// if name not present return
		if(person == null) {
			System.out.println("name not found!");
			return;
		}
		
		System.out.println("Changing details, Enter new details  of "+name);
		referenceBook[i] = intake();
	}
	
	public void display() {
		Scanner sc = new Scanner(System.in);
		PersonDetails person = null; 
		System.out.println("Persons present in the address book:");
		for(int i=0; referenceBook[i] != null;i++) {
			System.out.print(referenceBook[i].getFirstName()+"  ");
		}
		System.out.println();
		System.out.println("Enter name to see details");
		String name = sc.next();
		
		for(int i = 0;referenceBook[i]!= null;i++) {
			if(referenceBook[i].getFirstName().equals(name)) {
				person = referenceBook[i];
				break;
			}
		}
		if(person == null) {
			System.out.println("name not found!");
			return;
		}
		
		output(person);
		
	}
	
	
	public void deletePerson(String name) {
		int i=0;
		for(i=0;referenceBook[i]!=null;i++) {
			if(referenceBook[i].getFirstName().equals(name)) {
				break;
			}
		}
		if(i==numOfContacts) {
		System.out.println("Name not found");
		return;
		}
		while(referenceBook[i+1]!= null) {
			referenceBook[i] = referenceBook[i+1];
			i++;
		}
		referenceBook[i] = null;
		System.out.println("Deleted details of : "+ name);
	}
	
	
	
	
	private static PersonDetails intake() {
		Scanner sc = new Scanner(System.in);
		PersonDetails person1 = new PersonDetails();
		
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
		return person1;
	}
	
	private static void output(PersonDetails person) {
		System.out.println("Enter firstName : "+person.getFirstName());
		System.out.println("Enter SecondName : "+ person.getLastName());
		System.out.println("Enter Address : "+ person.getAddress());
		System.out.println("Enter City : "+person.getCity());
		System.out.println("Enter State : "+person.getState());
		System.out.println("Enter Pin code : "+person.getPinCode());
		System.out.println("Enter Phone nmber : "+person.getPhoneNumber() );
		System.out.println("Enter email : "+person.getEmail());
	}
	
}