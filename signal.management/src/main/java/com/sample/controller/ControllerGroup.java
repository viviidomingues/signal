package com.sample.controller;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.sample.dao.SignalGroupDao;
import com.sample.dao.SignalGroupDaoImplement;
import com.sample.model.SignalGroup;

@SuppressWarnings("serial")
@ViewScoped
@Named
public class ControllerGroup implements Serializable {

	private SignalGroupDao signalGroupDao = SignalGroupDaoImplement.getInstance();
	private SignalGroup signalGroup;

	private Long groupId;
	private String groupIdentifier;

	public ControllerGroup() {
		this.signalGroup = new SignalGroup();
	}

	@PostConstruct
	public void init() {
		this.reset();
	}

	private void reset() {
		this.groupId = null;
		this.groupIdentifier = null;
	}

	public String saveSignalGroup() {
		String identifier = this.groupIdentifier;
		Boolean verify = verifyExistingGroup(identifier);
		
		System.out.println( "group save: " +   identifier + " group id: " + this.groupId + " " + verify);

		if (verify != true) {
			
			if (identifier != null) {
				SignalGroup group = new SignalGroup();
				group.setIdentifier(this.groupIdentifier);
				group.setId(this.groupId);
				
				this.signalGroupDao.save(group);
				this.reset();
			} else {
				this.signalGroupDao.save(signalGroup);
				this.signalGroup = new SignalGroup();
				this.reset();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Grupo já cadastrado", identifier));
		}

		this.reset();
		return null;
	}

	public boolean verifyExistingGroup(String identifier) {
		boolean result = false;
	
		for (SignalGroup groups : getSignalGroups()) {
			if (groups.getIdentifier().equals(identifier)){
				result = true;
			}
		}
		
		System.out.println("verify " + result);
		return result;
	}

	public String editSignalGroup() {
		Long id = this.getIdParameter();
		if (id != null) {
			this.signalGroup = this.signalGroupDao.getById(id);
			if(this.signalGroup != null){
				this.groupId = this.signalGroup.getId();
				this.groupIdentifier = this.signalGroup.getIdentifier();
			}
		}
		return null;
	}
	
	public void delete() {
		Long id = this.getIdParameter();
		this.signalGroupDao.delete(id);
	}

	private Long getIdParameter() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = currentInstance.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String idString = requestParameterMap.get("id");
		Long id = Long.parseLong(idString);
		return id;
	}

	public SignalGroup getSignalGroup() {
		return this.signalGroup;
	}

	public void setSignalGroup(SignalGroup signalGroup) {
		this.signalGroup = signalGroup;
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public List<SignalGroup> getSignalGroups() {
		return this.signalGroupDao.findAll();
	}

}
