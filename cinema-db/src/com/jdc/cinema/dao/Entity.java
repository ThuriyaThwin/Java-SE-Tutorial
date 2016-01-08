package com.jdc.cinema.dao;

import java.io.Serializable;

public interface Entity extends Serializable{
	Param getInsertParam();
	IdParam getIdParam();
}
