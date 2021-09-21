package com.bridgelabz.addressbook;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Writer;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class OpenCSVServiceProvider  {
	List<PersonDetails> myUsers = new ArrayList<>();
	public void writeData(PersonDetails person, String path)  {
		List<PersonDetails> contactList = new ArrayList<>();
		try( Reader reader = Files.newBufferedReader(Paths.get(path));){
			CsvToBean<PersonDetails> csvToBean = new CsvToBeanBuilder(reader)
		            .withType(PersonDetails.class)
		            .withIgnoreLeadingWhiteSpace(true)
		            .build();
		
		    Iterator<PersonDetails> csvUserIterator = csvToBean.iterator();
		
		    while (csvUserIterator.hasNext()) {
		    	PersonDetails contact = csvUserIterator.next();
		    	System.out.println(contact.toString());
		    	contactList.add(contact);
		    }
		}
		catch( Exception e) {
			e.printStackTrace();
		}
		try( Writer writer = Files.newBufferedWriter(Paths.get(path));) {
		    contactList.add(person);
            StatefulBeanToCsv<PersonDetails> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
           
            beanToCsv.write(contactList); 	
		}
		catch( Exception e) {
			e.printStackTrace();
		}
	}
	
	public long readData(String path) {
		long count  =0;
        try (
            Reader reader = Files.newBufferedReader(Paths.get(path));
        ) {
            CsvToBean<PersonDetails> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(PersonDetails.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<PersonDetails> csvUserIterator = csvToBean.iterator();

            while (csvUserIterator.hasNext()) {
            	PersonDetails Person = csvUserIterator.next();
            	System.out.println(Person);
            	count++;
            }
        }
        catch( IOException e) {
        	e.printStackTrace();
        }
        return count;
	}


}
