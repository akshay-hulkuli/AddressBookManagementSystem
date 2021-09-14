package com.bridgelabz.addressbook;

import java.util.Scanner;

public class AddressBookMain {

	public static void main(String[] args) {
		AddressBookSystem addressBookSystem = new AddressBookSystem();
		int numOfBooks =0;
		
		System.out.println("****** Welcome to address book program ! ******");
		Scanner sc = new Scanner(System.in);
		
		AddressBook currentBook;
		boolean exit1 = true;
		while(exit1) {
			System.out.println("Select option 1:Add address Book 2:open Address Book 3:search by city or state  4 : display users in city or state 5:exit");
			switch(sc.nextInt()) {
				case 1: 
					System.out.println("Enter the address book name");
					String name = sc.next();
					currentBook  = new AddressBook();
					addressBookSystem.addressBooks.add(currentBook);
					addressBookSystem.addressBookName[numOfBooks] = name;
					numOfBooks++;
					break;
				case 2:
					System.out.println("The Address books available :");
					for(int i=0;i<numOfBooks;i++) {
						System.out.println(addressBookSystem.addressBookName[i]);
					}
					System.out.println("Enter the name");
					String book = sc.next();
					int i =0;
					for(i=0;i<numOfBooks;i++) {
						if(addressBookSystem.addressBookName[i].equals(book)) break;
					}
					if(i == numOfBooks) {
						System.out.println("name Not Found");
						break;
					}
					currentBook = addressBookSystem.addressBooks.get(i);
					addressBookSystem.addressMenu(currentBook);
					break;
				case 3:
					System.out.println("Select 1:search by city 2: search by state");
					int option = sc.nextInt();
					if(option == 1) {
						System.out.println("enter city");
						String city = sc.next();
						System.out.println("enter firstName");
						String firstName  = sc.next();
						addressBookSystem.searchByCity(city,firstName);
					}
					else {
						System.out.println("enter state");
						String state = sc.next();
						System.out.println("enter firstName");
						String firstName  = sc.next();
						addressBookSystem.searchByState(state,firstName);
					}
					break;
				case 4:
					System.out.println("Select 1: display persons in a city 2: display persons in a state");
					if(sc.nextInt() == 1) {
						System.out.println("Enter city name");
						addressBookSystem.personsInCity(sc.next());
					}
					else {
						System.out.println("Enter state name");
						addressBookSystem.personsInState(sc.next());
					}
					break;
				default:
					exit1 = false;
			
			}
		}
		
		
		
		sc.close();

	}

}