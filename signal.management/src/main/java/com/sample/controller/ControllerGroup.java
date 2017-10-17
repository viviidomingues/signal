package com.sample.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
		
		System.out.println("identifier " + groupIdentifier);

		if (verify != true) {

			if (identifier != null) {
				SignalGroup group = new SignalGroup();
				group.setIdentifier(this.groupIdentifier);
				group.setId(this.groupId);
				System.out.println("identifier " + groupIdentifier + " id " + groupId);

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

	public Boolean verifyExistingGroup(String identifier) {
		Boolean result = false;

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
			if (this.signalGroup != null) {
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

	public Long getIdParameter() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = currentInstance.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String idString = requestParameterMap.get("id");
		Long id = Long.parseLong(idString);
		return id;

	}

	public SignalGroup getSignalGroup() {
		return signalGroup;
	}

	public void setSignalGroup(SignalGroup signalGroup) {
		this.signalGroup = signalGroup;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupIdentifier() {
		return groupIdentifier;
	}

	public void setGroupIdentifier(String groupIdentifier) {
		this.groupIdentifier = groupIdentifier;
	}

	public List<SignalGroup> getSignalGroups() {
		return this.signalGroupDao.findAll();
	}

}
