package com.bridgelabz.addressbook;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressBookTxtFileTest {
	PersonDetails person1;
	PersonDetails person2;
	
	static int  sum =0;
	@Before
	public void initialize() {
		person1  = new PersonDetails();
		person1.setFirstName("akshay");
		person1.setLastName("hulkuli");
		person1.setAddress("hulkuli");
		person1.setCity("Thirathalli");
		person1.setState("karnataka");
		person1.setPinCode(577415);
		person1.setPhoneNumber("6352417895");
		person1.setEmail("akshay@gmail.com");
		
		person2 = new PersonDetails();
		person2.setFirstName("ankith");
		person2.setLastName("Kumar");
		person2.setAddress("padbhanabhanagar");
		person2.setCity("bengalurur");
		person2.setState("karnataka");
		person2.setPinCode(560070);
		person2.setPhoneNumber("6352417897");
		person2.setEmail("ankith@gmail.com");
	}
	@Test
	public void givenAContact_WhenAddedToFile_ShouldReturnCorectSize() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.TXT_FILE_IO);
		addressBook.addPerson(person2, IOServiceEnum.TXT_FILE_IO);
		long size = 0;
		try {
			size = Files.lines(Paths.get("AddressBook-file.txt")).count();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(2,size);
	}
	

	@Test
	public void whenCalled_ReadFromFileMethod_ShouldPrintFile() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.TXT_FILE_IO);
		addressBook.addPerson(person2, IOServiceEnum.TXT_FILE_IO);
		long size  = addressBook.readData(IOServiceEnum.TXT_FILE_IO);
		Assert.assertEquals(2,size);
	}
	
}
