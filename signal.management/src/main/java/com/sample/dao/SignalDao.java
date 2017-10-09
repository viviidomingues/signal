package com.sample.dao;

import java.util.List;

import com.sample.model.Signal;

public interface SignalDao {
	
	Signal save(Signal signal);
	Signal getById(Long id);
	
	List<Signal> findAll();

}
