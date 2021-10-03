package com.bridgelabz.addressbook;

import java.util.List;

public class AddressBookData {
	private String addressBookName;
	private String addressBookType;
	private List<Contacts> contactList;
	
	public AddressBookData(String name, String type, List<Contacts> contacts) {
		this.addressBookName = name;
		this.addressBookType = type;
		this.contactList = contacts;
	}
	
	public String getAddressBookName() {
		return addressBookName;
	}
	public void setAddressBookName(String addressBookName) {
		this.addressBookName = addressBookName;
	}
	public String getAddressBookType() {
		return addressBookType;
	}
	public void setAddressBookType(String addressBookType) {
		this.addressBookType = addressBookType;
	}
	public List<Contacts> getContactList() {
		return contactList;
	}
	public void setContactList(List<Contacts> contactList) {
		this.contactList = contactList;
	}
	
	@Override
	public String toString() {
		return "\n AddressBookName: "+this.addressBookName+" AddressBookType: "+ this.addressBookType+" ContactList: "+this.contactList; 
	}
	
	
	@Override
    public boolean equals(Object object) {
    	if(object == this)  return true;
    	if(!(object instanceof AddressBookData)) return false;
    	AddressBookData that = (AddressBookData) object;
    	return (this.addressBookName.equals(that.addressBookName) && this.addressBookType.equals(that.addressBookType) 
    			&& this.contactList.equals(that.contactList));
    }
}
