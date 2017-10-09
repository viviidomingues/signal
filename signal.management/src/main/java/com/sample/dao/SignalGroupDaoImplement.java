package com.sample.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.model.SignalGroup;


public class SignalGroupDaoImplement implements SignalGroupDao {
	
	private static final SignalGroupDaoImplement INSTANCE = new SignalGroupDaoImplement();
	
	private Map<Long, SignalGroup> database = new HashMap<Long, SignalGroup>();
	private Long nextId = 0L;
	
	private SignalGroupDaoImplement() {
		SignalGroup signalGroup = new SignalGroup();
		signalGroup.setIdentifier("Teste 1");
		this.save(signalGroup );
		signalGroup = new SignalGroup();
		signalGroup.setIdentifier("Teste 2");
		this.save(signalGroup );
		signalGroup = new SignalGroup();
		signalGroup.setIdentifier("Teste 3");
		this.save(signalGroup );
	}
	
	public static SignalGroupDaoImplement getInstance(){
		return INSTANCE;
	}

	@Override
	public SignalGroup save(SignalGroup signalGroup) {
		if(signalGroup != null){
			if(signalGroup.getId() == null){
				this.nextId = this.nextId + 1;
				signalGroup.setId(this.nextId);
			}
			this.database.put(signalGroup.getId(), signalGroup);
		}
		return signalGroup;
	}
	
	public SignalGroup getById(Long id) {
		return this.database.get(id);
	}

	@Override
	public void delete(Long id) {
		if(id != null){
			this.database.remove(id);
		}
	}

	@Override
	public List<SignalGroup> findAll() {
		return new ArrayList<SignalGroup>(this.database.values());
	}
}
