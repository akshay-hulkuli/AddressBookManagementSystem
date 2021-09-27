package com.bridgelabz.addressbook;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		String sql = "SELECT * FROM address_book_old";
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
				contactList.add(person);
			}
			connection.close();
		}
		catch(SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		return contactList;
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
}
