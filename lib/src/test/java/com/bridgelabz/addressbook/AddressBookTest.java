package com.bridgelabz.addressbook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AddressBookTest {
	PersonDetails person1;
	PersonDetails person2;
	Contacts contact;
	Address address;
	
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
		person2.setCity("bengaluru");
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
	public void givenANullContact_WhenAddedToList_ShouldThrowError() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.LIST_DS_IO);
		PersonDetails person3 = new PersonDetails();
		try {
			addressBook.addPerson(person3, IOServiceEnum.LIST_DS_IO);
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(AddressBookException.class);
		}
		catch(AddressBookException e) {
			e.printStackTrace();
		}
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
	public void whenCityIsGiven_ShouldReturnTheCount() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		addressBook.addPerson(person1, IOServiceEnum.LIST_DS_IO);
		addressBook.addPerson(person2, IOServiceEnum.LIST_DS_IO);
		long count  = addressBook.countByCity("bengaluru", IOServiceEnum.LIST_DS_IO);
		Assert.assertEquals(1,count);
	}
	
	@Test
	public void whenStateIsGiven_ShouldReturnTheCount() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		addressBook.addPerson(person1, IOServiceEnum.LIST_DS_IO);
		addressBook.addPerson(person2, IOServiceEnum.LIST_DS_IO);
		long count  = addressBook.countByState("karnataka", IOServiceEnum.LIST_DS_IO);
		Assert.assertEquals(2,count);
	}
	
	@Test
	public void whenNullValueForCityIsGiven_ShouldThrowException() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		addressBook.addPerson(person1, IOServiceEnum.LIST_DS_IO);
		addressBook.addPerson(person2, IOServiceEnum.LIST_DS_IO);
		try {
			long count  = addressBook.countByCity("bengaluru", IOServiceEnum.LIST_DS_IO);
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(AddressBookException.class);
		}
		catch (AddressBookException e) {
			e.printStackTrace();
		}
	}
	
	
}
