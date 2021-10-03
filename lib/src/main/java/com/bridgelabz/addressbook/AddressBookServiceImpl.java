package com.bridgelabz.addressbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*; 
import java.util.function.Predicate;


public class AddressBookServiceImpl implements AddressBookServiceIF {
	public static String CSV_FILE_NAME = "AddressBook-file.csv";
	public static String TXT_FILE_NAME = "AddressBook-file.txt";
	public static String JSON_FILE_NAME = "AddressBook-file.json";
	public static ArrayList<PersonDetails> referenceBook; 
	public static ArrayList<AddressBookData> addressBookList;
	public  HashMap<String, ArrayList<PersonDetails>> personsByCity = new HashMap<String, ArrayList<PersonDetails>>();
	public  HashMap<String, ArrayList<PersonDetails>> personsByState = new HashMap<String, ArrayList<PersonDetails>>();
	private int numOfContacts = 0;
	static long count = 0;
	private DBServiceProvider addressBookDBService;
	public AddressBookServiceImpl() {
		clearCSV();
		referenceBook = new ArrayList<PersonDetails>();
		addressBookDBService = DBServiceProvider.getInstance();
	}
	
	public void addPerson(PersonDetails person , IOServiceEnum type) {
		if(type.equals(IOServiceEnum.LIST_DS_IO)) {
			boolean isDuplicate = referenceBook.stream().anyMatch(contact -> person.equals(contact));
			if(isDuplicate) {
				System.out.println("Duplicate data entry. discarded");
			}
			else{
				referenceBook.add(person);
				if(personsByCity.get(person.getCity()) == null) personsByCity.put(person.getCity(), new ArrayList<>());
				personsByCity.get(person.getCity()).add(person);
				if(personsByState.get(person.getState()) == null) personsByState.put(person.getState(), new ArrayList<>());
				personsByState.get(person.getState()).add(person);
			}
		}
		else if(type.equals(IOServiceEnum.TXT_FILE_IO)){
			TxtFileIOServiceProvider fileIO = new TxtFileIOServiceProvider();
			fileIO.writeData(person, TXT_FILE_NAME);
		}
		
		else if(type.equals(IOServiceEnum.CSV_IO)) {
			OpenCSVServiceProvider csvIO = new OpenCSVServiceProvider();
			csvIO.writeData(person, CSV_FILE_NAME);
		}
		else if(type.equals(IOServiceEnum.JSON_IO)) {
			JsonServiceProvider jsonIO = new JsonServiceProvider();
			jsonIO.writeData(person,JSON_FILE_NAME );
		}
		else if(type.equals(IOServiceEnum.DB_IO)) {
			DBServiceProvider databaseIO = addressBookDBService;
			databaseIO.writeDB(person);
			addressBookList = (ArrayList<AddressBookData>) databaseIO.readData();
		}
	}
	
	
	public long readData(IOServiceEnum type) {
		count  = 0;
		if(type.equals(IOServiceEnum.LIST_DS_IO)) {
			referenceBook.stream().forEach(contact -> {
				System.out.println(contact);
				count++;
			});
		}
		else if(type.equals(IOServiceEnum.TXT_FILE_IO)) {
			TxtFileIOServiceProvider fileIO = new TxtFileIOServiceProvider();
			count  = fileIO.readData(TXT_FILE_NAME);
		}
		else if(type.equals(IOServiceEnum.CSV_IO)) {
			OpenCSVServiceProvider csvIO = new OpenCSVServiceProvider();
			count  = csvIO.readData(CSV_FILE_NAME);
		}
		else if(type.equals(IOServiceEnum.JSON_IO)) {
			JsonServiceProvider jsonIO = new JsonServiceProvider();
			count  = jsonIO.readData(JSON_FILE_NAME);
		}
		else if(type.equals(IOServiceEnum.DB_IO)) {
			DBServiceProvider databaseIO = addressBookDBService;
			addressBookList = (ArrayList<AddressBookData>) databaseIO.readData();
			System.out.println(addressBookList);
			return getNumberOfContacts(addressBookList);
		}
		return count;
	}
	
	
	private int getNumberOfContacts(ArrayList<AddressBookData> addressBookList) {
		int size = 0;
		for(AddressBookData addressBook : addressBookList) size += addressBook.getContactList().size();
		return size;
	}

	public void searchByCity(String city,String firstName) {
		Predicate<PersonDetails> searchPerson = (contact -> contact.getCity().equals(city)&& contact.getFirstName().equals(firstName));
		referenceBook.stream().filter(searchPerson).forEach(person -> output(person));
	}
	
	public void searchByState(String state, String firstName) {
		Predicate<PersonDetails> searchPerson = (contact -> contact.getState().equals(state)&& contact.getFirstName().equals(firstName));
		referenceBook.stream().filter(searchPerson).forEach(person -> output(person));
	}
	
	public void personsInCity(String city) {
		ArrayList<PersonDetails> list = personsByCity.get(city);
		list.stream().forEach(person -> output(person));
	}
	
	public void personsInState(String State) {
		ArrayList<PersonDetails> list = personsByState.get(State);
		list.stream().forEach(person -> output(person));
	}
	
	public int countByCity(String city, IOServiceEnum type) {
		return (personsByCity.get(city)==null)?0:personsByCity.get(city).size();
	}
	public int countByState(String state, IOServiceEnum type) {
		return personsByState.get(state)==null?0:personsByState.get(state).size();
	}
	
