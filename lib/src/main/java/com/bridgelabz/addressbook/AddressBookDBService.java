package com.bridgelabz.addressbook;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.mysql.jdbc.Connection;

public class AddressBookDBService {
	
	private static final String Connection = null;
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
		List<PersonDetails> contactList = new ArrayList<>();
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			contactList = getAddressBookData(result);
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
	
	public int updatePhonenumberOfContact(String phoneNumber, int id) {
		int count = 0;
		if(this.addressBookPreparedStatement == null) {
			this.prepareStatementForAddressBook();
		}
		try {
			addressBookPreparedStatement.setString(1, phoneNumber);
			addressBookPreparedStatement.setInt(2, id);
			count = addressBookPreparedStatement.executeUpdate();
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
			String sqlStatement = "update contacts set phoneNumber = ? where contact_id = ?;";
			addressBookPreparedStatement = connection.prepareStatement(sqlStatement);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<PersonDetails> getAddressBookData(int id ){
		String sql = String.format("SELECT * FROM address a , contacts c WHERE a.contact_id = c.contact_id and c.contact_id = %d",id);
		List<PersonDetails> contactList = null;
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			contactList = getAddressBookData(result);
			connection.close();
		}
		catch(SQLException e){
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		return contactList;
	}
	
	public List<PersonDetails> getAddressBookData(ResultSet result){
		
		HashMap<Integer, HashMap<String,ArrayList<String>>> contactMap = getContactMap(); 
		List<PersonDetails> contactList = new ArrayList<>();
		try {
			while(result.next()) {
				PersonDetails person  = new PersonDetails();
				person.setId(result.getInt("contact_id"));
				person.setFirstName(result.getString("firstName"));
				person.setLastName(result.getString("lastName"));
				person.setAddress(result.getString("address"));
				person.setCity(result.getString("city"));
				person.setState(result.getString("state"));
				person.setPinCode(result.getInt("zip"));
				person.setPhoneNumber(result.getString("phoneNumber"));
				person.setEmail(result.getString("email"));
				person.setDate_added(result.getDate("date_added").toLocalDate());
				person.setAddressBookNameTypeMap(contactMap.get(result.getInt("contact_id")));
				contactList.add(person);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return contactList;
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
	
	public List<PersonDetails> getEmployeeInADateRange(String date1, String date2){
		String sql = String.format("select * from contacts c , address a where c.contact_id = a.contact_id and date_added between cast('%s' as date) and "
				+ "cast('%s' as date);",date1,date2);
		List<PersonDetails> contactList = new ArrayList<>();
		try(Connection connection = this.getConnection();){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			contactList = getAddressBookData(resultSet);
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "cannot execute the query");
		}
		
		return contactList;
	}
	
	public HashMap<String, ArrayList<String>> getContactsByCity(String city){
		String sql = String.format("SELECT a.addressBook_name, firstName FROM address_book a JOIN addressBook_type t ON "
				+ "a.addressBook_name = t.addressBook_name JOIN contacts c ON c.contact_id = t.contact_id JOIN address ad ON "
				+ "ad.contact_id = c.contact_id WHERE city = '%s'",city);
		HashMap<String, ArrayList<String>> contactsBycity = new HashMap<>();
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				String addressBook_name  = resultSet.getString(1);
				if(contactsBycity.get(addressBook_name) == null) contactsBycity.put(addressBook_name, new ArrayList<>());
				String firstName  = resultSet.getString(2);
				contactsBycity.get(addressBook_name).add(firstName);
			}
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "cannot execute the query");
		}
		return contactsBycity;
	}
	
	public HashMap<String, ArrayList<String>> getContactsByState(String state){
		String sql = String.format("SELECT a.addressBook_name, firstName FROM address_book a JOIN addressBook_type t ON "
				+ "a.addressBook_name = t.addressBook_name JOIN contacts c ON c.contact_id = t.contact_id JOIN address ad ON "
				+ "ad.contact_id = c.contact_id WHERE state = '%s'",state);
		HashMap<String, ArrayList<String>> contactsBycity = new HashMap<>();
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				String addressBook_name  = resultSet.getString(1);
				if(contactsBycity.get(addressBook_name) == null) contactsBycity.put(addressBook_name, new ArrayList<>());
				String firstName  = resultSet.getString(2);
				contactsBycity.get(addressBook_name).add(firstName);
			}
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "cannot execute the query");
		}
		return contactsBycity;
	}
}
