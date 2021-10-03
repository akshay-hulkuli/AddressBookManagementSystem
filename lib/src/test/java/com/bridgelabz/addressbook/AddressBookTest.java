package com.bridgelabz.addressbook;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;

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
	
	@Test
	public void givenAContact_WhenAddedToCSVFile_ShouldReturnCorrectSize() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.CSV_IO);
		addressBook.addPerson(person2, IOServiceEnum.CSV_IO);
		long size = 0;
		try {
			size = Files.lines(Paths.get("AddressBook-file.csv")).count();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(2,size-1);
	}
	
	@Test
	public void whenCalled_ReadFromCSVMethod_ShouldPrintFile() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.CSV_IO);
		addressBook.addPerson(person2, IOServiceEnum.CSV_IO);
		long size  = addressBook.readData(IOServiceEnum.CSV_IO);
		Assert.assertEquals(2,size);
	}
	
	@Test
	public void givenAContact_WhenAddedToJSONFile_ShouldReturnCorrectSize() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.JSON_IO);
		addressBook.addPerson(person2, IOServiceEnum.JSON_IO);
		long count =0;
		try {
			Gson gson = new Gson();
			BufferedReader br = new BufferedReader(new FileReader("AddressBook-file.json"));
			PersonDetails[] usrObj = gson.fromJson(br, PersonDetails[].class);
			List<PersonDetails> csvUSerList = Arrays.asList(usrObj);
			count = csvUSerList.size();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(2,count);
	}
	
	@Test
	public void whenCalled_ReadFromJsonMethod_ShouldPrintFile() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();	
		addressBook.addPerson(person1, IOServiceEnum.JSON_IO);
		addressBook.addPerson(person2, IOServiceEnum.JSON_IO);
		long size  = addressBook.readData(IOServiceEnum.JSON_IO);
		Assert.assertEquals(2,size);
	}
	
	@Test
	public void givenAddressBookInDB_WhenRetrived_ShouldReturnCount()
	{
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		long count  = addressBook.readData(IOServiceEnum.DB_IO);
		Assert.assertEquals(6, count);
	}
	
	@Test
	public void givenAContact_WhenInserted_ShouldGetUpdatedSize()
	{
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		long initialSize  = addressBook.readData(IOServiceEnum.DB_IO);
		addressBook.addPerson(person1, IOServiceEnum.DB_IO);
		long updatedSize = addressBook.readData(IOServiceEnum.DB_IO);
		Assert.assertEquals(initialSize+1, updatedSize);
	}
	
	@Test
	public void givenAAddressBookName_WhenInserted_ShouldReturnOne()
	{
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		int result = addressBook.insertAddressBook("address_book3");
		Assert.assertTrue(result==1);
	}
	
	@Test
	public void givenContactIdAndPhonenumber_WhenUpdated_shouldReturnOne()
	{
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		addressBook.readData(IOServiceEnum.DB_IO);
		addressBook.updatePhonenumberOfContact("4568561236", 1);
		boolean result = addressBook.checkAddressBookInsyncWithDB(1);
		Assert.assertTrue(result);
	}

	@Test
	public void givenDateRange_FindContactsAddedInThatRange_ShouldReturnCount() {
		AddressBookServiceImpl addressBookService = new AddressBookServiceImpl();
		String date1 = "2019-01-01";
		String date2 = "2020-09-09";
		List<AddressBookData> contactList = addressBookService.getEmployeeInADateRange(date1,date2);
		System.out.println(contactList);
		sum =0;
		contactList.stream().forEach(addressBook -> {
			addressBook.getContactList().stream().forEach(contact -> sum += contact==null?0:1);
		});
		Assert.assertEquals(3, sum);
	}
	
	@Test
	public void givenCity_FindContactsInThatCity_ShouldReturnCount() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		HashMap<String,ArrayList<String>> contactsByAddressBook = addressBook.getContactsByCity("Bengaluru");
		System.out.println(contactsByAddressBook);
		Assert.assertEquals(2, contactsByAddressBook.get("address_book1").size());
	}
	
	@Test
	public void givenState_FindContactsInThatState_ShouldReturnCount() {
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		HashMap<String,ArrayList<String>> contactsByAddressBook = addressBook.getContactsByState("Maharashtra");
		System.out.println(contactsByAddressBook);
		Assert.assertEquals(2, (contactsByAddressBook.get("address_book1").size()+contactsByAddressBook.get("address_book2").size()));
	}
	
	@Test
	public void givenAWrongQuery_WhenExecuted_ShouldThrowCustomException()
	{
		DBServiceProvider databaseIO = DBServiceProvider.getInstance();
		String sql = "SELECT * FROM random_table";
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(AddressBookException.class);
			databaseIO.demoQuery(sql);
		}
		catch( AddressBookException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void givenAWorngContactData_WhenInserted_ShouldThrowCustomException()
	{
		AddressBookServiceImpl addressBook = new AddressBookServiceImpl();
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(AddressBookException.class);
			addressBook.addPerson(person2, IOServiceEnum.DB_IO);
		}
		catch( AddressBookException e) {
			e.printStackTrace();
		}
	}
	
}