	public void editPerson(String name) {
		int i=0;
		for(i=0;i<referenceBook.size();i++) {
			if(name.equals(referenceBook.get(i).getFirstName())) break;
		}
		if(i == referenceBook.size()) {
			System.out.println("name not found");
			return;
		}
		System.out.println("Changing details, Enter new details  of "+name);
		referenceBook.add(intake());
	}
	
	public static void display() {
		Scanner sc = new Scanner(System.in);
		PersonDetails person = null; 
		System.out.println("Persons present in the address book:");
		for(int i=0; i<referenceBook.size();i++) {
			System.out.print(referenceBook.get(i).getFirstName()+"  ");
		}
		System.out.println();
		System.out.println("Enter name to see details");
		String name = sc.next();
		
		for(int i = 0;i<referenceBook.size();i++) {
			if(referenceBook.get(i).getFirstName().equals(name)) {
				person = referenceBook.get(i);
				break;
			}
		}
		if(person == null) {
			System.out.println("name not found!");
			return;
		}
		
		output(person);
		
	}
	
	
	public void deletePerson(String name) {
		int i=0;
		for(i=0;i<referenceBook.size();i++) {
			if(referenceBook.get(i).getFirstName().equals(name)) {
				break;
			}
		}
		if(i==numOfContacts) {
			System.out.println("Name not found");
			return;
		}
		referenceBook.remove(i);
		System.out.println("Deleted details of : "+ name);
	}
	
	
	
	
	private static PersonDetails intake() {
		Scanner sc = new Scanner(System.in);
		PersonDetails person1 = new PersonDetails();
		
		System.out.println("Enter firstName:");
		person1.setFirstName(sc.next());
		System.out.println("Enter SecondName:");
		person1.setLastName(sc.next());
		System.out.println("Enter Address:");
		person1.setAddress(sc.next());
		System.out.println("Enter City:");
		person1.setCity(sc.next());
		System.out.println("Enter State:");
		person1.setState(sc.next());
		System.out.println("Enter Pin code:");
		person1.setPinCode(sc.nextInt());
		System.out.println("Enter Phone nmber:");
		person1.setPhoneNumber(sc.next());
		System.out.println("Enter email:");
		person1.setEmail(sc.next());
		return person1;
	}
	
	private static void output(PersonDetails person) {
		System.out.println("firstName : "+person.getFirstName());
		System.out.println("SecondName : "+ person.getLastName());
		System.out.println("Address : "+ person.getAddress());
		System.out.println("City : "+person.getCity());
		System.out.println("State : "+person.getState());
		System.out.println("Pin code : "+person.getPinCode());
		System.out.println("Phone nmber : "+person.getPhoneNumber() );
		System.out.println("email : "+person.getEmail());
	}
	
	
	public  void sortByFirstName() {
		referenceBook.stream()
					 .sorted((contact1,contact2) -> contact1.getFirstName().compareTo(contact2.getFirstName()))
					 .forEach(System.out::println);
	}
	public  void sortByZip() {
		referenceBook.stream()
					 .sorted((contact1,contact2) -> contact1.getPinCode()-contact2.getPinCode())
					 .forEach(System.out::println);
	}
	public  void sortByCity() {
		referenceBook.stream()
					 .sorted((contact1,contact2) -> contact1.getCity().compareTo(contact2.getCity()))
					 .forEach(System.out::println);
	}
	public  void sortByState() {
		referenceBook.stream()
					 .sorted((contact1,contact2) -> contact1.getState().compareTo(contact2.getState()))
					 .forEach(System.out::println);
	}
	
	
	public static void clearCSV() {
		try {
			Files.deleteIfExists(Paths.get(TXT_FILE_NAME));
			Files.deleteIfExists(Paths.get(CSV_FILE_NAME));
			Files.deleteIfExists(Paths.get(JSON_FILE_NAME));
			File file = new File(TXT_FILE_NAME);
			file.createNewFile();
			file = new File(CSV_FILE_NAME);
			file.createNewFile();
			file = new File(JSON_FILE_NAME);
			file.createNewFile();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updatePhonenumberOfContact(String phoneNumber, int id) {
		int result =  addressBookDBService.updatePhonenumberOfContact(phoneNumber, id);
		if( result == 0) return;
		Contacts contact = this.getPersonDetailsData(id);
		if( contact != null) contact.setPhoneNumber(phoneNumber);
	}
	public boolean checkAddressBookInsyncWithDB(int id) {
		List<AddressBookData> employeePayrollDataList =  addressBookDBService.readData();
		Contacts localContacts = getPersonDetailsData(id);
		for(AddressBookData addressBook : employeePayrollDataList) {
			for(Contacts contact : addressBook.getContactList()) {
				if(contact.equals(localContacts)) return true;
			}
		}
		return false;
	}
	
	private Contacts getPersonDetailsData(int id) {
		for(AddressBookData addressBook : addressBookList) {
			for(Contacts contact : addressBook.getContactList()) {
				if(contact.getContactId() == id) {
					return contact;
				}
			}
		}
		return null;
	}

	public List<AddressBookData> getEmployeeInADateRange(String date1, String date2) {
		
		return addressBookDBService.getEmployeeInADateRange(date1,date2);
	}
	
	public HashMap<String, ArrayList<String>> getContactsByCity(String city){
		return addressBookDBService.getContactsByCity(city);
	}
	public HashMap<String, ArrayList<String>> getContactsByState(String state){
		return addressBookDBService.getContactsByState(state);
	}
	
	public int insertAddressBook(String name) {
		return addressBookDBService.insertAddressBook(name);
	}
	
}