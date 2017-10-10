package com.sample.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.model.Signal;

public class SignalDaoImplement implements SignalDao {

	private static SignalDaoImplement INSTANCE = new SignalDaoImplement();
	
	private Map<Long, Signal> database = new HashMap<Long, Signal>();
	private Long nextId = 0L;

	private SignalDaoImplement() {
	}

	public static SignalDaoImplement getInstance() {
		return INSTANCE;
	}

	@Override
	public Signal save(Signal signal) {
		if (signal != null) {
			if (signal.getId() == null) {
				this.nextId = this.nextId + 1;
				signal.setId(this.nextId);
			}
			this.database.put(signal.getId(), signal);
		}
		return signal;
	}

	@Override
	public Signal getById(Long id) {
		return this.database.get(id);
	}

	@Override
	public List<Signal> findAll() {
		return new ArrayList<Signal>(this.database.values());
	}
}