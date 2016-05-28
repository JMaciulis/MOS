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
		os.createResource(this, ResName.IVEDIMO_IRENGINYS,
				os.getRealMachine().usrInputDevice);
		os.createResource(this, ResName.ISVEDIMO_IRENGINYS
				, os.getRealMachine().usrOutputDevice);
	}
	
	private void createSystemProcesses() {
		os.createProcess(this, ProcName.WAIT_FOR_JOB);
		os.createProcess(this, ProcName.LOADER);
		os.createProcess(this, ProcName.MAIN_PROC);
		os.createProcess(this, ProcName.INTERRUPT);
		os.createProcess(this, ProcName.GET_LINE);
		os.createProcess(this, ProcName.PRINT_LINE);
	}
	
	private void destroySystemResources() {
		LinkedList<FResource> tmpList = new LinkedList<>();
		for (FResource res : this.pDesc.createdResList) {
			tmpList.add(res);
		}
		
		for (FResource res : tmpList) {
			os.destroyResource(res);
		}
	}
	
	private void destroySystemProcesses() {
		LinkedList<Process> tmpList = new LinkedList<>();
		for (Process proc : this.pDesc.childrenList) {
			tmpList.add(proc);
		}
		
		for (Process proc : tmpList) {
			os.destroyProcess(proc);
		}
	}
}