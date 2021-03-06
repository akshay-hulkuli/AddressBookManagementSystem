package com.bridgelabz.addressbook;

public class Address {
	private int contactId;
	private String address;
	private String state;
	private String city;
	private int pinCode;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPinCode() {
		return pinCode;
	}
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	@Override
    public boolean equals(Object object) {
    	if(object == this)  return true;
    	if(!(object instanceof Address)) return false;
    	Address that = (Address) object;
    	return (this.address.equals(that.address) && this.state.equals(that.state) && this.city.equals(that.city) && this.pinCode == that.pinCode);
    }
	
}
