package com.sample.controller;

import java.io.Serializable;
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
		this.groupIdentifier = null;
	}

	public String saveSignalGroup() {
		String identifier = signalGroup.getIdentifier();
		Boolean verify = verifyExistingSignal(identifier);
		System.out.println(verify);
		
		if (verify != true){
			if (this.groupIdentifier != null) {
				SignalGroup group = new SignalGroup();
				group.setIdentifier(this.groupIdentifier);
				this.signalGroupDao.save(group);

				this.reset();
			} else {
				this.signalGroupDao.save(signalGroup);
				this.signalGroup = new SignalGroup();
			}
		} else {
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Grupo já cadastrado", identifier));
			 this.reset();
		}

		
		return null;
	}

	public boolean verifyExistingSignal(String identifier) {
		boolean result = false;
		for (SignalGroup groups : getSignalGroups()) {
			if (groups.getIdentifier().equals(identifier)) {
				result = true;
			}
		}
		return result;
	}

	public String editSignalGroup() {
		Long id = this.getIdParameter();
		if (id != null) {
			this.signalGroup = this.signalGroupDao.getById(id);
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
