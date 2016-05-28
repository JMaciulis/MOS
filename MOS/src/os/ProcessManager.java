/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import os.OS.ProcName;
import os.OS.ProcessState;

/**
 *
 * @author Mantas
 */
public class ProcessManager {

	private OS os;
	
	public ProcessManager(OS os) {
		this.os = os;
		
	}

	public void execute(){
		OS.printStuff("================PROCMAN=================");
		
		if (os.runProcess != null){
			OS.printStuff("---" + os.runProcess.toString());
			checkCurrent();
		} else {
			OS.printStuff("--- NOTHING");
		}
		//Block current process if needed
		
		
		Process candidate = getTopPriorityReadyProc();
		
		if (candidate != null && os.runProcess == null){
			//Jei procesorius laisvas ir yra kandidatas jį užimti - duodam
			prepareProcess(candidate);
		} else if (candidate != null && os.runProcess != null){
			//Jei užimtas - tikrinam ar yra svarbesnių procesų
			if (candidate.pDesc.priority > os.runProcess.pDesc.priority &&
					os.runProcess.pDesc.pState == ProcessState.READY){
				stopProcess();
				prepareProcess(candidate);
			}
		} else {
			//Jei nėra nei kandidato, nei vykdomo proceso
			OS.printStuff("Resources:");
			for (Resource item : os.resources) {
				OS.printStuff(item.toString());
			}
		}
		
		if (os.runProcess != null)
			OS.printStuff("+++" + os.runProcess.toString());
		else {
			OS.printStuff("NO READY PROCESS");
			
		}
		OS.printStuff("----------------------------------------");
	}
	
	/**
	 * Load registers and prepare process for RUN state
	 */
	private void prepareProcess(Process newProcess) {
		os.runProcess = newProcess;
		newProcess.pDesc.pState = ProcessState.RUN;
		os.readyProcesses.remove(newProcess);
		newProcess.loadCPU();
		newProcess.resetTimer();
	}
	
	/**
	 * Execute process blocks, 
	 * set READY processes to RUN state.
	 */
	private void checkCurrent(){
		Process proc = os.runProcess;
		
		if (proc.pDesc.pState == ProcessState.BLOCKED){
			stopProcess();
		} else if (proc.pDesc.pState == ProcessState.READY) {
				proc.pDesc.pState = ProcessState.RUN;
		}
	}
	
	public void stopProcess(){
		Process proc = os.runProcess;
		os.runProcess = null;
		
		if (proc.pDesc.extId == ProcName.VIRTUAL_MACHINE){
			proc.decPriority();
		}
		
		//Save CPU state
		proc.saveCPU();
		
		if (proc.pDesc.pState != ProcessState.BLOCKED){
			os.readyProcesses.add(proc);
			
		} else {
			os.blockedProcesses.add(proc);
			os.readyProcesses.remove(proc);
		}
			
	}
	
	private Process getTopPriorityReadyProc() {
		Process tmp = null;
		
		if (!os.readyProcesses.isEmpty()){
			tmp = os.readyProcesses.element();
			for (Process proc : os.readyProcesses) {
				if (proc.pDesc.priority > tmp.pDesc.priority)
					tmp = proc;
			}
		}
		
		return tmp;
	}
	
}

