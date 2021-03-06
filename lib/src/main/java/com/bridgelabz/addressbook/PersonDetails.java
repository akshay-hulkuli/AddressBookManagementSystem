
package com.bridgelabz.addressbook;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.bean.CsvBindByName;
import com.bridgelabz.dto.Contacts;
import com.bridgelabz.dto.Address;


public class PersonDetails {
	private int id;
	@CsvBindByName
    private String firstName;
	@CsvBindByName
    private String lastName;
	@CsvBindByName
    private String address;
	@CsvBindByName
    private String city;
	@CsvBindByName
    private String state;
	@CsvBindByName
    private String phoneNumber;
	@CsvBindByName
    private int pinCode;
	@CsvBindByName
    private String email;
	HashMap<String, ArrayList<String>> addressBookNameTypeMap = null;
    private LocalDate dateAdded;
    
    public PersonDetails() {}
    
    public PersonDetails( Contacts contact, Address address, HashMap<String, ArrayList<String>> addressBookNameTypeMap) {
    	this.firstName = contact.getFirstName();
    	this.lastName = contact.getLastName();
    	this.address = address.getAddress();
    	this.city = address.getCity();
    	this.state = address.getState();
    	this.phoneNumber = contact.getPhoneNumber();
    	this.pinCode = address.getPinCode();
    	this.email = contact.getEmail();
    	this.dateAdded = contact.getDateAdded();
    	this.addressBookNameTypeMap = addressBookNameTypeMap;
    }
    
    //setters
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
    public void setAddress(String address) {
    	this.address = address;
    }
    public void setCity(String city) {
    	this.city = city;
    }
    public void setState(String state) {
    	this.state = state;
    }
    public void setPhoneNumber(String number) {
    	this.phoneNumber = number;
    }
    public void setPinCode(int pinCode) {
    	this.pinCode = pinCode;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    
    
    
    //getters
    public String getFirstName() {
    	return this.firstName;
    }
    public String getLastName() {
    	return this.lastName;
    }
    public String getAddress() {
    	return this.address;
    }
    public String getCity() {
    	return this.city;
    }
    public String getState() {
    	return this.state;
    }
    public String getPhoneNumber() {
    	return this.phoneNumber;
    }
    public int getPinCode() {
    	return this.pinCode;
    }
    public String getEmail() {
    	return this.email;
    }

    @Override
    public boolean equals(Object object) {
    	if(object == this)  return true;
    	if(!(object instanceof PersonDetails)) return false;
    	PersonDetails person1 = (PersonDetails) object;
    	return (this.firstName.equals(person1.firstName)  && this.lastName.equals(person1.lastName) && this.address.equals(person1.address) && this.city.equals(person1.city)
    			&& this.state.equals(person1.state) && this.phoneNumber.equals(person1.phoneNumber) && this.pinCode == person1.pinCode && this.email.equals(person1.email));
    }
    
    @Override
    public String toString() {
    	return "firstName: "+this.getFirstName()+", SecondName: "+ this.getLastName()+", Address: "+ this.getAddress() + ", City: "+this.getCity() +", State: "+this.getState()+
		", Pincode: "+this.getPinCode()+", Phone number: "+this.getPhoneNumber()+", email: "+this.getEmail()+
		((addressBookNameTypeMap == null)?("\n"):(" addressBook name and types: "+this.addressBookNameTypeMap)+ "\n");
    }
	public HashMap<String, ArrayList<String>> getAddressBookNameTypeMap() {
		return addressBookNameTypeMap;
	}
	public void setAddressBookNameTypeMap(HashMap<String, ArrayList<String>> addressBookNameTypeMap) {
		this.addressBookNameTypeMap = addressBookNameTypeMap;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(LocalDate date_added) {
		this.dateAdded = date_added;
	}
    
}