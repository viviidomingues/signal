package com.sample.dao;

import java.util.List;

import com.sample.model.SignalGroup;

public interface SignalGroupDao {

	SignalGroup save(SignalGroup signalGroup);
	
	void delete(Long id);
	
	SignalGroup getById(Long idSignalGroup);
	
	List<SignalGroup> findAll();
}
