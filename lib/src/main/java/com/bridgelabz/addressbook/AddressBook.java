package com.bridgelabz.addressbook;

import java.util.*; 

public class AddressBook {
	private ArrayList<PersonDetails> referenceBook = new ArrayList<PersonDetails>();
	
	public void addPerson() {
		System.out.println("Enter Person details:");
		referenceBook.add(intake());
	}
	
	public void editPerson(String name) {
		PersonDetails person = null ;
		int i = 0;
		//Check entered person name
		while(i < referenceBook.size()) {
			if(referenceBook.get(i).getFirstName().equals(name)) {
				person = referenceBook.get(i);
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
		referenceBook.add(i,intake());
	}
	
	public void display() {
		Scanner sc = new Scanner(System.in);
		PersonDetails person = null; 
		System.out.println("Persons present in the address book:");
		for(int i=0; i<referenceBook.size();i++) {
			System.out.print(referenceBook.get(i).getFirstName()+"  ");
		}
		System.out.println();
		System.out.println("Enter name to see details");
		String name = sc.next();
		
		for(int i = 0;i < referenceBook.size();i++) {
			if(referenceBook.get(i).getFirstName().equals(name)) {
				person = referenceBook.get(i);
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
		for(int i=0;i<referenceBook.size();i++) {
			if(referenceBook.get(i).getFirstName().equals(name)) {
				referenceBook.remove(i);
				System.out.println("Deleted details of : "+name);
				return;
			}
		}
		System.out.println("Name not found");
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
