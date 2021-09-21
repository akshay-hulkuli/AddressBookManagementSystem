package com.bridgelabz.addressbook;

import java.util.*;
import java.util.Scanner;
public class AddressBookSystem {
	public static List<AddressBook> addressBooks = new LinkedList<AddressBook>();
	public static String[] addressBookName = new String[10];
	
	
	public boolean checkName(String name) {
		for(int i=0;i<addressBooks.size();i++) {
			if(addressBookName[i].equals(name)) return true;
		}
		return false;
	}
	
	public void addressMenu(AddressBook addressBook) {
		Scanner sc = new Scanner(System.in);
		int option = 0;
		boolean exit = true;
		while(exit) {
			System.out.println("Select option 1: add user.  2: edit existing user.  3: display all users 4:Delete contact. 5: sortby name   8:Switch Address Book");
			option  = sc.nextInt();
			switch(option) {
				case 1 :
					PersonDetails person = intake(); 
					System.out.println(" select 1: add to list 2: add to txt file 3: add to csv file  4: add to json file");
					switch(sc.nextInt()) {
						case 1:
							addressBook.addPerson(person, IOService.LIST_DS_IO);
							break;
						case 2:
							addressBook.addPerson(person, IOService.TXT_FILE_IO);
							break;
						case 3:
							addressBook.addPerson(person, IOService.CSV_IO);
							break;
						case 4:
							addressBook.addPerson(person, IOService.JSON_IO);
							break;
					}
					
					break;
				case 2 :
					System.out.println("Enter the user name to edit");
					addressBook.editPerson(sc.next());
					break;
				case 3: 
					System.out.println(" select 1: display from list 2: display from txt file 3: display from CSV file 4: display from json file");
					switch(sc.nextInt()) {
						case 1:
							addressBook.readData(IOService.LIST_DS_IO);
							break;
						case 2:
							addressBook.readData(IOService.TXT_FILE_IO);
							break;
						case 3:
							addressBook.readData(IOService.CSV_IO);
							break;
						case 4:
							addressBook.readData(IOService.JSON_IO);
							break;
					}
					break;
				case 4:
					System.out.println("Enter name");
					addressBook.deletePerson(sc.next());
					break;
				case 5:
					System.out.println("Sort by 1:firstName 2:zipCode 3:city 4:state");
					switch(sc.nextInt()) {
						case 1:
							System.out.println("after sorting");
							addressBook.sortByFirstName();
							break;
						case 2:
							System.out.println("after sorting");
							addressBook.sortByZip();
							break;
						case 3:
							System.out.println("after sorting");
							addressBook.sortByCity();
							break;
						case 4:
							System.out.println("after sorting");
							addressBook.sortByState();
							break;
						default:
							System.out.println("worng entry");
					}
					break;
				default:
					exit = false;
					
			}
			System.out.println();
		}
	}
	
	public void searchByCity(String city, String name) {
		for(int i=0;i<addressBooks.size();i++) {
			addressBooks.get(i).searchByCity(city,name);
		}
	}
	
	public void searchByState(String state, String name) {
		for(int i=0;i<addressBooks.size();i++) {
			addressBooks.get(i).searchByState(state,name);
		}
	}
	
	public void personsInCity(String city) {
		System.out.println("Persons in city: "+city);
		for(int i=0;i<addressBooks.size(); i++) {
			addressBooks.get(i).personsInCity(city);
		}
	}
	
	public void personsInState(String State) {
		System.out.println("Persons in state: "+State);
		for(int i=0;i<addressBooks.size(); i++) {
			addressBooks.get(i).personsInState(State);
		}
	}
	
	public void countByCity(String city) {
		int count =0;
		for(int i=0;i<addressBooks.size();i++) {
			count+= addressBooks.get(i).countByCity(city);
		}
		System.out.println("the number person in city : "+city+" is : "+count);
	}
	public void countByState(String State) {
		int count =0;
		for(int i=0;i<addressBooks.size();i++) {
			count+= addressBooks.get(i).countByState(State);
		}
		System.out.println("the number person in city : "+State+" is : "+count);
	}
	
	private static PersonDetails intake() {
		System.out.println("Enter Person details:");
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
}