package com.jdc.conc.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ContactModelFile implements ContactModel{

	private List<Contact> list;
	private static ContactModel MODEL;
	
	@SuppressWarnings("unchecked")
	private ContactModelFile() {
		try (ObjectInputStream in = 
				new ObjectInputStream(new FileInputStream("contact.dat"))){
			list = (List<Contact>) in.readObject();
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		if(null == list)
			list = new ArrayList<>();
	}
	
	static ContactModel getModel() {
		if(null == MODEL) {
			MODEL = new ContactModelFile();
		}
		return MODEL;
	}

	@Override
	public void create(Contact c) {
		list.add(c);
		
		try(ObjectOutputStream out = 
				new ObjectOutputStream(new FileOutputStream("contact.dat"))) {
			out.writeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Contact> find(Predicate<Contact> pred) {
		return list.stream().filter(pred).collect(Collectors.toList());
	}

}
