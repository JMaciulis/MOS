/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.processes;

import java.util.LinkedList;

import os.OS;
import os.OS.ProcName;
import os.OS.ProcessState;
import os.OS.ResName;
import os.Resource;
import os.machine.CPU;

/**
 *
 * @author Mantas
 */
public class StartStop extends os.Process {

	public StartStop(int intId, ProcName extId, String pName,
			LinkedList<os.Process> processList, os.Process parentProcess, CPU cpu,
			OS os, ProcessState pState, int priority) {
		super(intId, extId, pName, processList, parentProcess, cpu, os, pState,
				priority);
		
	}

	@Override
	public void step() {
		switch (nxtInstruction){
		case 1:
			createSystemResources();
			OS.printStuff("Resources created!");
			nxtInstruction++;
			break;
		case 2:
			createSystemProcesses();
			nxtInstruction++;
			break;
		case 3:
			os.requestResource(this, ResName.SHUTDOWN);
			nxtInstruction++;
			break;
		case 4:
			destroySystemProcesses();
			nxtInstruction++;
			break;
		case 5:
			destroySystemResources();
			os.stopOS = true;
			break;
		}
		
	}
	
	private void createSystemResources(){
		//Memory
		os.createResource(this, ResName.VARTOTOJO_ATMINTIS,
				os.getRealMachine().getMemory());
		os.createResource(this, ResName.KANALAS_1,
				os.getRealMachine().usrInputDevice);
		os.createResource(this, ResName.KANALAS_2
				, os.getRealMachine().usrOutputDevice);
	}
	
	private void createSystemProcesses() {
		os.createProcess(this, ProcName.PROGRAM_TO_EXT_MEM);
		os.createProcess(this, ProcName.LOAD_PROGRAM);
		os.createProcess(this, ProcName.READER);
		os.createProcess(this, ProcName.INTERRUPT);
		os.createProcess(this, ProcName.OUTPUT);
		os.createProcess(this, ProcName.INPUT);
		os.createProcess(this, ProcName.LAUKIMAS);
		os.createProcess(this, ProcName.MAIN_PROC);
	}
	
	private void destroySystemResources() {
		LinkedList<Resource> tmpList = new LinkedList<>();
		for (Resource res : this.pDesc.createdResList) {
			tmpList.add(res);
		}
		
		for (Resource res : tmpList) {
			os.destroyResource(res);
		}
	}
	
	private void destroySystemProcesses() {
		LinkedList<os.Process> tmpList = new LinkedList<>();
		for (os.Process proc : this.pDesc.childrenList) {
			tmpList.add(proc);
		}
		
		for (os.Process proc : tmpList) {
			os.destroyProcess(proc);
		}
	}
}