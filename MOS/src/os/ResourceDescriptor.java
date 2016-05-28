/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import os.OS.ResName;

/**
 *
 * @author Mantas
 */
public class ResourceDescriptor {

	private int intId;
	private ResName extId;
	private boolean reusable;
	private OS os;
	
	private Object component;
	
	private Process user;
	
	private Process creatorProcess;

	public ResourceDescriptor(int intId, ResName extId, OS os, 
			Process creatorProcess, boolean reusable,
			Object component) {
		this.intId = intId;
		this.extId = extId;
		this.reusable = reusable;
		this.os = os;
		this.creatorProcess = creatorProcess;
		this.setComponent(component);
	}

	public OS getOS() {
		return os;
	}

	public void setOS(OS os) {
		this.os = os;
	}
	
	public Process getCreatorProcess() {
		return creatorProcess;
	}

	public void setCreatorProcess(Process creatorProcess) {
		this.creatorProcess = creatorProcess;
	}
	public int getIntId() {
		return intId;
	}

	public void setIntId(int intId) {
		this.intId = intId;
	}

	public ResName getExtId() {
		return extId;
	}

	public void setExtId(ResName extId) {
		this.extId = extId;
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public boolean isReusable() {
		return reusable;
	}

	public void setReusable(boolean reusable) {
		this.reusable = reusable;
	}

	public Object getComponent() {
		return component;
	}

	public void setComponent(Object component) {
		this.component = component;
	}

	public Process getUser() {
		return user;
	}

	public void setUser(Process user) {
		this.user = user;
	}
}