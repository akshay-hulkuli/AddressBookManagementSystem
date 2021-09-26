package com.bridgelabz.addressbook;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

public class AddressBookDBService {
	
	public List<PersonDetails> readData() {
		String sql = "SELECT * FROM address a, contacts c WHERE a.contact_id = c.contact_id";
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
			e.printStackTrace();
		}
		return contactList;
	}
	
	public Connection getConnection() throws SQLException
	{
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?userSSL=false";
		String userName = "root";
		String password = "Root$241";
		Connection connection;
		System.out.println("Connecting to database:"+jdbcURL);
		connection =  (Connection) DriverManager.getConnection(jdbcURL,userName,password);
		System.out.println("Connection is successful!!!!"+connection);
		return connection;
	
	}
}
