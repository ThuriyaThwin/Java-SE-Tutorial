package com.jdc.conc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ContactModelMem implements ContactModel{
	
	private List<Contact> list;
	private static ContactModel MODEL;
	
	private ContactModelMem() {
		list = new ArrayList<>();
	}
	
	static ContactModel getModel() {
		if(null == MODEL) {
			MODEL = new ContactModelMem();
		}
		return MODEL;
	}

	@Override
	public void create(Contact c) {
		list.add(c);
	}

	@Override
	public List<Contact> find(Predicate<Contact> pred) {
		return list.stream().filter(pred).collect(Collectors.toList());
	}

}
