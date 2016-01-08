package com.jdc.conc.model;

import java.util.List;
import java.util.function.Predicate;

import com.jdc.conc.MainFrame;

public interface ContactModel {
	
	public static ContactModel getModel() {
		if(MainFrame.isMemory()) {
			return ContactModelMem.getModel();
		}
		
		return ContactModelFile.getModel();
	}

	void create(Contact c);
	List<Contact> find(Predicate<Contact> pred);
}
