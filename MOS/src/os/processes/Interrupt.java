/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.processes;

import java.util.LinkedList;

import os.InterruptMessage;
import os.OS;
import os.OS.IntType;
import os.OS.ProcName;
import os.OS.ProcessState;
import os.OS.ResName;
import os.ProcessDescriptor;
import os.Resource;
import os.ResourceManager;
import os.machine.CPU;

/**
 *
 * @author Mantas
 */
public class Interrupt extends os.Process {

	public Interrupt(int intId, ProcName extId, String pName,
			LinkedList<os.Process> processList, os.Process parentProcess, CPU cpu,
			OS os, ProcessState pState, int priority) {
		super(intId, extId, pName, processList, parentProcess, cpu, os, pState,
				priority);
	}

	@Override
	public void step() {
		switch (nxtInstruction) {
		case 1:
			os.requestResource(this, ResName.PERTRAUKIMAS);
			nxtInstruction++;
			break;
		case 2:
			processInterruptForVM();
			nxtInstruction = 1;
			break;
		}
	}

	private void processInterruptForVM() {
		Resource descRes = ResourceManager.findResourceByExtId(
				pDesc.ownedResList, ResName.PERTRAUKIMAS);
		ProcessDescriptor desc =
				((ProcessDescriptor) descRes.getComponent());
		int parentId = desc.parentProcess.pDesc.intId;
		
		OS.printStuff("PROCESSING INT: " + desc.extId);
		InterruptMessage msg = null;
		
		IntType type = IntType.PASIRUOSES;
		
		switch (desc.savedState.regSI.getValue()){
		case 1:
			type = IntType.INPUT;
			break;
		case 2:
			type = IntType.OUTPUT;
			break;
		case 3:
			type = IntType.HALT;
			break;
		}
		
		switch (desc.savedState.regPI.getValue()){
		case 1:
			type = IntType.ILLEGAL_COMMAND;
			break;
		case 2:
			type = IntType.NEGATIVE_RESULT;
			break;
		case 3:
			type = IntType.DIV_BY_ZERO;
			break;
		case 4:
			type = IntType.OVERFLOW;
			break;	
		}
		
		msg = new InterruptMessage(type, parentId);
		os.destroyResource(descRes);
		os.createResource(this, ResName.PERTRAUKIMAS, msg);
	}
	

}
