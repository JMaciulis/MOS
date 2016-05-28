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
public class Resource {
	public ResourceDescriptor resDesc;
	public static int numberOfInstances;
	private Object component;
	
	public Resource(int intId, ResName extId, OS os, 
			os.Process creatorProcess, boolean reusable,
			Object component) {
		
		this.component = component;
		this.resDesc = new ResourceDescriptor(
				intId, extId, os, creatorProcess,
				reusable, component);
	}

	public String toString(){
		String tmp = "RES:" + resDesc.getIntId() + 
				":" + resDesc.getExtId();
		if (resDesc.getUser() != null){
			tmp += " [USED:" + resDesc.getUser().pDesc.extId +
					"]";
		}
		if (component == null){
			tmp += " [null]";
		}
		return tmp;
	}
	
	public ResourceDescriptor getResDesc() {
		return resDesc;
	}

	public void setResDesc(ResourceDescriptor resDesc) {
		this.resDesc = resDesc;
	}

	public Object getComponent() {
		return component;
	}

	public void setComponent(Object component) {
		this.component = component;
	}    
}
