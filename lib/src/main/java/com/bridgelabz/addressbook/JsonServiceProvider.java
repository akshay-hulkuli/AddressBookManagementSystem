package com.bridgelabz.addressbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.reflect.*;

import com.google.gson.Gson;

public class JsonServiceProvider {
	public void writeData(PersonDetails person, String path) {
		try {
			Gson gson = new Gson();
			BufferedReader br = new BufferedReader(new FileReader(path));
			List<PersonDetails> contacts = gson.fromJson(br, new TypeToken<List<PersonDetails>>() {}.getType());
			if(contacts == null) {
				contacts = new ArrayList<>();
				contacts.add(person);
			}
			else contacts.add(person);
			String json = gson.toJson(contacts);
			System.out.println(json);
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}
	
	public long readData(String path) {
		long count =0; 
		try {
			Gson gson = new Gson();
			BufferedReader br = new BufferedReader(new FileReader(path));
			PersonDetails[] usrObj = gson.fromJson(br, PersonDetails[].class);
			List<PersonDetails> csvUSerList = Arrays.asList(usrObj);
			System.out.println(csvUSerList);
			count = csvUSerList.size();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return count;
	}
}
