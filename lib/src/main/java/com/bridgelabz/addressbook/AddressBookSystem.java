package com.bridgelabz.addressbook;

import java.util.Scanner;
public class AddressBookSystem {
	public static void main(String[] args) {
		System.out.println("****** Welcome to address book program ! ******");
		Scanner sc = new Scanner(System.in);
		AddressBook addressBook = new AddressBook();
		int option = 0;
		boolean exit = true;
		while(exit) {
			System.out.println("Select option 1: add user.  2: edit existing user.  3: display all users 4:Delete contact. 5:Exit");
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
				default:
					exit = false;
					
			}
			System.out.println();
		}
		sc.close();
		
	}
}