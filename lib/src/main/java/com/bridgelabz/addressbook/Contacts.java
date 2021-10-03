package com.bridgelabz.addressbook;

import java.time.LocalDate;

public class Contacts {
	private int contactId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private LocalDate dateAdded;
	private Address addressObj;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(LocalDate date_added) {
		this.dateAdded = date_added;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public Address getAddressObj() {
		return addressObj;
	}
	public void setAddressObj(Address addressObj) {
		this.addressObj = addressObj;
	}
	
	@Override
    public boolean equals(Object object) {
    	if(object == this)  return true;
    	if(!(object instanceof Contacts)) return false;
    	Contacts that = (Contacts) object;
    	return (this.firstName.equals(that.firstName) && this.lastName.equals(that.lastName) && this.phoneNumber.equals(that.phoneNumber) && 
    			this.email.equals(that.email) && this.addressObj.equals(that.addressObj));
    }
}
