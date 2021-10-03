package com.bridgelabz.addressbook;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressBookTest {
	PersonDetails person1;
	PersonDetails person2;
	Contacts contact;
	Address address;
	
	static int  sum =0;
	@Before
	public void initialize() {
		
		contact = new Contacts();
		contact.setFirstName("arun");
		contact.setLastName("anand");
		contact.setPhoneNumber("6352417895");
		contact.setEmail("akshay@gmail.com");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2019";
		LocalDate date_added = LocalDate.parse(date, formatter);
		contact.setDateAdded(date_added);
		
		address = new Address();
		address.setAddress("hulkuli");
		address.setCity("Thirathalli");
		address.setState("karnataka");
		address.setPinCode(577415);
		
		HashMap<String,ArrayList<String>> nameTypeMap = new HashMap<String, ArrayList<String>>();
		nameTypeMap.put("address_book2", new ArrayList<String>(Arrays.asList("family")));
		nameTypeMap.put("address_book1", new ArrayList<String>(Arrays.asList("profession")));
		
		person1 = new PersonDetails(contact, address, nameTypeMap);
		
		
		
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
	public void givenAContact_WhenAddedToList_ShouldReturnCorrectSize() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.LIST_DS_IO);
		addressBook.addPerson(person2, IOServiceEnum.LIST_DS_IO);
		Assert.assertEquals(2,addressBook.referenceBook.size());
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
	public void whenCalled_ReadFromListMethod_ShouldPrintList() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.LIST_DS_IO);
		addressBook.addPerson(person2, IOServiceEnum.LIST_DS_IO);
		long size  = addressBook.readData(IOServiceEnum.LIST_DS_IO);
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
