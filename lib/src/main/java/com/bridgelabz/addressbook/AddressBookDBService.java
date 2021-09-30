package com.bridgelabz.addressbook;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	private HashSet<String> getAddressBooks(){
		HashSet<String> addressBooks = new HashSet<>();
		try(Connection connection= this.getConnection();){
			String sql = "select * from address_book";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				addressBooks.add(result.getString(1));
			}
		}
		catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		return addressBooks;
	}
	
	public PersonDetails writeDB(PersonDetails contact) {
		int contact_id  = -1;
		Connection connection = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		}
		catch(Exception e) {
			throw new AddressBookException(AddressBookException.ExceptionType.FAILED_TO_CONNECT, "Connection failed");
		}
		
		HashSet<String> addressBooks = getAddressBooks();
		
		try {
			for(Map.Entry<String, ArrayList<String>> entry : contact.getAddressBookNameTypeMap().entrySet()) {
				if(!addressBooks.contains(entry.getKey())) 
					throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "The addressBook : "+entry.getKey()+" is not present");
			}
		}catch(Exception e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Given empty map of addressBook name and types");
		}
		
		try(Statement statement = connection.createStatement()){
			String sql = String.format("insert into contacts(firstName, lastName, phoneNumber, email, date_added) values ('%s','%s','%s','%s','%s');", contact.getFirstName(),contact.getLastName()
					,contact.getPhoneNumber(),contact.getEmail(),contact.getDateAdded().toString());
			int result = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if(result == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next()) contact_id = resultSet.getInt(1);
			}
		} 
		catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		try(Statement statement = connection.createStatement()){
			String sql = String.format("insert into address(contact_id,address,city,state,zip) values (%d,'%s','%s','%s','%d');", contact_id,contact.getAddress()
					,contact.getCity(),contact.getState(),contact.getPinCode());
			int result = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
		} 
		catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		
		boolean success = true;
		
		for(Map.Entry<String, ArrayList<String>> entry : contact.getAddressBookNameTypeMap().entrySet()) {
			String addressBook_name = entry.getKey();
			for(String type : entry.getValue()) {
				try(Statement statement = connection.createStatement()){
					String sql = String.format("insert into addressBook_type(addressBook_name,addressBook_type,contact_id) values ('%s','%s',%d);", 
						addressBook_name,type,contact_id);
					int result = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
					if(result != 1) success = false;
				} 
				catch (SQLException e) {
					e.printStackTrace();
					try {
						connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
				}
			}
		}
		if(success) {
			contact.setId(contact_id);
		}
		
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return contact;
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
				Contacts contact = new Contacts();
				Address address = new Address();
				address.setContactId(result.getInt("contact_id"));
				contact.setContactId(result.getInt("contact_id"));
				contact.setFirstName(result.getString("firstName"));
				contact.setLastName(result.getString("lastName"));
				address.setAddress(result.getString("address"));
				address.setCity(result.getString("city"));
				address.setState(result.getString("state"));
				address.setPinCode(result.getInt("zip"));
				contact.setPhoneNumber(result.getString("phoneNumber"));
				contact.setEmail(result.getString("email"));
				contact.setDateAdded(result.getDate("date_added").toLocalDate());
				contactList.add(new PersonDetails(contact,address,contactMap.get(result.getInt("contact_id"))));
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
	
	public int insertAddressBook(String name) {
		int result = 0;
		String sql = String.format("insert into address_book (addressBook_name) values('%s');",name);
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			result = statement.executeUpdate(sql); 
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "cannot execute the query");
		}
		return result;
	}
}
