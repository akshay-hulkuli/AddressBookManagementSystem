package com.bridgelabz.addressbook;

public interface AddressBookServiceIF {
	public void addPerson(PersonDetails person , IOServiceEnum type);
	public long readData(IOServiceEnum type);
	public long searchByCity(String city,String firstName);
	public long searchByState(String state, String firstName);
	public void personsInCity(String city);
	public void personsInState(String State);
	public int countByCity(String city, IOServiceEnum type);
	public int countByState(String state,IOServiceEnum type);
	public void editPerson(String name);
	public void deletePerson(String name);
	public  void sortByFirstName();
	public  void sortByZip();
	public  void sortByCity();
	public  void sortByState();
}
