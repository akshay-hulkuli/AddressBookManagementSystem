package com.bridgelabz.addressbook;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.mysql.jdbc.Connection;

public class AddressBookDBService {
	
	private PreparedStatement addressBookPreparedStatement;
	private static AddressBookDBService addressBookDBService;
	private AddressBookDBService() {}
	
	public static AddressBookDBService getInstance() {
		if(addressBookDBService == null) {
			addressBookDBService = new AddressBookDBService();
		}
		return addressBookDBService;
	}
	
	public Connection getConnection() throws AddressBookException
	{
		Connection connection;
		try {
			String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?userSSL=false";
			String userName = "root";
			String password = "Root$241";
			System.out.println("Connecting to database:"+jdbcURL);
			connection =  (Connection) DriverManager.getConnection(jdbcURL,userName,password);
			System.out.println("Connection is successful!!!!"+connection);
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.FAILED_TO_CONNECT, "Can not connect to the database");
		}
		return connection;
	
	}
	
	public List<PersonDetails> readData() {
		String sql = "SELECT * FROM address a , contacts c WHERE a.contact_id = c.contact_id";
		HashMap<Integer, HashMap<String,ArrayList<String>>> contactMap = getContactMap(); 
		System.out.println(contactMap);
		List<PersonDetails> contactList = new ArrayList<>();
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				PersonDetails person  = new PersonDetails();
				person.setFirstName(result.getString("firstName"));
				person.setLastName(result.getString("lastName"));
				person.setAddress(result.getString("address"));
				person.setCity(result.getString("city"));
				person.setState(result.getString("state"));
				person.setPinCode(result.getInt("zip"));
				person.setPhoneNumber(result.getString("phoneNumber"));
				person.setEmail(result.getString("email"));
				person.setAddressBookNameTypeMap(contactMap.get(result.getInt("contact_id")));
				contactList.add(person);
			}
			connection.close();
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		return contactList;
	}
	
	private HashMap<Integer, HashMap<String,ArrayList<String>>> getContactMap(){
		
		HashMap<Integer, HashMap<String,ArrayList<String>>> contactMap = new HashMap<>();
		Connection connection= null;
		try {
			connection = this.getConnection();
		}
		catch(Exception e) {
			throw new AddressBookException(AddressBookException.ExceptionType.FAILED_TO_CONNECT, "Can not connect to the database");
		}
		try(Statement statement = connection.createStatement();){
			String sql = "SELECT contact_id FROM contacts";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				contactMap.put(result.getInt(1),new HashMap<>());
			}
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		try(Statement statement = connection.createStatement();){
			String sql = "SELECT * from addressBook_type";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				contactMap.get(result.getInt(3)).put(result.getString(1), new ArrayList<>());
			}
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		try(Statement statement = connection.createStatement();){
			String sql = "SELECT * from addressBook_type";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				contactMap.get(result.getInt(3)).get(result.getString(1)).add(result.getString(2));
			}
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return contactMap;
	}
	
	
	public void writeDB(PersonDetails contact) {
		String sql = String.format("INSERT INTO address_book_old(firstName, lastName, address, city, state, zip, phoneNumber, email)VALUES"
				+ "('%s','%s','%s','%s','%s','%d','%s','%s')",contact.getFirstName(),contact.getLastName(),contact.getAddress(),contact.getCity(),contact.getState(),
				contact.getPinCode(),contact.getPhoneNumber(),contact.getEmail());
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(sql);
			connection.close();
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.UPDATE_FAILED, "Can not insert into table");
		}
	}
	
	public int getNumberOfContactsInACity(String city) {
		int count = 0;
		if(this.addressBookPreparedStatement == null) {
			this.prepareStatementForAddressBook();
		}
		try {
			addressBookPreparedStatement.setString(1, "Bengaluru");
			ResultSet resultSet = addressBookPreparedStatement.executeQuery();
			while(resultSet.next()) {
				count = resultSet.getInt("count"); 
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		return count;
	}
	
	private void prepareStatementForAddressBook() {
		try {
			Connection connection = this.getConnection();
			String sqlStatement = "SELECT city,count(*) as 'count' FROM address_book_old WHERE city = ? GROUP BY city;";
			addressBookPreparedStatement = connection.prepareStatement(sqlStatement);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void demoQuery(String sql) {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			connection.close();
		}
		catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "cannot execute the query");
		}
	}
}
