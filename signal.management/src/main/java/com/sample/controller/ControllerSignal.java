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

import com.sample.dao.SignalDao;
import com.sample.dao.SignalDaoImplement;
import com.sample.dao.SignalGroupDao;
import com.sample.dao.SignalGroupDaoImplement;
import com.sample.model.Signal;
import com.sample.model.SignalGroup;


@SuppressWarnings("serial")
@ViewScoped
@Named
public class ControllerSignal implements Serializable {

	private SignalDao signalDao = SignalDaoImplement.getInstance();
	private SignalGroupDao signalGroupDao = SignalGroupDaoImplement.getInstance();
	private SignalGroup signalGroup;
	private Signal signal;

	private Long signalId;
	private String signalAddress;
	private String signalDetails;
	private Boolean signalActive;
	private Long groupId;

	public ControllerSignal() {
		this.signal = new Signal();
	}

	@PostConstruct
	public void init() {
		this.reset();
	}

	private void reset() {
		this.signalId = null;
		this.signalAddress = null;
		this.signalDetails = null;
		this.signalActive = Boolean.TRUE;
		this.groupId = null;
	}

	public String saveSignal() {
		String address = this.signalAddress;
		Boolean verify = verifyExistingAddress(address, this.groupId);
		System.out.println(address + " " + verify + " " + getGroupId());
 
		
		if (verify != true) {

			if (address != null && this.signalDetails != null && this.groupId != null) {
				this.signal = new Signal();
				signal.setId(this.signalId);
				signal.setAddress(address);
				signal.setDetails(this.signalDetails);
				signal.setActive(this.signalActive);
				SignalGroup group = this.signalGroupDao.getById(this.groupId);
				signal.setSignalGroup(group);

				this.signalDao.save(signal);
				this.reset();

			} else {
				this.signalDao.save(this.signal);
				this.signal = new Signal();
				this.reset();
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag já cadastrada", address));
		}
		this.reset();
		return null;
	}

	private Boolean verifyExistingAddress(String address, Long id) {
		boolean result = false;
		for (Signal signals : getSignals()) {
			if (signals.getAddress().equals(address) && signals.getId() != id) {
				result = true;
			}
		}
		return result;
	}

	public String editSignal() {
		Long id = this.getIdParameter();
		if (id != null) {
			this.signal = this.signalDao.getById(id);
			if (this.signal != null) {
				if (this.signal.getSignalGroup() != null) {
					this.groupId = this.signal.getSignalGroup().getId();
				}
				this.signalId = this.signal.getId();
				this.signalActive = this.signal.getActive();
				this.signalAddress = this.signal.getAddress();
				this.signalDetails = this.signal.getDetails();
			}
		}
		return null;
	}

	public String active() {
		Long id = this.getIdParameter();
		if (id != null) {
			Signal activeSignal = this.signalDao.getById(id);
			if (activeSignal != null) {
				activeSignal.setActive(true);
			}
		}
		return null;
	}

	public String desactive() {
		Long id = this.getIdParameter();
		if (id != null) {
			Signal activeSignal = this.signalDao.getById(id);
			if (activeSignal != null) {
				activeSignal.setActive(false);
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

	public List<Signal> getSignals() {
		return this.signalDao.findAll();
	}

	public String getSignalAddress() {
		return this.signalAddress;
	}

	public void setSignalAddress(String signalAddress) {
		this.signalAddress = signalAddress;
	}

	public String getSignalDetails() {
		return this.signalDetails;
	}

	public void setSignalDetails(String signalDetails) {
		this.signalDetails = signalDetails;
	}

	public Boolean getSignalActive() {
		return this.signalActive;
	}

	public void setSignalActive(Boolean signalActive) {
		this.signalActive = signalActive;
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

	public Long getSignalId() {
		return signalId;
	}

	public void setSignalId(Long signalId) {
		this.signalId = signalId;
	}

}