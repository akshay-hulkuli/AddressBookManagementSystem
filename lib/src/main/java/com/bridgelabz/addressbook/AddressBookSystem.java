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
			System.out.println("Select option 1: add user.  2: edit existing user.  3: display all users 4:Delete contact. 5: sortby name 6:Switch Address Book");
			option  = sc.nextInt();
			switch(option) {
				case 1 :
					addressBook.addPerson();
					break;
				case 2 :
					System.out.println("Enter the user name to edit");
					addressBook.editPerson(sc.next());
					break;
				case 3:
					addressBook.display();
					break;
				case 4:
					System.out.println("Enter name");
					addressBook.deletePerson(sc.next());
					break;
				case 5:
					System.out.println("after sorting");
					addressBook.sortByFirstName();
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
}