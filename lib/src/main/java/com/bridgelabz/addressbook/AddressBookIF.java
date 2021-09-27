package com.bridgelabz.addressbook;

public interface AddressBookIF {
	public void addPerson(PersonDetails person , IOService type);
	public long readData(IOService type);
	public void searchByCity(String city,String firstName);
	public void searchByState(String state, String firstName);
	public void personsInCity(String city);
	public void personsInState(String State);
	public int countByCity(String city, IOService type);
	public int countByState(String state,IOService type);
	public void editPerson(String name);
	public void deletePerson(String name);
	public  void sortByFirstName();
	public  void sortByZip();
	public  void sortByCity();
	public  void sortByState();
}
