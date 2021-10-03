package com.bridgelabz.addressbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class AddressBookJsonTest {
	PersonDetails person1;
	PersonDetails person2;
	
	@Before
	public void initailize() {
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
}
