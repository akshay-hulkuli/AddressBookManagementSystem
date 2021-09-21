package com.bridgelabz.addressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TxtFileIOServiceProvider {
	long count  =0;
	public void writeData(PersonDetails person, String path) {
		System.out.println("writing addressBook to a file ");
		StringBuffer sb = new StringBuffer();
		try {
			Files.lines(Paths.get(path))
			 	 .map(contact -> contact.trim())
			 	 .forEach(contact -> sb.append(contact.toString().concat("\n")));
			sb.append(person.toString());
			Files.write(Paths.get(path),sb.toString().getBytes());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public long readData(String path) {
		count  = 0;
		try {
			Files.lines(Paths.get(path))
				 .map(contact -> contact.trim())
				 .forEach(contact -> {
					 System.out.println(contact);
					 count++;
				 });
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return count;
	}
}
